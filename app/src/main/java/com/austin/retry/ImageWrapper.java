package com.austin.retry;

import android.content.Context;
import android.graphics.Bitmap;

public class ImageWrapper {

    private byte[] blob;
    private String name;
    private Context helpME;
    private Bitmap bitmap;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public ImageWrapper(){
    }
    public ImageWrapper(byte[] a, String b){
        blob = a;
        name = b;
    }


    public void setBlob(byte[] b){
        blob = b;
    }
    public void setName(String n){
        name = n;
    }
    public void setHelpME(Context c){
        helpME = c;
    }
    public byte[] getBlob(){
        return blob;
    }
    public String getName(){
        return name;
    }

    public Context getHelpME() {
        return helpME;
    }
}
