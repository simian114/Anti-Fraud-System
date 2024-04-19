package antifraud.web.validator.annotation;

import antifraud.constants.RoleCodeEnum;
import antifraud.web.validator.AvailableRoleValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AvailableRoleValidator.class)
public @interface ValidRoles {
    RoleCodeEnum[] anyOf();

    String message() default "Only SUPPORT or MERCHANT is supported";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
