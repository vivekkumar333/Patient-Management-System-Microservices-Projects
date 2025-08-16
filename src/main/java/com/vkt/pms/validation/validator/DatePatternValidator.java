package com.vkt.pms.validation.validator;

import com.vkt.pms.validation.annotation.DatePattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DatePatternValidator implements ConstraintValidator<DatePattern,String> {
    private String pattern;

    @Override
    public void initialize(DatePattern constraintAnnotation) {
       this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if(date == null || date.trim().isEmpty()){
            return true;    // Leave @NotNull to handle null checks
        }
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(this.pattern);
            LocalDate.parse(date,formatter);
            return true;
        }catch(DateTimeParseException ex){
            return false;
        }
    }
}
