
public class yxiao11Board {

    public enum Direction {I, N, NE, NW,E, W, S, SE, SW,};
    public enum State {Empty, MinPlayer, MaxPlayer}; 
    State[][] Grid = new State[8][8];
    private int[][] player1Neighbors = new int[8][8];
    private int[][] player2Neighbors = new int[8][8];
    boolean[][] visited = new boolean[8][8];

    boolean isLegal(Move move, int playerNum) {
        if(Grid[move.toRow -'A'][move.toCol-1] != State.Empty){
            System.out.println("Invalid: There is already a piece in that position.");
            return false;
        }
        
        if((move.moveType != Move.MoveType.PLACE_PIECE)){
            if(Grid[move.fromRow -'A'][move.fromCol-1] != player(playerNum)){
                System.out.println("Invalid MoveType!");
                return false;
            }
        }

        if(move.toCol == 1 || move.toCol == 8){
            if(move.toRow == 'A' || move.toRow == 'H'){
                System.out.println("Invalid: Piece lands in the corner.");
                return false;
            }   
        }

        if (playerNum == 1){
            if(move.toCol == 1 || move.toCol == 8){
                System.out.println("Invalid: Piece lands in opponent's goal regions.");
                return false;
            }
        } 
        else {
            if(move.toRow == 'A' || move.toRow == 'H'){
                System.out.println("Invalid: Piece lands in opponent's goal regions.");
                return false;
            }
        }

        if (DualWin(move, playerNum)){
            System.out.println("Invalid: Dual win.");
            return false;           
        }
        
        if(Clumps(move, playerNum)){
            System.out.println("Invalid: Creates Clump.");
            return false;
        }
        return true;
    }
    
    boolean isNetwork(int playerNum, int x, int y, int length, Direction dir) {
        State player = playerNum == 1 ? State.MaxPlayer : State.MinPlayer;
        State opponentPlayer = playerNum == 1 ? State.MinPlayer : State.MaxPlayer;
        visited[x][y] = true;
        if (playerNum == 1 && x == 7) {
            if(length >= 6) {
                return true;
            }
        }

        if (playerNum == 2 && y == 7) {
            if(length >= 6) {
                return true;
            }
        }
        
        //South
        if (dir != Direction.S){
            for(int i = y + 1; i < 8; i++){
                if(Grid[x][i] == opponentPlayer){
                    break;
                }
                if(Grid[x][i] == player && !visited[x][i]){
                    boolean tmp = isNetwork(playerNum, x, i, length + 1, Direction.S);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }

        //SouthWest
        if (dir != Direction.SW){
            for(int i = 0; x + i < 8 && y - i > 0; i++){
                if(Grid[x+i][y-i] == opponentPlayer){
                    break;
                }
                if(Grid[x+i][y-i] == player && !visited[x+i][y-i]){
                    boolean tmp = isNetwork(playerNum, x+i, y-i, length + 1, Direction.SW);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }

        //SouthEast
        if (dir != Direction.SE){
            for(int i = 0; x + i < 8 && y + i < 8; i++){
                if(Grid[x+i][y+i] == opponentPlayer){
                    break;
                }
                if(Grid[x+i][y+i] == player && !visited[x+i][y+i]){
                    boolean tmp = isNetwork(playerNum, x+i, y+i, length + 1, Direction.SE);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }

        //North
        if (dir != Direction.N){
            for(int i = 0; x - i > 0; i++){
                if(Grid[x-i][y] == opponentPlayer){
                    break;
                }
                if(Grid[x-i][y] == player && !visited[x-i][y]){
                    boolean tmp = isNetwork(playerNum, x-i, y, length+1, Direction.N);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }
        
        //NorthWest
        if (dir != Direction.NW){
            for(int i = 0; x - i > 0 && y - i > 0; i++){
                if(Grid[x-i][y-i] == opponentPlayer){
                    break;
                }
                if(Grid[x-i][y - i] == player && !visited[x-i][y-i]){
                    boolean tmp = isNetwork(playerNum, x-i, y-i, length + 1, Direction.NW);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }
        
        //NorthEast
        if (dir != Direction.NE){
            for(int i = 0; x - i > 0 && y + i < 8; i++){
                if(Grid[x-i][y+i] == opponentPlayer){
                    break;
                }
                if(Grid[x-i][y + i] == player && !visited[x-i][y+i]){
                    boolean tmp = isNetwork(playerNum, x-i, y+i, length + 1, Direction.NE);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }

        //West
        if (dir != Direction.W){
            for(int i = 0; y - i > 0; i++){
                if(Grid[x][y-i] == opponentPlayer){
                    break;
                }
                if(Grid[x][y-i] == player && !visited[x][y-i]){
                    boolean tmp = isNetwork(playerNum, x, y-i, length + 1, Direction.W);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }
        }

        //East
        if (dir != Direction.E){
            for(int i = 0; y + i < 8; i++){
                if(Grid[x][y+i] == opponentPlayer){
                    break;
                }
                if(Grid[x][y+i] == player && !visited[x][y+i]){
                    boolean tmp = isNetwork(playerNum, x, y+i, length + 1, Direction.E);
                    if(!tmp){
                        visited[x][y] = false;
                    }
                    return tmp;
                }
            }

        }
        return false;
    }

    void setVisited(int xStart, int yStart) {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                visited[i][j] = false;
            }
        }
        
        if(xStart == 0){
            for(int i = 0; i < 8; i++){
                visited[0][i] = true;
            }
            visited[0][yStart] = false;
        }
        
        if(yStart ==0){
            for(int i = 0; i < 8; i++){
                visited[i][0] = true;
            }
            visited[xStart][0] = false;
        }
    }
    
    private boolean DualWin(Move move, int playerNum){
        boolean OpponentWin = false;     
        //Initial the board
        State tmp = Grid[move.toRow -'A'][move.toCol-1];

        if (move.moveType == Move.MoveType.PLACE_PIECE){
            //Check to see if it creates network for player 2
            if(playerNum == 1){
                Grid[move.toRow - 'A'][move.toCol - 1] = State.MaxPlayer;
                for (int i = 1; i < 7; i++){
                    if (Grid[i][0] == State.MinPlayer){
                        setVisited(i,0);
                        OpponentWin = isNetwork(2, i, 0, 1, Direction.I);
                        if(OpponentWin){
                            break;                  
                        }
                    }
                }
                if (isWinning(move, playerNum) && OpponentWin){
                    //Set board back to initial state
                    Grid[move.toRow -'A'][move.toCol-1] = tmp;
                    return true;
                }
            }
            //Check to see if it creates network for player1
            if (playerNum == 2){
                Grid[move.toRow - 'A'][move.toCol - 1] = State.MinPlayer;
                for(int i = 1; i < 7; i++){
                    if(Grid[0][i] == State.MaxPlayer){
                        setVisited(0, i);
                        OpponentWin = isNetwork(1, 0, i, 1, Direction.I);
                        if(OpponentWin){
                            break;
                        }
                    }
                }
                if(isWinning(move, playerNum) && OpponentWin){
                    //Set board back to initial state
                    Grid[move.toRow -'A'][move.toCol-1] = tmp;
                    return true;
                }
            }
            //Restore initial state of board
            Grid[move.toRow -'A'][move.toCol-1] = tmp;       
        } 
        else if (move.moveType == Move.MoveType.MOVE_PIECE){
            State fromTmp = Grid[move.fromRow - 'A'][move.fromCol-1];
            Grid[move.fromRow - 'A'][move.fromCol-1] = State.Empty;
            Grid[move.toRow - 'A'][move.toCol -1] = fromTmp;
            //update board

            if(playerNum == 1){
                //Check to see if it creates network for player 2
                for (int i = 1; i < 7; i++){
                    if (Grid[i][0] == State.MinPlayer){
                        setVisited(i,0);
                        OpponentWin = isNetwork(2, i, 0, 1, Direction.I);
                        if(OpponentWin){
                            break;                  
                        }
                    }
                }
                if (isWinning(move, playerNum) && OpponentWin){
                    Grid[move.toRow - 'A'][move.toCol - 1] = tmp;
                    Grid[move.fromRow - 'A'][move.fromCol -1] = fromTmp;
                    return true;
                }
            }

            if (playerNum==2){
                for(int i = 1; i < 7; i++){
                    if(Grid[0][i] == State.MaxPlayer){
                        setVisited(0, i);
                        OpponentWin = isNetwork(1, 0, i, 1, Direction.I);
                        if(OpponentWin){
                            break;
                        }
                    }
                }
                if(isWinning(move, playerNum) && OpponentWin){
                    Grid[move.toRow -'A'][move.toCol-1] = tmp;
                    Grid[move.fromRow - 'A'][move.fromCol-1] = tmp;
                    return true;
                }
                
            }
            Grid[move.toRow -'A'][move.toCol-1] = tmp;
            Grid[move.fromRow - 'A'][move.fromCol-1] = tmp;
        }
        return false;
    }
    
    private boolean hasNeighbor(int playerNum, int rowIndex, int colIndex) {
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++ ) {
            //Except 1 8 and A H
            if(i < 0 || i > 7) {
                continue;
            }
            for(int j = colIndex - 1; j <= colIndex + 1; j++) {
                if(j < 0 || j > 7 || (i == rowIndex && j == colIndex)) {
                    continue;
                }
                if(playerNum == 1) {
                    if (Grid[rowIndex][colIndex] == State.MaxPlayer){
                        if (player1Neighbors[rowIndex][colIndex] > 0){
                            return true;
                        }
                    }
                } 
                else if(playerNum == 2) {
                    if (Grid[rowIndex][colIndex] == State.MinPlayer){
                        if (player2Neighbors[rowIndex][colIndex] > 0){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    private void NeighborCount(int playerNum, int rowIndex, int colIndex, boolean increment) {

        for (int i = rowIndex - 1; i <= rowIndex + 1; i++ ) {
            if(i < 0 || i > 7) {
                continue;
            }
            for(int j = colIndex - 1; j <= colIndex + 1; j++) {
                if(j < 0 || j > 7 || (i == rowIndex && j == colIndex)) {
                    continue;
                }
                if(playerNum == 1) {
                    if(increment) {
                        player1Neighbors[i][j]++;                       
                    } 
                    else{
                        player1Neighbors[i][j]--;
                    }
                } 
                else if(playerNum == 2) {
                    if(increment) {
                        player2Neighbors[i][j]++;                       
                    } 
                    else {
                        player2Neighbors[i][j]--;
                    }
                }
            }
        }
    }
    
    private boolean Clumps(Move move, int playerNum){
        int toLocalCount = 0;
        int toRowIndex = move.toRow -'A';
        int toColIndex = move.toCol-1;

        if(move.moveType == Move.MoveType.PLACE_PIECE){
            toLocalCount = (playerNum == 1 ? player1Neighbors[toRowIndex][toColIndex] : player2Neighbors[toRowIndex][toColIndex]);
        } 
        else if(move.moveType == Move.MoveType.MOVE_PIECE){
            State tmp = Grid[toRowIndex][toColIndex]; 
            Grid[toRowIndex][toColIndex] = State.Empty;

            NeighborCount(playerNum, toRowIndex, toColIndex, false);
            toLocalCount = (playerNum == 1 ? player1Neighbors[toRowIndex][toColIndex] : player2Neighbors[toRowIndex][toColIndex]);

            NeighborCount(playerNum, toRowIndex, toColIndex, true);
            //Setting it back to initial state
            Grid[toRowIndex][toColIndex] = tmp;

        }

        if(toLocalCount == 0) {
            return false;
        }
        
//        if(toLocalCount == 1) {
//            if (playerNum == 1) {
//                NeighborCount(playerNum, toRowIndex, toColIndex, false);
//                System.out.println("Player1 " + player1Neighbors[toRowIndex][toColIndex]);
//            }
//            else {
//                NeighborCount(playerNum, toRowIndex, toColIndex, false);
//                System.out.println("Player2 " + player2Neighbors[toRowIndex][toColIndex]);                
//            }
//            return false;
//        }
        
        else if(toLocalCount == 2) {
            System.out.println("Creates clump.");
            return true;
        }
        return hasNeighbor(playerNum, toRowIndex, toColIndex);
    }
    
    boolean hasWon(int playerNum) {
        boolean win = false;
        if(playerNum == 1) {
            for(int i = 1; i < 7; i++) {
                if(Grid[0][i] == State.MaxPlayer) {
                    setVisited(0, i);
                    win = isNetwork(playerNum, 0, i, 1, Direction.I);
                    if(win){
                        return true;
                    }
                }
            }
        }
        
        if(playerNum == 2) {
            for (int i = 1; i < 7; i++) {
                if (Grid[i][0] == State.MinPlayer) {
                    setVisited(i,0);
                    win = isNetwork(playerNum, i, 0, 1, Direction.I);
                    if(win){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private State player(int playerNum)
    {
        if (playerNum == 1) {
            return State.MaxPlayer;
        }
        if (playerNum == 2) {
            return State.MinPlayer;
        }
        return null;
    }
    
    boolean isWinning(Move move, int playerNum) {

        if(move.moveType == Move.MoveType.PLACE_PIECE){
            Grid[move.toRow - 'A'][move.toCol - 1] = player(playerNum);

        } 
        else if(move.moveType == Move.MoveType.MOVE_PIECE) {
            Grid[move.fromRow - 'A'][move.fromCol - 1] = State.Empty;
            Grid[move.toRow - 'A'][move.toCol -1] = player(playerNum);
        }
        
        boolean isWin = hasWon(playerNum);
        //reset board

        if(move.moveType == Move.MoveType.PLACE_PIECE) {
            Grid[move.toRow - 'A'][move.toCol -1] = State.Empty;
        } 
        else if(move.moveType == Move.MoveType.MOVE_PIECE) {
            Grid[move.toRow - 'A'][move.toCol - 1] = State.Empty;
            Grid[move.fromRow - 'A'][move.fromCol -1] = player(playerNum);
        }
        if(isWin) {
            System.out.println("Player " + playerNum + " win");
            return true;
        } 
        else {
            return false;           
        }
    }

    boolean placeMove(Move move, int playerNum) {
        int rowIndex = move.toRow -'A';
        int colIndex = move.toCol-1;
        
        if (!isLegal(move, playerNum)) {
            return false;
        }

        if (move.moveType == Move.MoveType.PLACE_PIECE) {
            Grid[rowIndex][colIndex] = player(playerNum);
            NeighborCount(playerNum, rowIndex, colIndex, true);
        }
        else {
            Grid[move.fromRow -'A'][move.fromCol-1] = State.Empty;
            NeighborCount(playerNum, move.fromRow -'A', move.fromCol-1, false);
            Grid[rowIndex][colIndex] = player(playerNum);
            NeighborCount(playerNum, rowIndex, colIndex, true);
        }

        return true;
    }
    
    //Empty Board
    public void initiateBoard() {
        for(int i = 0; i < 8; i ++) {
            for(int j = 0; j < 8; j++) {
                Grid[i][j] = State.Empty;
            }
        }
    }
    
    //Show Board
    public void printBoard() {
        System.out.print("   1 2 3 4 5 6 7 8");
        System.out.print("\n");

        for(int i = 0; i < 8; i++) {
            System.out.print((char)('A' + i));
            System.out.print("  ");
            for(int j = 0; j < 8; j++) {
                if(Grid[i][j] == State.Empty) {
                    System.out.print(". ");
                }
                if(Grid[i][j] == State.MaxPlayer) {
                    System.out.print("1 ");
                }
                if(Grid[i][j] == State.MinPlayer) {
                    System.out.print("2 ");
                }
            }
            System.out.print("\n");
        }   
    }
   
//    public static void main(String[] args){
//        yxiao11Board b = new yxiao11Board();
//        b.initiateBoard();
//        b.printBoard();
//        System.out.println("A , 6 - 1");
//        System.out.println(b.isLegal(new Move('A', 6), 1));
//        b.placeMove(new Move('A', 6), 1);
//        b.printBoard();
//        System.out.println("E , 2 - 2");
//        System.out.println(b.isLegal(new Move('E', 2), 2));
//        b.placeMove(new Move('E', 2), 2);
//        b.printBoard();
//        System.out.println("G , 7 - 1");
//        System.out.println(b.isLegal(new Move('G', 7), 1));
//        b.placeMove(new Move('G', 7), 1);
//        b.printBoard();
//        System.out.println("H , 1 - 2");
//        System.out.println(b.isLegal(new Move('H', 1), 2));
//        b.placeMove(new Move('H', 1), 2);
//        b.printBoard();
//        System.out.println("H , 2 - 2");
//        System.out.println(b.isLegal(new Move('H', 2), 2));
//        b.placeMove(new Move('H', 2), 2);
//        b.printBoard();
//        System.out.println("D , 7 - 1");
//        b.placeMove(new Move('D', 7), 1);
//        b.printBoard();
//        System.out.println("D , 4 - 1");
//        b.placeMove(new Move('D', 4), 1);
//        b.printBoard();
//        System.out.println("D , 5 - 2");
//        b.placeMove(new Move('D', 5), 2);
//        b.printBoard();
//        System.out.println("C , 7 - 1");
//        b.placeMove(new Move('C', 7), 1);
//        b.printBoard();
//        System.out.println("F , 4 - 1");
//        b.placeMove(new Move('F', 4), 1);
//        b.printBoard();
//        System.out.println("C , 5 - 1");
//        b.placeMove(new Move('C', 5), 1);   
//        b.printBoard();
//        System.out.println("E , 7 - 1");
//        b.placeMove(new Move('E', 7), 1); //wrong
//        b.printBoard();
//        System.out.println("C , 3 - 1");
//        b.placeMove(new Move('C', 3), 1);
//        b.printBoard();
//        System.out.println("D , 3 - 1"); //wrong
//        b.placeMove(new Move('D', 3), 1);
//        b.printBoard();
//        System.out.println(b.hasWon(1));
//    }
}