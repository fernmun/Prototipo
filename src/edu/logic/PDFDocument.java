/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.logic;

import java.security.cert.Certificate;
import java.util.Date;

/**
 *
 * @author DavidCamilo
 */
public class PDFDocument extends SignDocument{

    public PDFDocument(int idDocument, String name, String uri, Date created, Date updated) {
        super(idDocument, name, uri, created, updated);
    }

    @Override
    public boolean isSigned() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Certificate getSignatureCertificate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
