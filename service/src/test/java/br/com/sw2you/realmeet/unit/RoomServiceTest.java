package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.domain.entity.Room.newBuilder;
import static br.com.sw2you.realmeet.utils.MapperUtils.roomMapper;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.roomBuilder;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.utils.MapperUtils;
import br.com.sw2you.realmeet.utils.TestConstants;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

class RoomServiceTest extends BaseUnitTest {

    private RoomService victim;

    @Mock
    private RoomRepository repository;

    @BeforeEach
    void setupEach() {

        victim = new RoomService(repository, roomMapper());
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

}
