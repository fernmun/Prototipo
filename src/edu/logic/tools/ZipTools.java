package edu.logic.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * <code>ZipTools</code> class allow to handle zip files to compress and uncompress
 * in easier way. It has two methods, <code>compressFiles</code> and 
 * <code>uncompressFile</code>.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class ZipTools {
 
    /**
     *
     * Compresses a list of files to generate a single compressed file.
     * 
     * @param files
     *        {@link ArrayDeque} List of all files to compress
     * @param zipFile
     *        {@link String} Whole path of the compressed file
     * @return {@link String}  Whole path of the compressed file if is successfull
     *                         in another way the kind of error ocurred.
     */
    public String compressFiles(ArrayDeque<String> files, String zipFile){
        byte[] buffer = new byte[1024];

        try{
            File tmp = new File(zipFile);
            if(tmp.exists()){
                zipFile = getNewFileName(zipFile);
                return compressFiles(files, zipFile);
            }
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            for(String file : files){

                    tmp = new File(file);
                    ZipEntry ze= new ZipEntry(tmp.getName());
                    zos.putNextEntry(ze);

                    FileInputStream in = new FileInputStream(file);

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
            return "Error compressing file: " + ex.getMessage();
        }
    }
    /**
     *
     * Uncompress a zip file in a specific path given.
     * 
     * @param zipFile
     *        {@link String} Whole path of zip file to uncompress
     * @param path
     *        {@link String} Specific path where file will be uncompressed
     * @return {@link ArrayDeque} List of files extracted of zip file
     */
    public ArrayDeque<File> uncompressFiles(String zipFile, String path){
        
        byte[] buffer = new byte[1024];
        ArrayDeque<File> files = new ArrayDeque<File>();
        File fPath = new File(path);
        if(fPath.exists() || fPath.mkdirs()){
            path = fPath.getAbsolutePath();
            try{
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
                ZipEntry entrada;
                while (null != (entrada=zis.getNextEntry()) ){

                    String filePath = path+"/"+entrada.getName();
                    File fileToSave = new File(filePath);
                    FileOutputStream fos = new FileOutputStream(fileToSave);
                    int leido;
                    while (0<(leido=zis.read(buffer))){
                       fos.write(buffer,0,leido);
                    }
                    fos.close();
                    zis.closeEntry();
                    System.out.println(fileToSave.getAbsolutePath());
                    files.add(fileToSave);
                }
            }catch(IOException ex){
                ex.printStackTrace(); 
            }
        }
        return files;
    }
    /**
     *
     * Uncompress a given zip file.
     * 
     * @param zipFile
     *        {@link String} Whole path of zip file to uncompress
     * @return {@link ArrayDeque} List of files extracted of zip file
     */
    public ArrayDeque<File> uncompressFiles(String zipFile){
        File file = new File(zipFile);
        String path = file.getAbsolutePath();
        path = path.substring(0,path.lastIndexOf("."));
        return uncompressFiles(zipFile,path);
    }
    /**
     * 
     * Returns a new name if the file's name already exists.
     * 
     * @param actualFileName
     *        {@link String} Current name to use
     * @return {@link String} Name that can be used
     */
    private String getNewFileName(String actualFileName){
        String newName;
        int n = actualFileName.lastIndexOf("_")+1;
        int ext = actualFileName.lastIndexOf(".");
        int b = 0;
        if(n>0){
            try{b = new Integer(actualFileName.substring(n, ext))+1;}
            catch(NumberFormatException e){b=-1;}
        }
        if(b>0){  
            newName = actualFileName.substring(0, n)+b+actualFileName.substring(ext);
        }
        else{
            newName = actualFileName.substring(0, ext)+"_1"+actualFileName.substring(ext);
        }
        
        return newName;
    }
    
 
}
