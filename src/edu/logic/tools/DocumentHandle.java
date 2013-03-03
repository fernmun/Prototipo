package edu.logic.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * <code>DocumentHandle</code> class allow to manage files in easy way. It creates
 * and reads files regardless kind of file due it handles all files like vector bytes.
 * 
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */ 
public class DocumentHandle {
    
    private static String name, path, successMessage, errorMessage;
    private byte[] buffer, bytes;

    static {
        successMessage = "The File has been created successfully";
        errorMessage = "The File has not been created successfully";
    } 

    /**
     * 
     * <code>DocumentHandle</code> constructor.
     * 
     * @param fileName
     *        {@link String} File name
     * @param filePath
     *        {@link String} Whole file path
     */
    public DocumentHandle(String fileName, String filePath) {

      name = fileName;
      path = filePath;

    }

    /**
     * 
     * Create a file given a bytes array and specfic path file.
     * 
     * @param data
     *        {@link byte} File in array bytes representation
     * @return {@link String} Success message or error after creating file
     */
    public String writeFile(byte[] data) {

      if(data != null){     
        try {
          File someFile = new File(path + name);
          FileOutputStream fos = new FileOutputStream(someFile);
          fos.write(data);
          fos.flush();
          fos.close();
          return successMessage;
        } catch (IOException ex) {
          Logger.getLogger(ex.getMessage());
        }
                  }

      return errorMessage;

    }

    /**
     * 
     * Read a file given a file name and path and returns it in a byte array.
     * 
     * @return {@link byte} File in a byte array
     * @throws FileNotFoundException 
     */
    public byte[] readFile() throws FileNotFoundException {

      File file = new File(path + name);
      FileInputStream fis = new FileInputStream(file);

      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      buffer = new byte[1024];
      try {
        for (int readNum; (readNum = fis.read(buffer)) != -1;) {
          bos.write(buffer, 0, readNum);
        }
      } catch (IOException ex) {

      }
      bytes = bos.toByteArray();

      return bytes;
    }
  
}

