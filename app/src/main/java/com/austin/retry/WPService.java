package com.austin.retry;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;


public class WPService extends WallpaperService {

    /**
     * Sam Ferguson, Austin Purtell, Michael Walling
     * This is the wallpaper service part of the application
     * it handles all of the life cycles in the live wallpaper
     * it returns the engine that android uses to do wallpaper things
     * it also has a draw method that we are going to use to do all the cool things
     *
     * most of this can be attributed to https://www.techrepublic.com/blog/software-engineer/a-bare-bones-live-wallpaper-template-for-android/
     * we needed a skeleton to build our code off of because it's pretty daunting without the resources.
     * that websites code only has a default draw method that we're going to build off of
     * and some basic overridden methods for all of the methods in the wallpaper lifecycle
     */

    //our custom wallpaper
    public WPService() {
        super();
    }

    @Override
    public Engine onCreateEngine() {
        return new WPEngine();
    }

    // ENGINE -- nested in WPService
    public class WPEngine extends Engine {
        private boolean mVisible = false;
        private final Handler mHandler = new Handler();

        ForegroundObject foregroundObject = new ForegroundObject(BitmapFactory.decodeResource(getResources(),R.drawable.square));


        private final Runnable mUpdateDisplay = new Runnable()
        {
            @Override
            public void run() {
                draw();
            }
        };

        @Override
        public void onVisibilityChanged(boolean visible) {
            mVisible = visible;
            if (visible) {
                draw();
            } else {
                mHandler.removeCallbacks(mUpdateDisplay);
            }
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder){

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            draw();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mVisible = false;
            mHandler.removeCallbacks(mUpdateDisplay);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            mVisible = false;
            mHandler.removeCallbacks(mUpdateDisplay);
        }

        // Updates position of objects
        void update() {
            foregroundObject.update();
        }

        private void draw() {

            SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            try {
                //this is where you draw objects to canvas
                c = holder.lockCanvas();
                if (c != null) {
                    c.drawColor(0xaa111111); // 0x AA(alpha) RR GG BB (note: lowering alpha will leave residual images)
                    update();
                    foregroundObject.draw(c);
                }
            } finally {
                //in here you post your paint to the canvas
                if (c != null)
                    holder.unlockCanvasAndPost(c);
                }
            // Handler delays execution of the runnable...
            // I set to 0 because it was causing low framerate effect, hope that doesn't break anything
            mHandler.removeCallbacks(mUpdateDisplay);
            if (mVisible) {
                mHandler.postDelayed(mUpdateDisplay, 0);
            }
        }

    }
}
