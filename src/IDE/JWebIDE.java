/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import IDE.SyntaxTree.JWebParser;
import IDE.autocomplete.AutocompleteContextFrame;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JToolTip;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument.BranchElement;
import javax.swing.text.BadLocationException;
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
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.apache.felix.framework.FrameworkFactory;
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
        AutocompleteContextFrame autoCompliteFrame;
       JWebParser jwebNashornParser = new JWebParser();
        DefaultMutableTreeNode objectTree =null;
   DefaultTreeCellRenderer renderer =   new DefaultTreeCellRenderer();
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
        autoCompliteFrame = new AutocompleteContextFrame(jTextPane1);
        painter = new DefaultHighlighter();
       
                
        doc = jTextPane1.getStyledDocument();
        setTabs(jTextPane1,3);
        document = jTextPane1.getDocument();
        document.addDocumentListener(new EditorChangeListener(doc, jLabel1));
      linePainter.setLighter(new Color(176,197,227));     
        
        ImageIcon leafIcon = new ImageIcon("E:\\Projects\\JWebIDE\\src\\main\\resources\\images\\stock_function_autopilot.png");
        ImageIcon logo = new ImageIcon("E:\\Projects\\JWebIDE\\src\\main\\resources\\images\\logo.png");
        this.setIconImage(logo.getImage());
        if(leafIcon.getImageLoadStatus() == MediaTracker.COMPLETE)
        {
        UIManager.put("Tree.closedIcon", leafIcon);
        UIManager.put("Tree.openIcon", leafIcon);
        UIManager.put("Tree.leafIcon", leafIcon);
        } else {
          Logger.getGlobal().severe("error "+leafIcon.getImageLoadStatus());
        }
      //  jTree1 = new JTree();
        DefaultTreeCellRenderer dtr = new DefaultTreeCellRenderer();
        dtr.setClosedIcon(leafIcon);
        //dtr.setDisabledIcon(leafIcon);
        dtr.setOpenIcon(leafIcon);
        dtr.setLeafIcon(leafIcon);
       
        jTree1.setCellRenderer(dtr);
        jTree1.setRootVisible(false);
        new FrameworkFactory().newFramework(null).
     
         
    }

    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = JWebIDE.class.getResource(path);                
        return new ImageIcon(imgURL);
    
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

        jMenuItem1 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);

        jButton2.setText("Test");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton1.setText("Load Script");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(150);

        jScrollPane1.setViewportView(jTree1);

        jTabbedPane2.addTab("Objects Tree", jScrollPane1);

        jSplitPane2.setLeftComponent(jTabbedPane2);

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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextPane1KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTextPane1);

        jTabbedPane1.addTab("default", jScrollPane3);

        jSplitPane2.setRightComponent(jTabbedPane1);

        jPanel4.add(jSplitPane2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(797, 100));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        jLabel1.setText("0");
        jPanel1.add(jLabel1, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        pack();
        setLocationRelativeTo(null);
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
  private void expandAll(JTree tree, TreePath parent) {
    TreeNode node = (TreeNode) parent.getLastPathComponent();
    if (node.getChildCount() >= 0) {
      for (Enumeration e = node.children(); e.hasMoreElements();) {
        TreeNode n = (TreeNode) e.nextElement();
        TreePath path = parent.pathByAddingChild(n);
        expandAll(tree, path);
      }
    }
    tree.expandPath(parent);
    // tree.collapsePath(parent);
  }
  
    private void updateTree(){
     
     
               String srcText = jTextPane1.getText();
                if(!srcText.equals("")){
                     JWebParser jwebNashorn = new JWebParser(srcText);
                     objectTree = jwebNashorn.createObjectTree();                                       
                     DefaultTreeModel treeModel = new DefaultTreeModel(objectTree);      
                     jTree1.setVisible(false);
                     jTree1.setModel(treeModel);
                     jTree1.revalidate();
                     jTree1.repaint();
                     ((DefaultTreeModel) jTree1.getModel()).reload();
                      jTree1.setVisible(true);
                    expandAll(jTree1, new TreePath(objectTree));
                  
                }
                   
    }
    
    private void jTextPane1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyPressed
    
                   
            int pos = jTextPane1.getCaretPosition();
       
                if(((evt.getKeyCode() == KeyEvent.VK_DOWN)||
                    (evt.getKeyCode() == KeyEvent.VK_UP)||
                    (evt.getKeyCode() == KeyEvent.VK_ESCAPE)||
                    (evt.getKeyCode() == KeyEvent.VK_ENTER))&& autoCompliteFrame.isVisible()){             
                    evt.consume();
                    return;
                }
                
        if( (evt.getKeyChar()+"").matches("[\\n\\s]")!=true){
            try {

                
               
                if(pos==0) {
                   // evt.consume();
                    return;
                };
                int wordStart = Utilities.getWordStart(jTextPane1,pos );
                int wordEnd = Utilities.getWordEnd(jTextPane1,pos );
                String key = jTextPane1.getText().substring(wordStart,wordEnd);
                
                
                
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
          
            
          //  jTextPane1.setCaretPosition(viewToModel);
            JTextPane jtp = (JTextPane) evt.getSource();
            Point pt = new Point(evt.getX(), evt.getY());
            int viewToModel = jtp.viewToModel(pt);
           
            
          //  jtp.setCaretPosition(viewToModel);
            String txt =  jtp.getText();
            int start  =  Utilities.getWordStart(jtp, viewToModel);
            int end    =  Utilities.getWordEnd(jtp, viewToModel);
            int len = end -start;
            String toolTipText = "";
            String word = jtp.getText(start, len);
           // jLabel3.setText(""+viewToModel);
            if(objectTree!=null){
            Enumeration node = objectTree.preorderEnumeration();
            while(node.hasMoreElements()){
               DefaultMutableTreeNode obj = (DefaultMutableTreeNode)node.nextElement();
//               if(obj!=null&&obj.getUserObject()!=null && !word.equals(" "))
            //   if(obj.getUserObject().toString().contains(word)){
                  
                    if(obj!=null&&obj.getUserObject()!=null&&obj.getUserObject().getClass() == ScriptObjectMirror.class){
                        
                             String type = ((AbstractJSObject)obj.getUserObject()).getMember("type").toString();
                                System.out.println(type);
                                if(type.equals("Identifier")){
                                String name = ((AbstractJSObject)obj.getUserObject()).getMember("name").toString();
                                
                                    if(name.equals(word)){
                                        toolTipText =  "Идентификатор "+((AbstractJSObject)obj.getUserObject()).getMember("name").toString();
                                        break;
                                    }                             
                                
                                } else if(type.equals("ExpressionStatement")){
                             
                                  
                                        AbstractJSObject expr = (AbstractJSObject) ((AbstractJSObject)obj.getUserObject()).getMember("expression");                                        
                                        String op = (String) expr.getMember("operator");
                                        AbstractJSObject left = (AbstractJSObject) expr.getMember("left");        
                                        AbstractJSObject right = (AbstractJSObject) expr.getMember("right");    
                                       String [] words = word.split("\\.");
                                        boolean contin = true;
                                        for(String word_l: words)
                                        if(left.getMember("property").equals(word_l)){   
                                            
                                            if(left.getMember("type").equals("MemberExpression"))
                                            {
                                                toolTipText = "Выражение this."+left.getMember("property")+op;
                                                
                                                if(right.getMember("type").equals("Literal")){
                                                
                                                    toolTipText += "'"+right.getMember("value")+"'";
                                                }
                                                else if(right.getMember("type").equals("FunctionExpression")){
                                                
                                                      toolTipText += "function()"+(right.getMember("id")==null?" unnamed":right.getMember("id"));
                                                    
                                                }
                                                
                                            
                                            
                                            } else {
                                                toolTipText = "Выражение "+left.getMember("property")+op+right.getMember("value");
                                            }
                                            
                                            contin = false;
                                              break;
                                        }
                                        
                                      if(!contin) break;
                                        
                                }
                                
                                
                                 
                                
                  
                                
                            } else {
                 //  toolTipText = ""+obj.getUserObject();
                    }
                            
                            
           
                   
                
              // }
            }
            
            
                               jLabel3.setText(toolTipText);
                                JToolTip jtoolp =   jtp.createToolTip();          
                                jtoolp.setBounds(evt.getX(), evt.getY(), 200, 50);
                                jtoolp.setVisible(true);
                                jtp.setToolTipText(toolTipText);
            
            }
            
            
   
            
        } catch (BadLocationException ex) {
            Logger.getLogger(JWebIDE.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }//GEN-LAST:event_jTextPane1MouseMoved

    private void jTextPane1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextPane1KeyReleased
        // TODO add your handling code here:
        
            try{
            
                   updateTree();
                   
                   
        } catch(Exception e){
            
          //  Object obj = e.getEcmaError();
            Logger.getGlobal().severe(e.getMessage()+" ");
        
        }
        
    }//GEN-LAST:event_jTextPane1KeyReleased
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
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
