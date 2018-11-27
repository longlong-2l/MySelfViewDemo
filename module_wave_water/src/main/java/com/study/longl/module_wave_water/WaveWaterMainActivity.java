package com.study.longl.module_wave_water;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.study.longl.module_wave_water.view.WaterWaveView;

@Route(path = "/ui/WaveWaterMainActivity")
public class WaveWaterMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_water_main);
         /*水波纹加载进度展示*/
        WaterWaveView waterWaveView = findViewById(R.id.wwv_test);
        waterWaveView.start();
    }
}
