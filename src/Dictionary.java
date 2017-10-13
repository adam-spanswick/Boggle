import java.io.*;
import java.util.HashSet;

//**********************************************************************************************************************
//Adam Spanswick
//
//This class is the dictionary to be used to check the user words. To use this class create a new object and it will read
//in the dictionary file and set up the game dictionary.
//**********************************************************************************************************************
public class Dictionary
{
  private static HashSet<String> dictionary = new HashSet<>();

  //********************************************************************************************************************
  //Parameters: none
  //Is a constructor
  //Constructor calls the makeDictionary method.
  //********************************************************************************************************************
  public Dictionary()
  {
    makeDictionary();
  }

  //********************************************************************************************************************
  //Parameters:
  //  1. wordToCheck is the string the user selected
  //Method returns true if the word is in the dictionary or false if the word is not in the dictionary.
  //********************************************************************************************************************
  public boolean validWord(String wordToCheck)
  {
    if(dictionary.contains(wordToCheck))
    {
      return true;
    }
    return false;
  }

  //********************************************************************************************************************
  //Parameters: none
  //Method returns void
  //This method reads in the dictionary file by setting up a new file reader set to where I put the dictionary file and
  //a new buffered reader to that file reader. Then loops over every line of the file and adds each word to the dictionary.
  //If that fails it catches the exception and prints the stack trace.
  //********************************************************************************************************************
  private void makeDictionary(){
    try
    {
      FileReader file = new FileReader(new File("../resources/OpenEnglishWordList.txt"));
      BufferedReader br = new BufferedReader(file);
      for (String line = br.readLine(); line != null; line = br.readLine())
      {
        dictionary.add(line);
      }
    }
    catch (FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
