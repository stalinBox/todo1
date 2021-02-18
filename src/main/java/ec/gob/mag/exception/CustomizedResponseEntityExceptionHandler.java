package ec.gob.mag.exception;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.RollbackException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import ec.gob.mag.util.ModifyString;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;
	private final static Logger LOGGER = LoggerFactory.getLogger(ResponseEntityExceptionHandler.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final ResponseEntity buildException(Exception ex, HttpStatus status, String msgProperty, String msgDetail,
			String msgException, EnumCodeExceptions myTypeCod) {
		String proyecto = messageSource.getMessage("name.proyect", null, LocaleContextHolder.getLocale());

		ExceptionResponse exResponse;
		try {
			String jsonStringU = ex.getMessage();
			ObjectMapper mapper = new ObjectMapper();
			ExceptionResponse myEx = mapper.readValue(jsonStringU, ExceptionResponse.class);
			myEx.setStatus(status);
			myEx.setTimestamp(new Date());
			exResponse = myEx;
//			String stack = MyExceptionUtility.getExceptionDump(ex);
//			myEx.setStack(stack);

		} catch (Exception e) {
			String msgEx = null;
			if (msgException == null) {
				if (msgProperty != null)
					msgEx = messageSource.getMessage(msgProperty, null, LocaleContextHolder.getLocale());
				else
					msgEx = ex.getMessage();
			} else
				msgEx = msgException;
//			String stack = MyExceptionUtility.getExceptionDump(ex);
			myTypeCod = (myTypeCod == null ? EnumCodeExceptions.ERROR_UNRECOGNIZABLE : myTypeCod);
			EnumTypeExceptions myTypeEx = (myTypeCod == EnumCodeExceptions.ERROR_UNRECOGNIZABLE
					? EnumTypeExceptions.CRITICAL
					: EnumTypeExceptions.WARN);
			exResponse = new ExceptionResponse(status, new Date(), msgEx, null, msgDetail, proyecto, null, null,
					myTypeCod, myTypeEx);

		}
		return new ResponseEntity(exResponse, status);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(MyNotFoundException.class)
	public final ResponseEntity<Object> handleMyNotFoundException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.NOT_FOUND, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(MyInternalException.class)
	public final ResponseEntity<Object> handleMyInternalException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(CmNotFoundException.class)
	public final ResponseEntity<Object> handleNotFoundException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.NOT_FOUND, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(MyBadRequestException.class)
	public final ResponseEntity<Object> handleBadRequestException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.BAD_REQUEST, null, null, null, null);
	}

	// ERRORES INTERNOS

	@SuppressWarnings("unchecked")
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Object> handleIllegalArgumentException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null, null);

	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(RollbackException.class)
	public final ResponseEntity<Object> handleRollbackException(Exception ex, WebRequest request) {
		return buildException(null, HttpStatus.INTERNAL_SERVER_ERROR, null, null, "sssssss", null);

	}

	@ExceptionHandler(TransactionSystemException.class)
	protected ResponseEntity<Object> handleConflict(TransactionSystemException ex, WebRequest request) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(NoSuchElementException.class)
	public final ResponseEntity<Object> handleNoSuchElementException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "error.entity_not_exist_find_array.message", null,
				null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(DataIntegrityViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "error.constraint_validation.message", null, null,
				EnumCodeExceptions.WARNING_CONTRAINT_SHEMA);

	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(value = { ConstraintViolationException.class })
	@ResponseBody
	ResponseEntity<Object> handleNotAuthenticated(RuntimeException ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, "error.constraint_validation.message", null, null,
				null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(StackOverflowError.class)
	public final ResponseEntity<Object> handleStackOverFlowError(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null, null);

	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(InternalError.class)
	public final ResponseEntity<Object> handleInternalException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.INTERNAL_SERVER_ERROR, null, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@ExceptionHandler(org.springframework.dao.IncorrectResultSizeDataAccessException.class)
	public final ResponseEntity<Object> handleNonUniqueResultException(Exception ex, WebRequest request) {
		return buildException(ex, HttpStatus.NOT_FOUND, "error.non_unique_result.message", null, null, null);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		return buildException(ex, HttpStatus.BAD_REQUEST, "error.method_argument_not_valid_exception.message",
				messageDetail, null, null);
	}

}
