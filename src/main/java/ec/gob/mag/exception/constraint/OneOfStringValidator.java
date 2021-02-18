package ec.gob.mag.exception.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOfStringValidator implements ConstraintValidator<OneOfString, Object> {
	private String[] values = new String[] {};
	private boolean caseInsensitive;
	private boolean ignoreWhitespace;

	@Override
	public void initialize(OneOfString constraintAnnotation) {
		this.values = constraintAnnotation.value();
		this.caseInsensitive = constraintAnnotation.ignoreCase();
		this.ignoreWhitespace = constraintAnnotation.ignoreWhitespace();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		System.out.println("valores: " + this.values);
		final String v = ignoreWhitespace ? value.toString().trim() : value.toString();
		if (caseInsensitive) {
			for (String s : values) {
				if (s.equalsIgnoreCase(v)) {
					return true;
				}
			}
		} else {
			for (String s : values) {
				if (s.equals(v)) {
					return true;
				}
			}
		}
		return false;
	}
}