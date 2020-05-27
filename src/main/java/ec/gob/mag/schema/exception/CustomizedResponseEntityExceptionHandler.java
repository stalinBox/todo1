package ec.gob.mag.schema.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ec.gob.mag.schema.controller.ApiController;
import ec.gob.mag.schema.util.ModifyString;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	private final static Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ExceptionHandler(NotFoundException.class)
	public final ResponseEntity<Object> handleOfficerNotFoundException(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.NOT_FOUND.value(), new Date(),
				ex.getMessage(), request.getDescription(false));
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult result = ex.getBindingResult();
		List<FieldError> fieldErrors = result.getFieldErrors();
		String messageDetail = "error:{";

		int sizeFieldError = fieldErrors.size();
		for (int it = 0; it < sizeFieldError; it++) {
			try {
				FieldError f = fieldErrors.get(it);
				String[] msgArr = (f.getDefaultMessage()).split("-");
				List<String> msgList = Arrays.asList(msgArr);
				String field = f.getField();
				List<Object> param = new ArrayList(msgList);
				param.set(0, field);
				String msg = msgArr[0];
				if (msgList.get(0).startsWith("_")) {
					msg = messageSource.getMessage(msgList.get(0), null, LocaleContextHolder.getLocale());
					msgList = new ArrayList(Arrays.asList(msg.split("%s")));
					msg = "";
					int k = 0;
					for (int j = 0; j < msgList.size(); j++) {
						if (k < param.size()) {
							msg = msg + (j == 0 ? "" : " ") + msgList.get(j) + " " + param.get(k)
									+ ((j + 1) == msgList.size() ? "" : " ");
							k++;
						} else
							msg = msg + (j == 0 ? "" : " ") + msgList.get(j) + ((j + 1) == msgList.size() ? "" : " ");
					}
				}
				msg = ModifyString.cleanBlanks(msg);
				messageDetail = messageDetail + f.getField() + " : " + msg + (it == (sizeFieldError - 1) ? "" : ",");
				LOGGER.info("Validation message ready");
			} catch (Exception ex1) {
				LOGGER.warn(ex1.getMessage());
			}
		}

		messageDetail = messageDetail + "}";
		messageDetail = ModifyString.cleanBlanks(messageDetail);
		ExceptionResponse exceptionResponse = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), new Date(),
				messageSource.getMessage("error.method_argument_not_valid_exception.message", null,
						LocaleContextHolder.getLocale()),
				null);
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
