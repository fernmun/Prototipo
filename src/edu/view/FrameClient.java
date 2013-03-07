package edu.view;

import edu.api.gui.UIBuilder;
import edu.logic.User;
import edu.logic.gui.Mediator;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class FrameClient extends JFrame{
  
  private int width, height, posX, posY;
  private String title;
  private UIDirector director;
  private TabsClient tbcTabs;
  private JLabel lblUserName, lblUser, lblUserRoll, lblUserImage;
  private JPanel pnlUserProfile, pnlUserText, pnlFrame, pnlContent;
  private Mediator mediator = new Mediator();
  private User user;
          
    /**
     *
     * @param width
     * @param height
     * @param title
     * @param posX
     * @param posY
     * @param user
     */
    public FrameClient(int width, int height, String title, int posX, int posY, User user) {
    // Creación de componentes
////    btnFindDocument = new FindDocumentButton();
    
    // Set all needed properties
    this.posX = posX;
    this.posY = posY;
    this.title = title;
    this.user = user;
    
    this.setBounds(posX, posY, width, height);
    this.setTitle(title);
    mediator.registerFrameClient(this);
//    factory = new BuilderFactory();
//    director = new UIDirector(factory.getBuilder(type));
     
    //User Profile
    lblUser = new JLabel("Usuario: "+user.getUserName());
    lblUserName = new JLabel("Nombre: "+user.getFirstName()+" "+user.getLastName());
    lblUserRoll = new JLabel("Perfil: "+user.getProfileName());
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
    
    
    
    UIBuilder SignBuilder = new SignUIBuilder(mediator);
    
    director = new UIDirector(SignBuilder);
    director.build();
    
    JPanel pnlSign = SignBuilder.getPanel();
    tbcTabs.addTab("Firmar", pnlSign);
    UIBuilder sendBuilder = new SendUIBuilder(mediator);
    
    director = new UIDirector(sendBuilder);
    director.build();
    
    JPanel pnlSend = sendBuilder.getPanel();
    tbcTabs.addTab("Enviar",pnlSend );
    
    UIBuilder documentBuilder = new InboxUIBuilder(mediator);
    
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
