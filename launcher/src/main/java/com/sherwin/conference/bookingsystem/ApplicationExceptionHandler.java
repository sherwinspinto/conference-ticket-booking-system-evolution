package com.sherwin.conference.bookingsystem;

import com.sherwin.conference.bookingsystem.infrastructure.db.entity.talk.TalkEntity;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  ResponseEntity<?> handleOptimisticLockingException(
      ObjectOptimisticLockingFailureException optimisticLockingException, WebRequest request) {
    if (Objects.equals(optimisticLockingException.getPersistentClass(), TalkEntity.class)) {
      return super.handleExceptionInternal(
          optimisticLockingException,
          Map.of(
              "message",
              "Someone just reserved the last seat for this talk with Id "
                  + optimisticLockingException.getIdentifier()
                  + ". Please try again later."),
          new HttpHeaders(),
          HttpStatus.CONFLICT,
          request);
    }

    return super.handleExceptionInternal(
        optimisticLockingException,
        Map.of("message", "Generic optimistic locking exception. Please try again later."),
        new HttpHeaders(),
        HttpStatus.CONFLICT,
        request);
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<?> handleGenericException(Exception exception, WebRequest request) {
    return super.handleExceptionInternal(exception, Map.of("message", "Generic exception."), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
