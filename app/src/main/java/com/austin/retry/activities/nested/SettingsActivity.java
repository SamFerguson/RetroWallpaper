package com.austin.retry.activities.nested;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.austin.retry.R;

import org.w3c.dom.Text;

public class SettingsActivity extends Activity {

    private SeekBar sizeSlider;
    private SeekBar speedSlider;
    private SeekBar angleSlider;
    private TextView sizeText;
    private TextView speedText;
    private TextView angleText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.austinsettings);


        // Need to determine what object we're editing
        /*String objectName = savedInstanceState.getString("objectID", "fail");*/

        sizeSlider = findViewById(R.id.sbSize);
        speedSlider = findViewById(R.id.sbSpeed);
        angleSlider = findViewById(R.id.sbAngle);
        sizeText = findViewById(R.id.sizeText);
        speedText = findViewById(R.id.speedText);
        angleText = findViewById(R.id.angleText);

        sizeSlider.setMax(2);
        speedSlider.setMax(199);
        angleSlider.setMax(360);

        imageView = findViewById(R.id.settingsImg);
        //imageView.setImageDrawable();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        String kerchoo = String.valueOf(progress + 1) + "%";
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

    }
}
