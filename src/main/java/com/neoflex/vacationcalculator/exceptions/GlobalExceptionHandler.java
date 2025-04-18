package com.neoflex.vacationcalculator.exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //handling of the exceptions @NotNull, @Min, @DecimalMin
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        cv -> {
                            String path = cv.getPropertyPath().toString();
                            return path.substring(path.lastIndexOf('.') + 1);
                        },
                        ConstraintViolation::getMessage
                ));
    }

    //handling of invalid type inputs (String instead Integer, etc.)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String param = ex.getName();
        Class<?> type = ex.getRequiredType();
        String message;
        if (type.equals(Integer.class) || type.equals(int.class)) {
            message = "Должно быть целое число, например, 1 или 3 (дня отпуска)";
        } else if (type.equals(Double.class) || type.equals(double.class)) {
            message = "Должно быть число, например, 12000.50 (у.е.)";
        } else {
            message = "Неверный формат параметра";
        }
        return Map.of(param, message);
    }
 //a required parameter is missing
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMissingParameter(MissingServletRequestParameterException ex) {
        return Map.of(ex.getParameterName(), "Параметр обязателен");
    }
}
