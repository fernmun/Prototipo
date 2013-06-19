package edu.view;

import edu.api.gui.UIBuilder;
import edu.logic.InboxTools;
import edu.logic.Document;
import edu.logic.gui.ButtonHandler;
import edu.logic.gui.Mediator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayDeque;
import java.util.Iterator;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

/**
 *
 * @author David Camilo Nova
 * @author Luis Fernando Muñoz
 */
public class InboxUIBuilder extends UIBuilder{

    private JTabbedPane jtpDocuments;
    private JLabel lblInbox, lblReadbox;
    private JTable tblInbox, tblReadbox;
    private ArrayDeque<Document> inDocuments;
    private ButtonHandler buttonHandler = new ButtonHandler();

    /**
     *
     * @param mediator
     */
    public InboxUIBuilder(Mediator mediator){
        super(mediator);
        mediator.registerInboxUIBuilder(this);
    } 
    
    /**
     *
     */
    @Override
    public void addUIControls() {
        panelUI = new JPanel(new BorderLayout(20, 20));
        
        jtpDocuments = new JTabbedPane();
        jtpDocuments.setTabPlacement(JTabbedPane.TOP);
        
        JPanel pnlInbox = buildInbox();
        JPanel pnlReadbox = buildReadbox();
        
        jtpDocuments.addTab("Recibidos", pnlInbox);
        jtpDocuments.addTab("Leídos", pnlReadbox);
        
        panelUI.add(new JPanel(), BorderLayout.NORTH);
        panelUI.add(new JPanel(), BorderLayout.WEST);
        panelUI.add(new JPanel(), BorderLayout.EAST);
        panelUI.add(new JPanel(), BorderLayout.SOUTH);
        panelUI.add(jtpDocuments, BorderLayout.CENTER);
    }

    /**
     *
     */
    @Override
    public void initialize() {
        System.out.println("Not supported yet.");
    }
    
    private JPanel buildInbox(){
        JPanel pnlInbox = new JPanel(new BorderLayout(10, 10));
        
        lblInbox = new JLabel("Documentos Recibidos:");
        
        InboxTools boxTools = new InboxTools();
        inDocuments= boxTools.getInboxDocuments();
               
        String[] columnNames = {" ", "Nombre", "Fecha"};
        Object[][] data1 = new Object[inDocuments.size()][];
        int i = 0;
        for (Iterator<Document> it = inDocuments.iterator(); it.hasNext();) {
            Document document = it.next();
            Object[] doc = {new Boolean(false),document.getName(),document.getUpdated()};
            data1[i] = doc;
            i++;
        }
        
        Object[][] data = {
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"},
            {new Boolean(false), "Documento 1", "1 Ago/2012"}
        };
        
//        tblInbox = new JTable(data, columnNames);
        int[] editableCols={0};
        tblInbox = new JTable(new DocumentListTableModel(data1, columnNames,editableCols));
        tblInbox.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tblInbox.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tblInbox);
        
        ReadButton btnRead = new ReadButton("Marcar como leído");
        DownloadDocumentButton btnDownload = new DownloadDocumentButton("Descargar", mediatorUI);
        btnDownload.addActionListener(buttonHandler);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        pnlButtons.add(btnRead);
        pnlButtons.add(btnDownload);
        
        pnlInbox.add(lblInbox, BorderLayout.NORTH);
        pnlInbox.add(scrollPane, BorderLayout.CENTER);
        pnlInbox.add(pnlButtons, BorderLayout.SOUTH);
        
        return pnlInbox;
    }
    
    private JPanel buildReadbox(){
        JPanel pnlReadbox = new JPanel(new BorderLayout(10, 10));
        
        lblReadbox = new JLabel("Documentos Recibidos:");
        
        String[] columnNames = {" ", "Nombre", "Fecha"};
        Object[][] data = {
            {new Boolean(false), "Documento 2", "1 Jul/2012"},
            {new Boolean(false), "Documento 2", "1 Jul/2012"},
            {new Boolean(false), "Documento 2", "1 Jul/2012"},
            {new Boolean(false), "Documento 2", "1 Jul/2012"}
        };
        
//        tblInbox = new JTable(data, columnNames);
        int[] editableCols={0};
        tblReadbox = new JTable(new DocumentListTableModel(data, columnNames,editableCols));
        tblReadbox.setPreferredScrollableViewportSize(new Dimension(500, 70));
        tblReadbox.setFillsViewportHeight(true);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(tblReadbox);
        
        UnreadButton btnUnread = new UnreadButton("Marcar como no leído");
        DownloadDocumentButton btnDownload = new DownloadDocumentButton("Descargar", mediatorUI);
        
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
        pnlButtons.add(btnUnread);
        pnlButtons.add(btnDownload);
        
        
        pnlReadbox.add(lblReadbox, BorderLayout.NORTH);
        pnlReadbox.add(scrollPane, BorderLayout.CENTER);
        pnlReadbox.add(pnlButtons, BorderLayout.SOUTH);
        
        return pnlReadbox;
    }
    
    public ArrayDeque<Document> getSelectedInboxDocuments(){
        
        ArrayDeque<Document> selectedInboxDocuments = new ArrayDeque<Document>();
        int i=0;
        for (Iterator<Document> it = inDocuments.iterator(); it.hasNext();) {
            Document document = it.next();
             if(tblInbox.getValueAt(i, 0).equals(new Boolean(true))){
                selectedInboxDocuments.add(document);
            }
            i++;
        }
        return selectedInboxDocuments;
    }
    
}
