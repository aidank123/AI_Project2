public class ChessBoard{
  private int n;
  private Square[][] board;
  private boolean testing = true;

  public ChessBoard(int n){
    this.n = n;
    board = new Square[n][n];
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        board[i][j] = new Square(0, false);
    for(int i = 0; i < n; i++)
      addQueenAt(i,i);
  }
  //return False if queen is already at that square
  public boolean moveQueen(int x0, int y0, int x1, int y1){
    if(!board[x0][y0].queen || board[x1][y1].queen){
      if(testing) System.out.printf("Queen move from (%d,%d) to (%d,%d) failed!\n",x0,y0,x1,y1);
      return false;
    }
    removeQueenAt(x0,y0);
    addQueenAt(x1,y1);
    return true;
  }
  public boolean addQueenAt(int x, int y){
    if(board[x][y].queen)
      return false;
    board[x][y].queen = true;
    handleAttacks(x, y, true);
    return true;
  }
  public boolean removeQueenAt(int x, int y){
    if(!board[x][y].queen)
      return false;
    board[x][y].queen = false;
    handleAttacks(x, y, false);
    return true;
  }
  public void handleAttacks(int x, int y, boolean sign){
    if (testing) System.out.printf("\n(%d,%d)\n***********************", x, y);
    int num = sign ? 1 : -1;
    board[x][y].attacks -= 4 * num;
    for(int i = 0; i < n; i++)
      board[i][y].attacks += num;
    for(int i = 0; i < n; i++)
      board[x][i].attacks += num;
    for(int i = -1 * Math.min(x,y); i < Math.min(n-x,n-y); i++)
      board[x+i][y+i].attacks += num;
    for(int i = -1 * Math.min(x,n-y-1); i < Math.min(n-x,y+1); i++)
      board[x+i][y-i].attacks += num;
    if(testing) printBoard();
  }
  public void printBoard(){
    for(int i = 0; i < n; i++){
      System.out.printf("\n");
      for(int j = 0; j < n; j++)
        System.out.printf("%d ", board[i][j].attacks);
      }
  }

  public boolean queenAt(int x, int y){
    return board[x][y].queen;
  }
  public boolean onBoard(int x, int y){
    return x > -1 && y > -1 && x < board.length && y < board[x].length;
  }

  class Square{
    int attacks;
    boolean queen;

    public Square(int attacks, boolean queen){
      this.attacks = attacks;
      this.queen = queen;
    }
    public boolean isValid(){
      return !(queen && attacks != 0);
    }
  }
}
