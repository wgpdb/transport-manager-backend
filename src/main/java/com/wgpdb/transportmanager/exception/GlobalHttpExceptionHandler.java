package com.wgpdb.transportmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TripNotFoundException.class)
    public ResponseEntity<Object> handleTripNotFoundException(TripNotFoundException exception) {
        return new ResponseEntity<>("Trip with provided ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TripRevenueNotFoundException.class)
    public ResponseEntity<Object> handleTripRevenueNotFoundException(TripRevenueNotFoundException exception) {
        return new ResponseEntity<>("Trip revenue details with provided ID do not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Object> handleExpenseNotFoundException(ExpenseNotFoundException exception) {
        return new ResponseEntity<>("Expense with provided ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpenseItemNotFoundException.class)
    public ResponseEntity<Object> handleExpenseItemNotFoundException(ExpenseItemNotFoundException exception) {
        return new ResponseEntity<>("Expense item with provided ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VendorNotFoundException.class)
    public ResponseEntity<Object> handleVendorNotFoundException(VendorNotFoundException exception) {
        return new ResponseEntity<>("Vendor with provided ID does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WeatherDataNotFoundException.class)
    public ResponseEntity<Object> handleWeatherDataNotFoundException(WeatherDataNotFoundException exception) {
        return new ResponseEntity<>("Weather data with provided ID does not exist", HttpStatus.NOT_FOUND);
    }
}