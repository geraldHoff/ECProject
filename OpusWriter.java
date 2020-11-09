import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/*
Written by Anthony Nguyen, who likes to go by Red
Extra Credit project for CptS 233, Fall 2020

**********************************
ARTIFICIAL INTELLIGENCE GRAND OPUS
**********************************
AIGO for short

AIGO is a program based off of a previous project I had worked on during my winter term of 2019 at a community college.
This program takes in a .txt file, and will parse through the contents of the file. Based off the 'k' level of its search
AIGO will start to find patterns in the text, and pair it with whatever character follows the pattern, and storing it in a
HASH MAP. Thus, AIGO builds up a giant HASH MAP full of incomplete strings and a character that follows it.
Each string being 'k' levels long. Using this giant hash map, it will form its own sentences, with each k level making its sentences
more alike whatever it sampled. A wonderful demonstration of the power of hash maps. Care must be taken however, most of the code was
written when I was young and a lot more sloppy. I tried my best to make it better, but there may be some errors.
As well, due to the relative inefficiency of the program, high k levels will make AIGO chug harder than a frat boy at a party.
Maybe in the future with more time on my hands, I will make AIGO faster and more efficient. For now, consider this an exercise in using hash maps.
*/

public class OpusWriter {
   HashMap<String, ArrayList<Character>> strCharPairs;   //Hashmap, with string keys and arraylists as items
   int level;
   
   //IT IS ASSUMED K IS A VALUE GREATER THAN OR EQUAL TO 1
   //IT IS ASSUMED THE STRING fName IS IN ["textname.txt"] FORMAT AND THE FILE IS IN THE DIRECTORY WITH THE PROGRAM
   public OpusWriter(String fName, int k) throws Exception  {  //Throwing an exception because of files. Too lazy to do a try catch
      File inputF = new File(fName);   //Setting up the file for reading
      Scanner fReader = new Scanner(inputF);
      String fullTxt = ""; //This is where the full text will be stored
      level = k;  //K level
      
      while(fReader.hasNextLine())  {  //Take the entire file and store it in the string
         fullTxt = fullTxt + fReader.nextLine()+" ";
      }
      strCharPairs = new HashMap<>(fullTxt.length()*2);  //Set up our hashmap with a size twice that of the full text length
      while(fullTxt.length() > level)  {  //This is the most disgusting thing I've ever written, and even I'm confused how this works 2 years later. Tried my best to fix it
                                          //Essentially, the next two while loops take apart the string piece by piece and storing data in the hashmap
         String baseString = fullTxt.substring(fullTxt.length()-level,fullTxt.length()-1);   //System.out.println(baseString); DEBUG
         char ch = fullTxt.charAt(fullTxt.length()-1);
         fullTxt = fullTxt.substring(0,fullTxt.length()-1);
         if(!strCharPairs.containsKey(baseString)) //Check to see if the key is already in the hashmap
            strCharPairs.put(baseString,new ArrayList<Character>()); //If not, add it
         ArrayList<Character> chList = strCharPairs.get(baseString);
         chList.add(ch);   //Adding the character to the arraylist
      }
      while(fullTxt.length() >= 1) {   //Handling remainder of text when it is less than the K level
         String baseString = fullTxt.substring(0,fullTxt.length()-1);   //System.out.println(baseString); DEBUG
         char ch = fullTxt.charAt(fullTxt.length()-1);
         fullTxt = fullTxt.substring(0,fullTxt.length()-1);
         if(!strCharPairs.containsKey(baseString))
            strCharPairs.put(baseString,new ArrayList<Character>());
         ArrayList<Character> chList = strCharPairs.get(baseString);
         chList.add(ch);
      }
   }
   
   public String makeSentence()  {  //Make sentence
      String sentence = "";
      while(!sentence.endsWith(".")) { //Stop when the sentence has a '.'
         sentence = sentence + getNextChar(sentence);
         System.out.println(sentence);
      }
      return sentence;
   }
   
   private char chooseRandom(ArrayList<Character> arr) { //Randomly chooses the next character value to append to the sentence from the arraylist
      if(arr == null)
         return ' ';
      Random rand = new Random();
      int index = rand.nextInt(arr.size());
      return arr.get(index);
   }
   
   private String getBaseString(String str)  {  //takes the last few characters of the sentence AIGO is making to compare it against the hashmap
      if(str.length() < level)
         return str;
      return str.substring(str.length()-level+1,str.length());
   }
   
   private char getNextChar(String str) { //Getting the next character using a combo of the two methods above
      return chooseRandom(strCharPairs.get(getBaseString(str)));
   }
}