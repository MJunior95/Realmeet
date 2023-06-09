package br.com.sw2you.realmeet.service;

import static java.util.Objects.requireNonNull;


import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.validator.RoomValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoomService {
    private final RoomRepository repository;
    private final RoomMapper roomMapper;

    private final RoomValidator roomValidator;

    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper, RoomValidator roomValidator) {
        this.repository = roomRepository;
        this.roomMapper = roomMapper;
        this.roomValidator = roomValidator;

    }

    public RoomDTO getRoom(Long id){
        Room room = getActiveRoomOrThrow(id);
        return roomMapper.fromEntityToDto(room);
    }
    public RoomDTO createRoom(CreateRoomDTO createRoomDTO){
        roomValidator.validate(createRoomDTO);
         var room  = roomMapper.fromCreateRoomDTOToEntity(createRoomDTO);
         repository.save(room);
         return roomMapper.fromEntityToDto(room);
    }

    @Transactional
    public void deleteRoom(Long id){
        Room room = getActiveRoomOrThrow(id);
        repository.deactivate(id);
    }

    private Room getActiveRoomOrThrow(Long id) {
        requireNonNull(id);
        return repository.findByIdAndActive(id, true).orElseThrow(() -> new RoomNotFoundException("Room: " + id + " not Found"));
    }

    @Transactional
    public void updateRoom(Long roomId, UpdateRoomDTO updateRoomDTO){
        getActiveRoomOrThrow(roomId);
        roomValidator.validate(roomId, updateRoomDTO);
        repository.updateRoom(roomId, updateRoomDTO.getName(), updateRoomDTO.getSeats());
    }

}
