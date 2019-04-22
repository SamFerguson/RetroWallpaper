package com.austin.retry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.austin.retry.wrappers.ImageWrapper;

/**
 * <b>Sam Ferguson</b>, Austin Purtell, Michael Walling
 * Most of the skeleton for this code came from the Annuzzi book, chapter 16
 * The helper class seems to be the Data Definition part where we make our table
 *
 */
public class WallpaperDBHelper extends SQLiteOpenHelper {
    /*
     * Strings for the constructor to use
     */
    private static final String DB_NAME = "wallpaper.db";
    private static final int DB_VERSION = 1;

    //The create statement run in the onCreate(db)
    //if it really matters I can make a drawing of our table
    private static final String IMAGE_DEFINITION = "CREATE TABLE WALLPAPER("
            + "WALLPAPER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "WALLPAPER_NAME TEXT,"
            + "IMAGE_DATA TEXT,"
            + "IS_CHOSEN BOOLEAN);";

    private static final String OBJECT_DEFINTION = "CREATE TABLE OBJECT("
            + "OBJECT_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "OBJECT_NAME TEXT,"
            + "OBJECT_SETTINGS TEXT,"
            + "WALLPAPER_ID INTEGER,"
            + "IS_CHOSEN BOOLEAN);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IMAGE_DEFINITION);
        db.execSQL(OBJECT_DEFINTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS WALLPAPER");
        onCreate(db);
    }


    public void insertImage(ImageWrapper image){
        ContentValues mContent = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        mContent.put("WALLPAPER_NAME", image.getName());
        mContent.put("IMAGE_DATA", image.getName());
        db.insert("WALLPAPER",null, mContent);
        db.close();
    }

    public void doSQL(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(s);
    }


    public int getSize(int tableCode){

        int count = -1;
        if(tableCode == 0){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor mCount= db.rawQuery("select count(*) from wallpaper", null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
        }
        if(tableCode == 1){
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor mCount= db.rawQuery("SELECT COUNT(*) FROM OBJECT;", null);
            mCount.moveToFirst();
            count= mCount.getInt(0);
            mCount.close();
        }
        return count;
    }

    public void updateImage(int wallpaperID, int objectID){

        SQLiteDatabase db = this.getWritableDatabase();
        //update the object's wallpaperid for the given objectid
        db.execSQL("update OBJECT SET WALLPAPER_ID = "+wallpaperID+ " WHERE OBJECT_ID = " + objectID);
        db.close();

    }


    public void setSelected(String fileName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE WALLPAPER SET IS_CHOSEN = 0");
        db.execSQL("UPDATE WALLPAPER SET IS_CHOSEN = 1 WHERE IMAGE_DATA = "+ fileName);
    }

    public Cursor getImages(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT WALLPAPER.*, WALLPAPER.WALLPAPER_ID as _id from WALLPAPER", null);
    }

    public Cursor getObject(){
        SQLiteDatabase db = this.getReadableDatabase();
        //this'll return the object id, the name, settings, and the filepath where the object has a connected wallpaper
        return db.rawQuery("SELECT OBJECT.OBJECT_ID as _id, OBJECT.OBJECT_NAME, OBJECT.OBJECT_SETTINGS, WALLPAPER.IMAGE_DATA, OBJECT.IS_CHOSEN " +
                "from OBJECT, WALLPAPER where WALLPAPER.WALLPAPER_ID = OBJECT.WALLPAPER_ID",null);
    }
    public Cursor getObjects(){
        SQLiteDatabase db = this.getReadableDatabase();
        //select * from objects where is chosen
        //select objects.settings, wallpaper.settings, from (select * from wallpaper
        return db.rawQuery("SELECT OBJECT.OBJECT_SETTINGS, WALLPAPER.IMAGE_DATA"+
                " from OBJECT, WALLPAPER where WALLPAPER.WALLPAPER_ID = OBJECT.WALLPAPER_ID AND OBJECT.IS_CHOSEN = 1",null);
    }

    public Cursor getSelected(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT WALLPAPER.*, WALLPAPER.WALLPAPER_ID AS _id FROM WALLPAPER WHERE IS_CHOSEN = 1",null);
    }


    public void clearObjects(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM OBJECT");
        db.close();
    }

    public void updateObject(String settings, String objectID){
        SQLiteDatabase db = this.getWritableDatabase();
        //set the settings to be the csv you pass where Object_id is empty
        System.out.println(settings + "    " + objectID);
        db.execSQL("UPDATE OBJECT SET OBJECT_SETTINGS = \"" + settings + "\" WHERE OBJECT_ID = " + objectID);
    }

    public void makeDefaultObject(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO OBJECT(OBJECT_NAME, OBJECT_SETTINGS, WALLPAPER_ID, IS_CHOSEN)" +
                "values(\"DEFAULT\", \"small,100,45\", 1,1);");
        db.close();
    }


    public WallpaperDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
}
