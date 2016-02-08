import java.util.Random;

public class yxiao11ComputerPlayer extends Player {
    
    yxiao11Board board = new yxiao11Board();
    int longestPath = 1;

    public yxiao11ComputerPlayer(int playerNum) {
        super(playerNum);
        board.initiateBoard();
    }
    
    @Override
    public void printBoard() {
        board.printBoard();
    }
    
    @Override
    public boolean hasWon(int playerNum) {
        return board.hasWon(playerNum);
    }
    
    @Override
    public Move getMove() {
        char row = 'A';
        int col = 0;
        Random r = new Random();
        Move m = null;

        do{
            if(playerNum == 1){
                String possibleRows = "ABCDEFGH";
                row = possibleRows.charAt(r.nextInt(possibleRows.length()));
                col = 2 + (int)(Math.random() * ((7 - 2) + 1));
                m = new Move(row, col);
            } 
            else{
                String possibleRows = "BCDEFG";
                row = possibleRows.charAt(r.nextInt(possibleRows.length()));
                col = 1 + (int)(Math.random() * ((8 - 1) + 1)); 
                m = new Move(row, col);
            }

        } 
        while(!(board.isLegal(m, playerNum)));

        return m;
    }

    @Override
    public void OpponentMove(Move m) {
        if(playerNum == 1) {
            board.placeMove(m, 2);
        } 
        else {
            board.placeMove(m, 1);
        }

    }
}