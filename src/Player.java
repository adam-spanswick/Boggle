import java.util.ArrayList;

//********************************************************************************************************************
//Adam Spanswick
//
//This class is used to keep track of the bookkepping for the player. To use this class call the constructor and it will
//initialize the player score and a list of guesses the player has made.
//********************************************************************************************************************
public class Player
{
  private ArrayList<String> guessedWords;
  private int score;

  //********************************************************************************************************************
  //Parameters: none
  //Is a constructor
  //Constructor sets the player score to 0 and initializes a new array list for the guessed words.
  //********************************************************************************************************************
  public Player()
  {
    this.score = 0;
    this.guessedWords = new ArrayList<>();
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //
  //
  //********************************************************************************************************************
  public int getScore(){ return score; }

  //********************************************************************************************************************
  //Parameters:
  //  1. word is the string for the score
  //Method returns void
  //This method calculates the score by checking if the size of the guessed words list is greater than 0 and if the word
  //is longer than 2. If both of those are true the score is calculated as the word length minus 2. If they are false
  //then it checks of the word is currently in the guessed words list. If it is not then the score is calculated.
  //********************************************************************************************************************
  public void calculateScore(String word)
  {
    if(guessedWords.size() == 0 && word.length() > 2)
    {
      score += word.length() - 2;
      return;
    }

    if(!guessedWords.contains(word) == true)
    {
      score += word.length() - 2;
    }
  }

  //********************************************************************************************************************
  //Parameters:
  //Method returns the arraylist of guessed words
  //********************************************************************************************************************
  public ArrayList<String> getGuessedWords() { return guessedWords; }

  //********************************************************************************************************************
  //Parameters:
  //  1. inputWord is the string to be added to the guessed words list
  //Method returns void
  //This method checks if the word is in the guessed words list and if the guessed words list is 0. If either of those is
  //true it adds the word to the list. If not the word is not added to the list.
  //********************************************************************************************************************
  public void guessedWordList(String inputWord)
  {
    if(!guessedWords.contains(inputWord) || guessedWords.size() == 0)
    {
      guessedWords.add(inputWord);
    }
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method resets the score to 0 and clears the guessed words list.
  //********************************************************************************************************************
  public void reset()
  {
    this.score = 0;
    this.guessedWords.clear();
  }
}
