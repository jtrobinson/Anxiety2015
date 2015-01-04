package zesty.squid.anxiety;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class TrainScene extends Scene implements IOnSceneTouchListener{

	private float swipeStartX = -1;
    private float swipeStartY = -1;
    private float swipeEndX = -1;
    private float swipeEndY = -1;
    
    public PlayerSprite blob;
	
	public TrainScene() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
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
	
}
