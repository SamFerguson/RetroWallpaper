

package com.austin.retry.dbshit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.austin.retry.dbshit.BackgroundImage;

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
    private static final String DEFINITION = "CREATE TABLE WALLPAPER("
            + "WALLPAPER_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "WALLPAPER_NAME TEXT,"
            + "IMAGE_DATA BLOB);";

    public WallpaperDBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DEFINITION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS WALLPAPER");
        onCreate(db);
    }
    /*
     * The async task passes a background image encapsulator and then it goes to the database
     *
     */
    public void insertBackground(BackgroundImage image){
        ContentValues mContent = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();
        mContent.put("WALLPAPER_NAME", image.getName());
        mContent.put("IMAGE_DATA", image.getBlob());
        db.insert("WALLPAPER",null, mContent);
    }
    public Cursor test(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM WALLPAPER", null);
    }
}
