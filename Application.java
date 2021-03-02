import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

	public class Application {

		public static void main(String[] args) throws IOException, FileNotFoundException{
			SudokuBoard b = new SudokuBoard();
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			String board_name = "";
			int userInput;
			
			System.out.println("Would you like to solve a sudoku board or the n-queens problem? Enter a 1 for sudoku or a 2 for n-queens.");
			userInput = sc1.nextInt();

			if (userInput == 1) {

				System.out.println("Please type in what board you would like to solve: ");
				board_name = sc2.nextLine();
					
				b.printBoard(board_name);
			
			} else if (userInput == 2) {
				System.out.println("n-queens problem");
				
			} else {
				System.out.println("Incorrect user input.");
				System.out.println("Exiting... please restart the program.");
				System.exit(0);
			}
			
			sc1.close();
			sc2.close();
			
		}
	}


