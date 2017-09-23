import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class GUIManager extends Application implements EventHandler<ActionEvent>
{
  private Dictionary dictionary = new Dictionary();
  private TextField wordchecker;
  private Button check;
  private Text input = new Text();

  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 300;


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
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Boggle");
    primaryStage.setResizable(false);

    VBox pane = new VBox();
    pane.setPadding(new Insets(10, 10, 10, 10));

    scene.setRoot(pane);

    wordchecker = new TextField();
    wordchecker.setPromptText("Enter the word to check");
    wordchecker.setPrefColumnCount(20);

    check = new Button("Check Word!");
    check.setOnAction(this);

    pane.getChildren().addAll(wordchecker, check, input);
    primaryStage.show();
  }

  public void checkWord(String word)
  {
    if (dictionary.validWord(word))
    {
      input.setText("The word is in the dictionary!");
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
    if (dictionary.validWord(wordchecker.getText()))
    {
      checkWord(wordchecker.getText());
    } else
    {
      checkWord(wordchecker.getText());
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
