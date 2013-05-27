package jpadfx;

//import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
//import java.lang.Integer;
import javafx.application.Application;
//import javafx.application.Platform;
import java.lang.ref.WeakReference;

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
import javafx.scene.control.Button;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.scene.text.*;

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

import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Fundamental Windows Builder. 
 * Initializing main window of the application
 */
public class WFBuilder extends Application
{
    private final static String DEFAULT_TITLE = "WFBuilder";
    private final static String DEFAULT_STATUS = "WFB_v1.0";
    private final static String SAVE_CHNGS = "Save changes?";
    
    /**
     * basic elements for building up window elements for a window in JAVAFX Application
     */
    private Stage stage;
    private Scene scene;
    private final Group root = new Group();
    
    //Used for passing objects via system built-in clipboard 
    private Clipboard systemClip;    
    
    // The outmost Pane, the parent of Top, Bottom, Center, Left
    private BorderPane mWinLayout = new BorderPane();
    
    // Every child Pane is held separaratedly
    private WFTopMenu mTop = null;
    private Label mBottom = null;
    private WFCentralPane mCenter = null;    
    private Rectangle mLeft = RectangleBuilder.create()
            .width(150).height(480)
            .fill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(1, Color.rgb(6,46,55)),
                new Stop(0, Color.rgb(26,36,55,0.5))
            }))
            .stroke(Color.BLACK)
            .build();
    
    // Nodes to accomplish a single straight line in the scheme
    private String gEditorStartNode;
    private String gEditorEndNode;
    private Map graphNodes = new HashMap<String, Pane>();
    private Map graphEdges = new HashMap<Line, String[]>();
    private Map connection_map = new HashMap<String, Line>();
    
    //final WeakReference<Line>
    private Line connection_tmp;
    private Line connection_final;
    final String tabsURL = "/ui/layouts/ribbonBar/tabs/tabs.css";

    /* 
     * Setting parameters for each configurable items
     * @no value is returned
     */
    private void setSceneProperties()
    {
        //The percentage values are used as multipliers for screen width/height.
        final double percentageWidth = 0.58 * Screen.getPrimary().getBounds().getWidth();
        final double percentageHeight = 0.45 * Screen.getPrimary().getBounds().getHeight();
                        
        //Create a scene object. Pass in the layout and set
        //the dimensions to 98% of screen width & 90% screen height.
        this.scene = new Scene(root, percentageWidth, percentageHeight, Color.WHITE);
        
        //scene = new Scene(root, 800, 600, Color.WHITE);
               
        //Add CSS Style Sheet (located in same package as this class).        
        //Add CSS for Tabs.
        String css = this.getClass().getResource("App.css").toExternalForm();
        final String tabsCSS = this.getClass().getResource(tabsURL).toExternalForm();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add(tabsCSS);
    }

    /* 
     * the very starting point of Main windows initilization
     */
   @Override
   public void start(final Stage primaryStage)
   {
       final float scale_ratio_x = 0.62f, scale_ratio_y = 0.95f;
        stage = primaryStage;
        Screen myscreen = Screen.getPrimary();
        Rectangle2D bounds = myscreen.getVisualBounds();
        systemClip = Clipboard.getSystemClipboard();
        root.getChildren().add(mWinLayout);
        
        mTop = new WFTopMenu(stage, this, mWinLayout, systemClip);
        final String insetTextCss = WFBuilder.class.getResource("InsetText.css").toExternalForm();
        mBottom = LabelBuilder.create().text("Success Started XXXXXXXXX...").build();
        mBottom.getStylesheets().add(insetTextCss);
        mWinLayout.setBottom(mBottom);
        
        mCenter = new WFCentralPane(root, stage, this);
        mWinLayout.setTop(mTop);        
        mWinLayout.setCenter(mCenter);
        mWinLayout.setLeft(mLeft);
        
        setSceneProperties();
//        scene.getStylesheets().add("scroller.css");
        primaryStage.setScene(scene);
        primaryStage.setTitle(DEFAULT_TITLE);
        primaryStage.setX(0);
        primaryStage.setY(0);     
        primaryStage.setWidth(scale_ratio_x * bounds.getWidth());
        primaryStage.setHeight(scale_ratio_y * bounds.getHeight());
        
        EventHandler<WindowEvent> ehwe;
        ehwe = new EventHandler<WindowEvent>()
        {
            @Override
            public void handle(WindowEvent mWinEvent)
            {
                Event general_we = mWinEvent;
                mTop.doExit(general_we);
            }
        };
      primaryStage.setOnCloseRequest(ehwe);
      primaryStage.show();
      
      Application.Parameters params = getParameters();
      List<String> uparams = params.getUnnamed();
      if (uparams.size() != 0)
         mTop.doOpen(new File(uparams.get(0)));
   }  
   
    private ToolBar createToolBar(String id) {
        return ToolBarBuilder.create().id(id).items(
                new Button("Button 1"),
                new Button("Button 2"),
                new Slider()).build();
    }    
    
    public static void main(String[] args)
   {
      launch(args);
   }
}
