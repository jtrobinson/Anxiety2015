package zesty.squid.anxiety;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;

public class TrainScene extends Scene implements ITouchArea{

	private float swipeStartX = -1;
    private float swipeStartY = -1;
    private float swipeEndX = -1;
    private float swipeEndY = -1;
    
    public PlayerSprite blob;
	
	public TrainScene() {
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		return blob.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
	}


	@Override
	public boolean contains(float pX, float pY) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
