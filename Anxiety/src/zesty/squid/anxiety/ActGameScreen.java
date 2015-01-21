package zesty.squid.anxiety;

import org.andengine.engine.options.EngineOptions;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
 
import java.io.IOException;
import java.io.InputStream;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

// Tower of Hanoi tutorial: http://www.raywenderlich.com/12065/how-to-create-a-simple-android-game

// Board game forum post: http://www.andengine.org/forums/gles1/help-with-boardgame-t1388.html

public class ActGameScreen extends SimpleBaseGameActivity implements IOnSceneTouchListener{

	protected static int CAMERA_WIDTH = 480;
	protected static int CAMERA_HEIGHT = 800;
	protected static int NUM_ROWS = 11;
	protected static int NUM_COLS = 7;
	protected static int SQUARE_SIDE = 61;
	
	protected static int BOARD_MIN_X = 27;
	protected static int BOARD_MIN_Y = 104;
	protected static int BOARD_MAX_X = 452;
	protected static int BOARD_MAX_Y = 773;
	
	public static int MOVING_NULL = -1;
    public static int MOVING_NORTH = 0;
    public static int MOVING_EAST = 1;
    public static int MOVING_SOUTH = 2;
    public static int MOVING_WEST = 3;
	
	// Q1  Q2
	//
	// Q3  Q4
	protected static SeatBoundary q1Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,287);
	protected static SeatBoundary q2Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,287);
	protected static SeatBoundary q3Boundary = new SeatBoundary(ActGameScreen.BOARD_MIN_X,147,592);
	protected static SeatBoundary q4Boundary = new SeatBoundary(331,ActGameScreen.BOARD_MAX_X,592);
	
	private ITextureRegion mBackgroundTextureRegion, mBlobTextureRegion, mAntiBlobTextureRegion, mSeatsTextureRegion;
	private PlayerSprite blob;
	private RiderSprite antiblob;
	private Sprite seats;
	
	private float swipeStartX = -1 ;
	private float swipeStartY = -1;
	private float swipeEndX = -1 ;
	private float swipeEndY = -1;
	
	private GridInfo gridInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		gridInfo = new GridInfo(); //BOARD_MIN_X, BOARD_MIN_Y, BOARD_MAX_X, BOARD_MAX_Y, SQUARE_SIDE);
		//setContentView(R.layout.act_game_screen);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.act_game_screen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		
		/*
		 The parameters that are passed while creating an instance of EngineOptions are:

    FullScreen: A boolean variable signifying whether or not the engine instance will use a fullscreen.
    ScreenOrientation: Specifies the orientation used while the game is running.
    ResolutionPolicy: Defines how the engine will scale the game assets on phones with different screen sizes.
    Camera: Defines the width and height of the final game scene.

		 */
	}

	@Override
	protected void onCreateResources() {
		try {
		    // 1 - Set up bitmap textures
		    ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/baseBackgroundwNumbers.png");
		        }
		    });
		    ITexture blobTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/dood60.png");
		        }
		    });
		    ITexture seatsSouthTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/seats.png");
		        }
		    });
		    ITexture antiblobTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
		        @Override
		        public InputStream open() throws IOException {
		            return getAssets().open("gfx/antidood60.png");
		        }
		    });
		    
		    
		    // 2 - Load bitmap textures into VRAM
		    backgroundTexture.load();
		    blobTexture.load();
		    antiblobTexture.load();
		    seatsSouthTexture.load();
		    
		    // 3 - Set up texture regions
		    this.mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
		    this.mBlobTextureRegion = TextureRegionFactory.extractFromTexture(blobTexture);
		    this.mAntiBlobTextureRegion = TextureRegionFactory.extractFromTexture(antiblobTexture);
		    this.mSeatsTextureRegion = TextureRegionFactory.extractFromTexture(seatsSouthTexture);
		    
		} catch (IOException e) {
		    Debug.e(e);
		}
		/* From Tower of Hanoi:
		 * Note: Instead of creating textures for each of your assets, you could have loaded 
		 * all the assets into one texture and extracted the individual assets as TextureRegions. 
		 * Doing this is out of scope for this tutorial, but I may cover it in detail in a future tutorial.
		 */
	}

	@Override
	protected Scene onCreateScene() {
		// 1 - Create new scene
		final Scene scene = new Scene(){
			public boolean onTouchEvent(MotionEvent event) {
		        
				return true;
		    }
		};
		
		Sprite backgroundSprite = new Sprite(0, 0, this.mBackgroundTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		
		/* From Tower of Hanoi:
		 * When creating a Sprite object, you pass four parameters. Here�s a brief description of each parameter:

		    xCoordinate: Defines the X-position of the sprite. The AndEngine coordinate system considers the top-left point as the origin.
		    yCoordinate: Defines the Y-position of the sprite.
		    TextureRegion: Defines what part of the texture the sprite will use to draw itself.
		    VertexBufferObjectManager: Think of a vertex buffer as an array holding the coordinates of a texture. 
		   		These coordinates are passed to the OpenGL ES pipeline and ultimately define what will be drawn. 
		   		A VertexBufferObjectManager holds all the vertices of all the textures that need to be drawn on the scene.

		 */
		
		// 2 - Add the person and seats
		seats = new AllSeats(BOARD_MIN_X, BOARD_MIN_Y, this.mSeatsTextureRegion, getVertexBufferObjectManager());
		blob = new PlayerSprite(0, 5, this.mBlobTextureRegion, getVertexBufferObjectManager());
		antiblob = new RiderSprite(2, 5, this.mAntiBlobTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(seats);
		scene.attachChild(antiblob);
		scene.attachChild(blob);

		// Add people to Grid
		gridInfo.setSquareOccupied(0, 5, true);
		gridInfo.setSquareOccupied(2, 5, true);
		Log.w("GRID_DEBUG", "GRID_DEBUG Initial " + gridInfo.debugString());
		
		// 3. Register swipe listeners
		// Working with touch on the entire scene: https://andengineguides.wordpress.com/2011/09/14/getting-started-touch-events/
		scene.setOnSceneTouchListener(this);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		return scene;
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		//boolean rValue = blob.onAreaTouched(pSceneTouchEvent, pSceneTouchEvent.getX(), pSceneTouchEvent.getY()); 
		//return rValue;
		
		if (pSceneTouchEvent.isActionDown())
        {
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
	}
	
	protected void swipeMove(float deltaX, float deltaY){
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
	    			moveDown(blob);
	    		}
	    		else{
	    			// Move Up for a positive number
	    			moveUp(blob);
	    		}
	    	}
	    	else{
	    		// Move is horizontal
	    		if (deltaX >= 0){
	    			// Move right for a positive number
	    			moveRight(blob);
	    		}
	    		else{
	    			// Move left for a positive number
	    			moveLeft(blob);
	    		}
	    	}
    	}
    	else {
    		// Detected a tap
    		blob.waitOnTap();
    	}
    }
	
	protected void waitOnTap(GameSprite currentSprite){
    	Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Detected a tap; waiting.");
    }
    protected void moveUp(GameSprite currentSprite){ // Confirmed to work
    	Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving UP");
    	if (!collidingWithObjects(MOVING_NORTH, currentSprite) && currentSprite.getY() >= ActGameScreen.BOARD_MIN_Y + ActGameScreen.SQUARE_SIDE){
    		Log.w("GRID_DEBUG", "GRID_DEBUG Inside MoveUp, current X and Y are  " 
    				+ currentSprite.getCurrentArrayX() + ", "  + currentSprite.getCurrentArrayY() + ".");
    		//gridInfo.setSquareOccupied(currentSprite.getCurrentArrayX(), currentSprite.getCurrentArrayY(), false);
    		gridInfo.setSquareOccupied(currentSprite.getCurrentArrayY(), currentSprite.getCurrentArrayX(), false);
    		
    		currentSprite.setY(currentSprite.getY() - ActGameScreen.SQUARE_SIDE);
    		currentSprite.setCurrentArrayY(currentSprite.getCurrentArrayY() - 1);
    		
    		//gridInfo.setSquareOccupied(currentSprite.getCurrentArrayX(), currentSprite.getCurrentArrayY(), true);
    		gridInfo.setSquareOccupied(currentSprite.getCurrentArrayY(), currentSprite.getCurrentArrayX(), true);
    	}
    	Log.w("GRID_DEBUG", "GRID_DEBUG After Move Up " + gridInfo.debugString());
    }
    protected void moveDown(GameSprite currentSprite){ // Confirmed to work
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving DOWN");
		if (!collidingWithObjects(MOVING_SOUTH, currentSprite) && currentSprite.getY() < ActGameScreen.BOARD_MAX_Y - ActGameScreen.SQUARE_SIDE){
    		currentSprite.setY(currentSprite.getY() + ActGameScreen.SQUARE_SIDE);
    	}
    }
	protected void moveLeft(GameSprite currentSprite){
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving LEFT");
		if (!collidingWithObjects(MOVING_WEST, currentSprite) && currentSprite.getX() >= ActGameScreen.BOARD_MIN_X + ActGameScreen.SQUARE_SIDE){
    		currentSprite.setX(currentSprite.getX() - ActGameScreen.SQUARE_SIDE);
    	}
	}
	protected void moveRight(GameSprite currentSprite){ // Confirmed to work
		Log.w("SWIPE_DEBUG", "SWIPE_DEBUG: Moving RIGHT");
		if (!collidingWithObjects(MOVING_EAST, currentSprite) && currentSprite.getX() <= ActGameScreen.BOARD_MAX_X - ActGameScreen.SQUARE_SIDE){
    		currentSprite.setX(currentSprite.getX() + ActGameScreen.SQUARE_SIDE);
    	}
	}
	
	private boolean collidingWithObjects(int _direction, GameSprite currentSprite){
		boolean rValue = false;
		
		switch (_direction){
			case 0: // MOVING_NORTH
				if (collidingAnySeatBoundary(MOVING_NORTH, currentSprite)){
					rValue = true;
				}
				break;
			case 1: // MOVING_EAST
				break;
			case 2: // MOVING_SOUTH
				if (collidingAnySeatBoundary(MOVING_SOUTH, currentSprite)){
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
	
	private boolean collidingAnySeatBoundary(int _direction, GameSprite currentSprite){
		return 
				collidingQ1SeatBoundary(_direction, currentSprite) || 
				collidingQ2SeatBoundary(_direction, currentSprite) || 
				collidingQ3SeatBoundary(_direction, currentSprite) || 
				collidingQ4SeatBoundary(_direction, currentSprite);
	}
	
	private boolean collidingQ1SeatBoundary(int _direction, GameSprite currentSprite){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue = currentSprite.getY() == ActGameScreen.q1Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q1Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q1Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue = currentSprite.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q1Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q1Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q1Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ2SeatBoundary(int _direction, GameSprite currentSprite){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue = currentSprite.getY() == ActGameScreen.q2Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q2Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q2Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue = currentSprite.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q2Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q2Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q2Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ3SeatBoundary(int _direction, GameSprite currentSprite){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue = currentSprite.getY() == ActGameScreen.q3Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q3Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q3Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue = currentSprite.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q3Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q3Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q3Boundary.leftX;
		}
		return rValue;
	}
	
	private boolean collidingQ4SeatBoundary(int _direction, GameSprite currentSprite){
		boolean rValue = false;
		if (_direction == MOVING_NORTH){
			rValue = currentSprite.getY() == ActGameScreen.q4Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q4Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q4Boundary.leftX;
		}
		else if (_direction == MOVING_SOUTH){
			rValue = currentSprite.getY() + ActGameScreen.SQUARE_SIDE == ActGameScreen.q4Boundary.yLine && 
					currentSprite.getX() <= ActGameScreen.q4Boundary.rightX && 
					currentSprite.getX() >= ActGameScreen.q4Boundary.leftX;
		}
		return rValue;
	}
}
