package com.austin.retry.dbshit;

import android.content.Context;

public class BackgroundImage {

    private byte[] blob;
    private String name;
    private Context helpME;

    public BackgroundImage(){
    }
    public BackgroundImage(byte[] a, String b){
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
