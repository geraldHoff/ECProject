class Main  {
   public static void main(String[] args) throws Exception{
      OpusWriter writer = new OpusWriter("PP.txt",5); //Testing out Dracula with a K level of 10. Don't worry if you think it's frozen, it just takes this long
      System.out.println(writer.makeSentence());
   }
}