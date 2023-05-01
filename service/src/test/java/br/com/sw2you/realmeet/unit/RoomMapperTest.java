package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.domain.entity.Room.newBuilder;
import static br.com.sw2you.realmeet.utils.MapperUtils.roomMapper;
import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_ID;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.entity.Room;
import br.com.sw2you.realmeet.mapper.RoomMapper;
import br.com.sw2you.realmeet.utils.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

class RoomMapperTest extends BaseUnitTest {

    private RoomMapper victim;

    @BeforeEach
    void setupEach() {
        victim = roomMapper();
    }

    @Test
    void testFromEntityToDto() {
        var room = newBuilder().id(DEFAULT_ROOM_ID).build();
        var dto = victim.fromEntityToDto(room);

        assertEquals(room.getName(), dto.getName());
        assertEquals(room.getSeats(), dto.getSeats());
        assertEquals(room.getId(), dto.getId());
    }

    @Test
    void testCreateRoomDtoToEntity() {
        var createRoomDTO = newCreateRoomDTO();
        var room = victim.fromCreateRoomDTOToEntity(createRoomDTO);

        assertEquals(createRoomDTO.getName(), room.getName());
        assertEquals(createRoomDTO.getSeats(), room.getSeats());
    }

}
