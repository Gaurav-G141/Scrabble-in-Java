import java.util.Scanner;
import java.io.*;
import java.util.HashMap;
class Reader{
    public static void main(String[] args) throws FileNotFoundException{
        //loads both json and dictionary
        File f = new File("dictionary.txt");
        HashMap<String,boolean[]> letterinwords= new HashMap<String,boolean[]>();
        Scanner words = new Scanner(f);
        //scans each word
        while(words.hasNextLine()){
        String word = words.nextLine();
            boolean[] letters = new boolean[208];
            for(int j = 0;j<word.length();j++){
                int i = (word.charAt(j)-65)*8;
                while(letters[i]){
                    i++;
                }
                letters[i] = true;
            }
        //Writes word info onto hashmap
        letterinwords.put(word,letters);
    }
    //saves to Object
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("letterinwords.ser"))) {
        out.writeObject(letterinwords);
        System.out.println("Map saved to letterinwords.ser");
    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println(letterinwords.size());
    System.out.println("project successful");
    }
}