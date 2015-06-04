/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import IDE.SyntaxTree.ASyntaxTree;
import IDE.SyntaxTree.ASyntaxTree.STNode;
import IDE.SyntaxTree.JWebParser;
import IDE.autocomplite.AutocompliteContextFrame;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;
import javax.swing.text.Utilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import sun.nio.ch.DirectBuffer;

/**
 *
 * @author Aleksey
 */
public class JWebIDE extends javax.swing.JFrame {
    private static Object JHTMLUtils;
    protected Highlighter painter;
        TextLineNumber textLineNumber;
        LinePainter linePainter;
        boolean DebugMode = false;
        
        Pattern multiLineComment = Pattern.compile("\\/\\*[\\s\\S]*?\\*\\/",Pattern.UNICODE_CASE  );
        Pattern singleLineComment = Pattern.compile("(?:^|[^\\\\])\\/\\/.*$");
        Pattern quotedStrings = Pattern.compile("\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
        Pattern strings = Pattern.compile("'[^'\\\\]*(?:\\\\.[^'\\\\]*)*'|\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
        Pattern fields = Pattern.compile("\\.([\\w]+)\\s*");
        Pattern methodCall = Pattern.compile("\\.([\\w]+)\\s*\\(");
        Pattern functionCall = Pattern.compile("\\b([\\w]+)\\s*\\(");
        Pattern brackets = Pattern.compile("\\{|\\}|\\(|\\)|\\[|\\]");
        Pattern numbers = Pattern.compile("\\b((?:(\\d+)?\\.)?[0-9]+|0x[0-9A-F]+)\\b");
        AutocompliteContextFrame autoCompliteFrame;
       JWebParser jwebNashornParser = new JWebParser();


         StyledDocument doc = null;
        Document document = null;
    /**
     * Creates new form JWebIDE
     */
    public JWebIDE() {
        

        
        initComponents();
        textLineNumber = new TextLineNumber(jTextPane1);
        linePainter = new LinePainter(jTextPane1);
        this.jScrollPane3.setRowHeaderView(textLineNumber);
        // autoTextComplete = new  AutoTextComplete(jTextPane1);
        autoCompliteFrame = new AutocompliteContextFrame(jTextPane1);
        painter = new DefaultHighlighter();
       
                
        doc = jTextPane1.getStyledDocument();
        setTabs(jTextPane1,3);
        document = jTextPane1.getDocument();
        document.addDocumentListener(new EditorChangeListener(doc, jLabel1));
        linePainter.setLighter(new Color(176,197,227));     
    }

     public void setTabs( JTextPane textPane, int charactersPerTab)
     {
          FontMetrics fm = textPane.getFontMetrics( textPane.getFont() );
          int charWidth = fm.charWidth( 'w' );
          int tabWidth = charWidth * charactersPerTab;

          TabStop[] tabs = new TabStop[10];

          for (int j = 0; j < tabs.length; j++)
          {
               int tab = j + 1;
               tabs[j] = new TabStop( tab * tabWidth );
          }

          TabSet tabSet = new TabSet(tabs);
          SimpleAttributeSet attributes = new SimpleAttributeSet();
          StyleConstants.setTabSet(attributes, tabSet);
          int length = textPane.getDocument().getLength();
          textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, true);
     }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jScrollPane1.setViewportView(jTree1);

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane3.setDoubleBuffered(true);

        jTextPane1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                jTextPane1CaretUpdate(evt);
            }
        });
        jTextPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTextPane1MouseMoved(evt);
            }
        });
        jTextPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextPane1MouseClicked(evt);
            }
        });
        jTextPane1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTextPane1CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jTextPane1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextPane1KeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTextPane1);

        jLabel1.setText("0");

        jToolBar1.setRollover(true);

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(173, 173, 173)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 229, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addContainerGap())))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            jTextPane1.setText(readServerFile(new File("e:\\Projects\\JWeb\\www\\Controller\\indexController.jap")));
                   updateTree();

        } catch (IOException ex) {
            Logger.getLogger(JWebIDE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

   
         
        if(!DebugMode){
        
             this.linePainter.setLighter(new Color(255,168,168));
             DebugMode = true;
        } else{
                  linePainter.setLighter(new Color(176,197,227));  
                  DebugMode = false;
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void updateTree(){
        
 
               String srcText = jTextPane1.getText();
                if(!srcText.equals("")){
                    
                     DefaultMutableTreeNode objectTree = jwebNashornParser.createObjectTree(srcText);                                       
                     DefaultTreeModel treeModel = new DefaultTreeModel(objectTree);      
                     jTree1.setVisible(false);
                     jTree1.setModel(treeModel);
                     jTree1.revalidate();
                     jTree1.repaint();
                     ((DefaultTreeModel) jTree1.getModel()).reload();
                      jTree1.setVisible(true);
                  
                }
                   
    }
    
    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
        
                   updateTree();
           
       
                if(((evt.getKeyCode() == KeyEvent.VK_DOWN)||
                    (evt.getKeyCode() == KeyEvent.VK_UP)||
                    (evt.getKeyCode() == KeyEvent.VK_ESCAPE)||
                    (evt.getKeyCode() == KeyEvent.VK_ENTER))&& autoCompliteFrame.isVisible()){             
                    evt.consume();
                    return;
                }
                
        if( (evt.getKeyChar()+"").matches("[\\n\\s]")!=true){
            try {

                
                int pos = jTextPane1.getCaretPosition();
                if(pos==0) {
                   // evt.consume();
                    return;
                };
                int wordStart = Utilities.getPreviousWord(jTextPane1,pos );
                String key = jTextPane1.getText().substring(wordStart,jTextPane1.getCaretPosition());
                
                
                
                String [] res = null;
                if(key.length()>0){
                    res= JavaScriptLexer.findKeywords(key);
                }
                
                Boolean isRes = false;
                if(res!=null && res.length > 0){
                    autoCompliteFrame.SetListData(res);
                    isRes = true;
                } else if(evt.getKeyChar() == '.'){
                    
                    autoCompliteFrame.SetListData(JavaScriptLexer.getKeywords());
                    isRes = true;
                }else {
                    autoCompliteFrame.setVisible(false);
                }
                
                if(isRes){
                    Rectangle r  = jTextPane1.modelToView( wordStart);
                    Rectangle r2 =  this.getBounds();
                    Rectangle r3 =  this.jScrollPane3.getBounds();
                    autoCompliteFrame.setVisible(false);
                    autoCompliteFrame.setFocusable(true);
                    autoCompliteFrame.requestFocusInWindow();
                    autoCompliteFrame.setBounds(r.x+r2.x+r3.x+30, r.y+r2.y+r3.y+50, 230, 150);                
                    autoCompliteFrame.setVisible(true);
                    autoCompliteFrame.initTableSelection();
                    
                    // autoCompliteFrame.setAlwaysOnTop(true);
                  //  evt.consume();
                }
                
            } catch (BadLocationException ex) {
                Logger.getLogger(JWebIDE.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        
    }//GEN-LAST:event_jTextPane1KeyPressed

    private void jTextPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPane1MouseClicked
        // TODO add your handling code here:
           
                        autoCompliteFrame.setFocusable(false);
                        autoCompliteFrame.setVisible(false);
    }//GEN-LAST:event_jTextPane1MouseClicked

    private void jTextPane1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextPane1CaretPositionChanged
        // TODO add your handling code here:
         
   
        
    }//GEN-LAST:event_jTextPane1CaretPositionChanged

    private void jTextPane1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_jTextPane1CaretUpdate
          
        Element root = jTextPane1.getDocument().getDefaultRootElement();
          int root2 =root.getElementIndex(evt.getDot());
           BranchElement el =  (BranchElement) root.getElement(root2);
          
          
           this.jLabel2.setText(""+(root2+1));
           
    }//GEN-LAST:event_jTextPane1CaretUpdate

    private void jTextPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextPane1MouseMoved
        
      
        
       
        try {
            int viewToModel = jTextPane1.viewToModel(evt.getPoint());
            
          //  jTextPane1.setCaretPosition(viewToModel);
            
            String txt =  jTextPane1.getText();
            int start = Utilities.getWordStart(jTextPane1, viewToModel);
            int end = Utilities.getWordEnd(jTextPane1, viewToModel);
            jLabel3.setText(""+viewToModel);
           // jLabel3.setText(txt.substring(start, end));
            
        } catch (BadLocationException ex) {
            Logger.getLogger(JWebIDE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }//GEN-LAST:event_jTextPane1MouseMoved
    public static String readServerFile(File file) throws FileNotFoundException, IOException {
        
      FileInputStream inputStream = new FileInputStream(file);
      final FileChannel inChannel = inputStream.getChannel();   
      
      String tmpContent = "";
      
                MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0 ,inChannel.size());
                buffer.load();        
                byte [] bb = new byte[new Long(inChannel.size()).intValue()];                
                buffer.get(bb);
                tmpContent = new String(bb);                           
               ((DirectBuffer) buffer).cleaner().clean();  
                
        return tmpContent;
            
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JWebIDE.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JWebIDE().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
