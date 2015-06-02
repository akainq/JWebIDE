/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IDE.SyntaxTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author kuznetsov
 * @param <T>
 */
  public class ASyntaxTree<T> {
    private final STNode<T> root;

    public ASyntaxTree(T rootData) {
        root = new STNode<>();
        root.data = rootData;
        root.children = new ArrayList<>();
    }

    public static class STNode<T> implements  TreeNode{
        public T data;
        public STNode<T> parent;
        public List<STNode<T>> children = new ArrayList<STNode<T>> ();

        
 
       public void setParent(STNode<T> parent){     
           
            this.parent = parent;        
            
        }
        public void setData(T data){        
            this.data = data;        
        }
        
        
       public void addNode(STNode<T>  data){     
           
                if(data!=null){
                 data.parent = this;            
                 this.children.add(data);        
            }
        }
        
        
        @Override
        public TreeNode getChildAt(int childIndex) {
           return children.get(childIndex);
        }

        @Override
        public int getChildCount() {
            return children.size();
        }

        @Override
        public TreeNode getParent() {
             return parent;
        }

        @Override
        public int getIndex(TreeNode node) {
           return  this.children.indexOf(node);
        }

        @Override
        public boolean getAllowsChildren() {
               return !this.children.isEmpty();
        }

        @Override
        public boolean isLeaf() {
            
             return this.parent == null;
        }

        @Override
        public Enumeration children() {
               return   Collections.enumeration(this.children);
        }

     
    }
}
