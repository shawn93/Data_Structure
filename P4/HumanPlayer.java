import java.util.Scanner;

public class HumanPlayer extends Player {

    yxiao11Board board = new yxiao11Board();
    
    public HumanPlayer(int playerNum) {
        super(playerNum);
        board.initiateBoard();
    }

    @Override
    public Move getMove() {
        Scanner sc = SingletonScanner.getInstance();
        Move move = null;
        while(move == null) {
            System.out.println("Player" +" " + playerNum + ": Enter row and column position to place piece");
            String row = sc.next();
            int col = sc.nextInt();
            System.out.println(row.charAt(0) + " " + col);
            move = new Move(row.charAt(0), col);
            if(!board.isLegal(move, playerNum)) {
                move = null;
            }
        }

        board.placeMove(move, playerNum);
//        board.printBoard();
        return move;
    }

    @Override
    public boolean hasWon(int playerNum) {
        return board.hasWon(playerNum);
    }
    
    @Override
    public void printBoard(){
        board.printBoard();
    }

    @Override
    public void OpponentMove(Move m) {
        if(playerNum == 1){
            board.placeMove(m, 2);          
        } else{
            board.placeMove(m, 1);
        }
    }
}