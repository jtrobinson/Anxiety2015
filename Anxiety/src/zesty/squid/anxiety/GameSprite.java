package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class GameSprite extends Sprite {
	
	protected int currentArrayX;
    protected int currentArrayY;
	
	public GameSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(ActGameScreen.BOARD_MIN_X + (ActGameScreen.SQUARE_SIDE*pX),ActGameScreen.BOARD_MIN_Y + (ActGameScreen.SQUARE_SIDE*pY),pTextureRegion, pVertexBufferObjectManager);
        currentArrayX = (int) pX;
        currentArrayY = (int) pY;
    }
	
	public void setCurrentArrayX(int x){
    	currentArrayX = x;
    }
    public void setCurrentArrayY(int y){
    	currentArrayX = y;
    }
    
    public int getCurrentArrayX(){
    	return currentArrayX;
    }
    public int getCurrentArrayY(){
    	return currentArrayY;
    }
}