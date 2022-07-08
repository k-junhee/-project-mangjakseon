package com.mangjakseon.security.validation;

import lombok.extern.log4j.Log4j2;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Log4j2
public abstract class AbstractValidator<T> implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @SuppressWarnings("unchecked")  // 컴파일러에서 경고하지않음
    @Override
    public void validate(Object target, Errors errors) {
        try {
            doValidate((T)target, errors);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    protected abstract void doValidate(final T memberDTO, final Errors errors);

}
