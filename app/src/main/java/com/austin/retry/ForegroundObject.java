package com.austin.retry;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;

class ForegroundObject {

    // editable attributes
    private Bitmap image;
    private String name = "Object";
    private int size = 200;
    private float angle = 45;
    private float speed = 100;
    private String shape = "round";

    // not editable
    private int x,y;
    private float xSpeed;
    private float ySpeed;
    private int xDir = 1;
    private int yDir = 1;

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

        xSpeed = speed * (float)Math.cos(angle) * xDir;
        ySpeed = speed * (float)Math.sin(angle) * yDir;

        x += xSpeed;
        y += ySpeed;

        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            xDir = xDir * -1;
        }
        if ((y > screenHeight - image.getWidth()) || (y < 0 - image.getWidth())) {
            yDir = yDir * -1;
        }

    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }
}
