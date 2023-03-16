package sixman.helfit.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sixman.helfit.exception.BusinessLogicException;
import sixman.helfit.exception.ExceptionCode;
import sixman.helfit.response.ErrorResponse;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /*
     * # Method Argument Not Valid
     *  - javax.validation.Valid | @Validated 바인딩 오류 여부 확인
     *  - HttpMessageConverter -> HttpMessageConverter 바인딩 오류 여부 확인
     *  - @RequestBody, @RequestPart 사용 유무 확인
     *
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);

        return ErrorResponse.of(ExceptionCode.INVALID_INPUT_VALUE, e.getBindingResult());
    }

    /*
     * # Method Argument Type Mismatch
     *
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);

        final ErrorResponse response = ErrorResponse.of(e);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /*
     * # Http Request Method Not Supported
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ExceptionCode.METHOD_NOT_ALLOWED.getStatus()));
    }

    /*
     * # Http Message Not Readable
     *
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("HttpMessageNotReadableException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.NOT_ACCEPTABLE);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ExceptionCode.NOT_ACCEPTABLE.getStatus()));
    }

    /*
     * # Access Denied
     *
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.ACCESS_DENIED);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ExceptionCode.ACCESS_DENIED.getStatus()));
    }

    /*
     * # Authentication 관련 예외 처리
     *
     */
    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        log.error("handleBadCredentialsException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.INVALID_CREDENTIAL_VALUE);
        return new ResponseEntity<>(response, HttpStatus.valueOf(ExceptionCode.INVALID_CREDENTIAL_VALUE.getStatus()));
    }

    /*
     * # 비지니스 로직 예외 처리
     *
     */
    @ExceptionHandler(BusinessLogicException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(final BusinessLogicException e) {
        log.error("handleBusinessException", e);

        final ExceptionCode exceptionCode = e.getExceptionCode();
        final ErrorResponse response = ErrorResponse.of(exceptionCode);
        return new ResponseEntity<>(response, HttpStatus.valueOf(exceptionCode.getStatus()));
    }

    /*
     * # 기타 예외 처리 (최상위)
     *
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("handleException", e);

        final ErrorResponse response = ErrorResponse.of(ExceptionCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
