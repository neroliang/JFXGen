/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpadfx;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCombination;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.lang.Integer;
import java.lang.ref.WeakReference;

import javafx.application.Application;
import javafx.application.Platform;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.control.Button;

import javafx.scene.input.*;

import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.LineBuilder;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.scene.control.TabPane;
import ui.layouts.ribbonBar.tabs.home.HomeTab;
import ui.layouts.ribbonBar.tabs.dataSource.DataSource;

import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.layouts.ribbonBar.tabs.dataSource.DataSource;
import ui.layouts.ribbonBar.tabs.home.HomeTab;
/**
 *
 * @author a0082252
 */
public class WFTopMenu extends VBox {
    private boolean fDirty = false;
    private String SAVE_CHNGS = "false";

    private final static String DEFAULT_TITLE = "WFBuilder";
    private final static String DEFAULT_STATUS = "WFB_v1.0";    
    Label bottomStatusLine = null;
    private final MenuItem miCopy = new MenuItem("Copy");
    private final MenuItem miCut = new MenuItem("Cut");
    
    //Stage mMainStage;
    private Stage mMainStage;
    private Application appRef;
    private FileChooser mFileChooser;
    private File fileCurrent;
    private BorderPane mPane;
    

    private TabPane tabPane;
    public WFTopMenu(Stage mStage, Application mApp, BorderPane mRoot, final Clipboard sysClip ) {
        
        mMainStage = mStage;
        appRef = mApp;
        mPane = mRoot;
        //setupFileChooser();
        //bottomStatusLine;
        MenuBar mMenu = new MenuBar();
        tabPane = new TabPane();
        HomeTab homeTab = new HomeTab(mStage, mApp, sysClip);
        DataSource insertTab = new DataSource();
        
        tabPane.getTabs().addAll(homeTab.get(), insertTab.get());
        ToolBar mToolBar = creatToolBar("standard");
        this.getChildren().addAll(tabPane, mToolBar);
   }
   

    /**
    * RibbonBar. Drop this into application UI. This class creates the entire
    * Ribbon Bar and could be placed in the BorderPane.NORTH region (for example).
    */
//    private class RibbonBar {
//
//        private TabPane tabPane;
//
//        public RibbonBar() {
//
//            tabPane = new TabPane();
//
//            buildTabs();
//        }
//
//        /**
//        * get. Return instance of the RibbonBar (TabPane).
//        * @return 
//        */
//        public TabPane get() {
//            return this.tabPane;
//        }
//
//        /**
//        * buidlTabs. Build each Tab that is added to TabPane.
//        */
//        private void buildTabs() {
//
//            HomeTab homeTab = new HomeTab(mMainStage, appRef, sysClip);
//            CommerceTab insertTab = new CommerceTab();
//
//            tabPane.getTabs().addAll(homeTab.get(), insertTab.get());
//        }
//    }

    private ToolBar creatToolBar(String id){
        String styledToolBarCss = WFTopMenu.class.getResource("StyledToolBar.css").toExternalForm();
        final ObservableList stringObservableList = FXCollections.observableArrayList(
                "Option 1", "Option 2","Option 3",
                "Longer ComboBox item",
                "Option 10", "Option 12");
        
        //private final ObservableList strings;
        //= FXCollections.observableList(null);
//                FXCollections.observableArrayList(
//            );
        
        //Non-editable combobox. Created with a builder
        ComboBox uneditableComboBox = ComboBoxBuilder.create()
                .id("uneditable-combobox")
                .promptText("Make a choice...")
                .items(FXCollections.observableArrayList(stringObservableList.subList(0, 6))).build();
        
        ToolBar attachedBar = ToolBarBuilder.create().id(id).items(
            new Button("Preview"),
            new Button("Run"),
            new Button("Stop"),
            new Button("Export"),
            new Slider(),
            uneditableComboBox).build();
        attachedBar.getStylesheets().add(styledToolBarCss);
        return attachedBar;
    }
    public void doOpen(File file)
   {
       
   }
    public void doExit(Event event)
   {
       
   }    

}
