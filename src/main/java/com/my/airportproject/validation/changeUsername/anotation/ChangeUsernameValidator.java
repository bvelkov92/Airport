package com.my.airportproject.validation.changeUsername.anotation;

import com.my.airportproject.validation.changeUsername.classes.ChangeUsernameValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ChangeUsernameValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChangeUsernameValidator {

    String message() default "Username already exist or field is empty!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
