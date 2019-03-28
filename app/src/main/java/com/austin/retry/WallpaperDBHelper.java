package com.austin.retry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
            + "WALLPAPER_ID INTEGER);";

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


    public void insertObject(ObjectWrapper object){
        ContentValues mContent = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        mContent.put("OBJECT_NAME", object.getObjectName());
        mContent.put("OBJECT_SETTINGS", object.getSettings());
        mContent.put("WALLPAPER_ID", object.getImage_Id());
        mContent.put("IS_CHOSEN", false);
        db.insert("OBJECT", null, mContent);
    }

    public void changeObjectName(ObjectWrapper object, String s){
        SQLiteDatabase db = this.getWritableDatabase();
        //set the objectname to be string on the object that you pass
        db.execSQL("UPDATE OBJECT SET OBJECT_NAME = "+ s+ " WHERE OBJECT_ID = "+object.getObjectId());
    }


    public Cursor getImages(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT WALLPAPER.*, WALLPAPER.WALLPAPER_ID as _id from WALLPAPER", null);
    }

    public WallpaperDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
}
