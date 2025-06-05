package co.edu.unicauca.distribuidos.core.errores.modelos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import co.edu.unicauca.distribuidos.core.errores.BusinessException;

import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import jakarta.validation.ConstraintViolationException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.AccountExpiredException;

import java.util.List;
import java.util.ArrayList;

@RestControllerAdvice
public class ErrorController {


    // Generic exception handler - should be last
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllExceptions(
            Exception ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_SERVER_ERROR.getCode(),
                ErrorCode.INTERNAL_SERVER_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setDetails("Un error inesperado ha ocurrido");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage(),
                ex.getHttpStatus().value());
        errorResponse.setDetails(ex.getDetails());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.FIELD_VALIDATION_ERROR.getCode(),
                ErrorCode.FIELD_VALIDATION_ERROR.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        errorResponse.setFieldErrors(fieldErrors);
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setDetails("Uno o más campos tienen errores de validación");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, HttpServletRequest request) {

        List<String> validationErrors = new ArrayList<>();
        ex.getConstraintViolations().forEach(
                violation -> validationErrors.add(violation.getPropertyPath() + ": " + violation.getMessage()));

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.CONSTRAINT_VIOLATION.getCode(),
                ErrorCode.CONSTRAINT_VIOLATION.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        errorResponse.setValidationErrors(validationErrors);
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.METHOD_NOT_ALLOWED.getCode(),
                ErrorCode.METHOD_NOT_ALLOWED.getMessage(),
                HttpStatus.METHOD_NOT_ALLOWED.value());
        errorResponse.setDetails("Métodos soportados: " + String.join(", ", ex.getSupportedMethods()));
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(
            HttpMessageNotReadableException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.MALFORMED_REQUEST.getCode(),
                ErrorCode.MALFORMED_REQUEST.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        errorResponse.setDetails("El cuerpo de la petición esta malformado o hace falta");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParameter(
            MissingServletRequestParameterException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.MISSING_PARAMETER.getCode(),
                ErrorCode.MISSING_PARAMETER.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        errorResponse.setDetails("Hace falta el parametro requerido: " + ex.getParameterName());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_FORMAT.getCode(),
                ErrorCode.INVALID_FORMAT.getMessage(),
                HttpStatus.BAD_REQUEST.value());
        errorResponse.setDetails("Valor invalido para parametro: " + ex.getName());
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Authentication and Authorization errors
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(
            AuthenticationException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.UNAUTHORIZED.getCode(),
                ErrorCode.UNAUTHORIZED.getMessage(),
                HttpStatus.UNAUTHORIZED.value());
        errorResponse.setDetails("Autenticación fallida");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.FORBIDDEN.getCode(),
                ErrorCode.FORBIDDEN.getMessage(),
                HttpStatus.FORBIDDEN.value());
        errorResponse.setDetails("Acceso denegado a este recurso");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    // Database errors
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ErrorResponse> handleDataAccessException(
            DataAccessException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.DATABASE_ERROR.getCode(),
                ErrorCode.DATABASE_ERROR.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setDetails("Fallo en operación de base de datos");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(
            BadCredentialsException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INVALID_CREDENTIALS.getCode(),
                ErrorCode.INVALID_CREDENTIALS.getMessage(),
                HttpStatus.UNAUTHORIZED.value());
        errorResponse.setDetails("Las credenciales son inválidas");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(
            UsernameNotFoundException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                "User not found",
                HttpStatus.NOT_FOUND.value());
        errorResponse.setDetails("El usuario en especifico no exite");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ErrorResponse> handleAccountExpired(
            AccountExpiredException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.AUTH_USER_DISABLED.getCode(),
                "Account expired",
                HttpStatus.UNAUTHORIZED.value());
        errorResponse.setDetails("La cuenta del usuario ha expirado");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ErrorResponse> handleDisabled(
            DisabledException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.AUTH_USER_DISABLED.getCode(),
                "Account disabled",
                HttpStatus.UNAUTHORIZED.value());
        errorResponse.setDetails("La cuenta del usuario esta deshabilitada");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ErrorResponse> handleLocked(
            LockedException ex, HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.AUTH_USER_LOCKED.getCode(),
                "Account locked",
                HttpStatus.UNAUTHORIZED.value());
        errorResponse.setDetails("La cuenta del usuario esta asegurada");
        errorResponse.setPath(request.getRequestURI());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
