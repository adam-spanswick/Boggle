import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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

//**********************************************************************************************************************
//Adam Spanswick
//
//This class is the main controller for the game. It sets up all the necessary objects and bookkeeping structures needed to
//play the game. To use the class run main.
//If the program freezes when you try to play a game please rerun it. IT will work sometimes ot takes a couple times. I
//could not figure out how to fix this bug.
//**********************************************************************************************************************
public class Controller extends Application implements EventHandler<ActionEvent>
{
  //Invalid Words
  private ArrayList<String> invalidWords = new ArrayList<>();

  //Word captured from mouse drag
  private String wordTocheck = "";

  //Objects
  private Dictionary dictionary = new Dictionary();
  private GameBoard board;
  private Player player = new Player();

  //What size board (true = 5x5, false = 4x4)
  private boolean boardSize;

  //Panes
  GridPane gBoard = new GridPane();
  HBox timerBox = new HBox();
  VBox buttons = new VBox();
  VBox leftPane = new VBox();
  VBox rightPane = new VBox();
  VBox topPane = new VBox();

  //Timer
  private final Integer START_TIME = 180;
  private Timeline timeline;
  private Label timerLabel = new Label();
  private Integer timeInSeconds = START_TIME;

  //Text fields
  private Text valid = new Text();
  private Text score = new Text();
  private Text guesses = new Text();
  private Text isVisited = new Text();
  private Text badGuesses = new Text();

  //Buttons
  private Button smallBoard = new Button("4x4 Game");
  private Button largeBoard = new Button("5x5 Game");
  private Button reset = new Button("Reset Game");
  private Button checkWord = new Button("Check Word");
  private Button resetWord = new Button("Clear Word");

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
  private static final int WINDOW_HEIGHT = 775;

  //********************************************************************************************************************
  //Parameters:
  //  1. primaryStage is the stage for JavaFX
  //Method returns void
  //start organizes all the nodes needed for the GUI. It adds children, spacing and listeners to the panes then displays
  //them.
  //********************************************************************************************************************
  @Override
  public void start(Stage primaryStage)
  {
    Group root = new Group();
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.AQUA);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Boggle");
    primaryStage.setResizable(false);

    topPane.setStyle("-fx-background-color: darkkhaki;");

    buttons.setPadding(new Insets(10, 10, 10, 10));

    scene.setRoot(topPane);

    smallBoard.setOnAction(this);
    largeBoard.setOnAction(this);
    reset.setOnAction(this);
    checkWord.setOnAction(this);
    resetWord.setOnAction(this);

    guesses.setWrappingWidth(400);

    buttons.getChildren().addAll(smallBoard, largeBoard, reset, checkWord, resetWord, valid, timerBox, score, guesses, badGuesses, isVisited);
    buttons.setSpacing(10);

    leftPane.getChildren().add(buttons);
    rightPane.getChildren().add(gBoard);

    topPane.setSpacing(10);
    topPane.getChildren().addAll(leftPane, rightPane);
    topPane.setPadding(new Insets(10, 100, 100, 100));

    primaryStage.show();
  }

  //********************************************************************************************************************
  //Parameters:
  //  1. event is the action event that will be implemented
  //Method returns void
  //handle takes in different events from the GUI and passes them to the proper component to be handled. It does this by
  //checking the source of the event against the buttons such as 4x4 game, 5x5 game, reset game and check word. Then calls
  //the proper methods to set up the correct game size, display the tray and calculate the score. At the end it resets
  //the game back to its starting state to be played again.
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
      boardSize = true;
      displayBoard(gBoard);
      largeBoard.setDisable(true);
      smallBoard.setDisable(true);
    }
    else if (source == checkWord)
    {
      if (dictionary.validWord(wordTocheck))
      {
        player.calculateScore(wordTocheck);
        player.guessedWordList(wordTocheck);
        score.setText("Score: " + player.getScore());
        guesses.setText("Valid Guessed Words:" + player.getGuessedWords());
        guesses.setFill(Color.BLACK);
        resetTiles();
      }
      else
      {
        invalidWords.add(wordTocheck);
        badGuesses.setText("Invalid Guessed Words:" + invalidWords);
        badGuesses.setFill(Color.RED);
        resetTiles();
      }
      resetWordToCheck();
    }
    else if(source == resetWord)
    {
      resetWordToCheck();
      resetTiles();
    }
    else if (source == reset || timerLabel.equals(0))
    {
      resetGame();
    }
  }

  //********************************************************************************************************************
  //Parameters:
  //  1. gBoard is a gridPane that represents the tray of letters
  //Method returns void
  //This method loops over the text based game board and creates a new LetterTile object for each space and sets that tile
  //to the correct letter from the text board. It also adds a mouse clicked listener to each tile that first adds a tile
  //when it is clicked to a arraylist that will be used to change the tile colors back after a word is submitted. Then,
  //if the tile has never been clicked on and the tile counter, used to repeat letters, is still 1, never been clicked on,
  //the string wordToCheck is updated with that letter, the counter is decremented and the tile color is changed to red.
  //If neither of those conditions hold the tile color is still set to red indicating it has already been clicked. After
  //those conditions are checked the tile is set to visited and the tile that was created is added to the gBoard.
  //********************************************************************************************************************
  private void displayBoard(GridPane gBoard)
  {
    for (int r = 0; r < board.getGameBoard().length; r++)
    {
      for (int c = 0; c < board.getGameBoard()[r].length; c++)
      {
        LetterTiles tile = new LetterTiles(board.getGameBoard()[r][c], r * gWidth, c * gHeight, gWidth, gHeight);
        temp[r][c] = tile;

        tile.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
          @Override
          public void handle(MouseEvent event)
          {
            tilesToChangeColor.add(tile);

            if (!tile.getVisited() && tile.getCounter() == 1)
            {
              wordTocheck += tile.getLetter();
              tile.decrementCounter();
              tile.setFillToRed();
            }
            else
            {
              isVisited.setText("Not a valid move");
              isVisited.setFill(Color.RED);
            }
            tile.setVisited();
            event.consume();
          }
        });
        gBoard.getChildren().add(tile);
      }
    }
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //Resets the global wordToCheck variable to a empty string.
  //********************************************************************************************************************
  private void resetWordToCheck()
  {
    wordTocheck = "";
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method sets up the game timer which always starts at 180 seconds (3 minutes). First it adds the timerLabel,
  //which is used to display the countdown, to the timerBox. Then it sets the text of the label and creates a new timeLine
  //which will animate the countdown. Then it adds a new event handler that decrements the counter and updates the timerLabel.
  //********************************************************************************************************************
  private void startTimer()
  {
    timerBox.getChildren().add(timerLabel);
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
                if (timeInSeconds <= 0)
                {
                  resetGame();
                  timeline.stop();
                }
              }
            }));
    timeline.playFromStart();
  }

  //********************************************************************************************************************
  //Parameters: none
  //MEthod returns void
  //This method stops the current timer (timeLine), resets it to 180 seconds and the re-adds it to the timeBox.
  //********************************************************************************************************************
  private void resetTimer()
  {
    timeline.stop();
    timeInSeconds = START_TIME;
    timerBox.getChildren().clear();
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method resets the entire game by turning the game size buttons back on, resetting the player which resets the
  //score and the list of guessed words. Then it resets the score text field and the guesses text field. Then resets the
  //timer and finally clears the gBoard pane so a new board can be displayed.
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
    invalidWords.clear();
    badGuesses.setText("Invalid Guessed Words:" + invalidWords);
    resetTimer();
    clearBoard(gBoard);
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method resets the LetterTile object fields so a tile can be clicked again, is a playable color and resets the
  //counter. It does this by looping over the arraylist of used tiles.
  //********************************************************************************************************************
  private void resetTiles()
  {
    for (LetterTiles t : tilesToChangeColor)
    {
      t.setNotVisited();
      t.setFillToBlue();
      t.setCounter();
    }
  }

  //********************************************************************************************************************
  //Parameters
  //  1. gBoard is the grid pane for the letter tray
  //Method returns void
  //This method clears the try and removing all the letterTile objects from gBoard.
  //********************************************************************************************************************
  private void clearBoard(GridPane gboard)
  {
    gboard.getChildren().clear();
  }

  //********************************************************************************************************************
  //Parameters:
  //  1. Command Line agrs
  //Method returns void
  //Main calls launch for the GUI.
  //********************************************************************************************************************
  public static void main(String[] args)
  {
    launch(args);
  }

}
