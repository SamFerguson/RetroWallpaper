package com.austin.retry;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class ForegroundActivity extends Activity {

    ArrayList<ForegroundObject> objects = new ArrayList<>();
    private DrawerLayout drawerLayout;
    FloatingActionButton addObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        RecyclerView recyclerView;
        RecyclerView.Adapter mAdapter;
        RecyclerView.LayoutManager layoutManager;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground);
        recyclerView = findViewById(R.id.recycler_view);

        addObj = (FloatingActionButton) findViewById(R.id.fab);
        addObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds Object
            }
        });

        // DISPLAYING THE LAYOUT
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        WallpaperDBHelper mHelper = new WallpaperDBHelper(getApplicationContext());
        Cursor cursor = mHelper.getImages();
        cursor.moveToFirst();

        // get objects from database
        // dont change my shit sam
        for(int i = 0; i < 5; i++){
            objects.add(new ForegroundObject(BitmapFactory.decodeResource(getResources(),R.drawable.square)));
            objects.add(new ForegroundObject(BitmapFactory.decodeResource(getResources(),R.drawable.steve_not_impressed)));
        }


        mAdapter = new ForegroundAdapter(objects);
        recyclerView.setAdapter(mAdapter);
    }
}
