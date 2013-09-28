package edu.view;

import edu.logic.User;
import edu.view.login.LoginFrame;
import javax.swing.JFrame;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class Start {

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
    FrameClient frame = new FrameClient(800, 600, "Ventana de prueba", 200, 50, User.getUser());
//    JFrame frame2 = new LoginFrame(350, 250, "Ventana de prueba", 200, 50);
  }

}
