/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 *
 * @author lmparra
 */
public class Document {
    
  private static String nombre, ruta, msjExitoso, msjFallido;
  private byte[] buf, bytes;
  
  static {
    msjExitoso = "Se ha generado el archivo correctamente";
    msjFallido = "No se ha podido crear el archivo";
  } 
  
  public Document(String nombreArchivo, String rutaArchivo) {
    
    nombre = nombreArchivo;
    ruta = rutaArchivo;
    
  }
  
  /**
   * Recibe un arreglo de bytes y genera el archivo en una ruta dada
   * @param data Arreglo de bytes
   * @return Mensaje si fue exitoso o no la operación
   */
  public String writeFile(byte[] data) {
  
    if(data != null){     
      try {
        File someFile = new File(ruta + nombre);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(data);
        fos.flush();
        fos.close();
        return msjExitoso;
      } catch (IOException ex) {
        Logger.getLogger(ex.getMessage());
      }
		}
    
    return msjFallido;
    
  }
  
  /**
   * Lee un archivo con un nombre y ruta dada
   * @return Arreglo de bytes
   * @throws FileNotFoundException 
   */
  public byte[] readFile() throws FileNotFoundException {
    
    File file = new File(ruta + nombre);
    FileInputStream fis = new FileInputStream(file);
    
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    buf = new byte[1024];
    try {
      for (int readNum; (readNum = fis.read(buf)) != -1;) {
        bos.write(buf, 0, readNum);
      }
    } catch (IOException ex) {

    }
    bytes = bos.toByteArray();
    
    return bytes;
  }
  
}

