import com.sun.javafx.geom.RoundRectangle2D;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
  private Label letter = new Label();
  private Character c;
  private Rectangle rectangle;
  private boolean visited;

  public LetterTiles(Character letter, double x, double y, double width, double height)
  {
    rectangle = new Rectangle(width, height);
    rectangle.setArcHeight(15);
    rectangle.setArcWidth(15);
    rectangle.setStroke(Color.BLACK);
    rectangle.setFill(Color.DARKKHAKI);

    //Store character for checking word
    this.c = letter;

    setLetter(letter);

    setTranslateX(x);
    setTranslateY(y);

    this.visited = false;

    getChildren().addAll(rectangle, this.letter);
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void setLetter(Character letter)
  {
    this.letter.setText(letter.toString());
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public Character getLetter()
  {
    return c;
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void setFillToRed()
  {
    this.rectangle.setFill(Color.RED);
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void setFillToBlue()
  {
    this.rectangle.setFill(Color.LIGHTBLUE);
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public boolean getVisited()
  {
    return visited;
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void setVisited()
  {
    this.visited = true;
  }








  public static class DragSelectionCell extends TableCell<Person, String> {

    public DragSelectionCell() {
      setOnDragDetected(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          startFullDrag();
          getTableColumn().getTableView().getSelectionModel().select(getIndex(), getTableColumn());
        }
      });
      setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {

        @Override
        public void handle(MouseDragEvent event) {
          getTableColumn().getTableView().getSelectionModel().select(getIndex(), getTableColumn());
        }

      });
    }


















  }
