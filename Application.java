import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

	public class Application {

		public static void main(String[] args) throws IOException, FileNotFoundException, InterruptedException, NullPointerException {

			HillClimbingVerbose a;
			HillClimbing b;
			SimulatedAnnealingVerbose c;
			SimulatedAnnealing d;
      ChessBoard e;
      ChessHillClimber f;
      
			Scanner sc1 = new Scanner(System.in);
			Scanner sc2 = new Scanner(System.in);
			String board_name = "";
			int userInput;

			System.out.println("Would you like to solve a sudoku board or the n-queens problem? Enter a 1 for sudoku or a 2 for n-queens.");
			userInput = sc1.nextInt();

			if (userInput == 1) {

				System.out.println("Please type in what board you would like to solve: ");
				board_name = sc2.nextLine();
					
				System.out.println("Would you like a verbose output? Enter a 1 for verbose or a 2 for non-verbose.");
				userInput = sc1.nextInt();
				
				if(userInput == 1) {
					a = new HillClimbingVerbose(board_name);
					a.hillClimbingSolution();
					c = new SimulatedAnnealingVerbose(board_name);
					c.simulatedAnnealingSolution();
				} else {
					b = new HillClimbing(board_name);
					b.hillClimbingSolution();
					d = new SimulatedAnnealing(board_name);
					d.simulatedAnnealingSolution();
				}

			} else if (userInput == 2) {
      	System.out.println("n-queens problem");
				System.out.print("What is your n: ");
				int n = sc2.nextInt();
				System.out.println("Doing hill climbing...");
				new ChessHillClimber(n);
				System.out.println("Doing simulated annealing...");
				new ChessSimulatedAnnealing(n);
				
				
				
			} else {
				System.out.println("Incorrect user input.");
				System.out.println("Exiting... please restart the program.");
				System.exit(0);
			}
			
			sc1.close();
			sc2.close();
			
		}
	}

