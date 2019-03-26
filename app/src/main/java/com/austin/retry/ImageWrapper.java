package com.austin.retry;

import android.content.Context;

public class ImageWrapper {

    private byte[] blob;
    private String name;
    private Context helpME;

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
