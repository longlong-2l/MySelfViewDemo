package com.study.longl.module_wave.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.study.longl.module_wave.R;
import com.study.longl.module_wave.view.MyWaveView;

/**
 * 水波纹扩散的自定义view，有问题
 */
public class WaveMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wave_activity_main);
        MyWaveView myWaveView = findViewById(R.id.wv_my_test);
        myWaveView.start();
    }
}
