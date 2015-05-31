/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author kuznetsov
 */
public class SyntaxMatcher implements Runnable{

      //  Pattern multiLineComment = Pattern.compile("\\/\\*[\\s\\S]*?\\*\\/",Pattern.MULTILINE  );
        Pattern multiLineComment = Pattern.compile("(\\/\\*[\\s\\S]*?\\*\\/)|(\\/\\*(.+))",Pattern.MULTILINE  );
        Pattern singleLineComment = Pattern.compile("(?:^|[^\\\\])\\/\\/.*$",Pattern.MULTILINE);
        Pattern quotedStrings = Pattern.compile("\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
        Pattern strings = Pattern.compile("'[^'\\\\]*(?:\\\\.[^'\\\\]*)*'|\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\"");
        Pattern fields = Pattern.compile("\\.([\\w]+)\\s*");
        Pattern methodCall = Pattern.compile("\\.([\\w]+)\\s*\\(");
        Pattern functionCall = Pattern.compile("\\b([\\w]+)\\s*\\(");
        Pattern brackets = Pattern.compile("\\{|\\}|\\(|\\)|\\[|\\]");
        Pattern numbers = Pattern.compile("\\b((?:(\\d+)?\\.)?[0-9]+|0x[0-9A-F]+)\\b");
        Pattern specialKeywords = Pattern.compile("\\babstract(?=[\\s\\.(])|boolean(?=[\\s\\.(])|break(?=[\\s\\.(])|byte(?=[\\s\\.(])|case(?=[\\s\\.(])|catch(?=[\\s\\.(])|char(?=[\\s\\.(])|class(?=[\\s\\.(])|const(?=[\\s\\.(])|continue(?=[\\s\\.(])|debugger(?=[\\s\\.(])|default(?=[\\s\\.(])|delete(?=[\\s\\.(])|do(?=[\\s\\.(])|double(?=[\\s\\.(])|else(?=[\\s\\.(])|enum(?=[\\s\\.(])|export(?=[\\s\\.(])|extends(?=[\\s\\.(])|final(?=[\\s\\.(])|finally(?=[\\s\\.(])|float(?=[\\s\\.(])|for(?=[\\s\\.(])|function(?=[\\s\\.(])|goto(?=[\\s\\.(])|if(?=[\\s\\.(])|implements(?=[\\s\\.(])|import(?=[\\s\\.(])|in(?=[\\s\\.(])|instanceof(?=[\\s\\.(])|int(?=[\\s\\.(])|interface(?=[\\s\\.(])|long(?=[\\s\\.(])|native(?=[\\s\\.(])|new(?=[\\s\\.(])|package(?=[\\s\\.(])|private(?=[\\s\\.(])|protected(?=[\\s\\.(])|public(?=[\\s\\.(])|return(?=[\\s\\.(])|short(?=[\\s\\.(])|static(?=[\\s\\.(])|super(?=[\\s\\.(])|switch(?=[\\s\\.(])|synchronized(?=[\\s\\.(])|this(?=[\\s\\.(])|throw(?=[\\s\\.(])|throws(?=[\\s\\.(])|transient(?=[\\s\\.(])|try(?=[\\s\\.(])|typeof(?=[\\s\\.(])|var(?=[\\s\\.(])|void(?=[\\s\\.(])|volatile(?=[\\s\\.(])|while(?=[\\s\\.(])|with(?=[\\s\\.(])|true(?=[\\s\\.(])|false(?=[\\s\\.(])|prototype\\b", Pattern.MULTILINE);
      
        Document document = null;
        StyledDocument doc_style = null;
        JLabel jLabel=null;
        EditorChangeListener listener = null;
        
    public SyntaxMatcher(Document document,StyledDocument doc_style ,JLabel jLabel, EditorChangeListener listener) {
        this.document = document;
        this.doc_style = doc_style;
        this.jLabel = jLabel;
        this.listener = listener;
    }
    
    private void doMatch() throws BadLocationException{
    
               this.document.removeDocumentListener(listener);
          
            
                
                Style defaultStyle = doc_style.addStyle("11", null);                                                                
                StyleConstants.setForeground(defaultStyle, new Color(0,0,0));                                
                defaultStyle.addAttribute(StyleConstants.FontSize, 13);
                defaultStyle.addAttribute(StyleConstants.FontFamily, "Courier New");
                
                Style CommentStyle = doc_style.addStyle("11", null);                                                                
                StyleConstants.setForeground(CommentStyle, new Color(0,128,0));                                
                CommentStyle.addAttribute(StyleConstants.FontSize, 13);
                //CommentStyle.addAttribute(StyleConstants.Italic, true);
                
                Style StringStyle = doc_style.addStyle("11", null);                                                                
                StyleConstants.setForeground(StringStyle, Color.red);                                
                StringStyle.addAttribute(StyleConstants.FontSize, 13);
                //StringStyle.addAttribute(StyleConstants.Bold, true);
                
                Style SpecialKeywords = doc_style.addStyle("11", null);                                                                
                StyleConstants.setForeground(SpecialKeywords, new Color(0,0,128));                                
                SpecialKeywords.addAttribute(StyleConstants.FontSize, 13);
                SpecialKeywords.addAttribute(StyleConstants.Italic, true);
             //   SpecialKeywords.addAttribute(StyleConstants.Bold, true);
                
                String text = document.getText(0, document.getLength());               
                doc_style.setCharacterAttributes(0, text.length(), defaultStyle, true);
                
                MatchPattern(specialKeywords, text, SpecialKeywords);
                MatchPattern(strings, text, StringStyle); 
                MatchPattern(multiLineComment, text, CommentStyle);
                MatchPattern(singleLineComment, text, CommentStyle);
                       
              
            
            int i =  Integer.parseInt(jLabel.getText());
            i++;
            jLabel.setText(""+i);
             
             this.document.addDocumentListener(listener);
    
    }

    @Override
    public void run() {
            try {
                doMatch();
            } catch (BadLocationException ex) {
                Logger.getLogger(SyntaxMatcher.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    public Boolean MatchPattern(Pattern patt, String text, Style style){
        Boolean  ret =false;
        
        int start = 0;
          int end = 0;
           Matcher  matcher = patt.matcher(text);
           while(matcher.find()){
               
               start = matcher.start();
               end = matcher.end();
               // doc.s
               int len = text.substring(start, end).length();
               doc_style.setCharacterAttributes(start, len, style, true);
               ret = true;
           }
     return ret;
    }
 
    
}
