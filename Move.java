public class Move {
    String word = "";
    int[] position = {0,0};
    boolean isvert = false;
    int points = 0;
    public Move(String word,int[] position,boolean isvert,int points){
        this.word = word;
        this.position = position;
        this.isvert = isvert;
        this.points = points;
    }
}
