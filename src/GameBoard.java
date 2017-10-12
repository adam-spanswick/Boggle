import java.util.*;

//**********************************************************************************************************************
//Adam Spanswick
//
//This class represents the gameboard or the letter tray. To use this class call a new object with the desired size of the
//game board.
//**********************************************************************************************************************
public class GameBoard
{
  private Character[][] gameBoard;
  private HashMap<Character, Integer> occurrences = new HashMap<>();
  private ArrayList<Character> letters = new ArrayList<>();

  //********************************************************************************************************************
  //Parameters:
  //  1. rows is the number of rows
  //  2. cols is the number of cols
  //Constructor initializes the game board to the size passed in, assigns letters to the board, counts the occurences of
  //all the letters and then deals with placing u's next to Q's
  //********************************************************************************************************************
  public GameBoard(int rows, int cols)
  {
    this.gameBoard = new Character[rows][cols];
    populateBoard();
    occurrences();
    dealWithQU();
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns a character object array that is the game board
  //********************************************************************************************************************
  public Character[][] getGameBoard()
  {
    return gameBoard;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method randomly assigns letters to the tray by looping over the tray and using a random object selects a random
  //letter then assgining that letter to the position in the board.
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
  }

  //********************************************************************************************************************
  //https://en.wikipedia.org/wiki/Backtracking
  //Parameters:
  //  1. board is a character object array
  //  2. word is the user inputted word
  //Method returns true if the word is on the board and false is it is not. It does this by looping over the board and
  //calling wordExists method with the current position and the inputted word.
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
  //Parameters: none
  //Method returns void
  //This method counts all the occurences of every letter in the board. It does this by looping over the board and adding
  //all the letters to a arraylist. Then it loops over the board again and checks if the letter at the current position
  //is in the letters array list. If it is the count is incremented and put into the hashmap occurrences. Finally it loops
  //over the occurrences hashmap and while a 4 exists it calls the checkForFour method.
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
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method checks for 4 or more letter occurrences on the board and changes them if necessary. It does this by looping
  //over the board while checking if the count is 4. If it is it finds the first occurrence of that letter and randomly
  //assigns it to another letter.
  //********************************************************************************************************************
  private void checkForFour()
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
  //Parameters: none
  //Method returns void
  //This method deals with letters Q and U being next to each other. It does this by looping over the board and finding
  //if the current positions is a q. If it is it then assigns a u either above, right, left, below, diagonal up left, diagonal
  //up right, diagonal down left or diagonal down right.
  //********************************************************************************************************************
  private void dealWithQU()
  {
    Character[] replacements = new Character[]{'a', 'a', 'e', 'e', 'i', 'i', 'o', 'o', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'u', 'y', 'y'};
    Random rand = new Random();

    for(int r = 0; r < gameBoard.length; r++)
    {
      for (int c = 0; c < gameBoard.length; c++)
      {
        if(gameBoard[r][c] == 'q')
        {
          if(c-1 >= 0) //left
          {
            gameBoard[r][c-1] = replacements[rand.nextInt(14)];
          }
          else if (c + 1 < gameBoard.length) //right
          {
            gameBoard[r][c+1] = replacements[rand.nextInt(14)];
          }
          else if(r - 1 >= 0) //up
          {
            gameBoard[r-1][c] = replacements[rand.nextInt(14)];
          }
          else if(r + 1 < gameBoard.length) //down
          {
            gameBoard[r+1][c] = replacements[rand.nextInt(14)];
          }
          else if(r - 1 >= 0 && c - 1 >= 0) //Diagonal up left
          {
            gameBoard[r-1][c-1] = replacements[rand.nextInt(14)];
          }
          else if(r - 1 >= 0 && c + 1 < gameBoard.length) //Diagonal up right
          {
            gameBoard[r-1][c+1] = replacements[rand.nextInt(14)];
          }
          else if(r + 1 < gameBoard.length && c - 1 >= 0) //Diagonal down left
          {
            gameBoard[r+1][c-1] = replacements[rand.nextInt(14)];
          }
          else if(r + 1 < gameBoard.length && c + 1 < gameBoard.length) //Diagonal down right
          {
            gameBoard[r+1][c+1] = replacements[rand.nextInt(14)];
          }
        }
      }
    }
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns a character object
  //This method selects a random letter. It does this by getting a random int that corresponds to a letter in the alphabet
  //********************************************************************************************************************
  private Character getRandomLetter()
  {
    Random rand = new Random();
    Character tile = (char) (rand.nextInt(26) + 'a');
    return tile;
  }

  //********************************************************************************************************************
  //https://en.wikipedia.org/wiki/Backtracking-- Explanation of the backtracking algorithm I used
  //Parameters:
  //  1. board is a 2d Character object array
  //  2. wordToFind is the user inputted word
  //  3. row is the current row
  //  4. col is the current col
  //  5. idxIntoWord is the position of the character we are searching for
  //  6. boardLength is how long the rows in the game board are
  //Method returns true if the word is on the board and false if it is not. It does this by first checking if the current
  //position if the not the same as the first letter in the user word. If that is true then it returns false. Then it checks
  //if the length of the user word is the same as index into the word. If it is we are done and we found the word. If that is
  //false then it checks left, right, up, down, diagonal up left, diagonal up right, diagonal down left and diagonal down right by
  //checking if we will exceed the size of the board and if not it continues to check by recursive calls until we either find
  //the word or the letters don't match. It does this in all directions until the word is found then returns true of the word
  //is not found and returns false.
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
