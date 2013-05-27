/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpadfx;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.image.Image;
import static jpadfx.ApplicationModule.FILE_ICON;

/**
 *
 * @author a0082252
 */
public class WFWidgetMap {
    
    // component type in the presenting layer (compositive model)
    public static class widgetNode{
        String vName = "";
        private Image vImage = null;
        public int intype = 0;
        public int outtype = 0;
        final public Map<String,String[]> InBoundPoint;
        final public Map<String,String[]> outBoundPoint;
        
        widgetNode(String name, Image mImage, int intype, int outtype, Map<String, String[]>ip, Map<String, String[]>op){
            this.vName = name;
            this.vImage = mImage;
            this.intype = intype; // 0 for no input node, 1 for normal, 9 * for dynamical number inputs
            this.outtype = outtype; // 0 for no output node, 1 for normal, 9 * for dynamical number outputs
            this.InBoundPoint = (Map<String, String[]>) ip;
            this.outBoundPoint = (Map<String, String[]>) op;
        }
                   
        widgetNode(String name, String mImageFileName, int intype, int outtype, Map<String, String[]>ip, Map<String, String[]>op){
            this.vName = name;
            this.vImage = new Image(getClass().getResourceAsStream(mImageFileName)); 
            this.intype = intype; // 0 for no input node, 1 for normal, 9 * for dynamical number inputs
            this.outtype = outtype; // 0 for no output node, 1 for normal, 9 * for dynamical number outputs
            this.InBoundPoint = (Map<String, String[]>) ip;
            this.outBoundPoint = (Map<String, String[]>) op;
        }        
        
        public Image getVImage() {
            return vImage;
        }        
    }

    // taking care of the type of nodes (input list representing the degree of inbound edges(1)
    static final Map inputList1 = 
            Collections.unmodifiableMap(new HashMap<String, String[]>() {{
                        put("0",new String[]{"ExampleTable"});
                    }}
    );
    
    // taking care of the type of nodes (input list representing the degree of inbound edges (2)
    static final Map inputList2 = 
            Collections.unmodifiableMap(new HashMap<String, String[]>() {{
                        put("0",new String[]{"ExampleTable"});
                        put("1",new String[]{"ExampleTable"});
                    }}
    );        
    
    // taking care of the type of nodes (input list representing the degree of inbound edges(1)
    static final Map outputList1 = 
            Collections.unmodifiableMap(new HashMap<String, String[]>() {{
                        put("O",new String[]{"ExampleTable"});
                    }}
    );
    
    /*
     * for the panel(DATA), this is the mapping table for all the three modules available
     * Showing the way of instantiating with module_name, image, and etc
     */

    private static Map mWidgetMap1 = new HashMap(){
    {
        // all nodes here are made immutable objects
        put(ApplicationModule.FILE_TEXT,
                new widgetNode(ApplicationModule.FILE_TEXT, new Image(getClass().getResourceAsStream(ApplicationModule.FILE_ICON)), 0, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.CONCATENATE_TEXT, 
                new widgetNode(ApplicationModule.CONCATENATE_TEXT, new Image(getClass().getResourceAsStream(ApplicationModule.CONCATENATE_ICON)), 0, 0, 
                Collections.unmodifiableMap(inputList2),  Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.DATATABLE_TEXT, 
                new widgetNode(ApplicationModule.DATATABLE_TEXT, new Image(getClass().getResourceAsStream(ApplicationModule.DATATABLE_ICON)), 9, 0, 
                Collections.unmodifiableMap(inputList1),  Collections.unmodifiableMap(outputList1)));

    }};

    /*
     * for the panel(Classify), this is the mapping table for all the five modules available
     * Showing the alternative way of instantiating with module_name, imageFile_name, and etc
     */
    private static Map mWidgetMap2 =  new HashMap(){
    {
        put(ApplicationModule.ITREE_TEXT, 
                new widgetNode(ApplicationModule.ITREE_TEXT, ApplicationModule.ITREE_ICON, 0, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.LR_TEXT, 
                new widgetNode(ApplicationModule.LR_TEXT, ApplicationModule.LR_ICON, 0, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.CLTREE_TEXT, 
                new widgetNode(ApplicationModule.CLTREE_TEXT, ApplicationModule.CLTREE_ICON, 9, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.C45_TEXT, 
                new widgetNode(ApplicationModule.C45_TEXT, ApplicationModule.C45_ICON, 9, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));
        put(ApplicationModule.BasicSVM_TEXT, 
                new widgetNode(ApplicationModule.BasicSVM_TEXT, ApplicationModule.BasicSVM_ICON, 9, 0, 
                Collections.unmodifiableMap(inputList1), Collections.unmodifiableMap(outputList1)));        
    }};
    
    private static Map mWidgetMap = new HashMap(){
    {
        putAll(mWidgetMap1);
        putAll(mWidgetMap2);
        // and many other categories
    }};
    
    private static Map mWidgetGroup = new HashMap() {
    {
        put("Data", mWidgetMap1);
        put("Classify", mWidgetMap2);
    }};
    
    static Map getWidgetByName(String category) {
        if(mWidgetGroup.containsKey(category))
            return (HashMap<String, widgetNode>)mWidgetGroup.get(category);
        return null;
    }

    static Map getWidgetAll() {   
        return (HashMap<String, widgetNode>)mWidgetMap;
    }
}
