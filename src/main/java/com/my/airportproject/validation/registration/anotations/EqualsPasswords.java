package com.my.airportproject.validation.registration.anotations;

import com.my.airportproject.validation.registration.classes.EqualsPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EqualsPasswordValidator.class)
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EqualsPasswords {

    String message() default "Passwords are not equals!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
