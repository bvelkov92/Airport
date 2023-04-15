package com.my.airportproject.validation.addFlight.anotations;

import com.my.airportproject.validation.addFlight.classes.FlightValidation;
import com.my.airportproject.validation.registration.anotations.UniqueEmail;
import com.my.airportproject.validation.registration.classes.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FlightValidation.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlightValidator {

    String message() default "This flight exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
