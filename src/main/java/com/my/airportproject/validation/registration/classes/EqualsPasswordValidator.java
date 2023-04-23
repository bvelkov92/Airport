package com.my.airportproject.validation.registration.classes;

import com.my.airportproject.validation.registration.anotations.EqualsPassword;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualsPasswordValidator implements ConstraintValidator<EqualsPassword, Object> {

    private String password;
    private String confirmPassword;
    private String message;


    @Override
    public void initialize(EqualsPassword constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object passwordValue = beanWrapper.getPropertyValue(this.password);
        Object confirmPasswordValue = beanWrapper.getPropertyValue(this.confirmPassword);

        if (passwordValue != null && passwordValue.equals(confirmPasswordValue)) {
            return true;
        }

        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(confirmPassword)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
