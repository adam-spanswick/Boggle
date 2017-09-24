import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameBoard
{
  private ArrayList<Character> letters = new ArrayList<>();
  private Character[][] gameBoard;
//  private boolean visited = false;

//    public static void main(String[] args)
//  {
//    GameBoard game  = new GameBoard(4,4);
//
//    System.out.println(game.checkForFour(game.getGameBoard()));
//  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public GameBoard(int rows, int cols)
  {
    this.gameBoard = new Character[rows][cols];
    generateLetters();
    populateBoard();
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public Character[][] getGameBoard()
  {
    return gameBoard;
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
    letters.add('A');
    letters.add('A');
    letters.add('A');
    letters.add('A');
    letters.add('A');
    letters.add('B');
    letters.add('C');
    letters.add('D');
    letters.add('E');
    letters.add('E');
    letters.add('E');
    letters.add('E');
    letters.add('E');
    letters.add('F');
    letters.add('G');
    letters.add('H');
    letters.add('I');
    letters.add('I');
    letters.add('I');
    letters.add('I');
    letters.add('I');
    letters.add('J');
    letters.add('K');
    letters.add('L');
    letters.add('M');
    letters.add('N');
    letters.add('O');
    letters.add('O');
    letters.add('O');
    letters.add('O');
    letters.add('O');
    letters.add('P');
    letters.add('Q');
    letters.add('R');
    letters.add('S');
    letters.add('T');
    letters.add('U');
    letters.add('U');
    letters.add('U');
    letters.add('U');
    letters.add('U');
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

    for(int r = 0; r < gameBoard.length; r++)
    {
      for(int c = 0; c < gameBoard[r].length; c++)
      {
        Character charToPlace = letters.get(rand.nextInt(letters.size()));
        gameBoard[r][c] = charToPlace;
      }
    }

//    //Prints the Board
//    for(int r = 0; r < gameBoard.length; r++)
//    {
//      for(int c = 0; c < gameBoard[r].length; c++)
//      {
//        System.out.print(gameBoard[r][c] + " ");
//      }
//      System.out.println();
//    }
  }

  private int checkForFour(Character[][] board)
  {
    int seen = 1;
    Character temp1 = board[0][0];
    System.out.println(temp1);

    for(int r = 0; r < gameBoard.length; r++)
    {
      for (int c = 0; c < gameBoard[r].length; c++)
      {
        if(board[r][c].equals(temp1))
        {
          seen++;
        }
        else
        {
          seen = 1;
        }
        temp1 = board[r][c];
      }
    }
    return seen;
  }

  public boolean findWord(Character[][] board, String word)
  {
    int idx = 0;
//    System.out.println("Enter a Word: ");
//    Scanner in = new Scanner(System.in);
//    String input = in.nextLine().toUpperCase();

    for(int r = 0; r < board.length; r++)
    {
      for(int c  = 0; c < board.length; c++)
      {
        if(wordExists(board, word.toUpperCase(), r, c, idx, board.length))
        {
          return true;
        }
      }
    }
    return false;
  }

  private boolean wordExists(Character[][] board, String wordToFind, int row, int col, int idxIntoWord, int boardLength)
  {
    if(wordToFind.charAt(idxIntoWord) != board[row][col])
    {
      return false;
    }

    if(wordToFind.length()-1 == idxIntoWord)
    {
      return true;
    }

    if(col-1 >= 0 && wordExists(board, wordToFind, row,col-1,idxIntoWord+1, boardLength))     //Check Left
    {
      return true;
    }
    else if(col + 1 < boardLength && wordExists(board, wordToFind, row, col + 1, idxIntoWord + 1, boardLength))    //Check Right
    {
      return true;
    }
    else if(row - 1 >= 0 && wordExists(board, wordToFind, row - 1, col, idxIntoWord + 1, boardLength))    //Check Up
    {
      return true;
    }
    else if(row + 1 < boardLength && wordExists(board, wordToFind, row + 1, col, idxIntoWord + 1, boardLength))    //Check Down
    {
      return true;
    }
    else if(row - 1 >= 0 && col - 1 >= 0 && wordExists(board, wordToFind, row - 1, col -1 , idxIntoWord + 1, boardLength))    //Check Diagonal Up Left
    {
      return true;
    }
    else if(row - 1 >= 0 && col + 1 < boardLength && wordExists(board, wordToFind, row - 1, col + 1, idxIntoWord + 1, boardLength))    //Check Diagonal Up Right
    {
      return true;
    }
    else if(row + 1 < boardLength && col - 1 >= 0 && wordExists(board, wordToFind, row + 1, col - 1, idxIntoWord + 1, boardLength))    //Check Diagonal Down Left
    {
      return true;
    }
    else if(row + 1 < boardLength && col + 1 < boardLength && wordExists(board, wordToFind, row + 1, col+1, idxIntoWord + 1, boardLength))    //Check Diagonal Down Right
    {
      return true;
    }
    return false;
  }
}
