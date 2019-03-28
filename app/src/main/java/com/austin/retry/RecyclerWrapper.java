package com.austin.retry;

import android.content.Context;
import android.graphics.Bitmap;

public class RecyclerWrapper {

    private Bitmap bitmap;
    private String fileName;
    private Context context;

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
