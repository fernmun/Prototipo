/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.view;

import edu.api.UIBuilder;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import  javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author lmparra
 */
public class FrameClient extends JFrame{
  
  private int width, height, posX, posY;
  private String title;
  private BuilderFactory factory;
  private UIDirector director;
  private TabsClient tbcTabs;
  private FindDocumentButton btnFindDocument;
  private JLabel lblUserName, lblUser, lblUserRoll, lblUserImage;
  private JPanel pnlUserProfile, pnlUserText, pnlFrame, pnlContent;
          
  public FrameClient(int width, int height, String title, int posX, int posY, String type) {
    // Creación de componentes
////    btnFindDocument = new FindDocumentButton();
    
    // Set all needed properties
    this.setBounds(posX, posY, width, height);
    this.setTitle(title);
//    factory = new BuilderFactory();
//    director = new UIDirector(factory.getBuilder(type));
     
    //User Profile
    lblUser = new JLabel("Usuario: ");
    lblUserName = new JLabel("Nombre: ");
    lblUserRoll = new JLabel("Perfil: ");
    ImageIcon icon = null;
    try {
        icon = new ImageIcon( new ImageIcon(new URL("http://static.elespectador.co/files/imagecache/560x373/imagenprincipal/591698ec135b7c7f1bd03348d65a7c28.jpg")).getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH));
    } catch (MalformedURLException ex) {
        Logger.getLogger(FrameClient.class.getName()).log(Level.SEVERE, null, ex);
    }
    lblUserImage = new JLabel(icon);
    pnlUserText = new JPanel(new GridLayout(3,1,5,10));
    pnlUserText.add(lblUser);
    pnlUserText.add(lblUserName);
    pnlUserText.add(lblUserRoll);
    
    pnlUserProfile = new JPanel(new BorderLayout(15, 5));
    
    pnlUserProfile.add(lblUserImage, BorderLayout.WEST);
    pnlUserProfile.add(pnlUserText, BorderLayout.CENTER);
    
    //Tabs
    tbcTabs = new TabsClient();
    tbcTabs.setTabPlacement(JTabbedPane.TOP);
    
    
    
    UIBuilder SignBuilder = new SignUIBilder();
    
    director = new UIDirector(SignBuilder);
    director.build();
    
    JPanel pnlSign = SignBuilder.getPanel();
    tbcTabs.addTab("Firmar", pnlSign);
    UIBuilder sendBuilder = new SendUIBuilder();
    
    director = new UIDirector(sendBuilder);
    director.build();
    
    JPanel pnlSend = sendBuilder.getPanel();
    tbcTabs.addTab("Enviar",pnlSend );
    
    UIBuilder documentBuilder = new InboxUIBuilder();
    
    director = new UIDirector(documentBuilder);
    director.build();
    
    JPanel pnlInbox = documentBuilder.getPanel();
    tbcTabs.addTab("Documentos", pnlInbox);

    pnlContent = new JPanel(new BorderLayout(20, 20));
    pnlContent.add(pnlUserProfile, BorderLayout.NORTH);
    pnlContent.add(tbcTabs, BorderLayout.CENTER);
    
    pnlFrame = new JPanel(new BorderLayout(20, 20));
    pnlFrame.add(pnlContent, BorderLayout.CENTER);
    pnlFrame.add(new JPanel(), BorderLayout.WEST);
    pnlFrame.add(new JPanel(), BorderLayout.EAST);
    pnlFrame.add(new JPanel(), BorderLayout.NORTH);
    pnlFrame.add(new JPanel(), BorderLayout.SOUTH);
    
    this.add(pnlFrame);
    this.setVisible(true);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
}
