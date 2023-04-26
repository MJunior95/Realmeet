package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.roomBuilder;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.api.facade.RoomApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.utils.TestConstants;
import br.com.sw2you.realmeet.utils.TestDataCreator;
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
}
