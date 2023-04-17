package com.my.airportproject.validation.ChangeRole.anotation;

import com.my.airportproject.validation.ChangeRole.classes.CRRoleValidation;
import com.my.airportproject.validation.ChangeRole.classes.CRUserValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CRRoleValidation.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CRRoleValidator {

    String message() default "Invalid change!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
