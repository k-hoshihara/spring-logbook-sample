package payroll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class OrderStatusTransitionAdvice {

    @ExceptionHandler(OrderStatusTransitionException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String handleOrderStatusTransition(OrderStatusTransitionException ex) {
        return ex.getMessage();
    }
}
