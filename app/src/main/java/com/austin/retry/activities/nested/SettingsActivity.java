package com.austin.retry.activities.nested;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.austin.retry.R;
import com.austin.retry.WallpaperDBHelper;
import com.austin.retry.activities.BackgroundActivity;
import com.austin.retry.activities.ObjectActivity;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class SettingsActivity extends Activity {

    private SeekBar sizeSlider;
    private SeekBar speedSlider;
    private SeekBar angleSlider;
    private TextView sizeText;
    private TextView speedText;
    private TextView angleText;
    private ImageView imageView;
    private Button test;
    private final String[] sizes = {"Small", "Medium", "Large"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.austinsettings);


        //we can do this by sending in the object's ID.
        //like this
        //get the settings currently displayed to the object adapter
        final String[] currentSettings = Objects.requireNonNull(getIntent().getExtras()).getStringArray("currentSettings");
        assert currentSettings != null;
        String currentSize = currentSettings[0];
        int sizeInt = 0;
        if (!currentSize.toLowerCase().equals("small")) {
            if(!currentSize.toLowerCase().equals("medium")){
                sizeInt  = 2;
            }
            else {
                sizeInt = 1;
            }
        }
        String currentSpeed = currentSettings[1];
        String currentAngle = currentSettings[2];
        String try_objectID = "-1";
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.dvd);
        try {
            String filename = (String) getIntent().getExtras().getString("currentbitmapfile");
            System.out.println(filename);
            File f = new File(getApplicationContext().getFilesDir().getAbsolutePath(), filename + ".png");
            try {
                System.out.println(getApplicationContext().getFilesDir().getAbsolutePath() + filename + ".png");
                b = BitmapFactory.decodeStream(new FileInputStream(f));
            } catch (FileNotFoundException reeeeeeeeeeee) {
            }
            try_objectID = getIntent().getExtras().getString("objectID");
        }catch(NullPointerException nada){
            Intent i = new Intent(getApplicationContext(), ObjectActivity.class);
            startActivity(i);
        }
        imageView = findViewById(R.id.settingsImg);
        imageView.setImageBitmap(b);
        final String objectID = try_objectID;
        //I'm going to program it with this "objectID in mind"
        // Need to determine what object we're editing
        /*String objectName = savedInstanceState.getString("objectID", "fail");*/
        //so it's passing the right object_id to the settings.
        System.out.println("you're in the settings activity and you were passed an object_id of : " +objectID);

        sizeSlider = findViewById(R.id.sbSize);
        speedSlider = findViewById(R.id.sbSpeed);
        angleSlider = findViewById(R.id.sbAngle);
        sizeText = findViewById(R.id.sizeText);
        speedText = findViewById(R.id.speedText);
        angleText = findViewById(R.id.angleText);
        test = findViewById(R.id.testbutton);
        sizeText.setText(currentSize);
        speedText.setText(currentSpeed);
        angleText.setText(currentAngle);

        sizeSlider.setMax(2);
        speedSlider.setMax(199);
        angleSlider.setMax(360);
        sizeSlider.setProgress(sizeInt);
        speedSlider.setProgress(Integer.parseInt(currentSpeed));
        angleSlider.setProgress(Integer.parseInt(currentAngle));

        //imageView.setImageDrawable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i  = new Intent(getApplicationContext(), BackgroundActivity.class);
                i.putExtra("aaa", true);
                i.putExtra("objectId", Integer.parseInt(objectID));
                //have to put the settings in the oldone to go back!!!!
                i.putExtra("settings", currentSettings);
                startActivityForResult(i, 69);


            }
        });


        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                switch (seekBar.getId()){

                    case (R.id.sbSize):

                        String size = "";
                        switch (progress) {
                            case 0:
                                size = "Small";
                                break;
                            case 1:
                                size = "Medium";
                                break;
                            case 2:
                                size = "Large";
                                break;
                        }
                        sizeText.setText(size);
                        break;
                    case (R.id.sbSpeed):
                        String kerchoo = String.valueOf(progress) + "%";
                        speedText.setText(String.valueOf(kerchoo));
                        break;
                    case (R.id.sbAngle):
                        String angel = String.valueOf(progress) + "°";
                        angleText.setText(angel);
                        break;

                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        sizeSlider.setOnSeekBarChangeListener(listener);
        speedSlider.setOnSeekBarChangeListener(listener);
        angleSlider.setOnSeekBarChangeListener(listener);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int size = sizeSlider.getProgress();
                int speed = speedSlider.getProgress();
                int angle = angleSlider.getProgress();
                WallpaperDBHelper db = new WallpaperDBHelper(getApplicationContext());
                String sizeString = sizes[size];
                String speedString = Integer.toString(speed);
                String angleString = Integer.toString(angle);
                //send them as a csv
                String dataBaseString = sizeString+","+speedString+","+angleString;
                //update that object's settings.
                db.updateObject(dataBaseString, objectID);
                SettingsActivity.this.onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), ObjectActivity.class);
        i.putExtra("notify", true);
        startActivity(i);
    }
}
