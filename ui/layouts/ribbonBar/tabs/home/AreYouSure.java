package ui.layouts.ribbonBar.tabs.home;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBoxBuilder;
import javafx.scene.layout.VBoxBuilder;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AreYouSure extends Stage
{
   private EventHandler<ActionEvent> ehaeYes, ehaeNo;
   public AreYouSure(Stage owner, String msg)
   {
      setTitle("Are You Sure?");
      initOwner(owner);
      initStyle(StageStyle.UTILITY);
      initModality(Modality.APPLICATION_MODAL);
      Button btnYes = new Button("Yes");
      btnYes.setOnAction(new EventHandler<ActionEvent>()
                         {
                            @Override
                            public void handle(ActionEvent ae)
                            {
                               if (ehaeYes != null)
                                  ehaeYes.handle(ae);
                               close();
                            }
                         });
      Button btnNo = new Button("No");
      btnNo.setOnAction(new EventHandler<ActionEvent>()
                        {
                           @Override
                           public void handle(ActionEvent ae)
                           {
                              if (ehaeNo != null)
                                 ehaeNo.handle(ae);
                              close();
                           }
                        });
      btnNo.setPrefWidth(60.0);
      btnYes.setPrefWidth(60.0);
      HBoxBuilder hbb;
      hbb = HBoxBuilder.create()
                       .children(btnYes, btnNo)
                       .spacing(10);
      VBoxBuilder vbb;
      vbb = VBoxBuilder.create()
                       .children(new Label(msg), hbb.build())
                       .padding(new Insets(10.0))
                       .spacing(10.0)
                       .alignment(Pos.CENTER);
      Scene scene = new Scene(vbb.build());
      setScene(scene);
      sizeToScene();
      setResizable(false);
      show(); hide(); // needed to get proper value from scene.getWidth() and
                      // scene.getHeight()
      setX(owner.getX()+Math.abs(owner.getWidth()-scene.getWidth())/2.0);
      setY(owner.getY()+Math.abs(owner.getHeight()-scene.getHeight())/2.0);
   }
   public void setOnYes(EventHandler<ActionEvent> ehae)
   {
      ehaeYes = ehae;
   }
   public void setOnNo(EventHandler<ActionEvent> ehae)
   {
      ehaeNo = ehae;
   }
}
