/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import com.sun.glass.ui.Window;
import com.sun.media.jfxmedia.logging.Logger;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import static javafx.scene.input.KeyCode.T;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.ir.LexicalContext;
import jdk.nashorn.internal.ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.OptionTemplate;
import jdk.nashorn.internal.runtime.options.Options;

/**
 *
 * @author kuznetsov
 */
public class JWebParser {
 
     jdk.nashorn.internal.parser.Parser nashornParser;
     StringWriter OutWriter = new StringWriter();
     StringWriter ErrorWriter = new StringWriter();
     ErrorManager em = new ErrorManager();
     ScriptEnvironment env;
    
    public JWebParser(String source) {
 
          Source textSource =   Source.sourceFor("mySource", source);
 
         Options opt = new Options("");
          env = new ScriptEnvironment(opt, new PrintWriter(OutWriter), new PrintWriter(ErrorWriter) );
          
         nashornParser =  new jdk.nashorn.internal.parser.Parser(env,textSource,em);   
        
    }
    
    
    public FunctionNode parse(){
    
      //  LexicalContext lc = new LexicalContext();
        
      return nashornParser.parse();
    
   
     //  Logger.logMsg(Level.ALL.intValue(), fNode.getCompileUnit());
       
    }

 
    
  
}
