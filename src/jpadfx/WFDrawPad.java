/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jpadfx;

import java.util.HashMap;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Modality;

import javafx.scene.Scene;
import javafx.scene.control.Button;
//import javafx.scene.text.Text;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.layout.VBoxBuilder;
import javafx.scene.Group;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.PaneBuilder;
/**
 *
 * @author a0082252
 */
public class WFDrawPad {

    private ContextMenu ctMenu = null;
    private String gEditorStartNode;
    private String gEditorEndNode;
    private Map<String, Pane> graphNodes = new HashMap();
    private Map<Line, String[]> graphEdges = new HashMap();
    Map<String, Line> connection_map = new HashMap<String, Line>();
    //final WeakReference<
    private Line connection_tmp;
    private Line connection_final;
    private Group mDrawPad = null;
    private Rectangle canvas = null;
    private double initX;
    private double initY;
    private Point2D dragAnchor;
    private Point2D baseline;
    private Stage mInsStage;

    public WFDrawPad(String mID, Stage mStage, Rectangle mCanvas) {
        mDrawPad = new Group();
        canvas = mCanvas;
        this.mInsStage = mStage;
        baseline = new Point2D((double) 160, (double) 190);
    }

    public Group getPad() {
        return mDrawPad;
    }

    public void insertVBox(final double x, final double y, final Pane mbox) {
        if (x <0 || y <0|| mbox == null)
            throw new IllegalArgumentException("insertVBox"+x+y+mbox);
        
        this.mDrawPad.getChildren().add(mbox);

        mbox.setTranslateX(x);
        mbox.setTranslateY(y);
        System.out.println("[]x-y" + x + ";" + y);
        System.out.println("[]Tx-Ty" + mbox.getTranslateX() + ";" + mbox.getTranslateY());
        //System.out.println("[]Tx-Ty" +mbox.getChildren().get(1).getTranslateX() +";" +mbox.getChildren().get(1).getTranslateY());
        return;
    }

    abstract public class AbstractJGDialog {

        private Button btn;
        private Group root;
        final private Stage stage = new Stage();

        public AbstractJGDialog(final Stage stg) {

            root = new Group();

            btn = ButtonBuilder.create().layoutX(100.0D).layoutY(80.0D)
                    .text("OK").onAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    stage.hide();
                }
            }).build();

            //Initialize the Stage with type of modal
            stage.initModality(Modality.APPLICATION_MODAL);
            //Set the owner of the Stage 
            stage.initOwner(stg);
            stage.setTitle("Top Stage With Modality");
            stage.setScene(new Scene(root, 300, 250, Color.LIGHTGREEN));
            stage.show();
        }

        public void openMenu() {
            root.getChildren().add(btn);
            Pane temp = (Pane) itemized();
            root.getChildren().add(temp);
        }

        abstract public Pane itemized();
    }

    final Circle setupLinkPoint(String element, boolean flagOrient) {
        final Circle mLinker = CircleBuilder.create().radius(6).fill(Color.WHITE).stroke(Color.BLACK).build();

        if (flagOrient) {  // OutBound Connection Point
            mLinker.setFill(Color.RED);
            mLinker.setId(element + "=Outport");
            mLinker.getProperties().put("name", element + "=output");

            System.out.println("***********7777*******");
            mLinker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println(">>>>>setOnMouseClicked:" + mLinker.getId());
                }
            });

            mLinker.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    //captured = true;
                    System.out.println(">>>>>setOnMousePressed:" + mLinker.getId());
//                    System.out.println(">>>>>setOnMousePressed:" + event.getSceneX() + event.getSceneY());

                    mLinker.getProperties().put("CenterX", mLinker.getParent().getParent().getTranslateX() + mLinker.getScene().getX() + mLinker.getCenterX());
                    mLinker.getProperties().put("CenterY", mLinker.getParent().getParent().getTranslateY() + mLinker.getScene().getY() + mLinker.getCenterY());

                    Point2D Start = new Point2D(Double.valueOf((Double) mLinker.getProperties().get("CenterX")),
                            Double.valueOf((Double) mLinker.getProperties().get("CenterY")));
                    Point2D centerPoint = new Point2D(mLinker.getParent().getTranslateX(), mLinker.getParent().getTranslateY());

                    Point2D centerPoint3 = new Point2D(mLinker.getTranslateX(), mLinker.getTranslateY());
                    Point2D centerPoint2 = new Point2D(centerPoint.getX() + centerPoint3.getX(),
                            centerPoint.getY() + centerPoint3.getY());

                    String temp = mLinker.getProperties().get("name").toString().split("=")[0];

                    gEditorStartNode = mLinker.getId().toString().split("=")[0] + "=ExampleTable";
                    connection_tmp = LineBuilder.create().stroke(Color.BLACK).strokeWidth(2.0)
                            .startX(centerPoint.getX() + centerPoint3.getX())
                            .startY(centerPoint.getY() + centerPoint3.getY())
                            .endX(centerPoint.getX() + centerPoint3.getX())
                            .endY(centerPoint.getY() + centerPoint3.getY())
                            .build();

                    connection_final =
                            LineBuilder.create().stroke(Color.BLACK).strokeWidth(2.0)
                            .startX(centerPoint2.getX())
                            .startY(centerPoint2.getY())
                            .endX(centerPoint2.getX())
                            .endY(centerPoint2.getY())
                            .build();

                    connection_final.setUserData(centerPoint2);

                    mDrawPad.getChildren().add(connection_tmp);
                    mDrawPad.getChildren().add(connection_final);
                    event.consume();
                }
            });
            
            mLinker.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    System.out.println(">>>>>setOnMouseReleased:" + mLinker.getId());
                    System.out.println(">>>>>setOnMouseReleased:" + me.getX() + ";" + me.getY());
                    mDrawPad.getChildren().remove(connection_tmp);
                    //gEditor.getChildren().remove(connection_final);
                }
            });

            mLinker.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    //System.out.println(">>>>>setOnMouseDragged---------:" + mLinker.getId());

                    connection_tmp.setEndX(me.getSceneX());
                    connection_tmp.setEndY(me.getSceneY());
                    //System.out.println(">--->>setOnMouseDragged:");
                    //connection_final.setEndX(me.getSceneX());
                    //connection_final.setEndY(me.getSceneY() - gEditor.getScene().getY() + 5); // ;+ me.getY()); //// );                    
                    //System.out.println(mLinker.getProperties().get("name") + ">> setOnMouseDragged___" + me.getSceneX());                    
                }
            });

        }
        if (flagOrient == false) {  //Inbound Connection Point
            mLinker.setFill(Color.GREY);
            mLinker.setId(element + "=Inport");
            mLinker.getProperties().put("name", element + "=input");
            System.out.println("***********999*******");
//            mLinker.setOnMouseEntered(new EventHandler<MouseEvent>() {
//                @Override                
//                public void handle(MouseEvent me) {
//                    System.out.println(">>>>>setOnMouseEntered:" + mLinker.getId()); 
//                    System.out.println(">>>>>setOnMouseEntered:" + me.getX() +";" + me.getY());
//                }
//            });           

            mLinker.setOnMouseExited(new EventHandler<MouseEvent>() {
                
                @Override
                public void handle(MouseEvent me) {
                    if (connection_final == null) {
                        return;
                    }
                    System.out.println(">>>>>setOnMouseExited:" + mLinker.getId());
                    System.out.println(">>>>>setOnMouseExited:" + me.getX() + ";" + me.getY());
                    Point2D centerPoint = new Point2D(mLinker.getParent().getTranslateX(), mLinker.getParent().getTranslateY());
                    Point2D centerPoint3 = new Point2D(mLinker.getTranslateX(), mLinker.getTranslateY());
                    //System.out.println(connection_final.toString());
                    connection_final.setEndX(centerPoint.getX() + centerPoint3.getX());
                    connection_final.setEndY(centerPoint.getY() + centerPoint3.getY()); // ;+ me.getY()); //// );
                    System.out.println("Adding ContextMenu>>>>>" + connection_final.toString());
                    //gDisplayArray.get(gDisplayCurr).getChildren().add(connection_final);

                    String temp = mLinker.getId().toString().split("=")[0];
                    gEditorEndNode = mLinker.getId().toString().split("=")[0] + "=Table";

                    //cm = new ContextMenu();

                    connection_final.setPickOnBounds(true);
                    connection_final.addEventHandler(MouseEvent.MOUSE_CLICKED,
                            new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent e) {
                            System.out.print(e.getSource().toString());
                            System.out.println("connection_final: clicked");
                            if (e.getButton().equals(MouseButton.SECONDARY)) {
                                ctMenu = new ContextMenu();
                                final MenuItem cmItem1 = new MenuItem("Delete");
                                cmItem1.getProperties().put("conn", e.getSource());
                                cmItem1.setOnAction(new EventHandler<ActionEvent>() {
                                    public void handle(ActionEvent e) {
                                        mDrawPad.getChildren().remove(cmItem1.getProperties().get("conn"));
                                        graphEdges.remove(cmItem1.getProperties().get("conn"));
                                    }
                                });
                                ctMenu.getItems().addAll(cmItem1);
                                System.out.println("connection_final: clicked -->>");
                                ctMenu.show(canvas, e.getScreenX(), e.getScreenY());
                            }
                        }
                    });
                    connection_map.put(gEditorEndNode.split("=")[0], connection_final);
                    connection_map.put(gEditorStartNode.split("=")[0], connection_final);
                    graphEdges.put(connection_final, new String[]{gEditorStartNode, gEditorEndNode});
                    connection_tmp = null;
                    connection_final = null;
                }
            });
            mLinker.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent me) {
                    System.out.println(">>>>>setOnMouseReleased:" + mLinker.getId());
                    System.out.println(">>>>>setOnMouseReleased:" + me.getX() + ";" + me.getY());
                }
            });
        }
        return mLinker;
    }

    final Pane setupWidgetBox(String element) {
        final Pane mPane = new Pane();
        int seqId = 1;
        //String paneId = Integer.toString(seqId) + "." + element;
        String paneId = Integer.toString(seqId) + "." + element;
        while (seqId < 30 * graphNodes.size()) {
            if (graphNodes.containsKey(paneId)) {
                seqId += 1;
                paneId = Integer.toString(seqId) + "." + element;
            } else {
                break;
            }
        }

        final Circle mlinker1 = setupLinkPoint(paneId, false);
        final Circle mlinker2 = setupLinkPoint(paneId, true);

        mlinker1.setTranslateX(5);
        mlinker1.setTranslateY(20);
        mlinker2.setTranslateX(65);
        mlinker2.setTranslateY(20);
        final Rectangle mLabel = RectangleBuilder.create()
                .width(40).height(45).fill(Color.WHITE)
                .stroke(Color.BLACK)
                .build();
        mLabel.setTranslateX(15);
        mLabel.toBack();
        //mLabel.setOpacity(0.1);

        //mRect2.setTranslateX(0);
        final Label mText = new Label();
        mText.setTranslateX(10);
        mText.setTranslateY(50);
        //mText.toFront();
        mText.setTextFill(Color.web("#0076a3"));
        mText.setFont(Font.font("Verdana", 10));

        mText.setContentDisplay(ContentDisplay.TOP);
        WFWidgetMap.widgetNode m = (WFWidgetMap.widgetNode) WFWidgetMap.getWidgetAll().get(element);
        final ImageView mIcon = new ImageView(((WFWidgetMap.widgetNode) WFWidgetMap.getWidgetAll().get(element)).getVImage());
        mIcon.setTranslateX(15);
        mIcon.setTranslateY(5);
        //mLabel.setGraphicTextGap(3.8);
        //mLabel.setGraphic();
        //mLabel.setTextAlignment(TextAlignment.JUSTIFY);
        //mLabel.setWrapText(true);
        //Label.setPrefSize(60, 80);
        mLabel.setWidth(40);
        //mLabel.setTextAlignment(TextAlignment.CENTER);
        mLabel.setTranslateX(15);
        mLabel.setTranslateY(0);
        //mLabel.setOpacity(1);
        mIcon.toFront();
        mIcon.setMouseTransparent(true);
        //mIcon.setTranslateX(0);
        mPane.getChildren().addAll(mLabel, mlinker1, mlinker2, mText, mIcon);
        mPane.getProperties().put("in_linker", new double[]{5.0, 20.0});
        mPane.getProperties().put("out_linker", new double[]{65.0, 20.0});
        mPane.setId(paneId);
        mPane.getProperties().put("name", element);

        graphNodes.put(paneId, mPane);
        System.out.println("pane:" + paneId + "is loaded");

        String paneName = element.length() < 11 ? element : element.substring(0, 10);
        mText.setText(Integer.toString(seqId) + "." + paneName);
        
        mLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent meve) {
                System.out.println("Clicked on [" + mPane.getId() + "], " + meve.getClickCount() + "times");

                if (meve.getButton().equals(MouseButton.PRIMARY)) {
                    if (meve.getClickCount() == 2) {
                        System.out.println("Double clicked");
                        //JGDialog md = new JGDialog(mStage);
                        new AbstractJGDialog(mInsStage) {
                            public Pane itemized() {
                                return PaneBuilder.create().id("null").build();
                            }
                        }.openMenu();

                    }
                }
                meve.consume();
            }
        });
        
        mLabel.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                // these two coordinates wont change in the context
                final double dragX = me.getSceneX() - dragAnchor.getX();
                final double dragY = me.getSceneY() - dragAnchor.getY();
                //calculate new position of the circle
                double newXPosition = initX + dragX;
                double newYPosition = initY + dragY;
                String nameNode = (String) mLabel.getParent().getId();
//                System.out.println("NodeName:" + nameNode);
                //if new position do not exceeds borders of the rectangle, translate to this position

                if (newXPosition >= baseline.getX())//0.5* mBox.getWidth() - gEditor.getScene().getX() {
                {
                    newXPosition = Math.min(canvas.getWidth() + baseline.getX() - 110, newXPosition);
                }

                if (newXPosition <= canvas.getWidth() + baseline.getX()) {  // - mPane.getWidth()
                    newXPosition = Math.max(baseline.getX(), newXPosition);
                }

                if (newYPosition >= baseline.getY())//0.5* mBox.getWidth() - gEditor.getScene().getX() {
                {
                    newYPosition = Math.min(canvas.getHeight() + baseline.getY() - 70, newYPosition); //- 0.5 * mPane.getHeight()
                }
                if (newYPosition <= canvas.getHeight() + baseline.getY()) {
                    newYPosition = Math.max(baseline.getY(), newYPosition);
                }

                mPane.setTranslateX(newXPosition);
                mPane.setTranslateY(newYPosition);

                for (Map.Entry<Line, String[]> entry : graphEdges.entrySet()) {
                    
//                    System.out.println(entry.getValue()[0]);
//                    System.out.println(entry.getValue()[1]);
                    if (entry.getValue()[0].startsWith(nameNode)) {
                        entry.getKey().setStartX(mPane.getTranslateX() + ((double[]) mPane.getProperties().get("out_linker"))[0]);
                        entry.getKey().setStartY(mPane.getTranslateY() + ((double[]) mPane.getProperties().get("out_linker"))[1]);
                    }
                    if (entry.getValue()[1].startsWith(nameNode)) {
                        entry.getKey().setEndX(mPane.getTranslateX() + ((double[]) mPane.getProperties().get("in_linker"))[0]);
                        entry.getKey().setEndY(mPane.getTranslateY() + ((double[]) mPane.getProperties().get("in_linker"))[1]);
                    }
                }
            }
        });

        mLabel.setOnMouseExited(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //showOnConsole("Mouse exited " + mBox.getProperties().get("name"));
            }
        });
        mLabel.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                //when mouse is pressed, store initial position
                initX = mPane.getTranslateX(); //.getTranslateX();
                initY = mPane.getTranslateY(); //.getScene().getY() - gEditor.getScene().getY(); // - gEditor.getScene().getY();
                dragAnchor = new Point2D(me.getSceneX(), me.getSceneY());
                //showOnConsole("Mouse pressed above " + mBox.getProperties().get("name"));
            }
        });
        mlinker1.toFront();
        mlinker2.toFront();
        return mPane;
    }
}
