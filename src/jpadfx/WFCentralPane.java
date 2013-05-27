/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpadfx;

import com.sun.javafx.scene.traversal.Direction;
import java.util.HashMap;
import java.util.Map;
import java.lang.IllegalArgumentException;
import java.util.LinkedList;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.RectangleBuilder;

/**
 *
 * @author a0082252
 */
public class WFCentralPane extends VBox {

    private Map<String, WFDrawPad> gDisplayArray = new HashMap<String, WFDrawPad>();
    private String gDisplayPrev = null;
    private String gDisplayCurr = null;
    private int gDisplayPrevIdx = -1;
    private Group mRoot;
    private VBox mLayout = VBoxBuilder.create().spacing(10).padding(new Insets(10)).build();
    private ContextMenu ctMenu = null;
//    LinkedList<WFDrawPad> mDraw = new LinkedList<WFDrawPad>();
    private Rectangle mCanvas = RectangleBuilder.create()
            .width(650).height(350)
            .fill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, new Stop[]{
        new Stop(1, Color.rgb(156, 216, 255)),
        new Stop(0, Color.rgb(156, 96, 255, 0.5))
    }))
            .stroke(Color.BLACK)
            .build();

    //Initialize a Button, and then Add a mouse listeners to response DragEvent    
    private final Button setupWidgetItem(Map.Entry<String, WFWidgetMap.widgetNode> inode) {
        final Button mWidgetItem = new Button();
        mWidgetItem.setTooltip(new Tooltip(inode.getKey()));
        mWidgetItem.setGraphic(new ImageView(inode.getValue().getVImage()));
        mWidgetItem.setPrefSize(40, 40);
        mWidgetItem.setId(inode.getKey());

        //t.setId("[-%d-%d]", i, j));
        mWidgetItem.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* allow any transfer mode */
                Dragboard db = mWidgetItem.startDragAndDrop(TransferMode.COPY);
                /* put a image on dragboard */
                ClipboardContent content = new ClipboardContent();
                String ButtonId = mWidgetItem.getId();
                content.putString(ButtonId);
                db.setContent(content);
                event.consume();
            }
        });
        mWidgetItem.setCursor(Cursor.HAND);
        return mWidgetItem;
    }

    private void configureItemType2() {
        VBox vb = VBoxBuilder.create().spacing(10).padding(new Insets(10, 0, 10, 0)).build();
//		AppLabel lbl = new AppLabel("#"+(itemCnt++)+" : Customised Scroller with menu direction to top and pref height");
//		lbl.getStyleClass().add("head-label");

//        final Scroller<Node> scroller = new Scroller<Node>();
//        scroller.setSpacing(6D);
//        scroller.setMinHeight(34);
//        scroller.setMenuDirection(Direction.UP);
//        scroller.setItems(getItemType2MockData(count_ItemType2, scroller));
//        scroller.getStyleClass().add("itemType2-scroller");
//        vb.getChildren().addAll(scroller);
//        mLayout.getChildren().add(vb);
    }

    public WFCentralPane(final Group root, Stage mStage, Application mApp) {
        mRoot = root;
        gDisplayPrevIdx = 0;
        if (gDisplayPrev != null) {
            mRoot.getChildren().remove(gDisplayArray.get(gDisplayPrev));
        }
        setupPane(mStage);
    }

    private void setupPane(Stage mStage) {

        final String[] DEFAULT = {"Editor-1", "Editor-2", "Editor-3"};

        //gDisplayArray.put("file3", new Group());
        gDisplayArray.put(DEFAULT[2], new WFDrawPad(DEFAULT[2], mStage, mCanvas));
        gDisplayArray.put(DEFAULT[1], new WFDrawPad(DEFAULT[1], mStage, mCanvas));
        gDisplayArray.put(DEFAULT[0], new WFDrawPad(DEFAULT[0], mStage, mCanvas));
        //gDisplayArray.put("file2", new Group());
        gDisplayCurr = DEFAULT[0];
        gDisplayPrev = DEFAULT[0];
        //gDisplayArray.put(gDisplayCurr, new Group());

        final Tab[] tabDocs = new Tab[]{TabBuilder.create().text(DEFAULT[2]).build(),
            TabBuilder.create().text(DEFAULT[1]).build(),
            TabBuilder.create().text(DEFAULT[0]).build()};
        for (final Tab tabEntry : tabDocs) {
            tabEntry.setId(tabEntry.getText());
            System.out.println("title:" + tabEntry.getText());
            tabEntry.selectedProperty().addListener(new ChangeListener<Boolean>() {
                public void changed(ObservableValue<? extends Boolean> ov,
                        Boolean tOld, Boolean tNew) {
//                    System.out.println(">>>>>>>>>>>>>");
//                    System.out.println("title:" + tabEntry.getText());
//                    System.out.println("titID:" + tabEntry.getId());
                    if (tOld == true) {
                        mRoot.getChildren().remove(gDisplayArray.get(tabEntry.getId()).getPad());
                    } else {
                        mRoot.getChildren().add(gDisplayArray.get(tabEntry.getId()).getPad());
                        gDisplayCurr = tabEntry.getId();
                    }
//                    System.out.println("OV:" + ov.getValue());
//                    System.out.println("tOld:" + tOld);
//                    System.out.println("tNew:" + tNew);
                }
            });
        }

        //root.getChildren().add(gDisplayArray.get(gDisplayCurr));      
        mCanvas.toBack();

        final TabPane tabDocToolBar = new TabPane();
        tabDocToolBar.getTabs().addAll(tabDocs);
        tabDocToolBar.setTabMinWidth(50);
        // When tab is selected, notify web page to save this in the
        // browser history

        class comTabPane extends TabPane {

            public comTabPane() {
                setPrefSize(100, 200);
                setSide(Side.TOP);
                setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
                setupTabItems();
            }

            private void setupTabItems() {
                GridPane tab1_gBox, tab2_gBox; // tab3_gBox, tab4_gBox;


                final Tab tab1 = TabBuilder.create().text("Data").build();
                final Tab tab2 = TabBuilder.create().text("Classify").build();
                final Tab tab3 = TabBuilder.create().text("Unsupervised").build();
                final Tab tab4 = TabBuilder.create().text("Supervisied").build();

                tab1.setContent(tab1_gBox = new GridPane());
                tab1_gBox.setPadding(new Insets(5));
                tab1_gBox.setHgap(5);
                tab1_gBox.setVgap(5);
                tab1_gBox.setPrefHeight(20);
                for (int i = 0; i < 1; i++) {
                    int j = 0;
                    for (Map.Entry<String, WFWidgetMap.widgetNode> entry
                            : (Set<Map.Entry<String, WFWidgetMap.widgetNode>>) WFWidgetMap.getWidgetByName("Data").entrySet()) {
                        Button inbox_widgetItem = setupWidgetItem(entry);
                        tab1_gBox.add(inbox_widgetItem, j * 3 + 1, i + 1);
                        j = j + 1;
                    }
                }

                tab2.setContent(tab2_gBox = new GridPane());
                tab2_gBox.setPadding(new Insets(5));
                tab2_gBox.setHgap(5);
                tab2_gBox.setVgap(5);
                tab2_gBox.setPrefHeight(20);
                for (int i = 0; i < 1; i++) {
                    int j = 0;
                    for (Map.Entry<String, WFWidgetMap.widgetNode> entry
                            : (Set<Map.Entry<String, WFWidgetMap.widgetNode>>) WFWidgetMap.getWidgetByName("Classify").entrySet()) {
                        Button inbox_widgetItem = setupWidgetItem(entry);
                        tab2_gBox.add(inbox_widgetItem, j * 3 + 1, i + 1);
                        j = j + 1;
                    }
                }

                ScrollPane scrollPane = ScrollPaneBuilder.create().styleClass("center-node")
                        .content(mLayout)
                        .styleClass("center-bg")
                        .fitToHeight(true)
                        .fitToWidth(true).build();
                scrollPane.setContent(mLayout);
                configureItemType2();
                tab3.setContent(scrollPane);

                this.getTabs().addAll(tab1, tab2, tab3, tab4);
            }
        }

        final comTabPane mToolBar = new comTabPane();

        this.getChildren().addAll(tabDocToolBar, mCanvas, mToolBar);
        mCanvas.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
//                System.out.println("DragOver-----------1");
                Dragboard db1 = event.getDragboard();

                if (db1.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY);
                }

                //event.consume();
            }
        });

        mCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Hiding ContextMenu");
                if (event.getButton().equals(MouseButton.PRIMARY)) {
                    if (ctMenu != null) {
                        ctMenu.hide();
                        ctMenu.getItems().remove(ctMenu.getItems());
                        ctMenu = null;
                    }
                }
            }
        });

        mCanvas.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                double axis_x = event.getX();
                double axis_y = event.getY();
                double abs_x = event.getSceneX();
                double abs_y = event.getSceneY();

                System.out.println("Dropped--1");
                Dragboard db = event.getDragboard();
                if (db.hasString()) {

                    //Pane mItem = setupWidgetBox(db.getString());
                    //String mItemId = db.getString();
                    Pane mItem = gDisplayArray.get(gDisplayCurr).setupWidgetBox(db.getString());
                    gDisplayArray.get(gDisplayCurr).insertVBox(abs_x, abs_y, mItem);
                    //insertVBox(abs_x, abs_y, gDisplayArray.get(gDisplayCurr), mItem);

                    //insertButtonHBox(db.getString(), targetBox);
                    System.out.println("Dropped--**************");
                    event.setDropCompleted(true);
                } else {
                    event.setDropCompleted(false);
                }
                System.out.println("--GestureDropped---");
                event.consume();
            }
        });

    }
//    private ObservableList<Node> getItemType2MockData(int cnt, final Scroller<Node> scroller) {
//        ObservableList<Node> list = FXCollections.observableArrayList();
//        for (int i = 1; i < cnt; i++) {
//            final ItemType2 btn = new ItemType2("Demo Button " + i);
//            list.add(btn);
//        }
//        return list;
//    }
}
