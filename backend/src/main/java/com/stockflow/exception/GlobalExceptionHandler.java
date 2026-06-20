package com.stockflow.exception;

import com.stockflow.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred"));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Object>> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception
    ) {
        String parameterName = exception.getName();

        String message = "Invalid value for parameter: " + parameterName;

        if (exception.getRequiredType() != null) {
            if (exception.getRequiredType().equals(java.time.LocalDate.class)) {
                message = "Invalid date format for parameter: " + parameterName + ". Expected format is yyyy-MM-dd";
            } else if (exception.getRequiredType().equals(Long.class)) {
                message = "Invalid number format for parameter: " + parameterName;
            } else if (exception.getRequiredType().equals(Integer.class) || exception.getRequiredType().equals(int.class)) {
                message = "Invalid number format for parameter: " + parameterName;
            }
        }

        return ResponseEntity.badRequest()
                .body(ApiResponse.error(message));
    }
}
