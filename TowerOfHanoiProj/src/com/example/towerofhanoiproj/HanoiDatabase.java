package com.example.towerofhanoiproj;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
public class HanoiDatabase extends SQLiteOpenHelper{
       
        static final String dbname = "Hanoi";
 
        public HanoiDatabase(Context context) {
                super(context, dbname, null, 2);
                // TODO Auto-generated constructor stub
        }
    //called when HanoiDatabase is instantiated
        @Override
        public void onCreate(SQLiteDatabase db) {
                // TODO Auto-generated method stub
                db.execSQL("CREATE TABLE IF NOT EXISTS Scores  (id INTEGER PRIMARY KEY, " +
                                                                                                          "level INT," +
                                                                                                          "disks INT," +
                                                                                                          "moves INT," +
                                                                                                          "time VARCHAR(50)     )");
                //populate database for test
                ContentValues cv = new ContentValues();
                cv.put("id", 1);
                cv.put("level",1 );
                cv.put("disks", 3);
                cv.put("moves", 20);
                cv.put("time", "00:00:60");
                       // db.insert("Scores", null, cv);
                       
            cv.put("id", 2);
                cv.put("level",2 );
                cv.put("disks", 4);
                cv.put("moves", 129);
                cv.put("time", "00:01:60");
                        //db.insert("Scores", null, cv);
                               
            cv.put("id", 3);
                cv.put("level",3 );
                cv.put("disks", 5);
                cv.put("moves", 30);
                cv.put("time", "00:03:60");
                       // db.insert("Scores", null, cv);
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // TODO Auto-generated method stub
                 db.execSQL("DROP TABLE IF EXISTS Scores");
         onCreate(db);
        }
        //display all scores
        public Cursor showScores(){
                SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM Scores order by level", null);      
                return cursor;
        }
        //add score if moves greater than previous
        public void addScore(int level,int disks,int moves,String time){
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();
                //get Time string in the text
                String newtimeformat = time.split(" ")[1];
                cv.put("level",level);
                cv.put("disks", disks);
                cv.put("moves", moves);
                cv.put("time", newtimeformat );
               
                Cursor c = db.rawQuery("select * from scores where level = ?", new String[]{Integer.toString(level)});
                if(c.moveToFirst()){
                       
                        if(c.getInt(c.getColumnIndex("moves")) > moves){
                                db.update("Scores", cv, "level=?", new String[]{Integer.toString(level)});
                        }              
                        else if(c.getInt(c.getColumnIndex("moves")) == moves){
                               
                                String[] prevtimeformat = c.getString(c.getColumnIndex("time")).split(":");
                                String[] currtimeformat = newtimeformat.split(":");
                                                               
                                int prevtotalseconds = 0;
                                int newtotalseconds = 0;
                       
                                prevtotalseconds += Integer.parseInt(prevtimeformat[0])*3600;
                                prevtotalseconds +=  Integer.parseInt(prevtimeformat[1])*60;
                                prevtotalseconds +=  Integer.parseInt(prevtimeformat[2]);
                               
                                newtotalseconds += Integer.parseInt(currtimeformat[0])*3600;
                                newtotalseconds += Integer.parseInt(currtimeformat[1])*60;
                                newtotalseconds += Integer.parseInt(currtimeformat[2]);
                               
                                if(prevtotalseconds > newtotalseconds ){
                                        db.update("Scores", cv, "level=?", new String[]{Integer.toString(level)});
                                }
                        }
                }else
                        db.insert("Scores", null, cv);
               
        }
 
}