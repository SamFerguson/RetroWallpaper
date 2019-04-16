package com.austin.retry.wrappers;

import android.content.Context;
import android.graphics.Bitmap;

import com.austin.retry.WallpaperDBHelper;

public class RecyclerWrapper {

    private Bitmap bitmap;
    private String fileName;
    private Context context;
    private int id;
    private String objectName;
    private String settings;
    private String idk;

    public RecyclerWrapper(){}

    public RecyclerWrapper(String s, String file){
        settings = s;
        fileName = file;
    }

    //settings in the form of "size,angle,speed"
    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getId() {
        return id;

    }

    public void setId(int id) {
        this.id = id;
    }






    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getFileName() {
        return fileName;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
