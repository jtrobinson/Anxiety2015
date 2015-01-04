package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.input.touch.TouchEvent;

import android.view.MotionEvent;

public class PlayerSprite extends Sprite {
	private int mWeight;
    //private Stack mStack; //this represents the stack that this ring belongs to
    private Sprite blob;
 
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
    
    
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
        //blob.setX(blob.getX() + SQUARE_SIDE);
    	this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
        return true;
    }
    
    /*
    @Override
    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
    	
        return true;
    }*/
}
