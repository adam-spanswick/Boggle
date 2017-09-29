import java.util.*;

public class GameBoard
{
  private Character[][] gameBoard;
  private HashMap<Character, Integer> occurrences = new HashMap<>();
  private ArrayList<Character> letters = new ArrayList<>();
//  private boolean visited = false;

  public static void main(String[] args)
  {
    GameBoard game  = new GameBoard(5,5);
    game.occurrences();
    game.printBoard();
  }

  public void printBoard()
  {
    for(int r = 0; r < gameBoard.length; r++)
    {
      for(int c = 0; c < gameBoard[r].length; c++)
      {
        System.out.print(gameBoard[r][c] + " ");
      }
      System.out.println();
    }
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public GameBoard(int rows, int cols)
  {
    this.gameBoard = new Character[rows][cols];
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
  public void populateBoard()
  {
    Random rand = new Random();

    for(int r = 0; r < gameBoard.length; r++)
    {
      for(int c = 0; c < gameBoard[r].length; c++)
      {
        Character tile = (char) (rand.nextInt(26) + 'a');
        gameBoard[r][c] = tile;
      }
    }

    //Prints the Board
//    for(int r = 0; r < gameBoard.length; r++)
//    {
//      for(int c = 0; c < gameBoard[r].length; c++)
//      {
//        System.out.print(gameBoard[r][c] + " ");
//      }
//      System.out.println();
//    }
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private void occurrences(){
    System.out.println("");
    int count;

    for(int r = 0; r < gameBoard.length; r++)
    {
      for(int c = 0; c < gameBoard.length; c++)
      {
        letters.add(gameBoard[r][c]);
      }
    }

    for (int r = 0; r < gameBoard.length; r++)
    {
      for (int c = 0; c < gameBoard.length; c++)
      {
        for (Character letter : letters)
        {
          if (gameBoard[r][c] == letter)
          {
            count = Collections.frequency(letters, gameBoard[r][c]);
            occurrences.put(gameBoard[r][c], count);
          }
        }
      }
    }

    //Get rid of 4 letters in the entire board
    while(occurrences.containsValue(4))
    {
      checkForFour();
    }
    System.out.println(Arrays.asList(occurrences));
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public void checkForFour()
  {
    for(Map.Entry<Character, Integer> element : occurrences.entrySet())
    {
      Character letter = element.getKey();
      Integer count = element.getValue();

      if(count == 4)
      {
        for(int r = 0; r < gameBoard.length; r++)
        {
          for(int c = 0; c < gameBoard.length; c++)
          {
            if(gameBoard[r][c] == letter)
            {
              gameBoard[r][c] = getRandomLetter();
              return;
            }
          }
        }
      }
    }
  }

  //********************************************************************************************************************
  //https://en.wikipedia.org/wiki/Backtracking
  //
  //
  //
  //********************************************************************************************************************
  public boolean findWord(Character[][] board, String word)
  {
    int idx = 0;

    for(int r = 0; r < board.length; r++)
    {
      for(int c  = 0; c < board.length; c++)
      {
        if(wordExists(board, word, r, c, idx, board.length))
        {
          return true;
        }
      }
    }
    return false;
  }

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  private Character getRandomLetter()
  {
    Random rand = new Random();
    Character tile = (char) (rand.nextInt(26) + 'a');
    return tile;
  }

  //********************************************************************************************************************
  //https://en.wikipedia.org/wiki/Backtracking
  //
  //
  //
  //********************************************************************************************************************
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
