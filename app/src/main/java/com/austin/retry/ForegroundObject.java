package com.austin.retry;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

class ForegroundObject {

    private Bitmap image;
    private int x,y;

    private int xVelocity = 8;
    private int yVelocity = -6;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    ForegroundObject(Bitmap bmp) {
        image = bmp;
        this.x = screenWidth/2;
        this.y = screenHeight/2;
    }

    void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    void update() {
        x += xVelocity;
        y += yVelocity;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            xVelocity = xVelocity * -1;
        }
        if ((y > screenHeight - image.getWidth()) || (y < 0)) {
            yVelocity = yVelocity * -1;
        }

    }
}
