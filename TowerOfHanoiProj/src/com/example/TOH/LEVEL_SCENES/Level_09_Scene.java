package com.example.TOH.LEVEL_SCENES;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.example.towerofhanoiproj.MainActivity;

public class Level_09_Scene extends Scene{

	private BitmapTextureAtlas levelBgAtlas;
	private ITextureRegion levelBgTextureRegion;
	
	BitmapTextureAtlas levelTextureAtlas;
	MainActivity activity;
	
	public Level_09_Scene()
	{
		activity = MainActivity.getSharedInstance();
		MainActivity.menuBgm.pause();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		levelBgAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(levelBgAtlas);
		levelBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBgAtlas,activity.getBaseContext(),"sea.png",0,0);
		final float centerX = (activity.mCamera.getWidth() - levelBgTextureRegion.getWidth()) / 2;
	    final float centerY = (activity.mCamera.getHeight() - levelBgTextureRegion.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, levelBgTextureRegion, activity.getVertexBufferObjectManager()));

		this.setBackground(bg);
	}
	
}
