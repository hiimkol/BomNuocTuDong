package com.krader.bomnuoctudong;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.race604.drawable.wave.WaveDrawable;

public class MainActivity extends AppCompatActivity {

    private ImageView image;
    private WaveDrawable waveDrawable;
    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private TextView textView;
    private Button button;
    FirebaseDatabase database;
    DatabaseReference dbRoot;
    private int min, max, current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initVar();
        listenr();


//        mWaveDrawable = new WaveDrawable(this, R.drawable.bottle);
//        mImageView.setImageDrawable(mWaveDrawable);
//
//        mLevelSeekBar = (SeekBar) findViewById(R.id.level_seek);
//        mLevelSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mWaveDrawable.setLevel(progress);
//            }
//        });
//
//        mAmplitudeSeekBar = (SeekBar) findViewById(R.id.amplitude_seek);
//        mAmplitudeSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mWaveDrawable.setWaveAmplitude(progress);
//            }
//        });
//
//        mLengthSeekBar = (SeekBar) findViewById(R.id.length_seek);
//        mLengthSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mWaveDrawable.setWaveLength(progress);
//            }
//        });
//
//        mSpeedSeekBar = (SeekBar) findViewById(R.id.speed_seek);
//        mSpeedSeekBar.setOnSeekBarChangeListener(new SimpleOnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                mWaveDrawable.setWaveSpeed(progress);
//            }
//        });
//
//        mRadioGroup = (RadioGroup) findViewById(R.id.modes);
//        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                final boolean indeterminate = checkedId == R.id.rb_yes;
//                setIndeterminateMode(indeterminate);
//            }
//        });
//        setIndeterminateMode(mRadioGroup.getCheckedRadioButtonId() == R.id.rb_yes);
//
//        ImageView imageView2 = (ImageView) findViewById(R.id.image2);
//        WaveDrawable chromeWave = new WaveDrawable(this, R.drawable.chrome_logo);
//        imageView2.setImageDrawable(chromeWave);
//
//        // Set customised animator here
////        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
////        animator.setRepeatMode(ValueAnimator.REVERSE);
////        animator.setRepeatCount(ValueAnimator.INFINITE);
////        animator.setDuration(4000);
////        animator.setInterpolator(new AccelerateDecelerateInterpolator());
////        chromeWave.setIndeterminateAnimator(animator);
//        chromeWave.setIndeterminate(true);
//
//        View view = findViewById(R.id.view);
//        int color = getResources().getColor(R.color.colorAccent);
//        WaveDrawable colorWave = new WaveDrawable(new ColorDrawable(color));
//        view.setBackground(colorWave);
//        colorWave.setIndeterminate(true);

    }

    private void listenr() {

        dbRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                min = Integer.valueOf(dataSnapshot.child("min").getValue().toString());
                max = Integer.valueOf(dataSnapshot.child("max").getValue().toString());
                current = Integer.valueOf(dataSnapshot.child("current").getValue().toString());
                int waveLevel = 10000 - (current * 1000);
                // int current = 10000 - (Integer.valueOf(dataSnapshot.child("current").getValue().toString()) * 1000);
                waveDrawable.setLevel(waveLevel);
                waveDrawable.setWaveAmplitude(10);
                waveDrawable.setWaveLength(300);
                waveDrawable.setWaveSpeed(5);
                // Log.i("TAG", "onDataChange: " + String.valueOf((current)));
                textView.setText(String.valueOf((current / 10000) * 100));
                editText1.setText(String.valueOf(min));
                if (10 - current < 0) {
                    editText2.setText("0");

                } else {
                    editText2.setText(String.valueOf(10 - current));
                }
                editText3.setText(String.valueOf(max));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("Database Error", "onCancelled: " + databaseError.toString());
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRoot.child("min").setValue(Integer.valueOf(editText1.getText().toString()));
                dbRoot.child("max").setValue(Integer.valueOf(editText3.getText().toString()));
            }
        });
    }

    private void initVar() {
        database = FirebaseDatabase.getInstance();
        dbRoot = database.getReference();
        waveDrawable = new WaveDrawable(this, R.drawable.bottle);
        image.setImageDrawable(waveDrawable);
    }

    // private void setIndeterminateMode(boolean indeterminate) {
//        mWaveDrawable.setIndeterminate(indeterminate);
//        mLevelSeekBar.setEnabled(!indeterminate);
//
//        if (!indeterminate) {
//            mWaveDrawable.setLevel(mLevelSeekBar.getProgress());
//        }
//        mWaveDrawable.setWaveAmplitude(mAmplitudeSeekBar.getProgress());
//        mWaveDrawable.setWaveLength(mLengthSeekBar.getProgress());
//        mWaveDrawable.setWaveSpeed(mSpeedSeekBar.getProgress());
//    }

//    private static class SimpleOnSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
//
//        @Override
//        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//            // Nothing
//        }
//
//        @Override
//        public void onStartTrackingTouch(SeekBar seekBar) {
//            // Nothing
//        }
//
//        @Override
//        public void onStopTrackingTouch(SeekBar seekBar) {
//            // Nothing
//        }
//    }

    private void initView() {
        image = findViewById(R.id.image);
        textView = findViewById(R.id.textView1);
        button = findViewById(R.id.button);
        editText1 = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText2.setEnabled(false);
        editText3 = findViewById(R.id.editText3);
    }
}
