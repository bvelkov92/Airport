package com.my.airportproject.validation.ChangeRole.anotation;

import com.my.airportproject.validation.ChangeRole.classes.CRUserValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CRUserValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CRUserValidator {

    String message() default "Username not found!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
