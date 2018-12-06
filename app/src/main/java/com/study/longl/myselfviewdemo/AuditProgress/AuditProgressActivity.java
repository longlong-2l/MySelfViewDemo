package com.study.longl.myselfviewdemo.AuditProgress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.study.longl.myselfviewdemo.R;

public class AuditProgressActivity extends AppCompatActivity {

    private int notCompleteImageArray[] = {R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete};
    private int completeImageArray[] = {R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete};
    private String text[] = {"一帆风顺", "一帆风顺", "风顺", "帆风顺", "风顺"};

    private int currentProgress = 1;
    private AuditProgressViewTwo auditProgressViewTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_progress);
        auditProgressViewTwo = findViewById(R.id.apvt_my);
        auditProgressViewTwo.setCompleteImageSource(completeImageArray);
        auditProgressViewTwo.setNotCompleteImageSource(notCompleteImageArray);
        auditProgressViewTwo.setTextSource(text);
        auditProgressViewTwo.build();

        findViewById(R.id.bt_progree_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentProgress++;
                auditProgressViewTwo.setCurrentPosition(currentProgress);
            }
        });
    }
}
