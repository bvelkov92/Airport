package com.my.airportproject.validation.addFlight.anotations;

import com.my.airportproject.validation.addFlight.classes.PlaneValidationFlights;
import com.my.airportproject.validation.addPlane.classes.PlaneValidationAdd;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlaneValidationFlights.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface PlaneValidatorFlights {
    String message() default "Plane is exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
