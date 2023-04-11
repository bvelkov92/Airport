package com.my.airportproject.validation.registration.anotations;

import com.my.airportproject.validation.registration.classes.CompanyValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CompanyValidation.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyValidator {

        String message() default "Invalid company!";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
    }

