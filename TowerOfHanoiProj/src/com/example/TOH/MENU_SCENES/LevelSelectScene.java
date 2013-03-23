package com.example.TOH.MENU_SCENES;


import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.color.Color;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.example.TOH.LEVEL_SCENES.Level_01_Scene;
import com.example.TOH.LEVEL_SCENES.Level_02_Scene;
import com.example.TOH.LEVEL_SCENES.Level_03_Scene;
import com.example.TOH.LEVEL_SCENES.Level_04_Scene;
import com.example.TOH.LEVEL_SCENES.Level_05_Scene;
import com.example.TOH.LEVEL_SCENES.Level_06_Scene;
import com.example.TOH.LEVEL_SCENES.Level_07_Scene;
import com.example.TOH.LEVEL_SCENES.Level_08_Scene;
import com.example.TOH.LEVEL_SCENES.Level_09_Scene;
import com.example.TOH.LEVEL_SCENES.Level_10_Scene;
import com.example.towerofhanoiproj.MainActivity;


public class LevelSelectScene extends Scene{

	MainActivity activity;
	
	final private int num_of_levels = 8;
	private BitmapTextureAtlas levelTextureAtlas;
	private ITextureRegion[] levelTextureRegion;
	private Sprite[] levelSprite;
	
	private ITextureRegion levelSelectTextureRegion;
	private Sprite levelSelectSprite;
	private Text textLevelNum;
	int level;
	
	private BitmapTextureAtlas levelBgAtlas;
	private ITextureRegion levelBgTextureRegion;
	
	public LevelSelectScene()
	{	initializeVariables();
		//setBackground(new Background(0.09804f, 0.6274f, 0));
	BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	levelBgAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
	activity.getEngine().getTextureManager().loadTexture(levelBgAtlas);
	levelBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBgAtlas,activity.getBaseContext(),"levelselectbg.png",0,0);
	levelTextureAtlas.load();
	final float centerX = (activity.mCamera.getWidth() - levelBgTextureRegion.getWidth()) / 2;
    final float centerY = (activity.mCamera.getHeight() - levelBgTextureRegion.getHeight()) / 2;
	SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, levelBgTextureRegion, activity.getVertexBufferObjectManager()));

	this.setBackground(bg);
		activity = MainActivity.getSharedInstance();
		MainActivity.menuBgm.start();
		
		loadResources();
	}
	public void initializeVariables()
	{	//num_of_levels = determineCurrentLevel();
		levelTextureRegion= new ITextureRegion[num_of_levels];
		levelSprite = new Sprite[num_of_levels];
		
	}
	public int determineCurrentLevel()
	{	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		int i = preferences.getInt("level", 1);
		return i;
	}

	public void loadResources()
	{	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
		level = preferences.getInt("level", 1);
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//levelTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480);
		//levelTextureAtlas.load();
		
		levelSelectTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelTextureAtlas,activity,"levelSelect.png",0,10);
		levelSelectSprite = new Sprite(190,10,levelSelectTextureRegion,activity.getEngine().getVertexBufferObjectManager());
		this.attachChild(levelSelectSprite);
		int k;
				
		for(int l = 0; l<num_of_levels;l++)
			levelTextureRegion[l] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelTextureAtlas,activity,"level.png",100,100);
		
		for(int i = 0,p=65,j=65; i< num_of_levels; i++)
		{	
			final int num = i;
			if(i<5)
			{
				levelSprite[i] = new Sprite(p,120,levelTextureRegion[i], activity.getEngine().getVertexBufferObjectManager())
				{	
					@Override
					 public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						activity.setCurrentSceneType(MainActivity.SceneType.GAMEPLAY);
						
						switch(num)
							{
								case 0:		
									activity.setCurrentScene(new Level_01_Scene());
									break;
								case 1:
			            	 		 activity.setCurrentScene(new Level_02_Scene());
			            	 		 break;
								case 2:
			            	 		 activity.setCurrentScene(new Level_03_Scene());
			            	 		 break;
								case 3:
									activity.setCurrentScene(new Level_04_Scene());
									break;
								case 4:
									activity.setCurrentScene(new Level_05_Scene());
									break;
								
								default:
									activity.setCurrentSceneType(MainActivity.SceneType.LEVEL_SELECTION);
									break;
									
							}
	                            return false;
			            };
				};
				textLevelNum= new Text(p + 45, 160, activity.mFont,"lock", activity.getVertexBufferObjectManager());
				if((i+1)<=level)
				{
						textLevelNum.setText(""+(i+1));
				this.setColor(Color.RED);
				this.registerTouchArea(levelSprite[i]);
				}
				this.attachChild(levelSprite[i]);
				this.setTouchAreaBindingOnActionDownEnabled(true);
				this.attachChild(textLevelNum);
				p+=140;
			}
			else
			{	
				levelSprite[i] = new ButtonSprite(j,280,levelTextureRegion[i], activity.getEngine().getVertexBufferObjectManager())
				{
					@Override
					 public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
						activity.setCurrentSceneType(MainActivity.SceneType.GAMEPLAY);
						
						switch(num)
							{
								
								case 5:
									activity.setCurrentScene(new Level_06_Scene());
									break;
								case 6:
									activity.setCurrentScene(new Level_07_Scene());
									break;
								case 7:
									activity.setCurrentScene(new Level_08_Scene());
									break;
								case 8:
									activity.setCurrentScene(new Level_09_Scene());
									break;
								case 9:
									activity.setCurrentScene(new Level_10_Scene());
									break;
								default:
									activity.setCurrentSceneType(MainActivity.SceneType.LEVEL_SELECTION);
									break;
									
							}
	                            return false;
			            };
				};
				
				textLevelNum= new Text(j + 45, 320, activity.mFont,"lock", activity.getVertexBufferObjectManager());
				if((i+1)<=level)
				{
						textLevelNum.setText(""+(i+1));
						this.registerTouchArea(levelSprite[i]);
				}
				this.attachChild(levelSprite[i]);
				this.attachChild(textLevelNum);
				j+=140;
			}			
		}
	}

}
