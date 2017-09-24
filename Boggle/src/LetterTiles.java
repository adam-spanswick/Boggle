import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class LetterTiles extends StackPane
{
  public LetterTiles(Character name, double x, double y, double width, double height)
  {
    // create rectangle
    Rectangle rectangle = new Rectangle( width, height);
    rectangle.setStroke(Color.BLACK);
    rectangle.setFill(Color.LIGHTBLUE);

    // create label
    Label label = new Label(name.toString());

    // set position
    setTranslateX( x);
    setTranslateY( y);

    getChildren().addAll( rectangle, label);
  }
}
