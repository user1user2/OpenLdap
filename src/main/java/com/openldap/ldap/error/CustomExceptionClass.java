package com.openldap.ldap.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.openldap.ldap.entity.Person;

@ControllerAdvice
public class CustomExceptionClass  extends  ResponseEntityExceptionHandler{
	
	

	@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
                    MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
            ExceptionDetails out = new ExceptionDetails(new Date(), "check the attributes", request.getDescription(false));

            return new ResponseEntity<Object>(out, HttpStatus.BAD_REQUEST);
    }
    
	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<Object> employeeException(EmployeeNotFoundException ex,WebRequest req)throws Exception{
		//EmployeeNotFoundException obj = new EmployeeNotFoundException(ex.getMessage(),req.getDescription(false))
		ExceptionDetails obj = new ExceptionDetails(new Date(), ex.getMessage(), req.getDescription(false));
		System.out.println(obj);
		return  new ResponseEntity<>(obj, HttpStatus.NOT_FOUND);
	}
	
	
	
	@ExceptionHandler(InvalidAttributeValueException.class)
	public final ResponseEntity<?> handleAttribute(InvalidAttributeValueException e,WebRequest req){
		ExceptionDetails obj = new ExceptionDetails(new Date(), "Attributes are null",req.getDescription(false));
		return new ResponseEntity<>(obj, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidIdException.class)
	public final ResponseEntity<Object> handleInvalid(InvalidIdException e,WebRequest req){
		ExceptionDetails ob = new ExceptionDetails(new Date(), "Check the ID", req.getDescription(false));
		return new ResponseEntity<Object>(ob, HttpStatus.BAD_REQUEST);
	}
	
	
	
	@ExceptionHandler(UserAlreadyExist.class)
	public final ResponseEntity<Object> employeeException(UserAlreadyExist e,WebRequest req){
		ExceptionDetails obj = new ExceptionDetails(new Date(), e.getMessage(), req.getDescription(false));
		return new ResponseEntity<Object>(obj, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
