/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import IDE.JWebIDE;
import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ClassNotLoadedException;
import com.sun.jdi.ClassType;
import com.sun.jdi.Field;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.InvalidTypeException;
import com.sun.jdi.InvocationException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.Method;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.ClassPrepareEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.StepEvent;
import com.sun.jdi.event.VMDeathEvent;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.ClassPrepareRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.jdi.request.StepRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author Aleksey
 */
public class Debuger {
  public static final String CLASS_NAME = "indexController";
  public static final String METHOD_NAME = "indexController";
  private Map<String, Location> _lineStat = new HashMap<String, Location>();
  
    /**
     * @param args the command line arguments
     */
    public void main(String[] args) throws IOException, InterruptedException, AbsentInformationException, InvalidTypeException, ClassNotLoadedException, IncompatibleThreadStateException, InvocationException {
    // connect
        JWebIDE jIDE = new JWebIDE();
        jIDE.setVisible(true);
        
        
        
    VirtualMachine vm = new VMAcquirer().connect(8000);

    // set watch field on already loaded classes
    List<ReferenceType> referenceTypes = vm.allClasses();
    for (ReferenceType refType : referenceTypes) {
        
        String fname = refType.name();
        if(fname.contains("indexController"))
        {            
          
            System.out.println("refType=" +refType.name());
              addClassWatch(vm,refType);
              addBreakpoint(vm, refType);
        }
         
    }
    // watch for loaded classes
  //  addClassWatch(vm);

    // resume the vm
    vm.resume();
   

    // process events
    EventQueue eventQueue = vm.eventQueue();
    while (true) {
      EventSet eventSet = eventQueue.remove();
      for (Event event : eventSet) {
        if (event instanceof VMDeathEvent
            || event instanceof VMDisconnectEvent) {
          // exit
          return;
          
        } else if (event instanceof ClassPrepareEvent) {
          // watch field on loaded class
          ClassPrepareEvent classPrepEvent = (ClassPrepareEvent) event;
          ReferenceType refType = classPrepEvent.referenceType();          
          addBreakpoint(vm, refType);
                                        
       } else if (event instanceof BreakpointEvent) {
            
          // a Test.foo has changed
          BreakpointEvent modEvent = (BreakpointEvent) event;
          
           BreakpointRequest breq =  (BreakpointRequest)modEvent.request();
          
              String filename  =  modEvent.location().sourceName();
              if (filename.endsWith(".jap") || filename.endsWith("eval")) {
                    //  Object sss =  getSource(modEvent);
                      //  System.out.println(breq.location().sourcePath()+"Source::"+filename+":"+breq.location().lineNumber()+":"+sss);
              }
         // System.out.println("methodName=" + modEvent.request().);
         addStepWatch(modEvent.thread(),vm);
        
       /*   System.out.println("sourcePath=" + modEvent.location().sourcePath());
          System.out.println("sourceName=" + modEvent.location().sourceName());
          System.out.println("lineNumber=" + modEvent.location().lineNumber());
          System.out.println("methodName=" + modEvent.location().method().name());
          System.out.println();*/
        } else if (event instanceof StepEvent) {
            
                vm.eventRequestManager().deleteEventRequest(event.request());

                  EventRequest stepRequest=event.request();

                 StepEvent stepEvent = (StepEvent) event;

             
                       String sourcePath = stepEvent.location().sourcePath();
                         if(sourcePath.contains("indexController.jap")){
                          int lineNumber = stepEvent.location().lineNumber();
                       String method = stepEvent.location().method().toString();
                       String lineKey = sourcePath + ":" + method  + ":" + lineNumber;


                         // System.out.println("lineKey=" + lineKey);

                              String filename  =  stepEvent.location().sourceName();
                    if (filename.endsWith(".jap") || filename.endsWith("eval")) {
                            Object sss =  getSource(stepEvent);
                              System.out.println( filename+":"+stepEvent.location().lineNumber()+":"+stepEvent.location().codeIndex());
                    }


                        List<StackFrame> frames = stepEvent.thread().frames();
                         int i=0;
                        for(StackFrame frame: frames){

                          //  List<LocalVariable> vars =   frames.get(0).location().sourceName()

                        //     System.out.println("Frame["+i+++"]"+frame.location().sourceName());
                            

                       }
                  }
                          addStepWatch(stepEvent.thread(),vm);
            
        }
      }
      eventSet.resume();
    }
  }
  private static StringReference getSource(StepEvent event) throws InvalidTypeException, ClassNotLoadedException, IncompatibleThreadStateException, InvocationException, AbsentInformationException {
 
                ReferenceType scriptType = event.location().declaringType();
               
               // List<Location> loc = scriptType.allLineLocations();
        //  Object fields = scriptType.allFields();
          Field sourceField = scriptType.fieldByName("source");
          
          if(sourceField==null) return null;
          ObjectReference source = (ObjectReference)scriptType.getValue(sourceField);
          ClassType sourceType = (ClassType)source.type();
          Method getStringMethod = sourceType.methodsByName("getString").get(0);
          
          ThreadReference thr = event.thread();
          
         StringReference sourceStringReference = null;
               try{  
                sourceStringReference = (StringReference)source.invokeMethod(thr,getStringMethod, new ArrayList<Value>(),ClassType.INVOKE_SINGLE_THREADED);
               }catch(Exception e){
                  Logger.getGlobal().severe(e.getLocalizedMessage());
               }
        return sourceStringReference;
   }
    
    
 private static void addStepWatch(ThreadReference thr, VirtualMachine vm) {
     EventRequestManager erm = vm.eventRequestManager();
     
      StepRequest request= erm.createStepRequest(thr, StepRequest.STEP_MIN, StepRequest.STEP_INTO);
       request.addClassExclusionFilter( "javax.*");
       request.addClassExclusionFilter( "java.*");
        request.addClassExclusionFilter( "com.*");
        request.addClassExclusionFilter( "sun.*");
        request.addClassExclusionFilter( "com.sun.jdi.*");
        request.addClassExclusionFilter("jdk.nashorn.api.*");
        request.addClassExclusionFilter("jdk.nashorn.codegen.*");
        request.addClassExclusionFilter("jdk.nashorn.ir.*");
        request.addClassExclusionFilter("jdk.nashorn.objects.*");
        request.addClassExclusionFilter("jdk.nashorn.parser.*");
        request.addClassExclusionFilter("jdk.nashorn.runtime.*");
        request.addClassExclusionFilter("jdk.nashorn.tools.*");
        request.addClassExclusionFilter("jdk.nashorn.internal.runtime.*");
        request.addClassExclusionFilter("jdk.nashorn.internal.objects.*");
      request.addCountFilter(1);
      request.enable();
    //  vm.resume();
   }
    
  /** Watch all classes of name "Test" */
  private static void addClassWatch(VirtualMachine vm,ReferenceType refType) {
    EventRequestManager erm = vm.eventRequestManager();
    ClassPrepareRequest classPrepareRequest = erm.createClassPrepareRequest();
   
    classPrepareRequest.addClassFilter(refType.name());
    classPrepareRequest.setEnabled(true);
  }

  /** Watch field of name "foo" */
  private static void addBreakpoint(VirtualMachine vm, ReferenceType refType) throws AbsentInformationException {
    EventRequestManager erm = vm.eventRequestManager();
     //  System.out.println("refType=" +refType.sourceName());
    
     List<Method> Methods = refType.allMethods();
     
     for(Method method: Methods){
         if(method.name().contains("Controller"))
     System.out.println("method="+method.toString());
     }
    
    if(!refType.methodsByName(METHOD_NAME).isEmpty()){        
    Method method = refType.methodsByName(METHOD_NAME).get(0);        
  List<Location> locs =   method.allLineLocations();   
  List<LocalVariable> locs2 =   method.variables();
  
   BreakpointRequest breakpointRequest = erm.createBreakpointRequest(method.location());
 //BreakpointRequest breakpointRequest = erm.createBreakpointRequest(refType.allLineLocations().get(0).location());
    
    
    
    breakpointRequest.enable();
    }
  }
  
  
}