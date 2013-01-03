/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author DavidCamilo
 */
public class ZipTools {
 
    public String compressFiles(Vector<String> files, String zipFile){
        byte[] buffer = new byte[1024];

        try{
            File tmp = new File(zipFile);
            if(tmp.exists()){
                zipFile = zipFile.substring(0, zipFile.lastIndexOf("."))+"_1.zip";
                return compressFiles(files, zipFile);
            }
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);



            for(String file : files){

                    tmp = new File(file);
                    ZipEntry ze= new ZipEntry(tmp.getName());
                    zos.putNextEntry(ze);

                    FileInputStream in = 
                           new FileInputStream(file);

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                    }

                    in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            return zipFile;
        }catch(IOException ex){
            ex.printStackTrace(); 
            return "";
        }
    }
    
    public static void main( String[] args )
    {
    }
 
}
