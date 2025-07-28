import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
public class Board {
    char[][] gameboard; //note: this is in [vertical][horizontal] corridinates
    ArrayList<String> allwords; //list of every word out there
    boolean isempty;
    //consturctor
    public Board(){
        gameboard = new char[15][15];
         try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("letterinwords.ser"))) {
            allwords = new ArrayList<>(((HashMap<String, boolean[]>) in.readObject()).keySet());
        }
        catch (IOException | ClassNotFoundException e) {
        e.printStackTrace();
        }
        isempty = true;
    }

    //adds a word vertically. Position is zero-indexed at [horizontal,vertical]
    void addvertword(char[] word, int[] position){
        if((position[1] + word.length) > 15){
            return;
        }
        for(int i = position[1];i<position[1]+word.length;i++){
            gameboard[i][position[0]] = word[i-position[1]];
        }
        this.isempty = false;
    }

    //adds a word horizontally. Note both functions assumes vailidity
    void addhoriword(char[] word, int[] position){
        if((position[0] + word.length) > 15){
            return;
        }
        for(int i = position[0];i<position[0]+word.length;i++){
            gameboard[position[1]][i] = word[i-position[0]];
        }
        this.isempty = false;
    }

    //checks if board is empty
    boolean isempty(){
        for(char[] a:gameboard){
            for(char b:a){
                if(b == '\u0000'){
                }
                else{
                    return false;
                }
            }   
        }
        return true;   
    }


    //checks if a word horizontally is vaild
    boolean horivaild(char[] word, int[] position, char[] allowedoverlaps){
        //checks for empty board. Note that we assume the move touches the central square 
        if (this.isempty){
            if(position[1] != 7){
                //System.out.println("Starting move must be on central square");
                return false;
            }
            if(position[0] > 7 || position[0] + word.length <= 7){
                //System.out.println("Starting move must be on central square");
                return false;                
            }
            if(allowedoverlaps.length == 0){
                return true;
            }
            return false;
        }
        //checks to see if move can even happen
        if((position[0] + word.length) > 15){
            //System.out.println("Invaild move, goes outside of board");
            return false;
        }
        //check for not overlapping
        boolean completeoverlaps = false;
        for(int i = position[0];i<position[0]+word.length;i++){
            if(gameboard[position[1]][i] != '\u0000'){
                char onboard = gameboard[position[1]][i];
                if(word[i - position[0]] != onboard){
                    //System.out.println("Invaild move. The overlap is invaild");
                    return false;
                }
                if((new String(allowedoverlaps)).indexOf(onboard) != -1){
                    completeoverlaps = true;
                }
            };
        }
        if (!completeoverlaps && allowedoverlaps.length != 0){
            //System.out.println("Invaild move. You had overlaps that you needed to complete");
            return false;
        }
        //check atleast for one touch on top or bottom
        boolean overlap = false;
        for(int i = position[0];i<position[0]+word.length;i++){
            if(position[1] != 0){
                if(gameboard[position[1]-1][i] != '\u0000'){
                    overlap = true;
                    i = 999;
                }
            }
            if(!overlap){
                if(position[1] != 14){
                    if(gameboard[position[1]+1][i] != '\u0000'){
                        overlap = true;
                        i = 999;
                    }
                } 
            }
        }
        //checks for edge touches
        if(!overlap){
            if(position[0] != 0){
                if(gameboard[position[1]][position[0] - 1] != '\u0000'){
                    overlap = true;
                }
            }
            if(position[0] + word.length != 14){
                if(gameboard[position[1]][position[0] + word.length + 1] != '\u0000'){
                    overlap = true;
                }
            }
        }
        if(!overlap){
            //System.out.println("Invaild move, there are no overlaps");
            return false;
        }
        return true;
    }//end of verifying method on horizontal end
    
    


    //checks if a word vertically is vaild
    boolean vertvaild(char[] word, int[] position, char[] allowedoverlaps){
        //checks for empty board. Note that we assume the move touches the central square 
        if (this.isempty){
            if(position[0] != 7){
                //System.out.println("Starting move must be on central square");
                return false;
            }
            if(position[1] > 7 || position[1] + word.length <= 7){
                //System.out.println("Starting move must be on central square");
                return false;                
            }
            if(allowedoverlaps.length == 0){
                return true;
            }
            return false;
        }
        //checks to see if move can even happen
        if((position[1] + word.length) > 15){
            //System.out.println("Invaild move, goes outside of board");
            return false;
        }
        //check for not overlapping
        boolean completeoverlaps = false;
        for(int i = position[1];i<position[1]+word.length;i++){
            if(gameboard[i][position[0]] != '\u0000'){
                char onboard = gameboard[i][position[0]];
                if(word[i - position[1]] != onboard){
                    //System.out.println("Invaild move. The overlap is invaild");
                    return false;
                }
                if((new String(allowedoverlaps)).indexOf(onboard) != -1){
                    completeoverlaps = true;
                }
            };
        }
        if (!completeoverlaps && allowedoverlaps.length != 0){
            //System.out.println("Invaild move. You had overlaps that you needed to complete");
            return false;
        }
        //check atleast for one touch on top or bottom
        boolean overlap = false;
        for(int i = position[1];i<position[1]+word.length;i++){
            if(position[0] != 0){
                if(gameboard[i][position[0]-1] != '\u0000'){
                    overlap = true;
                    i = 999;
                }
            }
            if(!overlap){
                if(position[0] != 14){
                    if(gameboard[i][position[0]+1] != '\u0000'){
                        overlap = true;
                        i = 999;
                    }
                } 
            }
        }
        //checks for edge touches
        if(!overlap){
            if(position[1] != 0){
                if(gameboard[position[1] - 1][position[0]] != '\u0000'){
                    overlap = true;
                }
            }
            if(position[1] + word.length != 14){
                if(gameboard[position[1] + word.length + 1][position[0]] != '\u0000'){
                    overlap = true;
                }
            }
        }
        if(!overlap){
            //System.out.println("Invaild move, there are no overlaps");
            return false;
        }
        return true;
    }//end of verifying method on vertical end
    
    
    //checks if board is vaild words
    boolean boardvaild(){
        String word = "";
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                if(gameboard[i][j] != '\u0000'){
                    word += gameboard[i][j];
                }
                else if(word.length() > 1){
                    if(allwords.indexOf(word) == -1){
                        //System.out.println("There is a non-vaild word in this board");
                        return false;
                    }
                }
                if(gameboard[i][j] == '\u0000'){
                    word = "";
                }
            }
        }
        for(int j = 0;j<15;j++){
            for(int i = 0;i<15;i++){
                if(gameboard[i][j] != '\u0000'){
                    word += gameboard[i][j];
                }
                else if(word.length() > 1){
                    if(allwords.indexOf(word) == -1){
                        //System.out.println("There is a non-vaild word in this board");
                        return false;
                    }
                }
                if(gameboard[i][j] == '\u0000'){
                    word = "";
                }
            }
        }
        return true;
    }

    //print out gameboard
    public String toString(){
        String ans = "";
        for(char[] a:gameboard){
            for(char b:a){
                if(b == '\u0000'){
                    ans += '-';
                }
                else{
                    ans += b;
                }
                ans += ' ';
            }   
            ans += "\n";
        }
        
    return ans;
    }
    

}
