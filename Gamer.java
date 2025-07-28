import java.util.*;
public class Gamer {
    public static Board testBoard; //testboard
    public static HashMap<String,char[]> tryablewords = new HashMap<String,char[]>(); //all the words that could be attempted
    public static HashMap<String,ArrayList<int[]>> possiblemoves = new HashMap<String,ArrayList<int[]>>(); //all the vaild moves
    public static Scanner scanner;
    public static Board board;
    public static void main(String[] args) {
        ArrayList<Move> allmoves = new ArrayList<Move>(); //all the moves that could be done with point totals
        board = new Board();
        testBoard = new Board();
        ComputeWords computewords = new ComputeWords();
        scanner = new Scanner(System.in);
        System.out.println("Are you first or second (Type 1 if first, 2 if second)");
        int ans = scanner.nextInt();
        scanner.nextLine();
        boolean isturn = false;
        Points points = new Points();
        if(ans == 1){
            isturn = true;
        }
        boolean changedrack = true;



        while(true){
            if(changedrack){
            possiblemoves.clear();
            allmoves.clear();
            tryablewords.clear();
            //calculates rack every time
            possiblemoves = new HashMap<>();
            System.out.println("What are the tiles in your rack");
            String rack = scanner.nextLine();
            System.out.println("Analyzing with rack " + rack);
            tryablewords = computewords.findwords(rack);
            changedrack = false;
            }
            if(isturn){
                for(String word: tryablewords.keySet()){
                    //horizontal
                    for(int i = 0;i < 15 - word.length();i++){
                        for(int j = 0;j<15;j++){
                            int[] pos = {i,j};
                            if(testmove(board,word.toCharArray(),pos,false,tryablewords.get(word))){
                                //adds it to an arrayList of vaild move
                                int[] pos2 = {i,j,0};
                                //System.out.println("The word " + word + " is horizontally vaild at the position " + (i+1) + "," + (j+1));
                                try{
                                    possiblemoves.get(word).add(pos2);
                                }
                                catch(Exception e){
                                    ArrayList<int[]> iii = new ArrayList<>();
                                    iii.add(pos2);
                                    possiblemoves.put(word, iii);
                                }
                            }
                        }
                    }

                    //vertical
                    for(int i = 0;i < 15;i++){
                        for(int j = 0;j<15 - word.length();j++){
                            int[] pos = {i,j};
                            if(testmove(board,word.toCharArray(),pos,true,tryablewords.get(word))){
                                //adds it to an arrayList of vaild move
                                int[] pos2 = {i,j,1};
                            //System.out.println("The word " + word + " is vertically vaild at the position " + (i+1) + "," + (j+1));
                                try{
                                    possiblemoves.get(word).add(pos2);
                                }
                                catch(Exception e){
                                    ArrayList<int[]> iii = new ArrayList<>();
                                    iii.add(pos2);
                                    possiblemoves.put(word, iii);
                                }
                            }
                        }
                    }                    
                }
            }


            //when not turn, collect new move
            else{
                placetile("What word did your rival put down?");
            }
            //calculates points
            if (isturn){
            for(String word: possiblemoves.keySet()){
                for(int[] position: possiblemoves.get(word)){
                    int[] pos = {position[0],position[1]};
                    boolean isvert = position[2] == 1 ? true : false;
                    int numpoints = points.getpoints(board, word.toCharArray(), position, isvert, (tryablewords.get(word)).length > 0);
                    System.out.println("The word " + word + " at " + (pos[0] + 1) + "," + (pos[1] + 1) + "when vertical play is " + isvert + " gets " + numpoints + " points!");
                    allmoves.add(new Move(word, pos, isvert, numpoints));
                }
            }
            //calcs the best points
            Move[] bestmoves = new Move[10];
            for(int i = 0;i<10;i++){
                bestmoves[i] = new Move("",new int[2],false,0);
            }
            String[] bestwords = new String[10];
            for(Move move:allmoves){
                for(int i = 0;i<10;i++){
                    if(move.points > bestmoves[i].points){
                        for(int j = 9;j>i;j--){
                            bestwords[j] = bestwords[j-1];
                            bestmoves[j] = bestmoves[j-1];
                        }
                        bestmoves[i] = move;
                    bestwords[i] = move.word;
                    i = 99;
                    } 
                }
            }
            System.out.println();
            System.out.println();
            for(int i = 0;i<10;i++){
                if(bestmoves[i] != null){
                System.out.println(i + ") The word " + bestwords[i] + " played at " + (bestmoves[i].position[0] + 1) + "," + (bestmoves[i].position[1] + 1) + " when vertically played is " + bestmoves[i].isvert + " nets " + bestmoves[i].points + "points"); 
            }
        }
        //finally, asks the player what they'd did    
        System.out.println("Would you like to pre-do a move? Type in 0-9 to do so, else ignore");
        String digit = scanner.nextLine();
        if(digit.length() > 0){
        if(digit.charAt(0) > 47 && digit.charAt(0) < 58){
                Move performed = bestmoves[digit.charAt(0) - 48];
                if(performed.isvert){
                    board.addvertword(performed.word.toCharArray(), performed.position);
                    testBoard.addvertword(performed.word.toCharArray(), performed.position);

                }
                else{
                    board.addhoriword(performed.word.toCharArray(), performed.position);
                    testBoard.addvertword(performed.word.toCharArray(), performed.position);

                }
                System.out.println(board.toString());
            }
        }
        else{
        placetile("What word would you like to put down?");
        }
        changedrack = true;
        }
        isturn = !isturn;
        }
    }
    //tests if a move is vaild using built-in board commands
    public static boolean testmove(Board b, char[] word, int[] position, boolean isvert, char[] neededchars){
        boolean ans = false;
        if(!isvert){
            if(!b.horivaild(word, position, neededchars)){
                return false;
            }
            else{
                testBoard.addhoriword(word, position);
                if(testBoard.boardvaild()){
                    ans = true;
                }
                else{
                    ans = false;
                }

            }
        }
        else{
            if(!b.vertvaild(word, position, neededchars)){
                return false;
            }
            else{
                testBoard.addvertword(word, position);
                if(testBoard.boardvaild()){
                    ans = true;
                }
                else{
                    ans = false;
                }

            }            
        }
        for(int i = 0;i<15;i++){
            for(int j = 0;j<15;j++){
                testBoard.gameboard[i][j] = b.gameboard[i][j];
            }
        }
        return ans;
    }
    //places word on board
    public static void placetile(String custommessage){
        boolean override = false;
        int[] positon = new int[2];
        boolean ishori;
        System.out.println(custommessage);
        char[] word = scanner.nextLine().toCharArray();            
        System.out.println("What's the corrids of the word?");
        positon[0] = scanner.nextInt() - 1;
        positon[1] = scanner.nextInt() - 1;
        scanner.nextLine();
        while(true){
            ishori = false;
            System.out.println("Is the word vertical or horizontal?");
            String ans = scanner.nextLine();
            if(ans.equals("Vertical")){
                ishori = false;
                break;
            }
            else if(ans.equals("Horizontal")){
                ishori = true;
                break;
            }
            else{
                System.out.println("Please write either \"Vertical\" or \"Horizontal\"");
            }
        }
        if(ishori){
            if(!board.horivaild(word, positon, new char[0])){
                System.out.println("Hmm, that move seems invaild, lets try again");
                System.out.println("Would you like to override it (Y/N)");
                if(!scanner.nextLine().equals("Y")){
                    for(int i = 0;i<15;i++){
                        for(int j = 0;j<15;j++){
                            board.gameboard[i][j] = testBoard.gameboard[i][j];
                        }
                    }
                placetile(custommessage);
                }
                else{
                    board.addhoriword(word,positon);
                    testBoard.addhoriword(word, positon);
                    override = true;
                }
            }
            else{
                board.addhoriword(word,positon);
                testBoard.addhoriword(word, positon);
            }
        }
        else{
            if(!board.vertvaild(word, positon, new char[0])){
                System.out.println("Hmm, that move seems invaild, lets try again");
                System.out.println("Would you like to override it (Y/N)");
                if(!scanner.nextLine().equals("Y")){
                    for(int i = 0;i<15;i++){
                        for(int j = 0;j<15;j++){
                            board.gameboard[i][j] = testBoard.gameboard[i][j];
                        }
                    }
                placetile(custommessage);
                }
                else{
                    board.addvertword(word,positon);
                    testBoard.addvertword(word, positon);
                    override = true;
                }
            }
            else{
            board.addvertword(word,positon);
            testBoard.addvertword(word, positon);
            }
        }
        if(!board.boardvaild() && !override){
            System.out.println("Hmm, the board doesn't seem to be vaild, lets try again");
            System.out.println("Would you like to override it (Y/N)");
            if(!scanner.nextLine().equals("Y")){
                for(int i = 0;i<15;i++){
                    for(int j = 0;j<15;j++){
                        board.gameboard[i][j] = testBoard.gameboard[i][j];
                    }
                }
            placetile(custommessage);
            }
            else{
                if(ishori){
                    board.addhoriword(word,positon);
                    testBoard.addhoriword(word, positon);
                }
                else{
                    board.addvertword(word,positon);
                    testBoard.addvertword(word, positon);                    
                }
            }
        }
        System.out.println(board.toString());
    }
}
