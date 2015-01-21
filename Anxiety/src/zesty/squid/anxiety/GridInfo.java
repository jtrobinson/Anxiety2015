package zesty.squid.anxiety;

import android.util.Log;

public class GridInfo {
	private int _minX;
	private int _minY;
	private int _maxX;
	private int _maxY;
	private int _squareSide;
	
	private GridSquare [][] squares;
	
	protected static SeatBoundary q1Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,287);
	protected static SeatBoundary q2Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,287);
	protected static SeatBoundary q3Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,592);
	protected static SeatBoundary q4Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,592);
	
	//public GridInfo (int minX, int minY, int maxX, int maxY, int squareSide){
	public GridInfo (){
		squares = new GridSquare [ActGameScreen.NUM_ROWS][ActGameScreen.NUM_COLS];
		int startX = ActGameScreen.BOARD_MIN_X;
		int startY = ActGameScreen.BOARD_MIN_Y;
		int topX, topY = 0;
		
		String output = "";
		for (int y = 0; y < ActGameScreen.NUM_ROWS; y++){
			topY = startY + (y*ActGameScreen.SQUARE_SIDE);
			for (int x = 0; x < ActGameScreen.NUM_COLS; x++){
				topX = startX + (x*ActGameScreen.SQUARE_SIDE);
				squares[y][x] = new GridSquare(topX, topY);
			}
		}
		
		setSeats();
	}
	
	public String debugString(){
		String output = "";
		for (int y = 0; y < ActGameScreen.NUM_ROWS; y++){
			for (int x = 0; x < ActGameScreen.NUM_COLS; x++){
				output = output + squares[y][x].getChar();
			}
			output = output + ';';
		}
		return output;
	}
	
	private void setSeats(){
		// Top Row
		squares[0][0].setSeat(true);
		squares[0][1].setSeat(true);
		squares[0][2].setSeat(true);
		squares[0][4].setSeat(true);
		squares[0][5].setSeat(true);
		squares[0][6].setSeat(true);
		
		// Q1, Q2
		squares[2][0].setSeat(true);
		squares[2][1].setSeat(true);
		squares[2][5].setSeat(true);
		squares[2][6].setSeat(true);
		squares[3][0].setSeat(true);
		squares[3][1].setSeat(true);
		squares[3][5].setSeat(true);
		squares[3][6].setSeat(true);
		
		// Q3, Q4
		squares[7][0].setSeat(true);
		squares[7][1].setSeat(true);
		squares[7][5].setSeat(true);
		squares[7][6].setSeat(true);
		squares[8][0].setSeat(true);
		squares[8][1].setSeat(true);
		squares[8][5].setSeat(true);
		squares[8][6].setSeat(true);
		
		// Bottom Row
		squares[10][0].setSeat(true);
		squares[10][1].setSeat(true);
		squares[10][2].setSeat(true);
		squares[10][4].setSeat(true);
		squares[10][5].setSeat(true);
		squares[10][6].setSeat(true);
	}
	
	public void setSquareOccupied(int row, int col, boolean occupied){
		squares[row][col].setOccupied(occupied);
	}
}
