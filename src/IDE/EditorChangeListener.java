/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import java.awt.EventQueue;
import javax.swing.JLabel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

/**
 *
 * @author kuznetsov
 */
public class EditorChangeListener implements DocumentListener{

    StyledDocument _style = null;
    JLabel jLabel  = null;
            
   
    public EditorChangeListener(StyledDocument style,JLabel jLabel) {
        _style = style;
         this.jLabel = jLabel;
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
         anyChanges(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        anyChanges(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    //   anyChanges(e);
    }
    
    public void anyChanges(DocumentEvent e){
     EventType type = e.getType();
     //Element   elem =  e.getChange(e.getDocument().getDefaultRootElement()).getElement();
        
        //new Thread( new 
       EventQueue.invokeLater( new SyntaxMatcher(e.getDocument(),_style,jLabel, this));
        //).start();       
         
    }
    
}
