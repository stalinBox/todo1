package ec.gob.mag.exception.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Repeatable(PredioVerificadorsExcluyente.class)
@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = PredioValidatorExcluyente.class)
public @interface PredioVerificadorExcluyente {

	String message() default "No puede ser null";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String selected();

	String[] required();

	String[] values();
}
