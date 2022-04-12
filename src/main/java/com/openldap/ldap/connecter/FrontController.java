package com.openldap.ldap.connecter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.openldap.ldap.entity.Person;
import com.openldap.ldap.error.CustomExceptionClass;
import com.openldap.ldap.error.EmployeeNotFoundException;
import com.openldap.ldap.error.ExceptionDetails;
import com.openldap.ldap.error.InvalidAttributeValueException;
import com.openldap.ldap.error.InvalidIdException;
import com.openldap.ldap.error.UserAlreadyExist;
//import com.openldap.ldap.error.CustomExceptionClass;
//import com.openldap.ldap.error.ErrorHandler;
import com.openldap.ldap.service.MethodsRepo;

@RestController
public class FrontController {
	
	    @Autowired
	    private MethodsRepo personRepo;
	    
	    @GetMapping("/users")
	    public ResponseEntity<?> retrieve(@RequestParam Map<String,String> q) {
	  		
	  		try {
	  			System.out.println(q);
	  		    List<Person>out =  personRepo.getUserByFilters(q);
	  			System.out.println(out);
	  			
	  			return ResponseEntity.status(HttpStatus.OK).body(out);
//	  		List<Person> result = personRepo.retrieve(ID,NAME);
//	  	    return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
	  		}catch(Exception e) {
	  			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
	  		}
	  		
	   
	    }
	    @GetMapping("/usersdata")
	    public ResponseEntity<?> getusers(){
	    	try {
	    		List<Person> out = personRepo.retrieve();
	    		return ResponseEntity.status(HttpStatus.OK).body(out);
	    	}catch(Exception e) {
	    		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check with url");
	    	}
	    }
	   
//	  	@GetMapping("/users")
//	    public ResponseEntity<?> retrieve() {
//	  		
//	  		try {
//	  		List<Person> result = personRepo.retrieve();
//	  	    return new ResponseEntity<List<Person>>(result, HttpStatus.OK);
//	  		}catch(Exception e) {
//	  			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
//	  		}
//	  		
//	   
//	    }
	  	
	  	@GetMapping("/{firstName}")
	  	public ResponseEntity<?> getAttributesById(@PathVariable String firstName){
	    List<Object> out = personRepo.getByName(firstName);
	    return ResponseEntity.status(HttpStatus.OK).body(out);
	  		  		
	  	}
	
	  	
	  	@GetMapping("/users/{id}")
	  	public ResponseEntity<?> getbyId(@PathVariable String id) throws InvalidIdException {
	  		System.out.println(id);
	  		if(id.isBlank()||id.isEmpty()) {
	  			throw new InvalidIdException("Please check the Primarykey ID");
	  		}
	  		List<Person> p = personRepo.retriveById(id); //employee or null
	  		try {
	  			if(p.isEmpty()) {
	  				//return new ResponseEntity<ErrorHandler>(new ErrorHandler(p),HttpStatus.NOT_FOUND);
	  			  throw new EmployeeNotFoundException("Employee Not found :"+id.toString() );
	  			}
	  			
	  		return new ResponseEntity<>(p.get(0), HttpStatus.OK);
	  			
	  		}
	  		catch(EmployeeNotFoundException e) { //-->check for exception handler with exception name class
	  			
	  			
	  			throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);  //no null
  		}
	  	}
	  	
	  	
	  	
	  	@PutMapping("/users/user")
	  	public ResponseEntity<?> getupdate(@RequestBody Person emp){
	  		List<Person> out = personRepo.update(emp);
	  		try {
	  		
	  		if(out.size()>0) {
	  		return ResponseEntity.status(HttpStatus.OK).body(out.get(1));
	  		}
	  		else {
	  			throw new EmployeeNotFoundException("Employee not Exist"+emp.getUserId());
	  		}
	  		}catch(Exception e) {
	  			throw new ResponseStatusException(HttpStatus.NO_CONTENT,null, e); //no null
	  		}
	  		
	  		
	  	}
	  	
	  	@DeleteMapping("/users/user/{id}")
	  	public ResponseEntity<?> remove(@PathVariable String id){
	  		List<Person> s = personRepo.remove(id);
	  		try {
	  			if(s.isEmpty()) {
	  				throw new EmployeeNotFoundException("Employee Not Exist"+id);
	  			}
	  		
	  		
	  		return ResponseEntity.status(HttpStatus.OK).body(s);
	  		}catch(EmployeeNotFoundException e) {
	  			
	  			
	  			throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e);
	  			
	  		}
	  		
	  		
	  	}
	  	@PostMapping("/users/newuser")
	  	public ResponseEntity<?> createUser(@Valid @RequestBody Person emp) {
	  		
	  		
	  		List<Person> out = personRepo.create(emp);
	  		
	  	
	  		try {
	  			if(out.isEmpty()) {
	  				throw new UserAlreadyExist("Employee Already Exist :"+emp.getUserId());
	  			}
	  			return ResponseEntity.status(HttpStatus.CREATED).body(out.get(0));
	  		}
	  		catch(UserAlreadyExist e) {
	  			
	  			
	  			throw new ResponseStatusException(HttpStatus.NOT_FOUND, null, e); //no null 
	  			//return new ResponseEntity<>(new ExceptionDetails(new Date(),"Enter the Non Existing Employee", e.getMessage()), HttpStatus.NOT_IMPLEMENTED); //null
	  			
	  		}
//	  		catch(InvalidAttributeValueException e) {
//	  			
//	  		}
	  	}

	  
}
	  		
	  	


//	public ResponseEntity<ErrorHandler> getbyId(@PathVariable String id){
//	List<Person> p  = personRepo.retriveById(id);
//	
//	try {
//		if(p.isEmpty()) {
//			return new ResponseEntity<ErrorHandler>(new ErrorHandler("Person not found",p),HttpStatus.NOT_FOUND);
//		}
//		else {
//			return new ResponseEntity<ErrorHandler>(new ErrorHandler("Found",p),HttpStatus.OK);
//		}
//	}
//	catch(Exception e) {
//		return new ResponseEntity<ErrorHandler>(new ErrorHandler(e.getStackTrace().toString(),p),HttpStatus.BAD_REQUEST);
//	}
//}
	  	
//	  	@PostMapping("/create-user")
//	  	public ResponseEntity<?> create(@RequestBody Person p1){
//	  		List<Person> out = personRepo.create(p1);
//	  		System.out.println(out);
//	  		try {
//	  			if(out.isEmpty()) {
//	  				System.out.println("IF---entered");
//	  				return new ResponseEntity<>(new ErrorHandler(out),HttpStatus.ACCEPTED);
//	  				
//	  			}
//	  			else {
//	  				System.out.println("else---entered");
//	  				//return new ResponseEntity<>(new ErrorHandler(out),HttpStatus.CREATED);
//	  				return ResponseEntity.status(HttpStatus.CREATED).body(out);
//	  			}
//	  		}
//	  		catch(IllegalArgumentException e) {
//	  			System.out.println("catch---entered");
//	  			//return new ResponseEntity<ErrorHandler>(new ErrorHandler("Not ceated error"),HttpStatus.INTERNAL_SERVER_ERROR);
//	  			return new ResponseEntity<ErrorHandler>(HttpStatus.ACCEPTED);
//	  		}
//	  		
//	  	}


	  		
//	@GetMapping("/users/{id}")
//	public ResponseEntity<List<Person>> getById(@PathVariable String id){
//		
//		ResponseEntity<List<Person>> result = new ResponseEntity<List<Person>>(personRepo.retriveById(id),HttpStatus.FOUND);
//		if(result.equals(null)) {
//			throw new ResponseEntityExceptionHandler() {
//		};
//		}
//	return result;
//		
//	}
		
	  	
//	  	@PostMapping("/user")
//	  	public ResponseEntity<?> createById(@RequestBody Person p){
//	  		String s = personRepo.create(p);
//	  		List<Person> dup = personRepo.create(p);
//	  		
//	  		
//	  			ResponseEntity<?> out = new ResponseEntity<String>(s,HttpStatus.CREATED );
//				return out;
//	  		}
	  	
	  	
	  		
		
	   



