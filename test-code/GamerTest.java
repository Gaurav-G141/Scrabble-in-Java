import java.util.Scanner;
public class GamerTest {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Board b = new Board();
        Board testBoard = new Board();
        int[] positon = new int[2];
        boolean ishori;
        while(true){
            System.out.println("What's your word?");
            char[] word = s.nextLine().toCharArray();            
            System.out.println("What's the corrids of your word?");
            positon[0] = s.nextInt() - 1;
            positon[1] = s.nextInt() - 1;
            s.nextLine();
            System.out.println("What characters are needed?");
            char[] neededchars = s.nextLine().toCharArray();
            
            while(true){
                ishori = false;
                System.out.println("Is your word vertical or horizontal?");
                String ans = s.nextLine();
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
            if(b.horivaild(word, positon, neededchars)){
                testBoard.addhoriword(word, positon);
                if(testBoard.boardvaild()){
                    b.addhoriword(word,positon);
                }
                else{
                    for(int i = 0;i<15;i++){
                        for(int j = 0;j<15;j++){
                            testBoard.gameboard[i][j] = b.gameboard[i][j];
                        }
                    }
                }
            }
        }
        else{
            if(b.vertvaild(word, positon, neededchars)){
                testBoard.addvertword(word, positon);
                if(testBoard.boardvaild()){
                    b.addvertword(word,positon);
                }
                else{
                    for(int i = 0;i<15;i++){
                        for(int j = 0;j<15;j++){
                            testBoard.gameboard[i][j] = b.gameboard[i][j];
                        }
                    }
                }
            }
        }
        System.out.println(b.toString());
        }

    }
}
