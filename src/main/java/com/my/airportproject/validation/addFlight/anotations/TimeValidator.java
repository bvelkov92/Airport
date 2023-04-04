package com.my.airportproject.validation.addFlight.anotations;

import com.my.airportproject.validation.addFlight.classes.FlightValidation;
import com.my.airportproject.validation.addFlight.classes.TimeValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimeValidation.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeValidator {

    String message() default "The time of publish and flight must be at least 1 hour!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
