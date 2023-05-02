package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.MapperUtils.roomMapper;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.utils.TestDataCreator.roomBuilder;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.validator.RoomValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RoomServiceTest extends BaseUnitTest {

    private RoomService victim;

    @Mock
    private RoomRepository repository;

    @Mock
    private RoomValidator roomValidator;

    @BeforeEach
    void setupEach() {
        victim = new RoomService(repository, roomMapper(), roomValidator);
    }

    @Test
    void testGetRoom() {
        var room = roomBuilder().id(DEFAULT_ROOM_ID).build();
        when(repository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(of(room));
        var dto = victim.getRoom(DEFAULT_ROOM_ID);

        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
        assertEquals(room.getId(), dto.getId());

    }

    @Test
    void testGetRoomNotFound() {
        when(repository.findByIdAndActive(DEFAULT_ROOM_ID, true)).thenReturn(Optional.empty());

        Assertions.assertThrows(RoomNotFoundException.class, () -> victim.getRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess(){
        var createRoomDTO = newCreateRoomDTO();
        var roomDTO = victim.createRoom(createRoomDTO);

        assertEquals(createRoomDTO.getName(), roomDTO.getName());
        assertEquals(createRoomDTO.getSeats(), roomDTO.getSeats());

        verify(repository, times(1)).save(any());

    }
}
