package com.lx.agent.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author hubery.chen
 */
@Entity
@Table(name = "User")
public class User {
    @GeneratedValue
    @Id
    @Column(name = "Id")
    private int id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Password")
    private String password;

    @Column(name = "EmailAddress")
    private String emailAddress;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Address")
    private String address;

    @Column(name = "ZipCode")
    private String zipCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "Permission")
    private Permission permission = Permission.Customer;

    public enum Permission {
        Customer, Admin
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}