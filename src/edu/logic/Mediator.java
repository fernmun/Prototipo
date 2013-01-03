/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import edu.view.FrameClient;
import java.io.File;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author DavidCamilo
 */
public class Mediator {
    
    
    private FrameClient frameClient;
    private File documentToSign;
    private Signer signer;
    private ZipTools zipTools;
    private String signedPack;
    
    public void registerFrameClient(FrameClient frame){
        frameClient = frame;
    }
    
    public String findDocument(){
        String strPath = "";
        JFileChooser jcfSelectorArchivo = new JFileChooser();
        int iSeleccion = jcfSelectorArchivo.showOpenDialog(frameClient);
        if(iSeleccion == JFileChooser.APPROVE_OPTION){
            documentToSign = jcfSelectorArchivo.getSelectedFile();
            strPath = documentToSign.getPath();
        }
        return strPath;
    }
    
    public boolean signDocument(){
        signer = new Signer();
        Vector files = signer.sign(documentToSign, null);
        String name = documentToSign.getPath();
        name = name.substring(0, name.lastIndexOf(".")+1)+"zip";
        zipTools = new ZipTools();
        signedPack = zipTools.compressFiles(files, name);
        JOptionPane.showMessageDialog(frameClient, "Documento Firmado");
        return !signedPack.isEmpty();
    }
    
    public boolean sendDocument(){
        return false;
    }
    
    public boolean signSendDocument(){
        if(signDocument()){
            return sendDocument();
        }
        else{
            return false;
        }
    }
}
