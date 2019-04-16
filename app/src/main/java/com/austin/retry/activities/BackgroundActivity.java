package com.austin.retry.activities;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;

import com.austin.retry.adapters.BackgroundAdapter;
import com.austin.retry.R;
import com.austin.retry.wrappers.RecyclerWrapper;
import com.austin.retry.WPService;
import com.austin.retry.WallpaperDBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class BackgroundActivity extends Activity {

    ArrayList<RecyclerWrapper> wrappers = new ArrayList<>();
    private DrawerLayout drawerLayout;
    FloatingActionButton addImg;
    boolean amIdoingit = false;
    int yesYouAre = -1;
    String[] previousSettings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        try {
            amIdoingit = intent.getExtras().getBoolean("aaa");
        }catch(NullPointerException e){ }
        System.out.println("you're in the background activity and you're doing it?: " + amIdoingit);
        //if you're coming from the settings and setitng the picture
        if(amIdoingit){
            yesYouAre = intent.getExtras().getInt("objectId");
            //get the previous settings
            previousSettings = intent.getExtras().getStringArray("settings");
        }
        System.out.println("you'er in the background activity and you're passed an object_id of: " + yesYouAre);
        setContentView(R.layout.activity_bkg);
        recyclerView = findViewById(R.id.recycler_view);

        addImg = (FloatingActionButton) findViewById(R.id.fab);
        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do the same thing as IMage library does right now
            }
        });

        // I'm not sure if this is how we should do it
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        if(menuItem.getItemId() == R.id.Preview){
                            Intent intent = new Intent(
                                    WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                    new ComponentName(getApplicationContext(), WPService.class));
                            startActivity(intent);
                        }

                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        return true;
                    }
                });


        // DISPLAYING THE LAYOUT
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        WallpaperDBHelper mHelper = new WallpaperDBHelper(getApplicationContext());
        Cursor cursor = mHelper.getImages();
        cursor.moveToFirst();

        for(int i = 0; i < cursor.getCount(); i ++){
            RecyclerWrapper w = new RecyclerWrapper();
            String fileName = cursor.getString(2);
            System.out.println(getApplicationContext().getFilesDir().getAbsolutePath());
            w.setId(cursor.getInt(0));
            File f = new File(getApplicationContext().getFilesDir().getAbsolutePath(), fileName+".png");
            w.setFileName(fileName);
            //byte[] bits = cursor.getBlob(2);
            Bitmap b = null;

            try{
                FileInputStream fileInputStream = new FileInputStream(f);
                b = BitmapFactory.decodeStream(fileInputStream);
                fileInputStream.close();
            }catch(Exception e){
                System.out.println(e.toString());
            }
            int ogHeight = b.getHeight();
            int ogWidth= b.getWidth();
            float aspectRatio = ogWidth/(float)ogHeight;

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int displaywidth = width-100;
            int displayheight = (int) (displaywidth/aspectRatio);
            Bitmap bScaled = Bitmap.createScaledBitmap(b, displaywidth, displayheight, true);
            w.setBitmap(bScaled);
            w.setContext(getApplicationContext());
            wrappers.add(w);
            cursor.moveToNext();
        }
        cursor.close();

        mAdapter = new BackgroundAdapter(wrappers,yesYouAre,previousSettings);
        recyclerView.setAdapter(mAdapter);
    }
}
