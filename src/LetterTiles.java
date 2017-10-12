import com.sun.javafx.geom.RoundRectangle2D;
import javafx.event.ActionEvent;
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



//**********************************************************************************************************************
//Adam Spanswick
//
//This class is letter tiles that will be displayed on GUI. To use this class call the constructor with the desired letter
//for the tile, the x and y coordinates, and the height and width for the rectangles.
//**********************************************************************************************************************
public class LetterTiles extends StackPane
{
  private Label letter = new Label();
  private Character c;
  private Rectangle rectangle;
  private boolean visited;
  private int counter;

  //********************************************************************************************************************
  //Parameters:
  //  1. letter is the letter from the text board to be displayed
  //  2. x is x coordinate the value for the nodes transform
  //  3. y is y coordinate the value for the nodes transform
  //  4. width is the width of the rectangle
  //  5. height is the height of the rectangle
  //Is a constructor
  //The constructor creates each "tile" as a rectangle with a fixed height and width and sets the proper letter and sets
  //the tile to not visited and a counter to 1 to indicate how many times it has been clicked. Finally it adds all them
  //to the eventual pane.
  //********************************************************************************************************************
  public LetterTiles(Character letter, double x, double y, double width, double height)
  {
    rectangle = new Rectangle(width, height);
    rectangle.setArcHeight(15);
    rectangle.setArcWidth(15);
    rectangle.setStroke(Color.BLACK);
    rectangle.setFill(Color.LIGHTBLUE);

    //Store character for checking word
    this.c = letter;

    setLetter(letter);

    setTranslateX(x);
    setTranslateY(y);

    this.visited = false;
    this.counter = 1;

    getChildren().addAll(rectangle, this.letter);
  }

  //********************************************************************************************************************
  //Parameters:
  //  1. letter is the character for the tile
  //Method returns void
  //This method sets the global letter for thetile to the letter passed in, in the constructor.
  //********************************************************************************************************************
  public void setLetter(Character letter)
  {
    this.letter.setText(letter.toString());
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns a string
  //This method returns the letter of the tile as a string. It does this by creating a empty string then concatenating the
  //character to the string.
  //********************************************************************************************************************
  public String getLetter()
  {
    String temp = "";
    temp += c;
    return temp;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets the fill color of the tile to red.
  //********************************************************************************************************************
  public void setFillToRed()
  {
    this.rectangle.setFill(Color.RED);
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets the tile color to blue.
  //********************************************************************************************************************
  public void setFillToBlue()
  {
    this.rectangle.setFill(Color.LIGHTBLUE);
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method returns if the tile is visited or not.
  //********************************************************************************************************************
  public boolean getVisited()
  {
    return visited;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets that the tile has been visited or clicked on.
  //********************************************************************************************************************
  public void setVisited()
  {
      this.visited = true;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets the tile to not visited or clicked on.
  //********************************************************************************************************************
  public void setNotVisited()
  {
    this.visited = false;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method decrements the counter to show the tile has been clicked
  //********************************************************************************************************************
  public void decrementCounter()
  {
    this.counter--;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method returns the number of times the tile has been clicked
  //********************************************************************************************************************
  public int getCounter()
  {
    return counter;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets the counter back to 1
  //********************************************************************************************************************
  public void setCounter()
  {
    this.counter = 1;
  }

}
