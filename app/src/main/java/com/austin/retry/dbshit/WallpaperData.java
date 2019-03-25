package com.austin.retry.dbshit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WallpaperData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper wallpaperHelp;

    private static final String[] COLUMNS = {
            "WALLPAPER_ID",
            "WALLPAPER_NAME",
            "IMAGE_DATA"
    };

    public WallpaperData(Context context){
        wallpaperHelp = new WallpaperDBHelper(context);
    }

    public void open(){
        db = wallpaperHelp.getWritableDatabase();
    }
    public void close(){
        if (wallpaperHelp != null){
            wallpaperHelp.close();
        }
    }
}
