import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;
import java.util.Scanner;
import java.util.Random;

public class HillClimbingVerbose {

	SudokuBoard board = new SudokuBoard();
	static int rows = 25;
	static int columns = 25;
	static int subrows = 5;
	static int subcolumns = 5;
	private int [][] current_board = new int [rows][columns];
	static String board_name;
	private int currentX;
	private int currentY;
	Scanner sc = new Scanner(System.in);

	public HillClimbingVerbose(String board_name) {
		
		HillClimbingVerbose.board_name = board_name;
		
	}
	//THIS IS THE MAIN METHOD THAT WILL RUN THE HILL CLIMBING ALGORITHM
	
	//IT CALCULATES THE NEIGHBOR OF LOWEST COST AND PERFORMS A SWAP ON THAT VALUE
	public boolean hillClimbingSolution() throws FileNotFoundException, IOException, InterruptedException, NullPointerException {
		
		//IMPORT BOARD AND CALL SETCURRENTBOARD METHOD
		getBoard();
		Random r = new Random();
		int rand_x;
		int rand_y;
		int c;
		int n;
		int s;
		int e;
		int w;
		int random;
		int iterations = 0;
		int max_iterations = 5000;
		int starting_conflicts;
		
		//COST OF A MOVE WILL BE SET VERY HIGH IF MOVE WOULD CAUSE AN ARRAY OUT OF BOUNDS EXCEPTION
		int out_of_bounds = 1000;
		
		//PRINT INITIAL BOARD
		board.printBoard(getCurrentBoard());
		
		System.out.println("This is the initial Sudoku Board you have selected. \nResize the display if necessary and enter a 1 to solve with the hill climbing algorithm.");
		int input = sc.nextInt();
		if (input == 1) {
			System.out.println("The hill climbing algorithm begins by randomly filling in all subarrays with the missing values 1 - 25.");
			
			//RANDOMIZE MISSING NUMBERS IN THE SUBARRAYS
			setCurrentBoard(replaceMissingNumbers(getCurrentBoard()));
		
			//PRINT RANDOMIZED BOARD
			board.updateBoard(getCurrentBoard(),0,0);
			
			System.out.println("The hill climbing algorithm will now start at a random spot on the sudoku board.");
			System.out.println("The moves available are swapping values with the neighboring spots to the north, south, east, or west.");
			System.out.println("The algorithm will select which of these moves causes the least number of conflicts in the board.");
			
			rand_x = r.nextInt(25);
			rand_y = r.nextInt(25);
			
			setCurrentX(rand_x);
			setCurrentY(rand_y);
			
			starting_conflicts = totalCost(getCurrentBoard());

			while(totalCost(getCurrentBoard()) != 0) {
				 
				if(iterations < max_iterations) {

				//FINDING THE NUMBER OF CONFLICTS AT CURRENT POSITION
				c = totalCost(getCurrentBoard());
				
				//TOTAL CONFLICTS IF CURRENT POSITION SWAPPED NORTH
				if (getCurrentX() - 1 < 0) {
					n = out_of_bounds;
				} else {
				n = costNorthSwap(getCurrentX(), getCurrentY());
				}
				
				//TOTAL CONFLICTS IF CURRENT POSITION SWAPPED SOUTH
				if (getCurrentX() + 1 > (rows - 1)) {
					s = out_of_bounds;
				} else {
					s = costSouthSwap(getCurrentX(), getCurrentY());
				}
				
				//TOTAL CONFLICTS IF CURRENT POSITION SWAPPED WEST
				if (getCurrentY() - 1 < 0) {
					w = out_of_bounds;
				} else {
					w = costWestSwap(getCurrentX(), getCurrentY());
				}
				
				//TOTAL CONFLICTS IF CURRENT POSITION SWAPPED EAST
				if (getCurrentY() + 1 > (columns - 1)) {
					e = out_of_bounds;
				} else {
					e = costEastSwap(getCurrentX(), getCurrentY());
				}
				
				System.out.println("Number of conflicts at current position " + c + "\nNumber of conflicts if swapping to the north: " + n + "\nNumber of conflicts if swapping to the south: " + s + "\nNumber of conflicts if swapping to the west: " + w  + "\nNumber of conflicts if swapping to the east: " + e);
				//CHOOSING THE BEST NEIGHBOR
				if (n <= c && n < s && n < w && n < e){
					northSwap(getCurrentX(), getCurrentY());
					System.out.println("Move north chosen.");
					board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
				} else if (s <= c && s < n && s < w && s < e) {
					
					southSwap(getCurrentX(), getCurrentY());
					System.out.println("Move south chosen.");
					board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
				} else if (e <= c && e < n && e < s && e < w) {
					
					eastSwap(getCurrentX(), getCurrentY());
					System.out.println("Move east chosen.");
					board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
				} else if (w <= c && w < n && w < s && w < e) {
					
					westSwap(getCurrentX(), getCurrentY());
					System.out.println("Move west chosen.");
					board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
				} else if (n == s && n < e && n < w && n < c) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == e && n < s && n < w && n < c) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == w && n < s && n < e && n < c) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (w == s && w < e && w < n && w < c) {
					random = r.nextInt(2);
					if (random == 0) {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("West swap chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (w == e && w < s && w < n && w < c) {
					random = r.nextInt(2);
					if (random == 0) {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("West swap chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (e == s && e < w && e < n && e < c) {
					random = r.nextInt(2);
					if (random == 0) {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == s && n == e && n < w && n < c) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == s && n == w && n < e && n < c) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == w && n == e && n < s && n < c) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == s && n == c && n < e && n < w) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == e && n == c && n < s && n < w) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == w && n == c && n < s && n < e) {
					random = r.nextInt(2);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (s == w && s == e && s < n && s < c) {
					random = r.nextInt(3);
					if (random == 0) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (s == e && s == c && s < n && s < w) {
					random = r.nextInt(2);
					if (random == 0) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (s == w && s == c && s < n && s < e) {
					random = r.nextInt(2);
					if (random == 0) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (e == w && e == c && e < n && e < s) {
					random = r.nextInt(2);
					if (random == 0) {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == w && n == e && n == s && n == c) {
					random = r.nextInt(4);
					if (random == 0) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 2) {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == s && n == w && n == c && n < e) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == s && n == e && n == c && n < w) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (n == w && n == e && n == c && n < s) {
					random = r.nextInt(3);
					if (random == 0) {
						northSwap(getCurrentX(), getCurrentY());
						System.out.println("Move north chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (s == w && s == e && s == c && s < n) {
					random = r.nextInt(3);
					if (random == 0) {
						southSwap(getCurrentX(), getCurrentY());
						System.out.println("Move south chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else if (random == 1) {
						westSwap(getCurrentX(), getCurrentY());
						System.out.println("Move west chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					} else {
						eastSwap(getCurrentX(), getCurrentY());
						System.out.println("Move east chosen.");
						board.updateBoard(getCurrentBoard(),getCurrentX(), getCurrentY());
					}
				} else if (c < n && c < s && c < e && c < w) {
					//local max
					System.out.println("You have reached a local minimum. This unsolved board started with " + starting_conflicts + " total conflicts and ended with " + c + " total conflicts.");
					Thread.sleep(5000);
					return true;
				} 
				} else {
					System.out.println("You have reached the timout condition of 5000 moves. This unsolved board started with " + starting_conflicts + " total conflicts and ended with " + totalCost(getCurrentBoard()) + " total conflicts.");
					Thread.sleep(5000);
					return true;
				}
				Thread.sleep(50);
				iterations ++;
		}
			
			System.out.println("The board is solved!");
			System.out.println("This board ended with 0 total conflicts.");
			Thread.sleep(5000);
	}
		return true;
	}

	//METHOD THAT SWAPS VALUES TO THE NORTH
	public void northSwap(int x, int y) {
		
		int m;
		int p;
		
		m = getCurrentBoard()[x][y];
		p = getCurrentBoard()[x - 1][y];
		
		getCurrentBoard()[x][y] = p;
		getCurrentBoard()[x - 1][y] = m;
		
		setCurrentX(x - 1);
		setCurrentBoard(getCurrentBoard());
		
	}
	//METHOD THAT SWAPS VALUES TO THE SOUTH
	public void southSwap(int x, int y) {
		
		int m;
		int p;
		
		m = getCurrentBoard()[x][y];
		p = getCurrentBoard()[x + 1][y];
		
		getCurrentBoard()[x][y] = p;
		getCurrentBoard()[x + 1][y] = m;
		
		setCurrentX(x + 1);
		setCurrentBoard(getCurrentBoard());
		
	}
	//METHOD THAT SWAPS VALUES TO THE WEST
	public void westSwap(int x, int y) {
		
		int m;
		int p;
		
		m = getCurrentBoard()[x][y];
		p = getCurrentBoard()[x][y - 1];
		
		getCurrentBoard()[x][y] = p;
		getCurrentBoard()[x][y - 1] = m;
		
		setCurrentY(y - 1);
		setCurrentBoard(getCurrentBoard());
		
	}
	//METHOD THAT SWAPS VALUES TO THE EAST
	public void eastSwap(int x, int y) {
		
		int m;
		int p;
		
		m = getCurrentBoard()[x][y];
		p = getCurrentBoard()[x][y + 1];
		
		getCurrentBoard()[x][y] = p;
		getCurrentBoard()[x][y + 1] = m;
		
		setCurrentY(y + 1);
		setCurrentBoard(getCurrentBoard());
		
	}
	//CALCULATES COST OF NORTH SWAP
	public int costNorthSwap(int x, int y){
		
		int tempboard[][] = new int [rows][columns];
		int m;
		int p;
		int cost;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				tempboard[i][j] = getCurrentBoard()[i][j];
			}
		}
		
		m = tempboard[x][y];
		p = tempboard[x - 1][y];
		
		tempboard[x][y] = p;
		tempboard[x - 1][y] = m;
		
		cost = (totalCost(tempboard));
		return cost;
	}
	//CALCULATES COST OF SOUTH SWAP
	public int costSouthSwap(int x, int y){
		
		int tempboard[][] = new int [rows][columns];
		int m;
		int p;
		int cost;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				tempboard[i][j] = getCurrentBoard()[i][j];
			}
		}
		
		m = tempboard[x][y];
		p = tempboard[x + 1][y];
		
		tempboard[x][y] = p;
		tempboard[x + 1][y] = m;
		
		cost = (totalCost(tempboard));
		return cost;
	}
	//CALCULATES COST OF WEST SWAP
	public int costWestSwap(int x, int y){
		
		int tempboard[][] = new int [rows][columns];
		int m;
		int p;
		int cost;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				tempboard[i][j] = getCurrentBoard()[i][j];
			}
		}
		
		m = tempboard[x][y];
		p = tempboard[x][y - 1];
		
		tempboard[x][y] = p;
		tempboard[x][y - 1] = m;
		
		cost = (totalCost(tempboard));
		return cost;
	}
	//CALCULATES COST OF EAST SWAP
	public int costEastSwap(int x, int y){
		
		int tempboard[][] = new int [rows][columns];
		int m;
		int p;
		int cost;
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				tempboard[i][j] = getCurrentBoard()[i][j];
			}
		}
		
		m = tempboard[x][y];
		p = tempboard[x][y + 1];
		
		tempboard[x][y] = p;
		tempboard[x][y + 1] = m;
		
		cost = (totalCost(tempboard));
		return cost;
	}
	
	
	//METHOD THAT WILL CALL IN THE BOARD FROM THE FILE
	public void getBoard () throws FileNotFoundException, IOException, NullPointerException, InterruptedException{
		
		int [][] board1 = new int [rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				board1[i][j] = board.buildBoard(board_name)[i][j];
			}
		}
		setCurrentBoard(board1);
	}
	
	//GETTER FOR X
	public int getCurrentX() {
		
		return currentX;
	}
	//SETTER FOR X
	public void setCurrentX(int x) {
		
		this.currentX = x;
	}
	//GETTER FOR Y
	public int getCurrentY() {
		
		return currentY;	
	}
	//SETTER FOR Y
	public void setCurrentY(int y) {
		
		this.currentY = y;
	}
	//GETTER FOR CURRENT BOARD
	public int [][] getCurrentBoard(){
		
		return current_board;
	}
	//SETTER FOR CURRENT BOARD
	public void setCurrentBoard(int [][] b) {
		
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				this.current_board [i][j] = b[i][j];
			}
		}
	}
	//METHOD ADDS UP ALL CONFLICTS IN SUBARRAYS
	public int totalSubArrays(int [][] board) {
		
		int total = 0;
		
		total = total + addSubArrays(subArray1(board));
		total = total + addSubArrays(subArray2(board));
		total = total + addSubArrays(subArray3(board));
		total = total + addSubArrays(subArray4(board));
		total = total + addSubArrays(subArray5(board));
		total = total + addSubArrays(subArray6(board));
		total = total + addSubArrays(subArray7(board));
		total = total + addSubArrays(subArray8(board));
		total = total + addSubArrays(subArray9(board));
		total = total + addSubArrays(subArray10(board));
		total = total + addSubArrays(subArray11(board));
		total = total + addSubArrays(subArray12(board));
		total = total + addSubArrays(subArray13(board));
		total = total + addSubArrays(subArray14(board));
		total = total + addSubArrays(subArray15(board));
		total = total + addSubArrays(subArray16(board));
		total = total + addSubArrays(subArray17(board));
		total = total + addSubArrays(subArray18(board));
		total = total + addSubArrays(subArray19(board));
		total = total + addSubArrays(subArray20(board));
		total = total + addSubArrays(subArray21(board));
		total = total + addSubArrays(subArray22(board));
		total = total + addSubArrays(subArray23(board));
		total = total + addSubArrays(subArray24(board));
		total = total + addSubArrays(subArray25(board));
		
		return total;
		
		
	}
	//ADDS UP CONFLICTS IN EACH SUBARRAY
	public int addSubArrays(int [][] subArray) {
		
		int one = 0;
		int two = 0;
		int three = 0;
		int four = 0;
		int five = 0;
		int six = 0;
		int seven = 0;
		int eight = 0;
		int nine = 0;
		int ten = 0;
		int eleven = 0;
		int twelve = 0;
		int thirteen = 0;
		int fourteen = 0;
		int fifteen = 0;
		int sixteen = 0;
		int seventeen = 0;
		int eighteen = 0;
		int nineteen = 0;
		int twenty = 0;
		int twentyone = 0;
		int twentytwo = 0;
		int twentythree = 0;
		int twentyfour = 0;
		int twentyfive = 0;

		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				
			if (subArray[i][j] == 1) {
				one = one + 1;
			} else if (subArray[i][j] == 2) {
				two = two + 1;
			} else if (subArray[i][j] == 3) {
				three = three + 1;
			} else if (subArray[i][j] == 4) {
				four = four + 1;
			} else if (subArray[i][j] == 5) {
				five = five + 1;
			} else if (subArray[i][j] == 6) {
				six = six + 1;
			} else if (subArray[i][j] == 7) {
				seven = seven + 1;
			} else if (subArray[i][j] == 8) {
				eight = eight + 1;
			} else if (subArray[i][j] == 9) {
				nine = nine + 1;
			} else if (subArray[i][j] == 10) {
				ten = ten + 1;
			} else if (subArray[i][j] == 11) {
				eleven = eleven + 1;
			} else if (subArray[i][j] == 12) {
				twelve = twelve + 1;
			} else if (subArray[i][j] == 13) {
				thirteen = thirteen + 1;
			} else if (subArray[i][j] == 14) {
				fourteen = fourteen + 1;
			} else if (subArray[i][j] == 15) {
				fifteen = fifteen + 1;
			} else if (subArray[i][j] == 16) {
				sixteen = sixteen + 1;
			} else if (subArray[i][j] == 17) {
				seventeen = seventeen + 1;
			} else if (subArray[i][j] == 18) {
				eighteen = eighteen + 1;
			} else if (subArray[i][j] == 19) {
				nineteen = nineteen + 1;
			} else if (subArray[i][j] == 20) {
				twenty = twenty + 1;
			} else if (subArray[i][j] == 21) {
				twentyone = twentyone + 1;
			} else if (subArray[i][j] == 22) {
				twentytwo = twentytwo + 1;
			} else if (subArray[i][j] == 23) {
				twentythree = twentythree + 1;
			} else if (subArray[i][j] == 24) {
				twentyfour = twentyfour + 1;
			} else if (subArray[i][j] == 25) {
				twentyfive = twentyfive + 1;
			} else {
			}
		}
		}
		
		int total_conflicts = 0;
		
		if(one > 1) {
			total_conflicts = total_conflicts + (one - 1);
		} else if(two > 1) {
			total_conflicts = total_conflicts + (two - 1);
		} else if(three > 1) {
			total_conflicts = total_conflicts + (three - 1);
		} else if(four > 1) {
			total_conflicts = total_conflicts + (four - 1);
		} else if(five > 1) {
			total_conflicts = total_conflicts + (five - 1);
		} else if(six > 1) {
			total_conflicts = total_conflicts + (six - 1);
		} else if(seven > 1) {
			total_conflicts = total_conflicts + (seven - 1);
		} else if(eight > 1) {
			total_conflicts = total_conflicts + (eight -1);
		} else if(nine > 1) {
			total_conflicts = total_conflicts + (nine - 1);
		} else if(ten > 1) {
			total_conflicts = total_conflicts + (ten - 1);
		} else if (eleven > 0) {
			total_conflicts = total_conflicts + (eleven - 1);
		} else if (twelve > 0) {
			total_conflicts = total_conflicts + (twelve - 1);
		} else if (thirteen > 0) {
			total_conflicts = total_conflicts + (thirteen - 1);
		} else if (fourteen > 0) {
			total_conflicts = total_conflicts + (fourteen - 1);
		} else if (fifteen > 0) {
			total_conflicts = total_conflicts + (fifteen - 1);
		} else if (sixteen > 0) {
			total_conflicts = total_conflicts + (sixteen - 1);
		} else if (seventeen > 0) {
			total_conflicts = total_conflicts + (seventeen - 1);
		} else if (eighteen > 0) {
			total_conflicts = total_conflicts + (eighteen - 1);
		} else if (nineteen > 0) {
			total_conflicts = total_conflicts + (nineteen - 1);
		} else if (twenty > 0) {
			total_conflicts = total_conflicts + (twenty - 1);
		} else if (twentyone > 0) {
			total_conflicts = total_conflicts + (twentyone - 1);
		} else if (twentytwo > 0) {
			total_conflicts = total_conflicts + (twentytwo - 1);
		} else if (twentythree > 0) {
			total_conflicts = total_conflicts + (twentythree - 1);
		} else if (twentyfour > 0) {
			total_conflicts = total_conflicts + (twentyfour - 1);
		} else if (twentyfive > 0) {
			total_conflicts = total_conflicts + (twentyfive - 1);
		}
		return total_conflicts;
	}
	//ADDS UP TOTAL CONFLICTS IN COLUMNS
	public int addTotalColumns(int[][] board) {

		int final_total = 0;
		int total_conflicts;
		int one;
		int two;
		int three;
		int four;
		int five;
		int six;
		int seven;
		int eight;
		int nine;
		int ten;
		int eleven;
		int twelve;
		int thirteen;
		int fourteen;
		int fifteen;
		int sixteen;
		int seventeen;
		int eighteen;
		int nineteen;
		int twenty;
		int twentyone;
		int twentytwo;
		int twentythree;
		int twentyfour;
		int twentyfive;

		for (int j = 0; j < board.length; j++) {

			one = 0;
			two = 0;
			three = 0;
			four = 0;
			five = 0;
			six = 0;
			seven = 0;
			eight = 0;
			nine = 0;
			ten = 0;
			eleven = 0;
			twelve = 0;
			thirteen = 0;
			fourteen = 0;
			fifteen = 0;
			sixteen = 0;
			seventeen = 0;
			eighteen = 0;
			nineteen = 0;
			twenty = 0;
			twentyone = 0;
			twentytwo = 0;
			twentythree = 0;
			twentyfour = 0;
			twentyfive = 0;
			total_conflicts = 0;

			for (int i = 0; i < board.length; i++) {

				if (board[i][j] == 1) {
					one = one + 1;
				} else if (board[i][j] == 2) {
					two = two + 1;
				} else if (board[i][j] == 3) {
					three = three + 1;
				} else if (board[i][j] == 4) {
					four = four + 1;
				} else if (board[i][j] == 5) {
					five = five + 1;
				} else if (board[i][j] == 6) {
					six = six + 1;
				} else if (board[i][j] == 7) {
					seven = seven + 1;
				} else if (board[i][j] == 8) {
					eight = eight + 1;
				} else if (board[i][j] == 9) {
					nine = nine + 1;
				} else if (board[i][j] == 10) {
					ten = ten + 1;
				} else if (board[i][j] == 11) {
					eleven = eleven + 1;
				} else if (board[i][j] == 12) {
					twelve = twelve + 1;
				} else if (board[i][j] == 13) {
					thirteen = thirteen + 1;
				} else if (board[i][j] == 14) {
					fourteen = fourteen + 1;
				} else if (board[i][j] == 15) {
					fifteen = fifteen + 1;
				} else if (board[i][j] == 16) {
					sixteen = sixteen + 1;
				} else if (board[i][j] == 17) {
					seventeen = seventeen + 1;
				} else if (board[i][j] == 18) {
					eighteen = eighteen + 1;
				} else if (board[i][j] == 19) {
					nineteen = nineteen + 1;
				} else if (board[i][j] == 20) {
					twenty = twenty + 1;
				} else if (board[i][j] == 21) {
					twentyone = twentyone + 1;
				} else if (board[i][j] == 22) {
					twentytwo = twentytwo + 1;
				} else if (board[i][j] == 23) {
					twentythree = twentythree + 1;
				} else if (board[i][j] == 24) {
					twentyfour = twentyfour + 1;
				} else if (board[i][j] == 25) {
					twentyfive = twentyfive + 1;
				}
			}
			if (one > 1) {
				total_conflicts = total_conflicts + (one - 1);
			} else if (two > 1) {
				total_conflicts = total_conflicts + (two - 1);
			} else if (three > 1) {
				total_conflicts = total_conflicts + (three - 1);
			} else if (four > 1) {
				total_conflicts = total_conflicts + (four - 1);
			} else if (five > 1) {
				total_conflicts = total_conflicts + (five - 1);
			} else if (six > 1) {
				total_conflicts = total_conflicts + (six - 1);
			} else if (seven > 1) {
				total_conflicts = total_conflicts + (seven - 1);
			} else if (eight > 1) {
				total_conflicts = total_conflicts + (eight - 1);
			} else if (nine > 1) {
				total_conflicts = total_conflicts + (nine - 1);
			} else if (ten > 1) {
				total_conflicts = total_conflicts + (ten - 1);
			} else if (eleven > 1) {
				total_conflicts = total_conflicts + (eleven - 1);
			} else if (twelve > 1) {
				total_conflicts = total_conflicts + (twelve - 1);
			} else if (thirteen > 1) {
				total_conflicts = total_conflicts + (thirteen - 1);
			} else if (fourteen > 1) {
				total_conflicts = total_conflicts + (fourteen - 1);
			} else if (fifteen > 1) {
				total_conflicts = total_conflicts + (fifteen - 1);
			} else if (sixteen > 1) {
				total_conflicts = total_conflicts + (sixteen - 1);
			} else if (seventeen > 1) {
				total_conflicts = total_conflicts + (seventeen - 1);
			} else if (eighteen > 1) {
				total_conflicts = total_conflicts + (eighteen - 1);
			} else if (nineteen > 1) {
				total_conflicts = total_conflicts + (nineteen - 1);
			} else if (twenty > 1) {
				total_conflicts = total_conflicts + (twenty - 1);
			} else if (twentyone > 1) {
				total_conflicts = total_conflicts + (twentyone - 1);
			} else if (twentytwo > 1) {
				total_conflicts = total_conflicts + (twentytwo - 1);
			} else if (twentythree > 1) {
				total_conflicts = total_conflicts + (twentythree - 1);
			} else if (twentyfour > 1) {
				total_conflicts = total_conflicts + (twentyfour - 1);
			} else if (twentyfive > 1) {
				total_conflicts = total_conflicts + (twentyfive - 1);
			}
			final_total = final_total + total_conflicts;
		}
		return final_total;
	}
	//ADDS UP TOTAL CONFLICTS IN ROWS
	public int addTotalRows(int[][] board) {

		int final_total = 0;
		int total_conflicts;
		int one;
		int two;
		int three;
		int four;
		int five;
		int six;
		int seven;
		int eight;
		int nine;
		int ten;
		int eleven;
		int twelve;
		int thirteen;
		int fourteen;
		int fifteen;
		int sixteen;
		int seventeen;
		int eighteen;
		int nineteen;
		int twenty;
		int twentyone;
		int twentytwo;
		int twentythree;
		int twentyfour;
		int twentyfive;

		for (int i = 0; i < board.length; i++) {

			one = 0;
			two = 0;
			three = 0;
			four = 0;
			five = 0;
			six = 0;
			seven = 0;
			eight = 0;
			nine = 0;
			ten = 0;
			eleven = 0;
			twelve = 0;
			thirteen = 0;
			fourteen = 0;
			fifteen = 0;
			sixteen = 0;
			seventeen = 0;
			eighteen = 0;
			nineteen = 0;
			twenty = 0;
			twentyone = 0;
			twentytwo = 0;
			twentythree = 0;
			twentyfour = 0;
			twentyfive = 0;
			total_conflicts = 0;

			for (int j = 0; j < board.length; j++) {

				if (board[i][j] == 1) {
					one = one + 1;
				} else if (board[i][j] == 2) {
					two = two + 1;
				} else if (board[i][j] == 3) {
					three = three + 1;
				} else if (board[i][j] == 4) {
					four = four + 1;
				} else if (board[i][j] == 5) {
					five = five + 1;
				} else if (board[i][j] == 6) {
					six = six + 1;
				} else if (board[i][j] == 7) {
					seven = seven + 1;
				} else if (board[i][j] == 8) {
					eight = eight + 1;
				} else if (board[i][j] == 9) {
					nine = nine + 1;
				} else if (board[i][j] == 10) {
					ten = ten + 1;
				} else if (board[i][j] == 11) {
					eleven = eleven + 1;
				} else if (board[i][j] == 12) {
					twelve = twelve + 1;
				} else if (board[i][j] == 13) {
					thirteen = thirteen + 1;
				} else if (board[i][j] == 14) {
					fourteen = fourteen + 1;
				} else if (board[i][j] == 15) {
					fifteen = fifteen + 1;
				} else if (board[i][j] == 16) {
					sixteen = sixteen + 1;
				} else if (board[i][j] == 17) {
					seventeen = seventeen + 1;
				} else if (board[i][j] == 18) {
					eighteen = eighteen + 1;
				} else if (board[i][j] == 19) {
					nineteen = nineteen + 1;
				} else if (board[i][j] == 20) {
					twenty = twenty + 1;
				} else if (board[i][j] == 21) {
					twentyone = twentyone + 1;
				} else if (board[i][j] == 22) {
					twentytwo = twentytwo + 1;
				} else if (board[i][j] == 23) {
					twentythree = twentythree + 1;
				} else if (board[i][j] == 24) {
					twentyfour = twentyfour + 1;
				} else if (board[i][j] == 25) {
					twentyfive = twentyfive + 1;
				}
			}
			if (one > 1) {
				total_conflicts = total_conflicts + (one - 1);
			} else if (two > 1) {
				total_conflicts = total_conflicts + (two - 1);
			} else if (three > 1) {
				total_conflicts = total_conflicts + (three - 1);
			} else if (four > 1) {
				total_conflicts = total_conflicts + (four - 1);
			} else if (five > 1) {
				total_conflicts = total_conflicts + (five - 1);
			} else if (six > 1) {
				total_conflicts = total_conflicts + (six - 1);
			} else if (seven > 1) {
				total_conflicts = total_conflicts + (seven - 1);
			} else if (eight > 1) {
				total_conflicts = total_conflicts + (eight - 1);
			} else if (nine > 1) {
				total_conflicts = total_conflicts + (nine - 1);
			} else if (ten > 1) {
				total_conflicts = total_conflicts + (ten - 1);
			} else if (eleven > 1) {
				total_conflicts = total_conflicts + (eleven - 1);
			} else if (twelve > 1) {
				total_conflicts = total_conflicts + (twelve - 1);
			} else if (thirteen > 1) {
				total_conflicts = total_conflicts + (thirteen - 1);
			} else if (fourteen > 1) {
				total_conflicts = total_conflicts + (fourteen - 1);
			} else if (fifteen > 1) {
				total_conflicts = total_conflicts + (fifteen - 1);
			} else if (sixteen > 1) {
				total_conflicts = total_conflicts + (sixteen - 1);
			} else if (seventeen > 1) {
				total_conflicts = total_conflicts + (seventeen - 1);
			} else if (eighteen > 1) {
				total_conflicts = total_conflicts + (eighteen - 1);
			} else if (nineteen > 1) {
				total_conflicts = total_conflicts + (nineteen - 1);
			} else if (twenty > 1) {
				total_conflicts = total_conflicts + (twenty - 1);
			} else if (twentyone > 1) {
				total_conflicts = total_conflicts + (twentyone - 1);
			} else if (twentytwo > 1) {
				total_conflicts = total_conflicts + (twentytwo - 1);
			} else if (twentythree > 1) {
				total_conflicts = total_conflicts + (twentythree - 1);
			} else if (twentyfour > 1) {
				total_conflicts = total_conflicts + (twentyfour - 1);
			} else if (twentyfive > 1) {
				total_conflicts = total_conflicts + (twentyfive - 1);
			}
			final_total = final_total + total_conflicts;
		}
		return final_total;
	}

	//ADDS UP ALL CONFLICTS IN BOARD
	public int totalCost(int [][] board) {
		int total_cost = 0;
		
		total_cost = addTotalRows(board) + addTotalColumns(board) + totalSubArrays(board);
		return total_cost;
	}
	
	public boolean checkForValue(int val, int [][] subArray){
	    for(int i = 0; i < 5; i++){
	        for(int j = 0; j < 5; j++){
	            if(subArray[i][j] == val) return true;
	        }
	    }
	    return false;
	}
	//USED TO RANDOMIZE BOARD AT THE START
	public int [][] replaceMissingNumbers(int [][] Board) {
		
		Stack <Integer> stack = new Stack <Integer>();
		
		// randomizing subArray1
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray1(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray1(Board)[i][j] == 0) {
					Board[i][j] = stack.pop();
				}
			}
		}
		// randomizing subArray2		
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray2(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray2(Board)[i][j] == 0) {
					Board[i][j + 5] = stack.pop();
				}
			}
		}
		// randomizing subArray3
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray3(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray3(Board)[i][j] == 0) {
					Board[i][j + 10] = stack.pop();
				}
			}
		}
		// randomizing subArray4
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray4(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray4(Board)[i][j] == 0) {
					Board[i][j + 15] = stack.pop();
				}
			}
		}

		// randomizing subArray5
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray5(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray5(Board)[i][j] == 0) {
					Board[i][j + 20] = stack.pop();
				}
			}
		}
		// randomizing subArray6
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray6(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray6(Board)[i][j] == 0) {
					Board[i + 5][j] = stack.pop();
				}
			}
		}
		// randomizing subArray7		
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray7(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray7(Board)[i][j] == 0) {
					Board[i + 5][j + 5] = stack.pop();
				}
			}
		}
		// randomizing subArray8
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray8(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray8(Board)[i][j] == 0) {
					Board[i + 5][j + 10] = stack.pop();
				}
			}
		}
		// randomizing subArray9
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray9(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray9(Board)[i][j] == 0) {
					Board[i + 5][j + 15] = stack.pop();
				}
			}
		}
		// randomizing subArray10
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray10(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray10(Board)[i][j] == 0) {
					Board[i + 5][j + 20] = stack.pop();
				}
			}
		}
		// randomizing subArray11
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray11(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray11(Board)[i][j] == 0) {
					Board[i + 10][j] = stack.pop();
				}
			}
		}
		// randomizing subArray12		
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray12(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray12(Board)[i][j] == 0) {
					Board[i + 10][j + 5] = stack.pop();
				}
			}
		}
		// randomizing subArray13
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray13(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray13(Board)[i][j] == 0) {
					Board[i + 10][j + 10] = stack.pop();
				}
			}
		}
		// randomizing subArray14
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray14(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray14(Board)[i][j] == 0) {
					Board[i + 10][j + 15] = stack.pop();
				}
			}
		}
		// randomizing subArray15
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray15(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray15(Board)[i][j] == 0) {
					Board[i + 10][j + 20] = stack.pop();
				}
			}
		}
		// randomizing subArray16
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray16(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray16(Board)[i][j] == 0) {
					Board[i + 15][j] = stack.pop();
				}
			}
		}
		// randomizing subArray17		
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray17(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray17(Board)[i][j] == 0) {
					Board[i + 15][j + 5] = stack.pop();
				}
			}
		}
		// randomizing subArray18
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray18(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray18(Board)[i][j] == 0) {
					Board[i + 15][j + 10] = stack.pop();
				}
			}
		}
		// randomizing subArray19
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray19(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray19(Board)[i][j] == 0) {
					Board[i + 15][j + 15] = stack.pop();
				}
			}
		}
		// randomizing subArray20
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray20(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray20(Board)[i][j] == 0) {
					Board[i + 15][j + 20] = stack.pop();
				}
			}
		}
		// randomizing subArray21
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray21(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray21(Board)[i][j] == 0) {
					Board[i + 20][j] = stack.pop();
				}
			}
		}
		// randomizing subArray22		
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray22(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray22(Board)[i][j] == 0) {
					Board[i + 20][j + 5] = stack.pop();
				}
			}
		}
		// randomizing subArray23
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray23(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray23(Board)[i][j] == 0) {
					Board[i + 20][j + 10] = stack.pop();
				}
			}
		}
		// randomizing subArray24
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray24(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray24(Board)[i][j] == 0) {
					Board[i + 20][j + 15] = stack.pop();
				}
			}
		}
		// randomizing subArray25
		for(int i = 0; i < subrows * subcolumns; i++) {
			if(!checkForValue((i + 1), subArray25(Board))) {
				stack.push(i + 1);
			}
		}
		for (int i = 0; i < subrows; i++) {
			for (int j = 0; j < subcolumns; j++) {
				if (subArray25(Board)[i][j] == 0) {
					Board[i + 20][j + 20] = stack.pop();
				}
			}
		}
		return Board;
	}
	
	//METHODS THAT TAKE IN THE BOARD AND RETURN THE SUBARRAYS
	public int [][] subArray1(int [][] Board){
		
		int [][] sub_1 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_1[i][j] = Board[i][j];
			}
		}
		return sub_1;
	}
	
	public int [][] subArray2(int [][] Board){
		
		int [][] sub_2 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_2[i][j] = Board[i][j + 5];
			}
		}
		return sub_2;
	}
	
	public int [][] subArray3(int [][] Board){
		
		int [][] sub_3 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_3[i][j] = Board[i][j + 10];
			}
		}
		return sub_3;
	}
	
	public int [][] subArray4(int [][] Board){
		
		int [][] sub_4 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_4[i][j] = Board[i][j + 15];
			}
		}
		return sub_4;
	}
	
	public int [][] subArray5(int [][] Board){
		
		int [][] sub_5 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_5[i][j] = Board[i][j + 20];
			}
		}
		return sub_5;
	}
	
	public int [][] subArray6(int [][] Board){
		
		int [][] sub_6 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_6[i][j] = Board[i + 5][j];
			}
		}
		return sub_6;
	}
	
	public int [][] subArray7(int [][] Board){
		
		int [][] sub_7 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_7[i][j] = Board[i + 5][j + 5];
			}
		}
		return sub_7;
	}
	
	public int [][] subArray8(int [][] Board){
		
		int [][] sub_8 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_8[i][j] = Board[i + 5][j + 10];
			}
		}
		return sub_8;
	}
	
	public int [][] subArray9(int [][] Board){
		
		int [][] sub_9 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_9[i][j] = Board[i + 5][j + 15];
			}
		}
		return sub_9;
	}
	
	public int [][] subArray10(int [][] Board){
		
		int [][] sub_10 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_10[i][j] = Board[i + 5][j + 20];
			}
		}
		return sub_10;
	}
	
	public int [][] subArray11(int [][] Board){
		
		int [][] sub_11 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_11[i][j] = Board[i + 10][j];
			}
		}
		return sub_11;
	}
	
	public int [][] subArray12(int [][] Board){
		
		int [][] sub_12 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_12[i][j] = Board[i + 10][j + 5];
			}
		}
		return sub_12;
	}
	
	public int [][] subArray13(int [][] Board){
		
		int [][] sub_13 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_13[i][j] = Board[i + 10][j + 10];
			}
		}
		return sub_13;
	}
	
	public int [][] subArray14(int [][] Board){
		
		int [][] sub_14 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_14[i][j] = Board[i + 10][j + 15];
			}
		}
		return sub_14;
	}
	
	public int [][] subArray15(int [][] Board){
		
		int [][] sub_15 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_15[i][j] = Board[i + 10][j + 20];
			}
		}
		return sub_15;
	}
	
	public int [][] subArray16(int [][] Board){
		
		int [][] sub_16 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_16[i][j] = Board[i + 15][j];
			}
		}
		return sub_16;
	}
	
	public int [][] subArray17(int [][] Board){
		
		int [][] sub_17 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_17[i][j] = Board[i + 15][j + 5];
			}
		}
		return sub_17;
	}
	
	public int [][] subArray18(int [][] Board){
		
		int [][] sub_18 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_18[i][j] = Board[i + 15][j + 10];
			}
		}
		return sub_18;
	}
	
	public int [][] subArray19(int [][] Board){
		
		int [][] sub_19 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_19[i][j] = Board[i + 15][j + 15];
			}
		}
		return sub_19;
	}
	
	public int [][] subArray20(int [][] Board){
		
		int [][] sub_20 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_20[i][j] = Board[i + 15][j + 20];
			}
		}
		return sub_20;
	}
	
	public int [][] subArray21(int [][] Board){
		
		int [][] sub_21 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_21[i][j] = Board[i + 20][j];
			}
		}
		return sub_21;
	}
	
	public int [][] subArray22(int [][] Board){
		
		int [][] sub_22 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_22[i][j] = Board[i + 20][j + 5];
			}
		}
		return sub_22;
	}
	
	public int [][] subArray23(int [][] Board){
		
		int [][] sub_23 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_23[i][j] = Board[i + 20][j + 10];
			}
		}
		return sub_23;
	}
	
	public int [][] subArray24(int [][] Board){
		
		int [][] sub_24 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_24[i][j] = Board[i + 20][j + 15];
			}
		}
		return sub_24;
	}
	
	public int [][] subArray25(int [][] Board){
		
		int [][] sub_25 = new int [5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				sub_25[i][j] = Board[i + 20][j + 20];
			}
		}
		return sub_25;
	}
	
}
