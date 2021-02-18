package ec.gob.mag.exception.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ec.gob.mag.util.Util;

public class CedulaValidator implements ConstraintValidator<CedulaVerificador, Object> {
	@Override
	public void initialize(CedulaVerificador constraintAnnotation) {
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Util util = new Util();
		if (util.verificarCedula(value.toString())) {
			return true;
		} else {
			return false;
		}
	}
}