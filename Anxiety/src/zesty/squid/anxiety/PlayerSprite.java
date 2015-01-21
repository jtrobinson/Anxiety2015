package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.MotionEvent;

public class PlayerSprite extends GameSprite {
	private int mWeight;
    private Sprite blob;
    private boolean moving = false;
    public float swipeStartX = -1;
    public float swipeStartY = -1;
    public float swipeEndX = -1;
    public float swipeEndY = -1;
    
    public static int MOVING_NULL = -1;
    public static int MOVING_NORTH = 0;
    public static int MOVING_EAST = 1;
    public static int MOVING_SOUTH = 2;
    public static int MOVING_WEST = 3;
    
    //protected int currentArrayX;
    //protected int currentArrayY;
 
    public PlayerSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        /*Log.w("GRID_DEBUG", "GRID_DEBUG: pX and pY are (" + (pX) + ", " + (pY) + ")");
        Log.w("GRID_DEBUG", "GRID_DEBUG: Start coordinates are (" + 
        		(ActGameScreen.BOARD_MIN_X + (ActGameScreen.SQUARE_SIDE*pX)) + ", " +
        		(ActGameScreen.BOARD_MIN_Y + (ActGameScreen.SQUARE_SIDE*pY)) +  ")");
        */
    }
 
    public int getmWeight() {
        return mWeight;
    }
 
    /*public Stack getmStack() {
        return mStack;
    }
 
    public void setmStack(Stack mStack) {
        this.mStack = mStack;
    }*/
    
    public Sprite getBlob() {
        return blob;
    }
 
    public void setBlob(Sprite blob) {
        this.blob = blob;
    }
    
    private void checkForCollisionsWithScenery() {
    }
    private void checkForCollisionsWithPeople() {
    }
    
    /*@Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	//set flag member variable that sprite has been touched
        if (pSceneTouchEvent.isActionDown())
        {
	        //here is where the touch was initiated so you 
	        //can store the x,y location. You obtain it by using pSceneTouchEvent.getX()
	        // and pSceneTouchEvent.getY()
        	if(pSceneTouchEvent.getX() >= ActGameScreen.BOARD_MIN_X && pSceneTouchEvent.getY() >= ActGameScreen.BOARD_MIN_Y){
		    	if (swipeStartX == -1 && swipeStartY == -1){
		    		swipeStartX = pSceneTouchEvent.getX();
		            swipeStartY = pSceneTouchEvent.getY();
		    	}
        	}
        }
        else if (pSceneTouchEvent.isActionUp())
        {
        	//this will be called when you release the sprite
        	// and tell the update handler to apply the force
        	swipeEndX = pSceneTouchEvent.getX();
            swipeEndY = pSceneTouchEvent.getY();
            
            Log.w("SWIPE_DEBUG", "Start coordinates were ("+swipeStartX+", "+swipeStartY+")");
            Log.w("SWIPE_DEBUG", "End coordinates were ("+swipeEndX+", "+swipeEndY+")");
        	
            swipeMove(swipeEndX - swipeStartX, swipeEndY - swipeStartY);            
            
        	// restore Swipe X and Y variables to normal at the end of the swipe
        	swipeStartX = -1;
            swipeStartY = -1;
            swipeEndX = -1;
            swipeEndY = -1;
        }
        return true;
    }*/
    
    /*protected void swipeMove(float deltaX, float deltaY){
    	float absDeltaX = Math.abs(deltaX);
    	float absDeltaY = Math.abs(deltaY);
    	
    	Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Entering SwipeMove");
    	
    	// If the player has just tapped the screen, don't do squat. Otherwise, get calculating.
    	if (absDeltaX >= 1 && absDeltaY >= 1){
    		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Better than a tap");
	    	// If the deltaY is greater than the deltaX, it means the swipe was more vertical than horizontal
	    	// Preference is given to vertical in the event that both are equal, since the screen is taller than it is wide.
	    	if (absDeltaY >= absDeltaX){
	    		// Move is vertical
	    		if (deltaY >= 0){
	    			// Move down for a positive number
	    			moveDown();
	    		}
	    		else{
	    			// Move Up for a positive number
	    			moveUp();
	    		}
	    	}
	    	else{
	    		// Move is horizontal
	    		if (deltaX >= 0){
	    			// Move right for a positive number
	    			moveRight();
	    		}
	    		else{
	    			// Move left for a positive number
	    			moveLeft();
	    		}
	    	}
    	}
    	else {
    		// Detected a tap
    		waitOnTap();
    	}
    }*/
    
    // Max X: 480
    // Max Y: 800
    // Increments of 80
   
    protected void waitOnTap(){
    	Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Detected a tap; waiting.");
    }
    protected void moveUp(){ // Confirmed to work
    	Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving UP");
    	if (!collidingWithObjects(MOVING_NORTH) && this.getY() >= ActGameScreen.BOARD_MIN_Y + ActGameScreen.SQUARE_SIDE){
    		this.setY(this.getY() - ActGameScreen.SQUARE_SIDE);
    	}
    }
    protected void moveDown(){ // Confirmed to work
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving DOWN");
		if (!collidingWithObjects(MOVING_SOUTH) && this.getY() < ActGameScreen.BOARD_MAX_Y - ActGameScreen.SQUARE_SIDE){
    		this.setY(this.getY() + ActGameScreen.SQUARE_SIDE);
    	}
    }
	protected void moveLeft(){
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving LEFT");
		if (!collidingWithObjects(MOVING_WEST) && this.getX() >= ActGameScreen.BOARD_MIN_X + ActGameScreen.SQUARE_SIDE){
    		this.setX(this.getX() - ActGameScreen.SQUARE_SIDE);
    	}
	}
	protected void moveRight(){ // Confirmed to work
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving RIGHT");
		if (!collidingWithObjects(MOVING_EAST) && this.getX() <= ActGameScreen.BOARD_MAX_X - ActGameScreen.SQUARE_SIDE){
    		this.setX(this.getX() + ActGameScreen.SQUARE_SIDE);
    	}
	}
	
	private boolean collidingWithObjects(int _direction){
		boolean rValue = false;
		
		switch (_direction){
			case 0: // MOVING_NORTH
				if (collidingAnySeatBoundary(MOVING_NORTH)){
					rValue = true;
				}
				break;
			case 1: // MOVING_EAST
				break;
			case 2: // MOVING_SOUTH
				if (collidingAnySeatBoundary(MOVING_SOUTH)){
					rValue = true;
				}
				break;
			case 3: // MOVING_WEST
				break;
			default: 
				break;
		}
		
		return rValue;
	}
	
	private boolean collidingAnySeatBoundary(int _direction){
		return collidingQ1SeatBoundary(_direction) || collidingQ2SeatBoundary(_direction) || collidingQ3SeatBoundary(_direction) || collidingQ4SeatBoundary(_direction);
	}
	
	private boolean collidingQ1SeatBoundary(int _direction){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue =  this.getY() == ActGameScreen.q1Boundary.yLine && this.getX() <= ActGameScreen.q1Boundary.rightX && this.getX() >= ActGameScreen.q1Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue =  this.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q1Boundary.yLine && this.getX() <= ActGameScreen.q1Boundary.rightX && this.getX() >= ActGameScreen.q1Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ2SeatBoundary(int _direction){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue =  this.getY() == ActGameScreen.q2Boundary.yLine && this.getX() <= ActGameScreen.q2Boundary.rightX && this.getX() >= ActGameScreen.q2Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue =  this.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q2Boundary.yLine && this.getX() <= ActGameScreen.q2Boundary.rightX && this.getX() >= ActGameScreen.q2Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ3SeatBoundary(int _direction){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue =  this.getY() == ActGameScreen.q3Boundary.yLine && this.getX() <= ActGameScreen.q3Boundary.rightX && this.getX() >= ActGameScreen.q3Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue =  this.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q3Boundary.yLine && this.getX() <= ActGameScreen.q3Boundary.rightX && this.getX() >= ActGameScreen.q3Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ4SeatBoundary(int _direction){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue =  this.getY() == ActGameScreen.q4Boundary.yLine && this.getX() <= ActGameScreen.q4Boundary.rightX && this.getX() >= ActGameScreen.q4Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue =  this.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q4Boundary.yLine && this.getX() <= ActGameScreen.q4Boundary.rightX && this.getX() >= ActGameScreen.q4Boundary.leftX;
		}
		return rValue;
	}
}
