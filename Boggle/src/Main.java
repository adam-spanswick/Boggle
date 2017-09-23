import java.io.*;
import java.util.*;


public class Main
{
  public static void main(String[] args)
  {
    Dictionary dictionary = new Dictionary();
    GameBoard sBoard = new GameBoard(4,4);
    GameBoard lBoard = new GameBoard(5,5);

    sBoard.populateBoard();
    System.out.println("\n");
    lBoard.populateBoard();
  }
}
