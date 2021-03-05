import java.util.*;
public class ChessHillClimber{
  private int utility;
  private ChessBoard board;
  private int n, consecutiveLaterals;
  private boolean verbose = false;

  public ChessHillClimber(int n){
    //start timer
    this.n = n;
    makeBoard();
    play();
    //board.print();
    System.out.println("\nDone");
    //end timer
  }
  public void makeBoard(){
      utility = 0;
      board = new ChessBoard(n);
      for(int row = 0; row < n; row++)
        utility += board.attacksAt(row, board.queenAt(row));
      utility /= 2;
  }
  public void play(){
    int count = 0;
    Random rand = new Random();
    while(utility != 0){
      if(consecutiveLaterals == 10)
        makeBoard();
      if(verbose) board.print();
      ArrayList<Integer> bestMoves = new ArrayList<Integer>();
      int greatestReduction = 0;
      for(int row = 0; row < n; row++){
        int currentAttacks = board.attacksAt(row,board.queenAt(row));
        for(int column = 0; column < n; column++){
          if(currentAttacks - board.attacksAt(row,column) > greatestReduction){
            if(verbose){
              System.out.println();
              for(int i: bestMoves)
                System.out.printf("(%d,%d) ", i / 1000, i % 1000);
              System.out.printf("| Difference: %d", greatestReduction);
            }
            greatestReduction = currentAttacks - board.attacksAt(row,column);
            bestMoves.clear();
            bestMoves.add(row * 1000 + column);
          }
          else if(currentAttacks - board.attacksAt(row,column) == greatestReduction)
            bestMoves.add(row * 1000 + column);
        }
      }
      int move = bestMoves.get(rand.nextInt(bestMoves.size()));
      board.moveQueen(move / 1000, move % 1000);
      utility -= greatestReduction;
      if(greatestReduction == 0)
        consecutiveLaterals++;
      else
        consecutiveLaterals = 0;
      if(verbose){
        System.out.println();
        for(int i: bestMoves)
          System.out.printf("(%d,%d) ", i / 1000, i % 1000);
        System.out.printf("| Difference: %d", greatestReduction);
        System.out.printf("\nReduced by: %d || Utility: %d ",greatestReduction, utility);
        System.out.printf("|| Move: (%d,%d) || Moves: %d",move / 1000, move % 1000, count);
      }
      count++;
    }
  }







}