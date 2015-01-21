package zesty.squid.anxiety;

public class GridSquare {
	
	private int _topX;
	private int _topY;
	
	private boolean _occupied;
	private boolean _seat;
	private char _debugChar;
	
	public GridSquare(int topX, int topY){
		_topX = topX;
		_topY = topY;
		_debugChar = '~';
	}
	
	public boolean isOccupied(){return _occupied;}
	public void setOccupied(boolean value){
		_occupied = value;
	}
	
	public boolean isSeat(){return _seat;}
	public void setSeat(boolean value){
		_seat = value;
	}
	
	public char getChar(){
		if (_seat){
			if (_occupied){
				_debugChar = 'X';
			}
			else{
				_debugChar = '#';
			}
		}
		else {
			if (_occupied){
				_debugChar = 'O';
			}
			else{
				_debugChar = '.';
			}
		}
		return _debugChar;
	}
}
