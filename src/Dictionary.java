import java.io.*;
import java.util.HashSet;

public class Dictionary
{
  private static HashSet<String> dictionary = new HashSet<>();

  //********************************************************************************************************************
  //
  //
  //
  //
  //********************************************************************************************************************
  public Dictionary()
  {
    makeDictionary();
  }

  //********************************************************************************************************************
  //
  //
  //
  //
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
  //
  //
  //
  //
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
