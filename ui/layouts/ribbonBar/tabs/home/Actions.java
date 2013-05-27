package ui.layouts.ribbonBar.tabs.home;


import java.io.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jpadfx.Alert;

/**
 * Tables. This class represents the Actions Ribbon Bar Component.
 */
public class Actions {
    
    private Button btnNew, btnOpen, btnEmail, btnPrint, btnPDF, btnDelete;
    private VBox root;
    private boolean fDirty = false;
    private String SAVE_CHNGS = "false";

    private final static String DEFAULT_TITLE = "WFBuilder";
    private final static String DEFAULT_STATUS = "WFB_v1.0";    
    Label bottomStatusLine = null;
    private final MenuItem miCopy = new MenuItem("Copy");
    private final MenuItem miCut = new MenuItem("Cut");
    
    //Stage mMainStage;
    Stage mMainStage;
    Application appRef;
    FileChooser mFileChooser;
    File fileCurrent;
    BorderPane mPane;
    /**
     * Default Constructor.
     */
    public void setfDirty(boolean newState){
        fDirty = newState;
    }
   public void setupFileChooser(){
      mFileChooser = new FileChooser();
      //fc.setInitialDirectory(new File("."));
      FileChooser.ExtensionFilter ef1;
      ef1 = new FileChooser.ExtensionFilter("workflow documents (*.rws)", "*.rws");
      FileChooser.ExtensionFilter ef2;
      ef2 = new FileChooser.ExtensionFilter("All Files", "*.*");
      mFileChooser.getExtensionFilters().addAll(ef1, ef2);
   }
   
    public Actions(Stage mStage, Application mApp,  final Clipboard sysClip) {
        this.root = new VBox();
        build(mStage, mApp, sysClip);
    }
    
    /**
     * get. Returns the VBox to be placed on the Ribbon Bar.
     * @return 
     */
    public VBox get() {
        return this.root;
    }
    
    /**
     * build. Helper method to build the layout.
     */
    private void build(Stage mStage, Application mApp,  final Clipboard sysClip) {
        mMainStage = mStage;
        appRef = mApp;
        setupFileChooser();
        
        //GridPane used to layout the components.
        GridPane layout = new GridPane();
        
        //Grid Lines to help layout buttons.
        layout.setGridLinesVisible(false);
        
        //Set horizontal spacing.
        layout.setHgap(5);
        
        
        //Build UI Controls
        this.buildNewButton();
        this.buildOpenButton();
        this.buildEmailButton();
        this.buildPDFButton();
        this.buildPrintButton();
        this.buildDeleteButton();
                
        //Add All Componets to the GridPane.
        layout.add(this.btnNew, 0, 0);
        layout.add(this.btnOpen, 1, 0);
        layout.add(this.btnDelete, 2, 0);
        layout.add(this.btnEmail, 3, 0);
        layout.add(this.btnPrint, 4, 0);
        layout.add(this.btnPDF, 5, 0);
        
        //Build the Toolbar Container Label.
        Label label = new Label("Actions");
        label.getStyleClass().add("ribbonLabel");
        label.setTooltip(new Tooltip("Order related stuff..."));
        
        //TODO: find a better way to center a label.
        VBox vbox = new VBox();
        vbox.getChildren().add(label);
        VBox.setVgrow(label, Priority.ALWAYS);
        vbox.setAlignment(Pos.BOTTOM_CENTER);
        vbox.setStyle("-fx-padding: 5 0 0 0");
        layout.add(vbox, 0, 2, 6, 1);
        
        //Center alignment in the VBox, add GridPane, set VBox CSS Selector.
        this.root.setAlignment(Pos.CENTER);
        this.root.getChildren().add(layout);
        this.root.getStyleClass().add("toolbarContainer");
    }
    
    /**
     * buildNewButton. Helper method to build a Button.
     */
    private void buildNewButton() {
        
        //Create button with text.
        this.btnNew = new Button("New");
        
        //Set the Image above the text.
        this.btnNew.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/new.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnNew.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnNew.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnNew.setTooltip(new Tooltip("New Order"));
        
        //Set simple Click Event Handler.
        this.btnNew.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("New Order Button clicked.");
                doNew();   
            }
     
        });
    }
    
    /**
     * buildOpenButton. Helper method to build a Button.
     */
    private void buildOpenButton() {
        
        //Create button with text.
        this.btnOpen = new Button("Open");
        
        //Set the Image above the text.
        this.btnOpen.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/open.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnOpen.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnOpen.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnOpen.setTooltip(new Tooltip("Open Order"));
        
        //Set simple Click Event Handler.
        this.btnOpen.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("Open Exist Order Button clicked.");
                doOpen();
                    
            }
     
        });
    }
    
    /**
     * buildEmailButton. Helper method to build a Button.
     */
    private void buildEmailButton() {
        
        //Create button with text.
        this.btnEmail = new Button("Email");
        
        //Set the Image above the text.
        this.btnEmail.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/email.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnEmail.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnEmail.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnEmail.setTooltip(new Tooltip("Email Order"));
        
        //Set simple Click Event Handler.
        this.btnEmail.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("Email Order Button clicked.");
                    
            }
     
        });
    }
    
    /**
     * buildPrintButton. Helper method to build a Button.
     */
    private void buildPrintButton() {
        
        //Create button with text.
        this.btnPrint = new Button("Print");
        
        //Set the Image above the text.
        this.btnPrint.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/print.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnPrint.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnPrint.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnPrint.setTooltip(new Tooltip("Print Order"));
        
        //Set simple Click Event Handler.
        this.btnPrint.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("Print Invoice Button clicked.");
                    
            }
     
        });
    }
    
    /**
     * buildDeleteButton. Helper method to build a Button.
     */
    private void buildDeleteButton() {
        
        //Create button with text.
        this.btnDelete = new Button("Delete");
        
        //Set the Image above the text.
        this.btnDelete.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/delete.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnDelete.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnDelete.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnDelete.setTooltip(new Tooltip("Delete Order"));
        
        //Set simple Click Event Handler.
        this.btnDelete.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("Delete Order Button clicked.");
                    
            }
     
        });
    }
    
    /**
     * buildPDFButton. Helper method to build a Button.
     */
    private void buildPDFButton() {
        
        //Create button with text.
        this.btnPDF = new Button("Export");
        
        //Set the Image above the text.
        this.btnPDF.setContentDisplay(ContentDisplay.TOP);
        
        //Add image.
        String imgPath = "/ui/common/images/pdf.png";
        Image image = new Image(this.getClass().getResourceAsStream(imgPath),
                24.0, 24.0, true, true);
        ImageView imageView = new ImageView(image);
        this.btnPDF.setGraphic(imageView);
        
        //Set CSS Styles. 
        this.btnPDF.getStyleClass().add("ribbonToggleButton");
        
        //Set Tooltip
        this.btnPDF.setTooltip(new Tooltip("Export Order to PDF"));
        
        //Set simple Click Event Handler.
        this.btnPDF.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {
                System.out.println("PDF Export Button clicked.");
                    
            }
     
        });
    }
   public void doExit(Event e)
   {
      if (fDirty)
      {
         AreYouSure ays = new AreYouSure(mMainStage, SAVE_CHNGS);
         EventHandler<ActionEvent> ehae1;
         ehae1 = new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent ae)
            {
               if (doSave())
                  mMainStage.close();
            }
         };
         ays.setOnYes(ehae1);
         EventHandler<ActionEvent> ehae2;
         ehae2 = new EventHandler<ActionEvent>()
         {
            @Override
            public void handle(ActionEvent ae)
            {
               mMainStage.close();
            }
         };
         ays.setOnNo(ehae2);
         ays.show();
      }
      else
         mMainStage.close();
      e.consume();
   }
   
   public void doNew()
   {
      fileCurrent = null;
      //gEditor.setText("");
      mMainStage.setTitle(DEFAULT_TITLE);
      fDirty = false;
   }
   public void doOpen()
   {
      doOpen(null);
   }
   public void doOpen(File file)
   {
      try
      {
         if (file == null)
            file = mFileChooser.showOpenDialog(mMainStage);
         if (file == null)
            return;
         
         mFileChooser.setInitialDirectory(file.getParentFile());
         if (file.toString().indexOf(".") == -1)
            file = new File(file+".txt");
         // gEditor.setText(read(file));
         // The following two lines ensure that scrollbars are displayed when
         // necessary.
         // gEditor.end();
         // gEditor.home();
         fileCurrent = file;
         // gEditor.home();
         // gEditor.requestFocus();
         mMainStage.setTitle(file.toString()+" - JPadFX");
         fDirty = false;
      }
      catch (Exception e) //(IOException ioe)
      {
         new Alert(mMainStage, "I/O error: "+e.getMessage()).show();
      }
   }
   private boolean doSave()
   {
      if (fileCurrent == null)
         return doSaveAs();
      try
      {
         //write(fileCurrent, gEditor.getText());
         fDirty = false;
         return true;
      }
      catch (Exception e) //(IOException ioe)
      {
         new Alert(mMainStage, "I/O error: "+e.getMessage()).show();
         return false;
      }
   }
   private boolean doSaveAs()
   {
      try
      {
         File file = mFileChooser.showSaveDialog(mMainStage);
         if (file == null)
            return false;
         mFileChooser.setInitialDirectory(file.getParentFile());
         if (file.getName().indexOf(".") == -1)
            file = new File(file+".txt");
         // write(file, gEditor.getText());
         fileCurrent = file;
         mMainStage.setTitle(file.toString()+" - JPadFX");
         fDirty = false;
         return true;
      }
      catch (Exception e) //(IOException ioe)
      {
         new Alert(mMainStage, "I/O error: "+e.getMessage()).show();
         return false;
      }
   }
   private String read(File f) throws IOException
   {
      try (FileReader fr = new FileReader(f))
      {
         char[] buf = new char[(int) f.length()];
         fr.read(buf);
         return new String(buf);
      }
   }
   private void write(File f, String text) throws IOException
   {
      try (FileWriter fw = new FileWriter(f);
           PrintWriter pw = new PrintWriter(fw))
      {
         pw.print(text);
      }
   }    
    
}
