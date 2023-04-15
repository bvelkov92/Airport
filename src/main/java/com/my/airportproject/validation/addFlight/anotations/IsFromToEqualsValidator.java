package com.my.airportproject.validation.addFlight.anotations;

import com.my.airportproject.validation.addFlight.classes.FlightValidation;
import com.my.airportproject.validation.addFlight.classes.IsFromToEqualsValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsFromToEqualsValidation.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsFromToEqualsValidator {

    String message() default "Values for Start-End points can't be equals";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
