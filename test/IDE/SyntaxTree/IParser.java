/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import com.sun.source.tree.CompilationUnitTree;
import jdk.nashorn.internal.scripts.JO;

/**
 *
 * @author Aleksey
 */
public interface IParser {
    
    public JO parse(String source);
    
}
