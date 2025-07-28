import java.lang.Math;
public class Points {
    final int[] points = {1,3,3,2,1,4
                        ,2,4,1,8,5,1,
                        3,1,1,3,10,1,
                        1,1,1,4,4,8,4,10};
    final int[][] doubleletterspots = {{3,0},{11,0},{6,2},{8,2},{0,3},{7,3},{14,3},
    {2,6},{6,6},{8,6},{12,6},{3,7},{11,7},{2,8},{6,8},{8,8},{12,8},{0,11},{7,11},{14,11},{6,12},{8,12},{3,13},{11,13}};
    final int[][] tripleletterspots = {{5,1},{9,1},{1,5},{5,5},{9,5},
    {13,5},{1,9},{5,9},{9,9},{13,9},{5,13},{9,13}};
    final int[][] triplewordspots = {{0,0},{7,0},{14,0},{0,7},{14,7},{0,14},{7,14},{14,14}};
    final int[][] doublewordspots = {{1,1},{13,1},{2,2},{12,2},{3,3},{11,3},{4,4},{10,4},{7,7},{4,10},{10,10},{3,11},{11,11},{2,12},{12,12},{1,13},{13,13}};
    final char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
    public boolean isempty(char spot){
        int a = (int)spot - 65;
        //System.out.println(spot);
        if(a >= 0 && a < 26){
            return false;
        }     
        return true;
    }
    //find index of spot, used to check if a premium sqaure applies
    public int indexof(int[][] spots, int[] spot){
        for(int i = 0;i<spots.length;i++){
            if(spots[i][0] == spot[0] && spots[i][1] == spot[1]){
                return i;
            }
        }
        return -1;
    }
    //adds the overlaps
    public int countoverlaps(char[] word){
        int ans = 0;
        if(word.length <= 1){
            return 0;
        }
        for (int i = 0;i<word.length;i++){
            if(word[i] != '?'){
            ans += points[word[i] - 65];
            }
        }
        return ans;
    }
    //gets overlap
    public char[] getoverlaps(Board b, int[] position, boolean isvert){
        boolean contiune = true;
        while(!isempty(b.gameboard[position[1]][position[0]]) && contiune){
            if(isvert){
                if(position[0] != 0){
                    position[0] --;
                }
                else{
                    contiune = false;
                }
            }
            else{
                if(position[1] != 0){
                    position[1] --;
                }
                else{
                    contiune = false;
                }
            }
        }
        String ans = ""; 
        contiune = true;
        while(!isempty(b.gameboard[position[1]][position[0]]) && contiune){
            if(isvert){
                if(position[0] != 14){
                    position[0] ++;
                    ans += b.gameboard[position[1]][position[0]];
                }
                else{
                    contiune = false;
                }
            }
            else{
                if(position[1] != 14){
                    position[1] ++;
                    ans += b.gameboard[position[1]][position[0]];
                }
                else{
                    contiune = false;
                }
            }
        }
        //System.out.println(ans);
        return ans.toCharArray();
    }
    public int getpoints(Board b, char[] word, int[] position, boolean isvert, boolean blankused){
        int ans = 0;
        int addon = 0; //accounts for overlaps
        int numdoubles = 0;
        int numtriples = 0;
        int numlettersused = word.length;
        int[] testpos = position;        
        //counts all the normal letters, including letter premiums
        for(int i = 0;i<word.length;i++){
            //System.out.println(testpos[0] + " " + testpos[1] + " " + word[i]);
            char tile = b.gameboard[testpos[1]][testpos[0]];
            int a = 0;
            if(isempty(tile)){
            a = countoverlaps(getoverlaps(b, position, isvert));
            }
            
            //for bingos
            if(!isempty(tile)){
                numlettersused--;
            }
            if(word[i] != '?'){
            ans += points[word[i] - 65];
            //double letters
            if(indexof(doubleletterspots, testpos) != -1){
                if(isempty(tile)){
                ans += points[word[i] - 65];
            }
            }
            //triple letters
            else if(indexof(tripleletterspots, testpos) != -1){
                if(isempty(tile)){
                    ans += points[word[i] - 65];
                    ans += points[word[i] - 65];
                }
            }
            if(indexof(doublewordspots,testpos) != -1){
                if(isempty(tile)){
                    numdoubles++;
                    a *= 2;
                }
            }
            else if(indexof(triplewordspots,testpos) != -1){
                if(isempty(tile)){
                    numtriples++;
                    a *= 3;
                }
            }
            //overlap
            addon += a;
            if(isvert){
                testpos[1] ++;
            }
            else{
                testpos[0] ++;
            }
        }
        }
    //counts double/triple bonuses
    ans *= Math.pow(2,numdoubles);
    ans *= Math.pow(3,numtriples);
    ans += addon;
    //does bingo bangle bonus
    if(numlettersused == 7){
        ans += 50;
    }
    return ans;

    
}
}
