package com.austin.retry;

import android.content.Context;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


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
        System.out.println("hello");
        return new WPEngine();
    }

    // ENGINE -- nested in WPService
    public class WPEngine extends Engine {
        private boolean mVisible = false;
        private final Handler mHandler = new Handler();
        private Paint paint = new Paint();
        WallpaperDBHelper hello = new WallpaperDBHelper(getApplicationContext());
        Bitmap bgimage;
        //make an array of foreground objects that are chosen
        ForegroundObject foregroundObject = new ForegroundObject(BitmapFactory.decodeResource(getResources(),R.drawable.square));
        ArrayList<RecyclerWrapper> tempObjects = new ArrayList<>();
        ArrayList<ForegroundObject> foregroundObjects = new ArrayList<>();

        public WPEngine(){
            //get the selected background
            Cursor background = hello.getSelected();
            Cursor foreground = hello.getObjects();
            foreground.moveToFirst();
            //cursor returns objid, name, settings, wallpaperid
            System.out.println("number of things in the foreground array " + foreground.getCount());
            while(!foreground.isAfterLast()){
                tempObjects.add(new RecyclerWrapper(foreground.getString(0), foreground.getString(1)));
                foreground.moveToNext();
            }
            //for every wrapper from the database, parse and make the backgroundimages
            for(RecyclerWrapper w: tempObjects){
                String[] temp = w.getSettings().split(",");
                // make a foregroundObject with the settings from each wrapper return
                String fileName = "data/data/com.austin.retry/files/"+w.getFileName()+".png";
                Bitmap tempy = BitmapFactory.decodeFile(fileName);
                tempy = Bitmap.createScaledBitmap(tempy, 100,100, true);
                foregroundObjects.add(new ForegroundObject(tempy    , temp[0], temp[1], temp[2]));
            }
            //FINALLY ALL OF THE FOREGROUND OBEJECTS ARE IN THE WALLPAPER ENGINE AND CAN BE DRAWN HOW THEY WANNA BE DRAWN
            background.moveToFirst();
            //get the file name and load the file into the bitmap
            File file;
            try {
                String fileName = background.getString(2);
                file = new File(getApplicationContext().getFilesDir().getAbsolutePath(), fileName + ".png");
            }catch(CursorIndexOutOfBoundsException e){
                file = new File(getApplicationContext().getFilesDir().getAbsolutePath(), "1.png");
            }
            try{
                bgimage = BitmapFactory.decodeStream(new FileInputStream(file));
            }catch(Exception e){
                System.out.println(e.toString());
            }
        }
        /*
         * This is where we would get all of the objects from the objects table
         * where they are marked with selected.
         */

        ImageWrapper iw = new ImageWrapper();
        /*
         * After we get those objects from the table where they're selected we're going to
         * parse the information and pass them to a recycler wrapper. Then they will be put into
         * maybe an array list
         */

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
            System.out.println("hello2");
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            System.out.println("hello3");
            Surface AA = holder.getSurface();
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
            for(ForegroundObject fg: foregroundObjects){
                fg.update();
            }
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas c = null;
            Bitmap b = bgimage;
            b = b.copy(b.getConfig(),true);

            int height= Resources.getSystem().getDisplayMetrics().heightPixels;
            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Bitmap bScaled = Bitmap.createScaledBitmap(b, width ,height, true);

            try {
                //this is where you draw objects to canvas
                c = holder.lockCanvas();

                if (c != null) {
                    c.drawColor(0xaa111111); // 0x AA(alpha) RR GG BB (note: lowering alpha will leave residual images)
                    c.drawBitmap(bScaled,0,0,paint);
                    update();
                    for(ForegroundObject fg: foregroundObjects) {
                        fg.draw(c);
                    }
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
