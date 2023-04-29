package br.com.sw2you.realmeet.unit;

import static br.com.sw2you.realmeet.utils.TestDataCreator.newCreateRoomDTO;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.core.BaseUnitTest;
import br.com.sw2you.realmeet.exception.InvalidRequestException;
import br.com.sw2you.realmeet.utils.TestDataCreator;
import br.com.sw2you.realmeet.validator.RoomValidator;
import br.com.sw2you.realmeet.validator.ValidationError;
import br.com.sw2you.realmeet.validator.ValidationErrors;
import br.com.sw2you.realmeet.validator.ValidatorConstants;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomValidatorUnitTest  extends BaseUnitTest {

    private RoomValidator victim;

    @BeforeEach
    void setupEach(){
        victim = new RoomValidator();
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
    void testValueWhenRoomSeatsIsMissing(){
      var exception = assertThrows(InvalidRequestException.class, () -> victim.validate(newCreateRoomDTO().seats(null)));

      assertEquals(1, exception.getValidationErrors().getNumbersOfErrors());
      assertEquals(new ValidationError(ROOM_SEATS, ROOM_SEATS + MISSING), exception.getValidationErrors().getError(0));
    }
}
