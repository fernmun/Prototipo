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
    private String uid;
    private String userName;
    private String firstName;
    private String lastName;
    private String distinguishedName;
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
    
}
