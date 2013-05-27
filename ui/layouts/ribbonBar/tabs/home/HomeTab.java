package ui.layouts.ribbonBar.tabs.home;

import javafx.application.Application;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.input.Clipboard;
import javafx.stage.Stage;
import javafx.scene.input.DataFormat;
/**
 * HomeTab. This class represents the "Home Tab"; where common functions
 * might be placed.
 */
public class HomeTab {
    
    private Tab tab;
    Clipboard mClip = null; 
    Stage mStage;
    Application mApp;
    /**
     * Default Constructor.
     */
    public HomeTab(Stage mStage, Application mApp,  final Clipboard sysClip) {
        mClip = sysClip;
        mApp = mApp;
        tab = new Tab("Home");
        buildTab();
    }
    
    /**
     * get. Returns an instance of the Home Tab. This will be added to the 
     * TabPane in the RibbonBar class.
     * @return 
     */
    public Tab get() {
        return this.tab;
    }
    
    /**
     * buildTab. Helper method to build the Home Tab UI.
     */
    private void buildTab() {

        //Do not allow tab to close.
        tab.setClosable(false);
        
        //The container holds all toolbar sections specific to a Tab.
        HBox container = new HBox();
        
        //Set ID (for CSS styles)
        container.setId("container");
        
        //Set preferred height.
        container.setPrefHeight(90);
        
        //Put spacing b/n each toolbar block
        container.setSpacing(5);
        
        
        //Add the Actions Ribbon Component
        Actions actions = new Actions(mStage, mApp, mClip);
        container.getChildren().add(actions.get());
       
        //Add the Clipboard Ribbon Component.
        ClipboardGroup mClipGroup = new ClipboardGroup();
        container.getChildren().add(mClipGroup.get());
        
        //Add the Fonts Ribbon Component.
        Attribute font = new Attribute();
        container.getChildren().add(font.get());
        
        //Add Container.
        tab.setContent(container);
        
        
    }
   
}
