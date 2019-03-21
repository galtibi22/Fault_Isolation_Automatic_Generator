package org.afeka.fi.backend.validator;


import org.afeka.fi.backend.common.FiCommon;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator extends FiCommon implements ConstraintValidator<Email, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
    }
}
