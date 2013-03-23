package com.example.TOH.MENU_SCENES;

import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.example.towerofhanoiproj.MainActivity;

//placeHolder scene class for the main menu, currently only includes a start menu item 
public class MainMenuScene extends MenuScene implements
		IOnMenuItemClickListener {
	MainActivity activity;
	final int MENU_START = 0;
//	final int MENU_SETTING = 1;
	final int MENU_INSTRUCTION = 1;
	final int MENU_SCORE = 2;
	BitmapTextureAtlas mBitmapTextureAtlas;

	BitmapTextureAtlas menuBgAtlas;

	ITextureRegion menuBgTextureRegion;
	
	ITextureRegion startTextureRegion;
	//ITextureRegion settingTextureRegion;
	ITextureRegion scoreTextureRegion;
	ITextureRegion instructionTextureRegion;
	
	public MainMenuScene() {
		super(MainActivity.getSharedInstance().mCamera);
		activity = MainActivity.getSharedInstance();
		
		MainActivity.menuBgm.start();
	    BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		menuBgAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(menuBgAtlas);
		mBitmapTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(mBitmapTextureAtlas);
		
		menuBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuBgAtlas,activity.getBaseContext(),"menuBg.png",0,0);
		
		startTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas,activity.getBaseContext(),"startButton.png",0,50);
	//	settingTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas,activity.getBaseContext(),"settingsButton.png",50,100);
		instructionTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas,activity.getBaseContext(),"instructionsButton.png",50,100);
		scoreTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas,activity.getBaseContext(),"scoresButton.png",100,150);
		
		final float centerX = (activity.mCamera.getWidth() - menuBgTextureRegion.getWidth()) / 2;
	    final float centerY = (activity.mCamera.getHeight() - menuBgTextureRegion.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, menuBgTextureRegion, activity.getVertexBufferObjectManager()));

		this.setBackground(bg);

		
		IMenuItem startButton0 = 	new SpriteMenuItem(MENU_START,startTextureRegion, activity.getVertexBufferObjectManager());
//		IMenuItem settingButton = 	new SpriteMenuItem(MENU_SETTING,settingTextureRegion, activity.getVertexBufferObjectManager());
		IMenuItem instructionButton = 	new SpriteMenuItem(MENU_INSTRUCTION,instructionTextureRegion, activity.getVertexBufferObjectManager());
		IMenuItem scoreButton = 	new SpriteMenuItem(MENU_SCORE,scoreTextureRegion, activity.getVertexBufferObjectManager());
		startButton0.setPosition(activity.mCamera.getWidth()/2 + 45, activity.mCamera.getHeight()/2 - 15); //-95
	//	settingButton.setPosition(activity.mCamera.getWidth()/2 + 45, activity.mCamera.getHeight()/2 - 15 );
		instructionButton.setPosition(activity.mCamera.getWidth()/2 + 45, activity.mCamera.getHeight()/2 +65);
		scoreButton.setPosition(activity.mCamera.getWidth()/2 + 45, activity.mCamera.getHeight()/2 +150);
		
		addMenuItem(startButton0);
		//addMenuItem(settingButton);
		addMenuItem(instructionButton);
		addMenuItem(scoreButton);
		
	
		/*IMenuItem startButton = new TextMenuItem(MENU_START, activity.mFont,
				activity.getString(R.string.start),
				activity.getVertexBufferObjectManager());
		startButton.setPosition(mCamera.getWidth() / 2 - startButton.getWidth()
				/ 2, mCamera.getHeight() / 2 - startButton.getHeight() / 2);

		addMenuItem(startButton);*/

		setOnMenuItemClickListener(this);
	}

	public boolean onMenuItemClicked(MenuScene arg0, IMenuItem arg1,
			float arg2, float arg3) {
		switch (arg1.getID()) {
		case MENU_START:
			activity.setCurrentSceneType(MainActivity.SceneType.LEVEL_SELECTION);
			activity.setCurrentScene(new LevelSelectScene());
			return true;
		case MENU_INSTRUCTION:
			activity.setCurrentSceneType(MainActivity.SceneType.INSTRUCTION);
			activity.setCurrentScene(new InstructionScene());
			return true;
		case MENU_SCORE:
			activity.setCurrentSceneType(MainActivity.SceneType.SCORE);
			activity.setCurrentScene(new ScoreScene());
			return true;
		default:
			break;
		}
		return false;
	}


}
