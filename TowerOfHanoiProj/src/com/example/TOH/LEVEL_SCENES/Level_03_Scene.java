package com.example.TOH.LEVEL_SCENES;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.example.TOH.MENU_SCENES.MainMenuScene;
import com.example.TOH.OBJ_CLASSES.Ring;
import com.example.towerofhanoiproj.HanoiDatabase;
import com.example.towerofhanoiproj.MainActivity;


public class Level_03_Scene extends Scene{
	
	private BitmapTextureAtlas levelBgAtlas;
	private ITextureRegion levelBgTextureRegion;
	
	BitmapTextureAtlas levelTextureAtlas;
	private ITextureRegion mTowerTextureRegion, mRing1, mRing2, mRing3, mRing4, mRing5;
	private Sprite mTower1, mTower2, mTower3;
	private Stack<Ring> mStack1, mStack2, mStack3;
	private MainActivity activity;
	private Text textTimer;
	private Text textMoves;
	public int timestart = 0, hours, minutes, seconds;
	public int moves = 0;
	public String formattedTimeString, formattedMovesString;
	
	public Level_03_Scene()
	{
		activity = MainActivity.getSharedInstance();
		MainActivity.menuBgm.pause();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		levelBgAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(levelBgAtlas);
		levelBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBgAtlas,activity.getBaseContext(),"night.png",0,0);
		final float centerX = (activity.mCamera.getWidth() - levelBgTextureRegion.getWidth()) / 2;
	    final float centerY = (activity.mCamera.getHeight() - levelBgTextureRegion.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, levelBgTextureRegion, activity.getVertexBufferObjectManager()));

		this.setBackground(bg);
		
		loadResources();
		
	}
	
	public void loadResources()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		levelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480);

		 try {
	        	// 1 - Set up bitmap textures
	            ITexture towerTexture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/tower.png");
	                }
	            });
	            ITexture ring1Texture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/lvl_3_ring_1.png");
	                }
	            });
	            ITexture ring2Texture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/lvl_3_ring_2.png");
	                }
	            });
	            ITexture ring3Texture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/lvl_3_ring_3.png");
	                }
	            });
	            ITexture ring4Texture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/lvl_3_ring_4.png");
	                }
	            });
	            ITexture ring5Texture = new BitmapTexture(activity.getTextureManager(), new IInputStreamOpener() {
	                public InputStream open() throws IOException {
	                    return activity.getAssets().open("gfx/lvl_3_ring_5.png");
	                }
	            });
	            // 2 - Load bitmap textures into VRAM
	            towerTexture.load();
	            ring1Texture.load();
	            ring2Texture.load();
	            ring3Texture.load();
	            ring4Texture.load();
	            ring5Texture.load();
	            // 3 - Set up texture regions
	            this.mTowerTextureRegion = TextureRegionFactory.extractFromTexture(towerTexture);
	            this.mRing1 = TextureRegionFactory.extractFromTexture(ring1Texture);
	            this.mRing2 = TextureRegionFactory.extractFromTexture(ring2Texture);
	            this.mRing3 = TextureRegionFactory.extractFromTexture(ring3Texture);
	            this.mRing4 = TextureRegionFactory.extractFromTexture(ring4Texture);
	            this.mRing5 = TextureRegionFactory.extractFromTexture(ring5Texture);
	            // 4 - Create the stacks
	            this.mStack1 = new Stack<Ring>();
	            this.mStack2 = new Stack<Ring>();
	            this.mStack3 = new Stack<Ring>();
	        } catch (IOException e) {
	            Debug.e(e);
	        }
		 
		// 2 - Add the towers
			mTower1 = new Sprite(170, 121, this.mTowerTextureRegion, activity.getVertexBufferObjectManager());
			mTower2 = new Sprite(388, 121, this.mTowerTextureRegion, activity.getVertexBufferObjectManager());
			mTower3 = new Sprite(612, 121, this.mTowerTextureRegion, activity.getVertexBufferObjectManager());
			this.attachChild(mTower1);
			this.attachChild(mTower2);
			this.attachChild(mTower3);
			// 3 - Create the rings
			Ring ring1 = new Ring(1, 127, 177, this.mRing1, activity.getVertexBufferObjectManager()) {
			    @Override
			    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			        if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
			            return false;
			        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			            checkForCollisionsWithTowers(this);
			            checkIfGameWin();
			        }
			        return true;
			    }
			};
			Ring ring2 = new Ring(2, 110, 211, this.mRing2, activity.getVertexBufferObjectManager()) {
			    @Override
			    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			        if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
			            return false;
			        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			            checkForCollisionsWithTowers(this);
			        }
			        return true;
			    }
			};
			Ring ring3 = new Ring(3, 100, 245, this.mRing3, activity.getVertexBufferObjectManager()) {
				 @Override
				    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				        if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
				            return false;
				        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
				        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
				            checkForCollisionsWithTowers(this);
				        }
				        return true;
				    }
			};
			Ring ring4 = new Ring(4, 87, 279, this.mRing4, activity.getVertexBufferObjectManager()) {
			    @Override
			    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			        if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
			            return false;
			        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			            checkForCollisionsWithTowers(this);
			        }
			        return true;
			    }
			};
			Ring ring5 = new Ring(5,78, 319, this.mRing5, activity.getVertexBufferObjectManager()) {
			    @Override
			    public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
			        if (((Ring) this.getmStack().peek()).getmWeight() != this.getmWeight())
			            return false;
			        this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
			        if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
			            checkForCollisionsWithTowers(this);
			        }
			        return true;
			    }
			};
			this.attachChild(ring1);
			this.attachChild(ring2);
			this.attachChild(ring3);
			this.attachChild(ring4);
			this.attachChild(ring5);
			// 4 - Add all rings to stack one
			this.mStack1.add(ring5);
			this.mStack1.add(ring4);
			this.mStack1.add(ring3);
			this.mStack1.add(ring2);
			this.mStack1.add(ring1);
			// 5 - Initialize starting position for each ring
			ring1.setmStack(mStack1);
			ring2.setmStack(mStack1);
			ring3.setmStack(mStack1);
			ring4.setmStack(mStack1);
			ring5.setmStack(mStack1);
			ring1.setmTower(mTower1);
			ring2.setmTower(mTower1);
			ring3.setmTower(mTower1);
			ring4.setmTower(mTower1);
			ring5.setmTower(mTower1);
			// 6 - Add touch handlers
			this.registerTouchArea(ring1);
			this.registerTouchArea(ring2);
			this.registerTouchArea(ring3);
			this.registerTouchArea(ring4);
			this.registerTouchArea(ring5);
			this.setTouchAreaBindingOnActionDownEnabled(true);
		textTimer = new Text(560,442,activity.mFont,"Timer: 00:00:00",activity.getVertexBufferObjectManager());
		TimerHandler timerHandler = new TimerHandler(1.0f, true, new ITimerCallback() {
            
            public void onTimePassed(TimerHandler pTimerHandler) {      
                    hours = timestart / (60 * 60);
                    minutes = (timestart / 60) % 60;
                    seconds = timestart % 60;
                           
                    if(hours < 99 && minutes < 60 && seconds < 60){        
                            formattedTimeString = String.format("Timer: %02d:%02d:%02d",hours, minutes, seconds);
                            textTimer.setText(formattedTimeString);
                           
                            timestart ++;
                    }
            }
    });
     registerUpdateHandler(timerHandler);
		
		textMoves = new Text(15,442,activity.mFont,"Moves: 0        ",activity.getVertexBufferObjectManager());
		this.attachChild(textTimer);
		this.attachChild(textMoves);

	}
	

	public void checkIfGameWin(){
        if(mStack3.size() == 5){
                activity.runOnUiThread(new Runnable() {

                        public void run() {
                                // TODO Auto-generated method stub     
                        	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                        	
                        	int level = preferences.getInt("level", 1);
                        	if(level<4)
                        	{
                        		Editor edit = preferences.edit();
                        		edit.putInt("level",4);
                        		edit.commit(); 
                        	}
                        	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    				activity);
                     
                    			// set title
                        	alertDialogBuilder.setTitle("Congratulations!");
                    		String info= "Moves: "+moves+ "Time: " + formattedTimeString;
                			// set dialog message
                			alertDialogBuilder
                				.setMessage("info")
                				.setCancelable(false)
                				.setPositiveButton("Continue",new DialogInterface.OnClickListener() {
                					public void onClick(DialogInterface dialog,int id) {
                    						// if this button is clicked, close
                    						// current activity
                    						

                    						activity.setCurrentScene(new Level_04_Scene());
                    						//activity.finish();
                    					}
                    				  })
                    				.setNegativeButton("Exit",new DialogInterface.OnClickListener() {
                    					public void onClick(DialogInterface dialog,int id) {
                    						// if this button is clicked, just close
                    						// the dialog box and do nothing
                    						dialog.cancel();
                    						activity.setCurrentScene(new MainMenuScene());
                    						}
                    				});
                     
                    				// create alert dialog
                    				AlertDialog alertDialog = alertDialogBuilder.create();
                     
                    				// show it
                    				alertDialog.show();
                        		HanoiDatabase mydb = new HanoiDatabase(activity);
                                mydb.addScore(3, 5, moves, formattedTimeString);
                                mydb.close();
                        }
               
            });
        }              
	}
	
	@SuppressWarnings("unchecked")
	private void checkForCollisionsWithTowers(Ring ring) {
	    Stack<Ring> stack = null;
	    Sprite tower = null;
	    if (ring.collidesWith(mTower1) && (mStack1.size() == 0 || ring.getmWeight() < ((Ring) mStack1.peek()).getmWeight())) {
	        stack = mStack1;
	        tower = mTower1;
	        moves++;
	        formattedMovesString = String.format("Moves: %d", moves);
	        textMoves.setText(formattedMovesString);
	    } else if (ring.collidesWith(mTower2) && (mStack2.size() == 0 || ring.getmWeight() < ((Ring) mStack2.peek()).getmWeight())) {
	        stack = mStack2;
	        tower = mTower2;
	        moves++;
	        formattedMovesString = String.format("Moves: %d", moves);
	        textMoves.setText(formattedMovesString);
	    } else if (ring.collidesWith(mTower3) && (mStack3.size() == 0 || ring.getmWeight() < ((Ring) mStack3.peek()).getmWeight())) {
	        stack = mStack3;
	        tower = mTower3;
	        moves++;
	        formattedMovesString = String.format("Moves: %d", moves);
	        textMoves.setText(formattedMovesString);
	    } else {
	        stack = (Stack<Ring>) ring.getmStack();
	        tower = ring.getmTower();
	    }
	    ring.getmStack().remove(ring);
	    if (stack != null && tower !=null && stack.size() == 0) {
	        ring.setPosition(tower.getX() + tower.getWidth()/2 - ring.getWidth()/2, tower.getY() + tower.getHeight() - ring.getHeight());
	    } else if (stack != null && tower !=null && stack.size() > 0) {
	        ring.setPosition(tower.getX() + tower.getWidth()/2 - ring.getWidth()/2, ((Ring) stack.peek()).getY() - ring.getHeight());
	    }
	    stack.add(ring);
	    ring.setmStack(stack);
	    ring.setmTower(tower);
	}
	
}
