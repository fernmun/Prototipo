/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.util.Vector;

/**
 *
 * @author DavidCamilo
 */
public class User {
    private int uid;
    private String userName;
    private String firstName;
    private String lastName;
    private String distinguishedName;
    private String profileName;
    private Vector availableCertificates;
    private Vector inboxDocuments;

    private static User user = new User();


    private User(){}

    public static User getUser(){
       return user;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException(); 
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public Vector getAvailableCertificates() {
        return availableCertificates;
    }

    public void setAvailableCertificates(Vector availableCertificates) {
        this.availableCertificates = availableCertificates;
    }

    public Vector getInboxDocuments() {
        return inboxDocuments;
    }

    public void setInboxDocuments(Vector inboxDocuments) {
        this.inboxDocuments = inboxDocuments;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    
    
    
}
