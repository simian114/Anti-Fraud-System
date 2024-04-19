package antifraud.web.validator;

import antifraud.constants.RoleCodeEnum;
import antifraud.web.validator.annotation.ValidRoles;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AvailableRoleValidator implements ConstraintValidator<ValidRoles, RoleCodeEnum> {
    private RoleCodeEnum[] subset;

    @Override
    public void initialize(ValidRoles constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(RoleCodeEnum code, ConstraintValidatorContext context) {
        if (code == null) {
            return false;
        }
        return Arrays.asList(this.subset).contains(code);
    }
}
