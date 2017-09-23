import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class GameBoard
{
  private ArrayList<Character> letters = new ArrayList<>();
  private Character[][] board;

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public GameBoard(int rows, int cols)
  {
    this.board = new Character[rows][cols];
    generateLetters();
    populateBoard();
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public Character[][] getBoard()
  {
    return board;
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void generateLetters()
  {
    letters.add('A');
    letters.add('B');
    letters.add('C');
    letters.add('D');
    letters.add('E');
    letters.add('F');
    letters.add('G');
    letters.add('H');
    letters.add('I');
    letters.add('J');
    letters.add('K');
    letters.add('L');
    letters.add('M');
    letters.add('N');
    letters.add('O');
    letters.add('P');
    letters.add('Q');
    letters.add('R');
    letters.add('S');
    letters.add('T');
    letters.add('U');
    letters.add('V');
    letters.add('W');
    letters.add('X');
    letters.add('Y');
    letters.add('Z');
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void populateBoard()
  {
    Random rand = new Random();

    for(int r = 0; r < board.length; r++)
    {
      for(int c  = 0; c < board[r].length; c++)
      {
        Character charToPlace = letters.get(rand.nextInt(letters.size()));
        board[r][c] = charToPlace;
      }
    }

//    for(int r = 0; r < board.length; r++)
//    {
//      for(int c = 0; c < board[r].length; c++)
//      {
//        System.out.print(board[r][c] + " ");
//      }
//      System.out.println();
//    }
  }

  public void validWord(Character wordToCheck, Character boardPos)
  {
    for(int r = 0; r < board.length; r++)
    {
      for (int c = 0; c < board[r].length; c++)
      {
        if(wordToCheck == board[r][c])
        {
          validWord(wordToCheck, board[r+1][c]);
        }
      }
    }
  }
}
