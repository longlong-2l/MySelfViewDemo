package com.study.longl.module_input_password.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.study.longl.module_input_password.R;

@Route(path="/password/InputPasswordMainActivity")
public class InputPasswordMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_password_activity_main);
        Button button = findViewById(R.id.bt_goo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/ui/WaveWaterMainActivity").navigation();
            }
        });
    }
}
