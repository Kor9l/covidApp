package com.covid.covidall.exception.handler;

import com.covid.covidall.dto.ApiErrorDTO;
import com.covid.covidall.exception.custom.NotFoundException;
import java.util.Collections;
import java.util.List;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public final ResponseEntity<ApiErrorDTO> handleException(RuntimeException ex, WebRequest request) {
    HttpHeaders headers = new HttpHeaders();

    if (ex instanceof NotFoundException notFoundException) {
      HttpStatus status = HttpStatus.NOT_FOUND;

      return handleUserNotFoundException(notFoundException, headers, status, request);
    } else {
      HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
      return handleExceptionInternal(ex, null, headers, status, request);
    }
  }

  protected ResponseEntity<ApiErrorDTO> handleUserNotFoundException(NotFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    List<String> errors = Collections.singletonList(ex.getMessage());
    return handleExceptionInternal(ex, new ApiErrorDTO(errors), headers, status, request);
  }

  protected ResponseEntity<ApiErrorDTO> handleExceptionInternal(Exception ex, ApiErrorDTO body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
      request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, RequestAttributes.SCOPE_REQUEST);
    }

    return new ResponseEntity<>(body, headers, status);
  }
}