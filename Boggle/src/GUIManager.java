import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;


public class GUIManager extends Application implements EventHandler<ActionEvent>
{
  //Word captured from mouse drag
  private String wordTocheck = "";

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
  HBox timerBox = new HBox();
  VBox buttons = new VBox();
  VBox leftPane = new VBox();
  VBox rightPane = new VBox();
  VBox topPane = new VBox();

  //Timer
  private Integer STARTTIME = 180;
  private Timeline timeline;
  private Label timerLabel = new Label();
  private Integer timeInSeconds = STARTTIME;

  //Text
  private Text valid = new Text();
  private Text score = new Text();
  private Text guesses = new Text();
  private Text isVisited = new Text();

  //Buttons
  private Button smallBoard = new Button("4x4 Game");
  private Button largeBoard = new Button("5x5 Game");
  private Button reset = new Button("Reset Game");

  //Text Game Board
  private ArrayList<LetterTiles> tilesToChangeColor = new ArrayList<>();
  private LetterTiles[][] temp;

  //Scene Size
  private double sWidth = 300;
  private double sHeight = 300;

  //Grid Size
  private double gWidth = sWidth / 4;
  private double gHeight = sHeight / 4;

  //Window Size
  private static final int WINDOW_WIDTH = 550;
  private static final int WINDOW_HEIGHT = 750;

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
    primaryStage.setResizable(false);

    FlowPane mainPane = new FlowPane();
    mainPane.setStyle("-fx-background-color: darkkhaki;");

    topPane.setStyle("-fx-background-color: darkkhaki;");

    buttons.setPadding(new Insets(10, 10, 10, 10));

    scene.setRoot(topPane);

    smallBoard.setOnAction(this);
    largeBoard.setOnAction(this);
    reset.setOnAction(this);

    buttons.getChildren().addAll(smallBoard, largeBoard, reset, valid, timerLabel, score, guesses, isVisited);
    buttons.setSpacing(10);

    leftPane.getChildren().add(buttons);
    rightPane.getChildren().add(gBoard);

    topPane.setSpacing(10);
    topPane.getChildren().addAll(leftPane, rightPane);
    topPane.setPadding(new Insets(10, 100, 100, 100));

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
    score.setText("Score: " + player.getScore());

    if (source == smallBoard)
    {
      startTimer();
      board = new GameBoard(4, 4);
      temp = new LetterTiles[4][4];
      small = board.getGameBoard();
      boardSize = false;
      displayBoard(gBoard);
      smallBoard.setDisable(true);
      largeBoard.setDisable(true);
    }
    else if (source == largeBoard)
    {
      startTimer();
      board = new GameBoard(5, 5);
      temp = new LetterTiles[5][5];
      large = board.getGameBoard();
      boardSize = true;
      displayBoard(gBoard);
      largeBoard.setDisable(true);
      smallBoard.setDisable(true);
    }
    else if (source == reset || timerLabel.equals(0))
    {
      resetGame();
    }
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void displayBoard(GridPane gBoard)
  {
    for(int r = 0; r < board.getGameBoard().length; r++)
    {
      for(int c = 0; c < board.getGameBoard()[r].length; c++)
      {
        LetterTiles tile = new LetterTiles(board.getGameBoard()[r][c], r * gWidth, c * gHeight, gWidth, gHeight);

        tile.setOnDragDetected(new EventHandler<MouseEvent>()
        {
          @Override
          public void handle(MouseEvent event)
          {
            Dragboard db = tile.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            content.putString(tile.getLetter());
            db.setContent(content);

            tilesToChangeColor.add(tile);

            if(tile.getVisited() == false)
            {
              tile.setFillToRed();
            }
            else
            {
              isVisited.setText("Invalid Move");
              isVisited.setFill(Color.RED);
            }
            event.consume();
          }
        });

        tile.setOnDragEntered(new EventHandler<DragEvent>() {
          @Override
          public void handle(DragEvent event)
          {
            event.acceptTransferModes(TransferMode.COPY);
            Dragboard db = event.getDragboard();
            ClipboardContent content = new ClipboardContent();
            content.putString(content + tile.getLetter());
            db.setContent(content);

            tilesToChangeColor.add(tile);

            if(tile.getVisited() == false && tile.getCounter() == 1)
            {
              wordTocheck += tile.getLetter();
              tile.decrementCounter();
              tile.setFillToRed();
            }
            event.consume();
          }
        });

        tile.setOnMouseDragExited(new EventHandler<MouseDragEvent>() {

          @Override
          public void handle(MouseDragEvent event) {

            if(tile.getVisited() == true)
            {
              isVisited.setText("Invalid Move");
              isVisited.setFill(Color.RED);
            }
            event.consume();
          }
        });

        tile.setOnDragDone(new EventHandler<DragEvent>()
        {
          @Override
          public void handle(DragEvent event)
          {
            if (tile.getVisited() == false)
            {
              tile.setFillToRed();
              tile.setVisited();

              System.out.println(wordTocheck);

              if (dictionary.validWord(wordTocheck))
              {
                player.calculateScore(wordTocheck);
                player.guessedWordList(wordTocheck);
                score.setText("Score: " + player.getScore());
                guesses.setText("Guessed Words:" + player.getGuessedWords());
              }
              resetWordToCheck();
            }
            else
            {
              isVisited.setText("Invalid Move");
              isVisited.setFill(Color.RED);
            }

            for(LetterTiles t: tilesToChangeColor)
            {
              t.setNotVisited();
              t.setFillToBlue();
              t.setCounter();
            }
            resetWordToCheck();
            event.consume();
          }
        });

        temp[r][c] = tile;
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
  private void resetWordToCheck()
  {
    wordTocheck = "";
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void startTimer()
  {
//    timerBox.getChildren().add(timerLabel);
    timerLabel.setText("Time Remaining: " + timeInSeconds.toString());
    timeline = new Timeline();
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>()
            {
              @Override
              public void handle(ActionEvent event)
              {
                timeInSeconds--;
                timerLabel.setText("Time Remaining: " + timeInSeconds.toString());
                if(timeInSeconds <= 0)
                {
                  resetGame();
                  timeline.stop();
                }
              }
            }));
    timeline.playFromStart();
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void resetTimer()
  {
    STARTTIME = 180;
    timeInSeconds = STARTTIME;
    timerBox.getChildren().clear();
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void resetGame()
  {
    valid.setText("");
    isVisited.setText("");
    largeBoard.setDisable(false);
    smallBoard.setDisable(false);
    player.reset();
    score.setText("Score: " + player.getScore());
    guesses.setText("Guessed Words:" + player.getGuessedWords());
    resetTimer();
    clearBoard(gBoard);
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void clearBoard(GridPane gboard)
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
