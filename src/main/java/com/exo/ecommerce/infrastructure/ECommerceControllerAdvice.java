package com.exo.ecommerce.infrastructure;
import com.exo.ecommerce.infrastructure.http.CartResponse;
import com.exo.ecommerce.usecases.NothingToCheckOutException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice(assignableTypes = ECommerceController.class)
@RequestMapping(produces = "application/json; charset=UTF-8")
public class ECommerceControllerAdvice {

    @ExceptionHandler(NothingToCheckOutException.class)
    public ResponseEntity nothingToCheckOutException(NothingToCheckOutException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }
}
