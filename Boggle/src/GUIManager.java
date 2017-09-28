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
  private GameBoard board;
  private Player player = new Player();

  //What size board (true = 5x5, false = 4x4)
  private boolean boardSize;

  //Big or Small Boards to pass
  private Character[][] small;
  private Character[][] large;

  //Panes
  GridPane gBoard = new GridPane();

  //Timer
  private Text timer = new Text("Time Remaining: ");

  //Text
  private TextField wordchecker;
  private Text valid = new Text();
  private Text score = new Text();

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

  //Window Size
  private static final int WINDOW_WIDTH = 800;
  private static final int WINDOW_HEIGHT = 600;

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

    FlowPane flow = new FlowPane();
    flow.setStyle("-fx-background-color: gray;");

    VBox buttons = new VBox();
    buttons.setPadding(new Insets(10, 10, 10, 10));

    VBox guessedWords = new VBox();
    guessedWords.setPadding(new Insets(10, 10, 10, 10));

    scene.setRoot(flow);

    wordchecker = new TextField();
    wordchecker.setPromptText("Enter the word to check");
    wordchecker.setPrefColumnCount(20);

    check = new Button("Check Word!");
    check.setOnAction(this);

    smallBoard.setOnAction(this);
    largeBoard.setOnAction(this);
    reset.setOnAction(this);

    buttons.getChildren().addAll(wordchecker, check, smallBoard, largeBoard, reset, valid, score);

    flow.getChildren().addAll(buttons, gBoard);

    gBoard.setAlignment(Pos.CENTER);
    buttons.setAlignment(Pos.CENTER_LEFT);

    primaryStage.show();
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
    score.setText("Score: " + player.getScore());

    if (source == smallBoard)
    {
      board = new GameBoard(4, 4);
      small = board.getGameBoard();
      boardSize = false;
      displayBoard(gBoard);
      smallBoard.setDisable(true);
      largeBoard.setDisable(true);
    }
    else if (source == largeBoard)
    {
      board = new GameBoard(5, 5);
      large = board.getGameBoard();
      boardSize = true;
      displayBoard(gBoard);
      largeBoard.setDisable(true);
      smallBoard.setDisable(true);
    }
    else if (source == check)
    {
      if (dictionary.validWord(wordToCheck) && wordToCheck.length() >= 3)
      {
        if(!boardSize)
        {
          if (board.findWord(small, wordToCheck))
          {
            valid.setText("Valid Word!");
            valid.setFill(Color.GREEN);
            player.guessedWordList(wordToCheck);
            player.calculateScore(wordToCheck);
            score.setText("Score: " + player.getScore());
          }
          else
          {
            valid.setText("Not a Valid Word!");
            valid.setFill(Color.RED);
          }
        }
        else
        {
          if (board.findWord(large, wordToCheck))
          {
            valid.setText("Valid Word!");
            valid.setFill(Color.GREEN);
            player.guessedWordList(wordToCheck);
            player.calculateScore(wordToCheck);
            score.setText("Score: " + player.getScore());
          } else
          {
            valid.setText("Not a Valid Word!");
            valid.setFill(Color.DARKORANGE);
          }
        }
      }
      else
      {
        valid.setText("Not a valid word");
        if(wordToCheck.length() < 3)
        {
          valid.setText("Word has to be longer than 2 characters");
        }
        valid.setFill(Color.RED);
      }
    }
    else if (source == reset)
    {
      largeBoard.setDisable(false);
      smallBoard.setDisable(false);
      player.reset();
      score.setText("Score: " + player.getScore());
      clearBoard(gBoard);
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
    this.gBoard.setHgap(30);
    this.gBoard.setVgap(30);

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
  public void clearBoard(GridPane gboard)
  {
    gboard.getChildren().clear();
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
