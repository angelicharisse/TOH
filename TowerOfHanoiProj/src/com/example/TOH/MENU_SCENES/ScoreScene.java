package com.example.TOH.MENU_SCENES;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;

import com.example.towerofhanoiproj.HanoiDatabase;
import com.example.towerofhanoiproj.MainActivity;

import android.database.Cursor;

public class ScoreScene extends Scene{

	MainActivity activity;
	private BitmapTextureAtlas scoreTextureAtlas;
	private ITextureRegion scoreTextureRegion;
	private Sprite scoreSprite;
	
	private BitmapTextureAtlas levelBgAtlas;
	private ITextureRegion levelBgTextureRegion;

	
	
	public ScoreScene()
	{
		activity = MainActivity.getSharedInstance();
       // setBackground(new Background(0.92f, 0.0742f, 0.9f));
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		levelBgAtlas = new BitmapTextureAtlas(activity.getTextureManager(),800,480,TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		activity.getEngine().getTextureManager().loadTexture(levelBgAtlas);
		levelBgTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBgAtlas,activity.getBaseContext(),"levelselectbg.png",0,0);
		final float centerX = (activity.mCamera.getWidth() - levelBgTextureRegion.getWidth()) / 2;
	    final float centerY = (activity.mCamera.getHeight() - levelBgTextureRegion.getHeight()) / 2;
		SpriteBackground bg = new SpriteBackground(new Sprite(centerX, centerY, levelBgTextureRegion, activity.getVertexBufferObjectManager()));

		this.setBackground(bg);
        
        
       
        showScores();
        loadResources();

	}
	
	public void loadResources()
	{
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		//scoreTextureAtlas = new BitmapTextureAtlas(activity.getTextureManager(), 800, 480);
		//scoreTextureAtlas.load();
		
		scoreTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(scoreTextureAtlas,activity,"scoresText.png",0,10);
		scoreSprite = new Sprite(190,10,scoreTextureRegion,activity.getEngine().getVertexBufferObjectManager());
		this.attachChild(scoreSprite);
	}
	
	public void showScores(){
        HanoiDatabase mydb = new HanoiDatabase(activity);
        Cursor cursor = mydb.showScores();
        float x = 100;
        float y = 100;
        Text rowheader = new Text(x,y,activity.mFont,String.format("%s  %10s    %10s  %10s","Level","Disk","Moves","Time"),activity.getVertexBufferObjectManager());   
        y+=30;

        for (int i = 0; i < 8; i++) {
                Text rowlevel =null;
                Text rowdisks =null;
                Text rowmoves =null;
                Text rowtime =null;
                if(cursor.moveToFirst()){
                       
                        do{                    
                                if(cursor.getInt(cursor.getColumnIndex("level")) == i+1){
                                        int level = cursor.getInt(cursor.getColumnIndex("level"));
                                        int disks = cursor.getInt(cursor.getColumnIndex("disks"));
                                        int moves = cursor.getInt(cursor.getColumnIndex("moves"));
                                        String time = cursor.getString(cursor.getColumnIndex("time"));
                                       
                                        rowlevel = new Text(x + 25,y,activity.mFont,                 Integer.toString(level),        activity.getVertexBufferObjectManager());
                                        rowdisks = new Text(x + 177,y,activity.mFont,   Integer.toString(disks),        activity.getVertexBufferObjectManager());
                                        rowmoves = new Text(x + 340,y,activity.mFont,   Integer.toString(moves),        activity.getVertexBufferObjectManager());
                                        rowtime =  new Text(x + 483,y,activity.mFont,   time,                                           activity.getVertexBufferObjectManager());                                                      
                                }                      
                        }while(cursor.moveToNext());                   
                }      
               
                if(rowlevel == null && rowdisks ==null && rowmoves ==null && rowtime ==null){
                        rowlevel = new Text(x + 25,y,activity.mFont,                 Integer.toString(i+1),  activity.getVertexBufferObjectManager());
                        rowdisks = new Text(x + 177,y,activity.mFont,       Integer.toString(i+3),  activity.getVertexBufferObjectManager());
                        rowmoves = new Text(x + 340,y,activity.mFont,   Integer.toString(0),    activity.getVertexBufferObjectManager());
                        rowtime =  new Text(x + 475,y,activity.mFont,   "--:--:--",                             activity.getVertexBufferObjectManager());      
                }
               
                this.attachChild(rowlevel);
                this.attachChild(rowdisks);
                this.attachChild(rowmoves);
                this.attachChild(rowtime);
                y+=30;
        }
       
        this.attachChild(rowheader);
        mydb.close();
       
}
}
