package com.example.towerofhanoiproj;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import com.example.TOH.MENU_SCENES.LevelSelectScene;
import com.example.TOH.MENU_SCENES.MainMenuScene;
import com.example.TOH.MENU_SCENES.SplashScene;


import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.KeyEvent;

public class MainActivity extends SimpleBaseGameActivity {

	static final int CAMERA_WIDTH = 800;
	static final int CAMERA_HEIGHT = 480;

	public Font mFont;
	public Camera mCamera;
	public static MediaPlayer menuBgm,lvl_1_Bgm;

	// A reference to the current scene
	public Scene mCurrentScene;
	public static MainActivity instance;
	public static enum SceneType
	{
		SPLASH,
		MAIN,
		INSTRUCTION,
		SCORE,
		LEVEL_SELECTION,
		GAMEPLAY,
	}
	private SceneType currentSceneType = SceneType.SPLASH;
	
	
	public static MainActivity getSharedInstance() {
		return instance;
	}

	public EngineOptions onCreateEngineOptions() {
		instance = this;
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR,
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	}

	@Override
	protected void onCreateResources() {
		mFont = FontFactory.create(this.getFontManager(),
				this.getTextureManager(), 256, 256,
				Typeface.create(Typeface.DEFAULT, Typeface.NORMAL), 35);
		mFont.load();
		menuBgm = MediaPlayer.create(getApplicationContext(), R.raw.menu);
		lvl_1_Bgm = MediaPlayer.create(getApplicationContext(), R.raw.night);
        try {
            menuBgm.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	@Override
	protected Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		mCurrentScene = new SplashScene();
		setCurrentSceneType(SceneType.SPLASH);
		return mCurrentScene;
	}

	// to change the current main scene
	public void setCurrentScene(Scene scene) {
		mCurrentScene = null;
		mCurrentScene = scene;
		getEngine().setScene(mCurrentScene);
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
	    {	    	
			switch (currentSceneType)
	    	{
	    		case SPLASH:
	    			break;
	    		case MAIN:
	    			System.exit(0);
	    			break;
	    		case INSTRUCTION:
	    			mCurrentScene = new MainMenuScene();
	    			getEngine().setScene(mCurrentScene);
	    			currentSceneType = SceneType.MAIN;
	    			break;
	    		case SCORE:
	    			mCurrentScene = new MainMenuScene();
	    			getEngine().setScene(mCurrentScene);
	    			currentSceneType = SceneType.MAIN;
	    			break;
	    		case LEVEL_SELECTION:
	    			mCurrentScene = new MainMenuScene();
	    			getEngine().setScene(mCurrentScene);
	    			currentSceneType = SceneType.MAIN;
	    			break;
	    		case GAMEPLAY:
	    			mCurrentScene = new LevelSelectScene();
	    			getEngine().setScene(mCurrentScene);
	    			currentSceneType = SceneType.LEVEL_SELECTION;
	    			break;
	    	}
	    }
	    return false; 
	}

	public SceneType getCurrentSceneType() {
		return currentSceneType;
	}

	public void setCurrentSceneType(SceneType currentSceneType) {
		this.currentSceneType = currentSceneType;
	}
	
}
