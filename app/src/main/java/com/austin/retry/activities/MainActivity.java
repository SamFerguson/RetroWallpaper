package com.austin.retry.activities;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.austin.retry.wrappers.ImageWrapper;
import com.austin.retry.R;
import com.austin.retry.WPService;
import com.austin.retry.WallpaperDBHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button butt1;
    private DrawerLayout drawerLayout;
    private final int RESULT_IMG = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

       // Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
       // setSupportActionBar(myToolbar);


        SharedPreferences firstTime = getSharedPreferences("default", 0);
        System.out.println(getResources().getResourceEntryName(R.drawable.bkg));

        if(firstTime.getBoolean("first", true)){

            ImageWrapper img = new ImageWrapper();
            System.out.println(R.drawable.dvd);
            final Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bkg);
            img.setName("1");
            img.setHelpME(getApplicationContext());
            img.setBitmap(b);
            new FileAsync(img,1).execute();
            new UploadAsync().execute(img);
            img = new ImageWrapper();

            final Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.dvd);
            img.setName("2");
            img.setHelpME(getApplicationContext());
            img.setBitmap(c);
            new FileAsync(img,2).execute();
            new UploadAsync().execute(img);

            //having more asyncs would cause a bug on opening, would need splash screen

            firstTime.edit().putBoolean("first", false).apply();

        }


        System.out.println(this.fileList() == null);

        /*if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Explain to the user why we need to read the contacts
            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    33);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique
        }*/

        setContentView(R.layout.activity_main);

        butt1 = (Button) findViewById(R.id.butt1);
        butt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(
                        WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                        new ComponentName(getApplicationContext(), WPService.class));
                startActivity(intent);
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        // set item as selected to persist highlight
                        if(menuItem.getItemId() == R.id.Preview){
                            Intent intent = new Intent(
                                    WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
                            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                    new ComponentName(getApplicationContext(), WPService.class));
                            startActivity(intent);
                        }
                        else if(menuItem.getItemId() == R.id.Background) {
                            Intent i = new Intent(getApplicationContext(), BackgroundActivity.class);
                            startActivity(i);
                        }
                        else if(menuItem.getItemId() == R.id.Foreground){
                            Intent i = new Intent(getApplicationContext(), ObjectActivity.class);
                            i.putExtra("which", "object");
                            startActivity(i);
                        }
                        else if(menuItem.getItemId() == R.id.ImageGallery){
                            Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            //then start the intent
                            startActivityForResult(i, RESULT_IMG);
                            Log.i("Menu Item",menuItem.getItemId() + "");
                        }
                        else if(menuItem.getItemId() == R.id.Upgrade){
                            Intent i = new Intent(getApplicationContext(), UpgradeActivity.class);
                            startActivity(i);
                            Log.i("Menu Item",menuItem.getItemId() + "");
                        }
                        else if(menuItem.getItemId() == R.id.About){
                            Intent i = new Intent(getApplicationContext(), AboutActivity.class);
                            startActivity(i);
                            Log.i("Menu Item",menuItem.getItemId() + "");
                        }
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

    }
    //sup fam
    @Override
    protected void onActivityResult(int reqCode, int result, Intent data) {
        if (reqCode == RESULT_IMG) {
            super.onActivityResult(reqCode, result, data);
            ImageWrapper img = new ImageWrapper();
            if (result == RESULT_OK) {
                try {
                    final Uri image = data.getData();
                    if (image != null) {
                        final InputStream stream = getContentResolver().openInputStream(image);
                        final Bitmap input = BitmapFactory.decodeStream(stream);
                        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
                        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
                        input.compress(Bitmap.CompressFormat.PNG, 100, stream2);
                        byte[] temp = stream2.toByteArray();
                        //get file name to pass to database
                        String filename = image.getLastPathSegment();
                        File f = new File(filename);
                        String imageName = f.getName();
                        img.setBlob(temp);
                        img.setName(imageName);
                        img.setHelpME(getApplicationContext());
                        try {
                            FileOutputStream out = new FileOutputStream(getApplicationContext().getFilesDir().getAbsolutePath()+"/"+imageName+".png");
                            input.compress(Bitmap.CompressFormat.PNG, 100, out);
                            out.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new UploadAsync().execute(img);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    static class UploadAsync extends AsyncTask<ImageWrapper, Void, Byte[]> {

        @Override
        protected Byte[] doInBackground(ImageWrapper... backgroundImages) {

            WallpaperDBHelper mHelper = new WallpaperDBHelper(backgroundImages[0].getHelpME());
            mHelper.insertImage(backgroundImages[0]);
            Cursor test = mHelper.getImages();
            test.moveToFirst();
            //System.out.println("image id " + test.getString(0) + " image name " + test.getString(1)+
                    //(test.getInt(3)==0) + test.getString(2));

            System.out.println("image id " + test.getString(0) + " image name " + test.getString(1)+
                    (test.getInt(3)==0) + test.getString(2));
            test.close();

            return new Byte[0];

        }
    }

    static class FileAsync extends AsyncTask<Void, Void, Void>{

        private ImageWrapper iw;
        private int i;
        public FileAsync(ImageWrapper imageWrapper, int i){
            iw = imageWrapper;
            this.i = i;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String AAAAAAAA = iw.getHelpME().getFilesDir().getAbsolutePath()+"/" + i + ".png";
                System.out.println(AAAAAAAA);
                FileOutputStream out = new FileOutputStream(AAAAAAAA);
                iw.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, out);
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
