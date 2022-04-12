package com.openldap.ldap.service;

import java.util.List;
import java.util.Map;

import com.openldap.ldap.entity.Person;
import com.openldap.ldap.error.InvalidAttributeValueException;

public interface MethodsRepo {
	  public List<Person> retrieve();
	  public  List<Person> retriveById(String id);
	    public List<Person> create(Person p) ;
	    public List<Person> update(Person p);
	    public List<Person> remove(String userId);
		String  updateAttributes(Person p);
         List<Object> getByName(String name);
         public List<Person> getUserByFilters(Map<String,String> q);
}
