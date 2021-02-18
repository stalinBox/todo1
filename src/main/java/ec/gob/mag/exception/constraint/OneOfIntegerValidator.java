package ec.gob.mag.exception.constraint;

import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.common.base.Objects;

public class OneOfIntegerValidator implements ConstraintValidator<OneOfInteger, Object> {

	private int[] values = new int[] {};
	private boolean ignoreCase;

	@Override
	public void initialize(OneOfInteger constraintAnnotation) {
		this.values = constraintAnnotation.value();
		this.ignoreCase = constraintAnnotation.ignoreCase();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (ignoreCase) {
			return true;
		}
		return Arrays.stream(values).anyMatch(s -> Objects.equal(value, s));

	}
}