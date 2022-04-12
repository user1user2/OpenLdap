package com.openldap.ldap.entity;



import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

public class Person{
	    @NotEmpty
	    private String userId;
	    private String fullName;
	    private String lastName;
	    private String description;
	    private String mobno;
	    @NotEmpty
	    private String userpasswd;
	    private String mail;
	    private String departmentnumber;
	    private String employeetype;
	   // private String manager;
	    private String roomnumber;
	    private String employeeNumber;
	    private String preferredlanguage;
	    private String businessCategory;
	    private String homephone;
	    private String homePostalAddress;
	    private String givenName;
	    private String diaplayName;
	    private String initials;
	    
		public String getInitials() {
			return initials;
		}
		public void setInitials(String initials) {
			this.initials = initials;
		}
		public String getDiaplayName() {
			return diaplayName;
		}
		public void setDiaplayName(String diaplayName) {
			this.diaplayName = diaplayName;
		}
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getFullName() {
			return fullName;
		}
		public void setFullName(String fullName) {
			this.fullName = fullName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getMobno() {
			return mobno;
		}
		public void setMobno(String mobno) {
			this.mobno = mobno;
		}
		public String getUserpasswd() {
			return userpasswd;
		}
		public void setUserpasswd(String userpasswd) {
			this.userpasswd = userpasswd;
		}
		public String getMail() {
			return mail;
		}
		public void setMail(String mail) {
			this.mail = mail;
		}
		public String getDepartmentnumber() {
			return departmentnumber;
		}
		public void setDepartmentnumber(String departmentnumber) {
			this.departmentnumber = departmentnumber;
		}
		public String getEmployeetype() {
			return employeetype;
		}
		public void setEmployeetype(String employeetype) {
			this.employeetype = employeetype;
		}
//		public String getManager() {
//			return manager;
//		}
//		public void setManager(String manager) {
//			this.manager = manager;
//		}
		public String getRoomnumber() {
			return roomnumber;
		}
		public void setRoomnumber(String roomnumber) {
			this.roomnumber = roomnumber;
		}
		public String getEmployeeNumber() {
			return employeeNumber;
		}
		public void setEmployeeNumber(String employeeNumber) {
			this.employeeNumber = employeeNumber;
		}
		public String getPreferredlanguage() {
			return preferredlanguage;
		}
		public void setPreferredlanguage(String preferredlanguage) {
			this.preferredlanguage = preferredlanguage;
		}
		public String getBusinessCategory() {
			return businessCategory;
		}
		public void setBusinessCategory(String businessCategory) {
			this.businessCategory = businessCategory;
		}
		public String getHomephone() {
			return homephone;
		}
		public void setHomephone(String homephone) {
			this.homephone = homephone;
		}
		public String getHomePostalAddress() {
			return homePostalAddress;
		}
		public void setHomePostalAddress(String homePostalAddress) {
			this.homePostalAddress = homePostalAddress;
		}
		public String getGivenName() {
			return givenName;
		}
		public void setGivenName(String givenName) {
			this.givenName = givenName;
		}
	    
	    
	    
	    //mail
	    //passwd
	    //depNo
	    

}
