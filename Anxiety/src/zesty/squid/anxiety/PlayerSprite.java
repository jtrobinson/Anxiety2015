package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import android.view.MotionEvent;

public class PlayerSprite extends Sprite {
	private int mWeight;
    private Sprite blob;
    private boolean moving = false;
    private float swipeStartX = -1;
    private float swipeStartY = -1;
    private float swipeEndX = -1;
    private float swipeEndY = -1;
 
    public PlayerSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
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
    
    /*
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        //blob.setX(blob.getX() + SQUARE_SIDE);
    	this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
        return true;
    }*/
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	//set flag member variable that sprite has been touched
        if (pSceneTouchEvent.isActionDown())
        {
	        //here is where the touch was initiated so you 
	        //can store the x,y location. You obtain it by using pSceneTouchEvent.getX()
	        // and pSceneTouchEvent.getY()
	    	if (swipeStartX == -1 && swipeStartY == -1){
	    		swipeStartX = pSceneTouchEvent.getX();
	            swipeStartY = pSceneTouchEvent.getY();
	    	}
        }

        if (pSceneTouchEvent.isActionMove())
        {
           //This will be called when you slide your finger, so you
           //can get the new coordinates by again using pSceneTouchEvent.getX()
           // and pSceneTouchEvent.getY()

        }

        if (pSceneTouchEvent.isActionUp())
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
    }
    
    private void swipeMove(float deltaX, float deltaY){
    	float absDeltaX = Math.abs(deltaX);
    	float absDeltaY = Math.abs(deltaY);
    	
    	// If the player has just tapped the screen, don't do squat. Otherwise, get calculating.
    	if (absDeltaX >= 1 && absDeltaY >= 1){
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
    }
    
    public void moveUp(){
    	Log.w("SWIPE_DEBUG", "Moving UP");
    }
	public void moveDown(){
		Log.w("SWIPE_DEBUG", "Moving DOWN");	
    }
	public void moveLeft(){
		Log.w("SWIPE_DEBUG", "Moving LEFT");
	}
	public void moveRight(){
		Log.w("SWIPE_DEBUG", "Moving RIGHT");
	}
}
