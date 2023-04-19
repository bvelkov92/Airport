package com.my.airportproject.validation.registration.anotations;

import com.my.airportproject.validation.registration.classes.EqualsPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = EqualsPasswordValidator.class)
public @interface EqualsPassword {

    String password();

    String confirmPassword();

    String message() default "Passwords miss match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}