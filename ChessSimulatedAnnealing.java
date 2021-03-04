import java.util.*;

public class ChessSimulatedAnnealing{
  private ChessBoard board;
  private int cost, n;
  private double decayRate = .001, temperature;

  public ChessSimulatedAnnealing(int n){
      cost = 0;
      this.n = n;
      board = new ChessBoard(n);
      for(int row = 0; row < n; row++)
        cost += board.attacksAt(row, board.queenAt(row));
      cost /= 2;
      temperature = n;
      play();
  }
  public void play(){
    Random rand = new Random();
    while(temperature > .001 && cost != 0){
      int propRow;
      do{
        propRow = rand.nextInt(n);
      }while(board.attacksAt(propRow, board.queenAt(propRow)) == 0);
      int propCol = rand.nextInt(n);
      int costChange = board.attacksAt(propRow, propCol) - board.attacksAt(propRow, board.queenAt(propRow));
      if(costChange <= 0 || Math.exp(-costChange / temperature) >= rand.nextDouble()){
        cost += costChange;
        board.moveQueen(propRow, propCol);
        System.out.printf("Move: (%d,%d) || Cost change: %d || Cost: %d || Probability: %f\n", propRow, propCol, costChange, cost, costChange <= 0 ? 1 : Math.exp(-costChange / temperature));
      }
      temperature *= 1 - decayRate;
    }
    board.print();
  }
}
