package com.austin.retry.extra;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import com.austin.retry.ForegroundObject;
import com.austin.retry.R;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setWillNotDraw(false);
    }

    ForegroundObject foregroundObject = new ForegroundObject(BitmapFactory.decodeResource(getResources(), R.drawable.square));

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        /*WallpaperManager m = WallpaperManager.getInstance(getContext());
        Drawable d = m.getDrawable();

        Canvas c = getHolder().lockCanvas();
        d.draw(c);
        getHolder().unlockCanvasAndPost(c);*/

        //draw();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        /*mVisible = false;
        mHandler.removeCallbacks(mUpdateDisplay);*/
    }

   /* private boolean mVisible = false;
    private final Handler mHandler = new Handler();

    private final Runnable mUpdateDisplay = new Runnable()
    {
        @Override
        public void run() {
            draw();
        }
    };

    @Override
    public void onWindowVisibilityChanged(int visible) {
        mVisible = (visible == 1);
        if (visible == 1) {
            draw();
        } else {
            mHandler.removeCallbacks(mUpdateDisplay);
        }
    }

    private void draw() {

        SurfaceHolder holder = getHolder();
        Canvas c = null;
        try {
            //this is where you draw objects to canvas
            c = holder.lockCanvas();
            if (c != null) {
                c.drawColor(0xaa111111); // 0x AA(alpha) RR GG BB (note: lowering alpha will leave residual images)
                foregroundObject.update();
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
    }*/
}
