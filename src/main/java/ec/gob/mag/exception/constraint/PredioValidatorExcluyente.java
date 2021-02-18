package ec.gob.mag.exception.constraint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import org.apache.commons.beanutils.BeanUtils;

import ec.gob.mag.util.BooleanList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import static org.springframework.util.StringUtils.isEmpty;

public class PredioValidatorExcluyente implements ConstraintValidator<PredioVerificadorExcluyente, Object> {

	private String selected;
	private String[] required;
//	private String message;
	private String[] values;

	@Override
	public void initialize(PredioVerificadorExcluyente constraintAnnotation) {
		this.selected = constraintAnnotation.selected();
		this.required = constraintAnnotation.required();
		this.values = constraintAnnotation.values();
	}

	public static BooleanList getLastElementUsingReduce(ArrayList<BooleanList> booleanList) {
		Stream<BooleanList> stream = booleanList.stream();
		return stream.reduce((first, second) -> second).orElse(null);
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {
		Boolean valid = true;
		try {
			Object checkedValue = BeanUtils.getProperty(object, selected);
			if (Arrays.asList(values).contains(checkedValue)) {
				ArrayList<BooleanList> booleanList = new ArrayList<BooleanList>();

				int i = 0;
				for (String propName : required) {
					Object requiredValue = BeanUtils.getProperty(object, propName);

					valid = requiredValue != null && !isEmpty(requiredValue);
//					System.out.println("valid: " + valid);
//					System.out.println("validar: =>" + !valid);
					booleanList.add(new BooleanList(valid, propName));
					try {
						if (booleanList.get(i - 1).getValid() == true)
							valid = true;
						if (booleanList.get(i - 1).getValid() == true & booleanList.get(i).getValid() == true)
							valid = false;
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (!valid) {
						context.disableDefaultConstraintViolation();
						context.buildConstraintViolationWithTemplate("revisar los requerimientos en la ficha")
								.addPropertyNode(propName).addConstraintViolation();
					}
					i++;
				}

				// IMPORTANTE NO ELIMINAR
//				BooleanList lastElement = getLastElementUsingReduce(booleanList);
//				System.out
//						.println("ulitmo element: " + lastElement.getPropName() + " valor: " + lastElement.getValid());

//				boolean areAllSuccessful1 = val.stream().map(entry -> entry.booleanValue()).reduce(Boolean.TRUE,
//						Boolean::logicalAnd);
//				System.out.println("result final And: " + areAllSuccessful1);

//				boolean areAllSuccessful3 = booleanList.stream().map(entry -> entry.getValid()).reduce(Boolean.FALSE,
//						Boolean::logicalXor);
//				System.out.println("result final XOR3: " + areAllSuccessful3);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valid;
	}
}