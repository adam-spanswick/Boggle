import javafx.stage.Stage;

import java.io.*;
import java.util.*;


public class Main
{
  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public static void main(String[] args)
  {
    GUIManager game = new GUIManager();
    Stage primaryStage = new Stage();
    game.start(primaryStage);
  }
}
