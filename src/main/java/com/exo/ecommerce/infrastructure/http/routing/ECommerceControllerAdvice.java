package com.exo.ecommerce.infrastructure.http.routing;
import com.exo.ecommerce.infrastructure.http.presentation.CartResponse;
import com.exo.ecommerce.usecases.additemtocart.UnavailableItemExeption;
import com.exo.ecommerce.usecases.additemtocart.UnknownItemException;
import com.exo.ecommerce.usecases.checkout.NothingToCheckOutException;
import com.exo.ecommerce.usecases.getcurrentcart.NoCurrentCartException;
import com.exo.ecommerce.usecases.getinvoice.InvoiceNotFoundException;
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

    @ExceptionHandler(UnknownItemException.class)
    public ResponseEntity unknownItemException(UnknownItemException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @ExceptionHandler(UnavailableItemExeption.class)
    public ResponseEntity unavailableItemException(UnavailableItemExeption e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
    }

    @ExceptionHandler(InvoiceNotFoundException.class)
    public ResponseEntity invoiceNotFoundException(InvoiceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @ExceptionHandler(NoCurrentCartException.class)
    public ResponseEntity noCurrentCartException(NoCurrentCartException e) {
        return ResponseEntity.status(HttpStatus.OK).body(new CartResponse(null));
    }
}
