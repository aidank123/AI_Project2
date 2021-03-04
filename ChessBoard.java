import java.util.*;
public class ChessBoard{
  private int n;
  private int[][] board;
  private int[] queenAt;
  private boolean testing = false;

  public ChessBoard(int n){
    this.n = n;
    board = new int[n][n];
    for(int i = 0; i < n; i++)
      for(int j = 0; j < n; j++)
        board[i][j] = 0;
    Random rand = new Random();
    queenAt = new int[n];
    for(int i = 0; i < n; i++){
      queenAt[i] = rand.nextInt(n);
      handleAttacks(i, queenAt[i], true);
    }
  }

  public void moveQueen(int row, int column){
    handleAttacks(row, queenAt[row], false);
    queenAt[row] = column;
    handleAttacks(row, column, true);
  }

  public void handleAttacks(int x, int y, boolean sign){
    if (testing) System.out.printf("\n(%d,%d)\n***********************", x, y);
    int num = sign ? 1 : -1;
    board[x][y] -= 3 * num;
    for(int i = 0; i < n; i++)
      board[i][y] += num;
    for(int i = -1 * Math.min(x,y); i < Math.min(n-x,n-y); i++)
      board[x+i][y+i] += num;
    for(int i = -1 * Math.min(x,n-y-1); i < Math.min(n-x,y+1); i++)
      board[x+i][y-i] += num;
    if(testing) print();
  }
  public void print(){
    for(int i = 0; i < n; i++){
      System.out.printf("\n");
      for(int j = 0; j < n; j++){
        if(queenAt[i] == j)
            System.out.printf("Q ");
        else
          System.out.printf("%d ", board[i][j]);
      }
    }
  }
  public int attacksAt(int row, int column){
    return board[row][column];
  }
  public int queenAt(int row){
    return queenAt[row];
  }
}
