package backend.drivor.base.domain.validator;

import backend.drivor.base.domain.annotation.Password;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        if(!StringUtils.hasText(password)) {
            return false;
        }

        if(password.trim().length() < 6) {
            return false;
        }
        return true;
    }
}
