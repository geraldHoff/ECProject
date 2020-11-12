import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
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
   HashMap<String, ArrayList<Character>> wordPairs;   //Hashmap, with string keys and arraylists as items
   int level;  //K level
   
   /**
   *  Constructs the Opus Writer, where the hash map of strings paired with characters is created, and the K level is set. It is assumed
   *  that all inputs are valid. Note that once the Opus Writer is initialized with a chosen file, it cannot create another hash map for another
   *  text document, and thus, each Opus Writer is entirely unique to its text. Nor can a new k level be set once the writer is initialized
   *  @author Anthony Nguyen ~ Red
   *  @param fName of the text file, in "[name].txt" format. Does not check if the file is in the directory and is handled elsewhere
   *  @param k level determines how deep AIGO analyzes a string, corresponding to how long the pattern of strings it examines. A higher k level increases accuracy of sentences but time drastically increases
   *  @since 11092020
   */
   public OpusWriter(String fName, int k) throws Exception  {  //Throws exception if the file is not found. It is assumed that the filename is valid however
      wordPairs = createHashMap(fName,k);
      //remove("",' ');
      level = k;
   }
   /**
   *  Constructs a sentence from pairs of strings and characters in the hash map, taking a k length segment from the current sentence, comparing it to the 
   *  hashmap, and finding a random character that would likely follow the string segment. Thus overtime, the text will begin to resemble the original text with a sufficient k level
   *  @return the complete sentence
   */
   public String makeSentence()   {
      String sentence = "";
      int length = 50;
      while(!sentence.endsWith("."))   {
         //System.out.println("CURRENT SENTENCE   :  " + sentence); //Debug print line, prints out the sentence as it is being built
         sentence = sentence + getNextLetter(grabLast(sentence));
      }
      return sentence;
   }
   
   /**
   @return string representation of the hashmap
   */
   public String getString()  {
      String hashMap = "";
      for(Map.Entry entry : wordPairs.entrySet())  {
         hashMap += entry.getKey() + "   :   " + entry.getValue() + "\n";
      }
      return hashMap;
   }
   
   
   private HashMap<String, ArrayList<Character>> createHashMap(String fileName,int k)  throws Exception  {
      File inputFile = new File(fileName);
      HashMap<String,ArrayList<Character>> hMap = new HashMap<>();
      Scanner fileReader = new Scanner(inputFile);
      while(fileReader.hasNextLine())   {
         String textStr = cleanText(fileReader.nextLine());
         //System.out.println("NEXT LINE :  " + textStr);  //Debug print statement, shows the next line the scanner read after being run through the cleanText method
         String baseString;
         char nextChar;
         int i = textStr.length()-1;
         int j;
         if(textStr.length()-(k+1)<0)
            j=0;
         else
            j=textStr.length()-(k+1);
         while(j<=i) {
            nextChar = textStr.charAt(i);
            baseString=textStr.substring(j,i);
            addToHashMap(baseString,nextChar,hMap); 
            //System.out.println("ADDED TO HASHMAP : " + baseString + ", " + nextChar);   //Debug print line when new element is added
            i--;
            if(j>0)
               j--;
         }
      }
      return hMap;
   }
   
   private String grabLast(String str) {
      if (str.length() < level+1)  {
         //System.out.println("GRABBING LAST STRING : " + str);   //Debug print line when getting last k characters in current sentence
         return str;
      }
      else  {
         //System.out.println("GRABBING LAST STRING : " + str.substring(str.length()-level,str.length()));  //Debug print line when getting last k characters in current sentence
         return str.substring(str.length()-level,str.length());
      }
   }
   
   private char getNextLetter(String str) {
      //System.out.println("GETTING NEXT LETTER FOR STRING : " +  str);  //Debug print line when getting next character
      ArrayList<Character> charList = wordPairs.get(str);
      if(charList == null) {
         //System.out.println("ERROR CANNOT FIND NEXT LETTER FOR STRING \"" + str +"\""); //Debug print line for when AIGO cannot any characters for the string given
         return '.';
      }
      int i = (new Random()).nextInt(charList.size());
      return charList.get(i);
   }
   
   private String cleanText(String str)   {  //Given a string, all nonprintable, non ASCII, control characters will be removed from the string
      str=str.replaceAll("\\p{Cntrl}", " "); //Replace all control characters with an empty space
      str=str.replaceAll("[^\\p{Print}]", "~"); //Replace all nonprintable characters with ~
      return str;
   }
   
   private void addToHashMap(String baseStr,char suffix,HashMap<String,ArrayList<Character>> hMap) {
      if(hMap.containsKey(baseStr))  {
         (hMap.get(baseStr)).add(suffix);
      }  else  {
         hMap.put(baseStr, new ArrayList<>(Arrays.asList(suffix)));
      }
   }
   
   private void remove(String key, char value)  {
      ArrayList<Character> characterArr = wordPairs.get(key);
      if(characterArr != null)   {
         characterArr.removeAll(Arrays.asList(value));
      }
   }
}