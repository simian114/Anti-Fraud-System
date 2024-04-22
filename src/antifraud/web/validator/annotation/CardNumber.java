package antifraud.web.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.LuhnCheck;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@LuhnCheck
public @interface CardNumber {
    String message() default "Invalid Card Number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
