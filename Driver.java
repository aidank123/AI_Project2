import java.util.Scanner;
public class Driver{


  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter size of chess board: ");
    int n = sc.nextInt();
    ChessBoard board = new ChessBoard(n);
  }
}
