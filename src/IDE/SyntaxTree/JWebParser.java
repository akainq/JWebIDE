/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import jdk.nashorn.internal.ir.FunctionNode;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.Source;
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
 
      
 
         Options opt = new Options("nashorn");
             opt.set("anon.functions", true);
          //   opt.set("parse.only", true);
             opt.set("scripting", true);
             
          Context context = new Context(opt, em, Thread.currentThread().getContextClassLoader());
          Context.setGlobal(new Global( context ));
          Source textSource =   Source.sourceFor("mySource", source);
          
          env = context.getEnv();//new ScriptEnvironment(opt, new PrintWriter(OutWriter), new PrintWriter(ErrorWriter) );
                                        
         nashornParser =  new jdk.nashorn.internal.parser.Parser(env,textSource,em);   
     
	
        
    }
    
    
    public FunctionNode parse(){
    
      //  LexicalContext lc = new LexicalContext();
        
      return nashornParser.parse();
    
   
     //  Logger.logMsg(Level.ALL.intValue(), fNode.getCompileUnit());
       
    }

 
    
  
}
