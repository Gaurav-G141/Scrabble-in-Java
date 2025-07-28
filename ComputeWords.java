import java.io.*;
import java.util.Scanner;
import java.util.HashMap;
class ComputeWords {
    static Scanner s;
    static Scanner w;
    static HashMap<String, boolean[]> letterinwords;
    static HashMap<String,char[]> possible_words;
    //does consturctor stuff
    public ComputeWords(){
        letterinwords = new HashMap<>();
        //loads in hashmap
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("letterinwords.ser"))) {
            letterinwords = (HashMap<String, boolean[]>) in.readObject();
            //System.out.println("Loaded map: " + letterinwords);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        possible_words = new HashMap<String,char[]>();
    }
    //method to get words
    public static HashMap<String,char[]> findwords(String tiles){
                //converts rack into boolean rck
                boolean[] rack = new boolean[208];
                for(int j = 0;j<tiles.length();j++){
                    if (tiles.charAt(j) != '?'){
                    int i = (tiles.charAt(j)-65)*8;
                    while(rack[i]){
                        i++;
                    }
                    rack[i] = true;
                }
                }
            
                //compares and contrasts for equivalance
                File f = new File("dictionary.txt");
                try{
                w = new Scanner(f);
                }
                catch(FileNotFoundException e){

                }
                while(w.hasNextLine()){
                    String word = w.nextLine();
                    boolean[] compare = booland(rack,letterinwords.get(word));
                    //if they're equal
                    if(twoboolsequal(compare, letterinwords.get(word))){
                        possible_words.put(word,new char[0]);
                    }
                    //if the blanks are good
                    else{
                    int blanks = 0;
                    for(int i = 0;i<tiles.length();i++){
                        if(tiles.charAt(i) == '?'){
                            blanks++;
                        }
                    }
                    boolean[] compare2 = boolxor(compare, letterinwords.get(word));
                    if((counttrues(compare2) - blanks) <= 1){
                        possible_words.put(word,neededletters(compare2));
                    }
                    }
                }
            return possible_words;
            }
    //helper methods
        public static void print(String s){
            System.out.println(s);
        }
        public static void printboolarr(boolean[] bools){
            for(boolean b: bools){
                System.out.print(b);
                System.out.print(",");
            }   
            System.out.println();
        }
        public static String input(String question){
            System.out.println(question);
            return s.nextLine();
        }
        public static char[] neededletters(boolean[] a){
            char[] ans = new char[counttrues(a)];
            int x = 0;
            for(int i = 0;i<a.length;i++){
                if(a[i]){
                    int c = (i - i%8)/8;
                    ans[x] = (char) (c+65);
                    x++;
                }
            }
            return ans;
        }
        public static String chararrtoString(char[] a){
            if(a.length == 0){
                return "";
            }
            String ans = "";
            for(int i = 0;i<a.length - 1;i++){
                ans += a[i];
                ans += ",";
            }
            ans += a[a.length - 1];
            return ans;
        }
        //below are bool list operators, such as and and xor. Note that all functions assume equal length
        public static boolean[] booland(boolean[] a,boolean[] b){
            boolean[] ans = new boolean[a.length];
            for(int i = 0;i<a.length;i++){
                ans[i] = a[i] & b[i];
            }
            return ans;
        }
        public static boolean twoboolsequal(boolean[] a, boolean[] b){
            for(int i = 0;i<a.length;i++){
                if(a[i] != b[i]){
                    return false;
                }
            }
            return true;
        }
        public static boolean[] boolxor(boolean[] a,boolean[] b){
            boolean[] ans = new boolean[a.length];
            for(int i = 0;i<a.length;i++){
                ans[i] = (a[i] != b[i]);
            }
            return ans; 
        }
        public static int counttrues(boolean[] a){
            int ans = 0;
            for(int i = 0;i<a.length;i++){
                if(a[i]){
                    ans++;
                }
            }
            return ans;
        }

}
