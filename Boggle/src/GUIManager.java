import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIManager extends Application implements EventHandler<ActionEvent>
{
  //Objects
  private Dictionary dictionary = new Dictionary();
  private GameBoard board = new GameBoard(4,4);

  //Panes
  GridPane gBoard = new GridPane();

  //Text
  private TextField wordchecker;
  private Text input = new Text();

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

    buttons.getChildren().addAll(wordchecker, check, input, smallBoard, largeBoard, reset);

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
      input.setText("The word is in the dictionary!");
      input.setFill(Color.BLACK);
    }
    else
    {
      input.setText("Not in Dictionary: " + word);
      input.setFill(Color.RED);
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
    char[] inputWord = wordToCheck.toCharArray();
    board.validWord(inputWord, board.getBoard());

    if(source == check)
    {
      if (dictionary.validWord(wordToCheck))
      {
        checkWord(wordToCheck);
      } else
      {
        checkWord(wordToCheck);
      }
    }
    else if(source == smallBoard)
    {
      System.out.println("4x4");
      board = new GameBoard(4,4);
      board.populateBoard();
      displayBoard(gBoard);
      smallBoard.setDisable(true);
      largeBoard.setDisable(true);
    }
    else if(source == largeBoard)
    {
      System.out.println("5x5");
      board = new GameBoard(5,5);
      board.populateBoard();
      displayBoard(gBoard);
      largeBoard.setDisable(true);
      smallBoard.setDisable(true);
    }
    else if(source == reset)
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

    for(int r = 0; r < board.getBoard().length; r++)
    {
      for(int c = 0; c < board.getBoard()[r].length; c++)
      {
        LetterTiles tile = new LetterTiles(board.getBoard()[r][c], r * gWidth, c * gHeight, gWidth, gHeight);
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
