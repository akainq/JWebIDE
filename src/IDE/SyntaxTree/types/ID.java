/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree.types;

import java.util.Collection;
import java.util.Set;
import jdk.nashorn.api.scripting.AbstractJSObject;

/**
 *
 * @author Aleksey
 */
public class ID {
    
    public String type;
    public Object name;

    
    public ID(AbstractJSObject src){
        
        type = (String) src.getMember("type");
        name =  src.getMember("name");
    }


   
}
