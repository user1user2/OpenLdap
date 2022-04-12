package com.openldap.ldap.service;
import static org.springframework.ldap.query.LdapQueryBuilder.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InvalidAttributesException;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.openldap.ldap.constants.Constent;
import com.openldap.ldap.entity.Person;
//import com.openldap.ldap.error.InvalidAttributeException;
import com.openldap.ldap.error.InvalidAttributeValueException;




	
@Service
public class PersonRepoImpl  implements MethodsRepo {
	    public static final String base = Constent.BASEDN;
	    @Autowired
	    private LdapTemplate ldapTemplate;
	    
	    
	    
	    
	    public List<Person> retrieve() {
	    	
	        SearchControls searchControls = new SearchControls();
	        
	        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        	        
	        List<Person> people =  ldapTemplate.search(query().where("objectclass").is("person"),
	                new PersonAttributeMapper());	        
	        return people;
	    }
	    
	    
	    
	    
	    @Override
	    public List<Person> create(Person p)  {
	    	
	    	//System.out.println("Entrting to impl");
	    	
	    	List<Person> flag = new ArrayList<>();
	    	
	    	SearchControls search = new SearchControls();
	    	
	    	search.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    	
	    	List<Person> res =
	    			ldapTemplate.search(query().where(Constent.UID).is(p.getUserId()),new PersonAttributeMapper());
	    	int len = res.size();
	    	
	    	if(len >0) {
	    		return flag;
	    	}
	    	
	        Name dn = buildDn(p.getUserId());	       	        
	        ldapTemplate.bind(dn, null, buildAttributes(p));
	        flag.add(p);
	        return flag;	       	        
	    }
	    	    
	    @Override
	    public List<Person> update(Person p) {
	    	SearchControls c = new SearchControls();
	    	c.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    	List<Person> flag = ldapTemplate.search(query().where("userId").is(p.getUserId().toString()), new PersonAttributeMapper());
	    	if(flag.size()>0) {
	    		
	        Name dn = buildDn(p.getUserId());
	        ldapTemplate.rebind(dn, null, buildAttributes(p));
	        flag.add(p);
	        return flag;
	    	}
	    	return flag;
	    	
	    }
	    
	    @Override
	    public List<Person> remove(String userId) {
	    	
	    	SearchControls c = new SearchControls();
	    	c.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    	List<Person> flag = ldapTemplate.search(query().where("userId").is(userId.toString()), new PersonAttributeMapper());
	    	if(flag.size()>0) {
	        Name dn = buildDn(userId);
	        // ldapTemplate.unbind(dn, true); //Remove recursively all entries
	        ldapTemplate.unbind(dn);
	    	
	        return flag;
	    	}
	    	return flag ;
	    }
	    
	    @Override
	    public String updateAttributes(Person p) {
	        Name dn = buildDn(p);
	        Attribute attr = new BasicAttribute("description", p.getDescription());
	        ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
	        ldapTemplate.modifyAttributes(dn, new ModificationItem[] {item});
	        return "updated attr";
	     }
	    @Override
		public List<Object> getByName(String name) {
	    	
	    	LdapQuery quer = query().attributes(Constent.mail,Constent.HPA,Constent.displayName).where("objectclass").is("inetOrgPerson").and(Constent.displayName).is(name);
			return ldapTemplate.search(quer, new AttributesMapper<Object>() {
				@Override
				public Object mapFromAttributes(Attributes att) throws NamingException {
					// TODO Auto-generated method stub
					List<Object> out = new ArrayList<>();
					out.add(att.get("mail"));
					out.add(att.get(Constent.HPA));
					//out.add(Constent.mail);
					//return att.get(Constent.mail).get().toString();
					for(int i=0;i<out.size();i++) {
						System.out.println(out.get(i));
						return att.get(out.get(i).toString());
						
					}
					return out;
				}
			});
			//return null;
		}
	    
	    
	   
	    
	    
	    
//	    for(int i=0;i<list;i++) {
//	    }
	   
	    private Attributes buildAttributes(Person p) {
	        BasicAttribute ocattr = new BasicAttribute("objectclass");
	        ocattr.add("top");
	        ocattr.add("person");
	        ocattr.add("inetorgPerson");
	        Attributes attrs = new BasicAttributes();
	        
	        attrs.put(ocattr);
	       
//	        List<Object> at = new ArrayList<>();
//	        at.add("uid");
//	        at.add("sn");
//	        at.add("cn");
//	        at.add("description");
//	        
//	        List<Object> pr = new ArrayList<>();
//	        pr.add(p.getUserId());
//	        pr.add(p.getLastName());
//	        pr.add(p.getFullName());
//	        pr.add(p.getDescription());
//	        
//	        for(int i=0;i<at.size();i++) {
//	        	
//	        	attrs.put(at.get(i).toString(),at.get(i));
//	        	
//	        	
//	        }
           
	        attrs.put(Constent.UID, p.getUserId());
	        attrs.put(Constent.CN, p.getFullName());	       
	        attrs.put(Constent.SN, p.getLastName());
	        attrs.put(Constent.DES, p.getDescription());
	        attrs.put(Constent.MOB, p.getMobno());
	        attrs.put(Constent.PASSD,p.getUserpasswd());
	        
	        attrs.put(Constent.displayName,p.getDiaplayName());
	        attrs.put(Constent.mail,p.getMail());
	        attrs.put(Constent.BC,p.getBusinessCategory());
	        attrs.put(Constent.DN,p.getDepartmentnumber());
	        attrs.put(Constent.empNo,p.getEmployeeNumber());
	        attrs.put(Constent.ET,p.getEmployeetype());
	        attrs.put(Constent.HP,p.getHomephone());
	        attrs.put(Constent.HPA,p.getHomePostalAddress());
	        attrs.put(Constent.IT,p.getInitials());
	        //attrs.put(Constent.MAN,p.getManager());
	        
	        attrs.put(Constent.RN,p.getRoomnumber());
	        return attrs;
	        
	    }
	    public Name buildDn(String userId) {
	    	try {
	        Name outDN = LdapNameBuilder.newInstance() .add("ou", "people").add(Constent.UID, userId).build();
	        System.out.println(outDN);
			return outDN;
	    	}
	    	catch(IllegalArgumentException e) {
	    		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"ID not to be null");
	    		//throw new ResponseStatusException(null, userId)
	    	}
	        
	    }
	    
	    public Name buildDn(Person p) {
	    	DistinguishedName  dn = new DistinguishedName(Constent.BASEDN);
	    	dn.add("ou",p.getBusinessCategory());
	    	dn.add("uid", p.getUserId());
	    	System.out.println(dn);
	    	return dn;
	    }
	    
	    
	   
	   
	    
	    
	    
	    
	    
	    
	    private class PersonAttributeMapper implements AttributesMapper<Person> {
	        @Override
	        public Person mapFromAttributes(Attributes attributes) throws NamingException {
	            Person person = new Person();
	            person.setUserId(null != attributes.get(Constent.UID) ? attributes.get(Constent.UID).get().toString() : null);
	            person.setFullName(null != attributes.get(Constent.CN) ? attributes.get(Constent.CN).get().toString() : null);
	            person.setLastName(null != attributes.get(Constent.SN) ? attributes.get(Constent.SN).get().toString() : null);
	            person.setDescription(null != attributes.get(Constent.DES) ? attributes.get(Constent.DES).get().toString() : null);
	          
	            person.setMobno(null != attributes.get(Constent.MOB) ? attributes.get(Constent.MOB).get().toString() : null);
	            person.setUserpasswd(null != attributes.get(Constent.PASSD) ? attributes.get(Constent.PASSD).get().toString() : null);
	           
	            
	            person.setDiaplayName(null != attributes.get(Constent.displayName) ? attributes.get(Constent.displayName).get().toString() : null);
	            person.setMail(null != attributes.get(Constent.mail) ? attributes.get(Constent.mail).get().toString() : null);
	            person.setBusinessCategory(null != attributes.get(Constent.BC) ? attributes.get(Constent.BC).get().toString() : null);
	            person.setDepartmentnumber(null != attributes.get(Constent.DN) ? attributes.get(Constent.DN).get().toString() : null);
	          
	            person.setEmployeeNumber(null != attributes.get(Constent.empNo) ? attributes.get(Constent.empNo).get().toString() : null);
	            person.setEmployeetype(null != attributes.get(Constent.ET) ? attributes.get(Constent.ET).get().toString() : null);
	           
	            
	            person.setHomephone(null != attributes.get(Constent.HP) ? attributes.get(Constent.HP).get().toString() : null);
	            person.setHomePostalAddress(null != attributes.get(Constent.HPA) ? attributes.get(Constent.HPA).get().toString() : null);
	            person.setInitials(null != attributes.get(Constent.IT) ? attributes.get(Constent.IT).get().toString() : null);
	            //person.setManager(null != attributes.get(Constent.MAN) ? attributes.get(Constent.MAN).get().toString() : null);
	          
	           
	            person.setRoomnumber(null != attributes.get(Constent.RN) ? attributes.get(Constent.RN).get().toString() : null);
	           
	            return person;
	        }

}
		@Override
		public List<Person> retriveById(String id) {
			
//			System.out.println(conste.valueOf("uid"));
//			System.out.println(conste.uid);
			
			
			SearchControls sc = new SearchControls();
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
			List<Person> p = ldapTemplate.search(query().where(Constent.UID).is(id), new PersonAttributeMapper());
			System.out.println(p);
			return p;
		}
		
		
		
		@Override
		public List<Person> getUserByFilters(Map<String, String> q) {
			
		   
		   
		   SearchControls s = new SearchControls();
		   
		   s.setSearchScope(SearchControls.SUBTREE_SCOPE);
		   
		   
		   List<Person> ou = ldapTemplate.search(query().where(Constent.UID).is(q.get(Constent.UID)).and(Constent.CN).is(q.get(Constent.CN)), new PersonAttributeMapper());
		   
		  
		   return ou;
		}


//		@Override
//		public List<String> getByName(String name) {
//			// TODO Auto-generated method stub
//			return null;
//		}
	}

