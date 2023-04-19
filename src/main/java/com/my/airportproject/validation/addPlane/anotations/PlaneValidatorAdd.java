package com.my.airportproject.validation.addPlane.anotations;

import com.my.airportproject.validation.addPlane.classes.PlaneValidationAdd;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlaneValidationAdd.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public  @interface PlaneValidatorAdd {
    String message() default "Invalid Plane!!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
