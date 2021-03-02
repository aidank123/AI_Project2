import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class SudokuBoard {  
	 
	int board[][];
	int rows = 25;
	int columns = 25;
	int value;
	String num;
	JFrame frame;
	
	//GraphicsEnvironment graphics = GraphicsEnvironment.getLocalGraphicsEnvironment();
	//GraphicsDevice device = graphics.getDefaultScreenDevice();
	  
	public SudokuBoard() {
		
	}
	
	public int[][] printBoard(String fileToRead) throws FileNotFoundException, IOException {
		
		frame = new JFrame();
		int rows = 25;
		int columns = 25;
		
		int [][] board = new int[rows][columns];
		BufferedReader br = new BufferedReader(new FileReader(fileToRead));
		
		for(int i = 0; i < rows;i++) {
			String[] st = br.readLine().trim().split("\\s*,\\s*");
			for(int j = 0; j < columns;j++) {
				board[i][j] = Integer.parseInt(st[j]);
				
				num = "" + board[i][j];
				JButton button = new JButton(num);
				
				frame.add(button);
				
			}
		}
		br.close();
		frame.setLayout(new GridLayout(rows, columns));
		frame.setSize(600,600);
		frame.setVisible(true);
		//device.setFullScreenWindow(frame);
		return board;
		
	}
}  