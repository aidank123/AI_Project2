import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;

public class SudokuBoard {  
	 
	static int rows = 25;
	static int columns = 25;
	static int subrows = 5;
	static int subcolumns = 5;
	static JFrame frame;

	public SudokuBoard() {
		
	}
	
	//THIS METHOD WILL BUILD THE SUDOKU BOARD FROM THE FILE INPUTS AND BE PASSED TO THE HILL CLIMBING AND SIMULATED ANNEALING CLASSES
	public int [][] buildBoard(String fileToRead) throws FileNotFoundException, IOException, InterruptedException, NullPointerException {
		
		int [][] board = new int[rows][columns];
		BufferedReader br = new BufferedReader(new FileReader(fileToRead));
		
		for(int i = 0; i < rows;i++) {
			String[] st = br.readLine().trim().split("\\s*,\\s*");
			for(int j = 0; j < columns;j++) {
				board[i][j] = Integer.parseInt(st[j]);
	}
		}
		br.close();
		return board;
	}
	
	//THIS METHOD PRINTS THE INITIAL SUDOKU BOARD WHEN IT IS CALLED
	public void printBoard(int [][] Board){

		String num1;
		frame = new JFrame();
	
		for(int i = 0; i < rows;i++) {
			for(int j = 0; j < columns;j++) {
				
				num1 = "" + Board[i][j];
				JLabel label = new JLabel(num1);
				label = new JLabel(num1);
				Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
				label.setBorder(border);
				label.setHorizontalAlignment(JLabel.CENTER);
			    label.setVerticalAlignment(JLabel.CENTER);

			    frame.add(label);
			}
		}

		frame.setLayout(new GridLayout(rows,columns));
		frame.setTitle("Sudoku Board Solver");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700,700);
		frame.setVisible(true);
	}
	
	//THIS METHOD WILL UPDATE THE BOARD EVERY TIME IT IS CALLED, REBUILDING IT
	public void updateBoard(int [][] Board, int x, int y) {
		
		String num2;
		clearBoard(Board);
		
		for(int s = 0; s < rows; s++) {
			for(int t = 0; t < columns;t++) {
				
				num2 = "" + Board[s][t];
				JLabel label = new JLabel(num2);
				label = new JLabel(num2);
				Border border1 = BorderFactory.createLineBorder(Color.BLACK, 1);
				label.setBorder(border1);
				if (s == x && t == y) {
					label.setForeground(Color.GREEN);
					Border border = BorderFactory.createLineBorder(Color.GREEN, 2);
					label.setBorder(border);
				}
				label.setHorizontalAlignment(JLabel.CENTER);
			    label.setVerticalAlignment(JLabel.CENTER);
				frame.add(label);
			}
		}
		frame.setVisible(true);
	}

	//THIS METHOD IS USED TO CLEAR THE BOARD
	public void clearBoard(int [][] Board) {
			
		frame.getContentPane().removeAll();
		frame.getContentPane().repaint();	
	}
	
}
