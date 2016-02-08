import java.util.Scanner;

public class Network {
    
    public void playGame(Player player1, Player player2) {
        while(true) {
            Move player1Move = player1.getMove();
            player2.OpponentMove(player1Move);
            // Win
            if (player1.hasWon(1)) {
                break;
            }
                
            Move player2Move = player2.getMove();
            player1.OpponentMove(player2Move);
            // Win
            if (player2.hasWon(2)) {
                break;
            }
            
            player1.printBoard();
            player2.printBoard(); 
        }   
    }
    
    public void StartGame() {
        Scanner sc = SingletonScanner.getInstance();
        int numOfPlayers = -1;
        
        while(numOfPlayers == -1) {
            System.out.println("Please Enter number of players (0, 1, or 2): ");
            numOfPlayers = sc.nextInt();
            if (numOfPlayers == 0) {
                yxiao11ComputerPlayer player1 = new yxiao11ComputerPlayer(1);
                yxiao11ComputerPlayer player2 = new yxiao11ComputerPlayer(2);
                playGame(player1, player2);

            } 
            else if (numOfPlayers == 1) {
                yxiao11ComputerPlayer player2 = new yxiao11ComputerPlayer(2);
                HumanPlayer player1 = new HumanPlayer(1);
                playGame(player1, player2);

            } 
            else if (numOfPlayers == 2) {
                HumanPlayer player1 = new HumanPlayer(1);
                HumanPlayer player2 = new HumanPlayer(2);
                playGame(player1, player2);

            } 
            else {
                System.out.println("Invalid number of players.");
            }
        }     
    }
    
    public static void main(String[] args){
        Network n = new Network();
        n.StartGame();
    }
}