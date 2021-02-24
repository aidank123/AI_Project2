public class Board{
  private int n;
  private Boolean[][] board;

  public Board(int n){
    this.n = n;
    board = new Boolean[n][n];
    for(int i = 0; i < board.length; i++)
      for(int j = 0; j < board[i].length; j++)
        board[i][j] = false;
  }
  //return False if queen is already at that square 
  public boolean placeQueenAt(int x, int y){
    boolean temp = board[x][y];
    board[x][y] = true;
    return !temp;
  }
  public boolean queenAt(int x, int y){
    return board[x][y];
  }
  public boolean onBoard(int x, int y){
    return x > -1 && y > -1 && x < board.length && y < board[x].length;
  }
}
