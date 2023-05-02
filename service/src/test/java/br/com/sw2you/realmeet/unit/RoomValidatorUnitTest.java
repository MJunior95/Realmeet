package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestConstants.DEFAULT_ROOM_NAME;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.utils.TestDataCreator.roomBuilder;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.utils.TestConstants;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidationError;
import br.com.sw2you.realmeet.validator.ValidationErrors;
import br.com.sw2you.realmeet.validator.ValidatorConstants;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.BDDAssumptions;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RoomValidatorUnitTest  extends BaseUnitTest {

    private RoomValidator victim;

    @Mock
    private RoomRepository roomRepository;

    @BeforeEach
    void setupEach(){
        victim = new RoomValidator(roomRepository);
    }

    @Test
    void testValueWhenRoomIsValid(){
        victim.validate(newCreateRoomDTO());
    }
    @Test
    void testValueWhenRoomNameIsMissing(){
      var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name("")));

      assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
      assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + MISSING), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValueWhenRoomNameExceedsLenght(){
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().name(StringUtils.rightPad("x",  ROOM_NAME_MAX_LENGTH + 1 , 'x'))));

        assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + MAX_LENGTH), exception.getValidationErrors().getError(0));
    }
    @Test
    void testValueWhenRoomSeatsIsMissing(){
      var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(null)));

      assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
      assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + MISSING), exception.getValidationErrors().getError(0));
    }
    @Test
    void testValueWhenRoomSeatsAreLessThanMinValue(){
      var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MIN_VALUE - 1)));

      assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
      assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + BELOW_MIN_VALUE), exception.getValidationErrors().getError(0));
    }
    @Test
    void testValueWhenRoomSeatsAreGreaterThanMaxValue(){
      var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(ROOM_SEATS_MAX_VALUE +1)));

      assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
      assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + EXCEEDS_MAX_VALUE), exception.getValidationErrors().getError(0));
    }

    @Test
    void testValidadeWhenRoomNameIsDuplicate(){
        given(roomRepository.findByNameAndActive(DEFAULT_ROOM_NAME, true))
                .willReturn(Optional.of(roomBuilder().build()));
        var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO()));

        assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
        assertEquals(new ValidationError(ROOM_NAME, ROOM_NAME + DUPLICATE), exception.getValidationErrors().getError(0));

    }
}
