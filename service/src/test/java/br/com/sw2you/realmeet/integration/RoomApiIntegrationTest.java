package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.utils.TestDataCreator.roomBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.HttpClientErrorException;

class RoomApiIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private RoomApi roomApi;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(roomApi.getApiClient(), "/v1");
    }

    @Test
    void testGetRoomSuccess() {
        var room = roomBuilder().build();
        roomRepository.saveAndFlush(room);

        assertNotNull(room.getId());
        assertTrue(room.getActive());

        var dto = roomApi.getRoom(DEFAULT_ROOM_ID);

        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
        assertEquals(room.getId(), dto.getId());

    }

    @Test
    void testGetRoomInactive() {
        var room = roomBuilder().active(false).build();

        assertFalse(room.getActive());

        assertThrows(HttpClientErrorException.class, () -> roomApi.getRoom(room.getId()));
    }

    @Test
    void testGetRoomDoesNotExist() {
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.getRoom(DEFAULT_ROOM_ID));
    }

    @Test
    void testCreateRoomSuccess(){
        var createRoomDTO = newCreateRoomDTO();
        var roomDTO = roomApi.createRoom(createRoomDTO);

        assertEquals(createRoomDTO.getName(), roomDTO.getName());
        assertEquals(createRoomDTO.getSeats(), roomDTO.getSeats());
        assertNotNull(roomDTO.getId());

      var room =  roomRepository.findById(roomDTO.getId()).orElseThrow();

        assertEquals(roomDTO.getName(), room.getName());
        assertEquals(roomDTO.getSeats(), room.getSeats());
    }

    @Test
    void testeDeleteRunSuccess(){
      Long roomId =  roomRepository.saveAndFlush(roomBuilder().build()).getId();
        roomApi.deleteRoom(roomId);

        assertFalse(roomRepository.findById(roomId).orElseThrow().getActive());
    }
    @Test
    void testeDeleteDoesNotExist(){
        assertThrows(HttpClientErrorException.NotFound.class, () -> roomApi.deleteRoom(123l));
    }

    @Test
    void testUpdateRoomSuccess(){
        var room = roomRepository.saveAndFlush(roomBuilder().build());
        var updateRoomDTO = new UpdateRoomDTO().name(room.getName() + "__").seats(room.getSeats() +2);

        roomApi.updateRoom(room.getId(), updateRoomDTO);

        var updatedRoom = roomRepository.findById(room.getId()).orElseThrow();

        assertEquals(updateRoomDTO.getName(), updatedRoom.getName());
        assertEquals(updateRoomDTO.getSeats(), updatedRoom.getSeats());
    }
    @Test
    void testUpdateRoomDoesNotExist(){
        assertThrows(HttpClientErrorException.NotFound.class ,() ->
                roomApi.updateRoom(4554l, new UpdateRoomDTO().name("room").seats(5)));
    }

    @Test
    void testUpdateRoomValidationError(){
        var room = roomRepository.saveAndFlush(roomBuilder().build());
        assertThrows(HttpClientErrorException.UnprocessableEntity.class ,() ->
                roomApi.updateRoom(room.getId(), new UpdateRoomDTO().name("room").seats(52)));
    }

}
