package de.telran.g_280323_m_be_shop.exception_handling;

import de.telran.g_280323_m_be_shop.exception_handling.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

    /*
    Плюсы данного способа: в том, что данный обработчик является глобальным, то есть ловит исключения, возникшие
    в любом классе приложения. Также в том, что мы при помощи объектов ResponseEntity можем отправить
     информационное сообщение об ошибке и выбранный нами статус.
     Минус - в том, что мы не сможем настроить точечную обработку для какого-то конкретного класса.
     */

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(ThirdTestException.class)
    public ResponseEntity<Response> handleException(ThirdTestException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ConstraintViolationException.class, EntityValidationException.class})
    public ResponseEntity<Response> handleValidationException(Exception e) {
        Response response = new Response("Validation failed: " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CartOperationException.class)
    public ResponseEntity<Response> handleException(CartOperationException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Response> handleException(CustomerNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidProductDataException.class)
    public ResponseEntity<Response> handleException(InvalidProductDataException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductDeletionException.class)
    public ResponseEntity<Response> handleException(ProductDeletionException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Response> handleException(ProductNotFoundException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
