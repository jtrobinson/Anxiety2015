package zesty.squid.anxiety;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class RiderSprite extends Sprite {
	
	protected int currentArrayX;
    protected int currentArrayY;
	
	public RiderSprite(float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
		super(
        		ActGameScreen.BOARD_MIN_X + ActGameScreen.SQUARE_SIDE*pX, 
        		ActGameScreen.BOARD_MIN_Y + ActGameScreen.SQUARE_SIDE*pY, 
        		pTextureRegion, pVertexBufferObjectManager
        	);
        currentArrayX = (int) pX;
        currentArrayY = (int) pY;
    }
}
