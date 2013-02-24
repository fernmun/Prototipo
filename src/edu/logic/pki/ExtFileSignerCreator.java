/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic.pki;

import edu.api.Signer;
import edu.api.SignerCreator;

/**
 *
 * @author david
 */
public class ExtFileSignerCreator implements SignerCreator{

    /**
     *
     * @param fileExt
     * @return
     */
    @Override
    public Signer getSigner(String fileExt) {
        
        Signer signer;
        
        if(fileExt.equals("zip")){
            signer = new FileSigner();
        }
        else if(fileExt.equals("pdf")){
            signer = new PDFSigner();
        }
        else{
            signer = new FileSigner();
        }
        
        return signer;
    }
    
}
