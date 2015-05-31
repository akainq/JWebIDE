/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE;

import com.sun.org.apache.xerces.internal.xs.StringList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Aleksey
 */
public class JavaScriptLexer {
    
 
    static final List<String> keywordsList = Arrays.asList(new String[] {"abstract","boolean","break","byte","case","catch","char","class","const","continue","debugger","default","delete","do","double","else","enum","export","extends","final","finally","float","for","function","goto","if","implements","import","in","instanceof","int","interface","long","native","new","package","private","protected","public","return","short","static","super","switch","synchronized","this","throw","throws","transient","try","typeof","var","void","volatile","while","with","true","false","prototype"});

     
    public static String [] getKeywords(){    
        return (String[])keywordsList.toArray();    
    }
    
    public static String [] findKeywords(String like){                           
         Object[] ss = keywordsList.stream().filter(s->{ return s.startsWith(like);}).toArray();   
      
         return Arrays.copyOf(ss,ss.length,String[].class);
    }
    
    
}
