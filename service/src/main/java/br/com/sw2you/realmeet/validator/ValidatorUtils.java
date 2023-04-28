package br.com.sw2you.realmeet.validator;

import static br.com.sw2you.realmeet.validator.ValidatorConstants.MISSING;
import static br.com.sw2you.realmeet.validator.ValidatorConstants.MAX_LENGTH;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Objects;

import br.com.sw2you.realmeet.exception.InvalidRequestException;
import org.apache.commons.lang3.StringUtils;

public final class ValidatorUtils {

    private ValidatorUtils(){}

    public static void throwOnError(ValidationErrors validationErrors){
        if(validationErrors.hasError()){
            throw new InvalidRequestException(validationErrors);
        }
    }

    public static boolean validateRequired(String field, String fieldName, ValidationErrors validationErrors){
        if(isBlank(fieldName)){
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }
    public static boolean validateRequired(Object field, String fieldName, ValidationErrors validationErrors){
        if(isNull(fieldName)){
            validationErrors.add(fieldName, fieldName + MISSING);
            return false;
        }
        return true;
    }
    public static boolean validateMaxLength(String field, String fieldName, int maxLength, ValidationErrors validationErrors){
        if(!isBlank(fieldName) && field.trim().length() > maxLength){
            validationErrors.add(fieldName, fieldName + MAX_LENGTH);
            return false;
        }
        return true;
    }
}
