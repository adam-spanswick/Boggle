import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUIManager extends Application implements EventHandler<ActionEvent>
{
  //Objects
  private Dictionary dictionary = new Dictionary();
  private GameBoard board = new GameBoard(4,4);

  //What size board (true = 5x5, false = 4x4)
  private boolean boardSize;

  //Big or Small Boards to pass
  private Character[][] small;
  private Character[][] large;

  //Panes
  GridPane gBoard = new GridPane();

  //Text
  private TextField wordchecker;
  private Text valid = new Text();
  private Text inDict = new Text();

  //Buttons
  private Button check;
  private Button smallBoard = new Button("Click for 4x4 Board");
  private Button largeBoard = new Button("Click for 5x5 Board");
  private Button reset = new Button("Reset Game");

  //Scene Size
  private double sWidth = 300;
  private double sHeight = 300;

  //Grid Size
  private double gWidth = sWidth / 4;
  private double gHeight = sHeight / 4;

  //Board
  LetterTiles[][] field = new LetterTiles[4][4];

  //Window Size
  private static final int WINDOW_WIDTH = 1000;
  private static final int WINDOW_HEIGHT = 800;


  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  @Override
  public void start(Stage primaryStage)
  {
    Group root = new Group();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.AQUA);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Boggle");
//    primaryStage.setResizable(false);

    BorderPane pane = new BorderPane();

    VBox buttons = new VBox();
    buttons.setPadding(new Insets(10, 10, 10, 10));

    scene.setRoot(pane);

    wordchecker = new TextField();
    wordchecker.setPromptText("Enter the word to check");
    wordchecker.setPrefColumnCount(20);

    check = new Button("Check Word!");
    check.setOnAction(this);

    smallBoard.setOnAction(this);
    largeBoard.setOnAction(this);

    buttons.getChildren().addAll(wordchecker, check, valid, inDict, smallBoard, largeBoard, reset);

    pane.setCenter(gBoard);
    gBoard.setAlignment(Pos.CENTER_LEFT);
    pane.setLeft(buttons);
    buttons.setAlignment(Pos.CENTER_LEFT);

    primaryStage.show();
  }

  public void checkWord(String word)
  {
    if (dictionary.validWord(word))
    {
      inDict.setText("The word is in the dictionary!");
      inDict.setFill(Color.BLACK);
    }
    else
    {
      inDict.setText("Not in Dictionary: " + word);
      inDict.setFill(Color.RED);
    }
  }


  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  @Override
  public void handle(ActionEvent event)
  {
    Object source = event.getSource();
    String wordToCheck = wordchecker.getText();

    if (source == smallBoard)
    {
      System.out.println("4x4");
      board = new GameBoard(4, 4);
      board.populateBoard();
      small = board.getGameBoard();
      boardSize = false;
      displayBoard(gBoard);
      smallBoard.setDisable(true);
      largeBoard.setDisable(true);
    }
    else if (source == largeBoard)
    {
      System.out.println("5x5");
      board = new GameBoard(5, 5);
      board.populateBoard();
      large = board.getGameBoard();
      boardSize = true;
      displayBoard(gBoard);
      largeBoard.setDisable(true);
      smallBoard.setDisable(true);
    }
    else if (source == check)
    {
      if (dictionary.validWord(wordToCheck))
      {
        if(!boardSize)
        {
          if (board.findWord(small, wordToCheck))
          {
            System.out.println("Valid Word");
            valid.setText("Valid Word!");
            valid.setFill(Color.DARKBLUE);
          }
          else
          {
            System.out.println("Not a Valid Word 4x4");
            valid.setText("Not a Valid Word!");
            valid.setFill(Color.RED);
          }
        }
        else
        {
          if (board.findWord(large, wordToCheck))
          {

            System.out.println("Valid Word");
            valid.setText("Valid Word!");
            valid.setFill(Color.DARKCYAN);
            inDict.setText("The word is in the dictionary!");
            inDict.setFill(Color.BLACK);
          } else
          {
            System.out.println("Not a Valid Word");
            valid.setText("Not a Valid Word!");
            valid.setFill(Color.DARKORANGE);
            inDict.setText("Not in Dictionary: " + wordToCheck);
            inDict.setFill(Color.RED);
          }
        }
      }
      else
      {
        inDict.setText("Not in Dictionary: " + wordToCheck);
        inDict.setFill(Color.RED);
      }
    }
    else if (source == reset)
    {
      largeBoard.setDisable(false);
      smallBoard.setDisable(false);
    }
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void displayBoard(GridPane gBoard)
  {
    gBoard.setHgap(10);
    gBoard.setVgap(10);

    for(int r = 0; r < board.getGameBoard().length; r++)
    {
      for(int c = 0; c < board.getGameBoard()[r].length; c++)
      {
        LetterTiles tile = new LetterTiles(board.getGameBoard()[r][c], r * gWidth, c * gHeight, gWidth, gHeight);
        gBoard.getChildren().add(tile);
      }
    }
  }


  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public static void main(String[] args)
  {
    launch(args);
  }

}
