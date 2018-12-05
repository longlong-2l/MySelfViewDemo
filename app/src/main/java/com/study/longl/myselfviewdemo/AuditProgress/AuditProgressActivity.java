package com.study.longl.myselfviewdemo.AuditProgress;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.study.longl.myselfviewdemo.R;

public class AuditProgressActivity extends AppCompatActivity {

    private int notCompleteImageArray[] = {R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete, R.drawable.audit_uncomplete};
    private int completeImageArray[] = {R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete, R.drawable.audit_complete};
    private String text[] = {"一帆风顺", "一帆风顺", "风顺", "帆风顺", "风顺"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audit_progress);
        AuditProgressViewTwo auditProgressViewTwo = findViewById(R.id.apvt_my);
        auditProgressViewTwo.setCompleteImageSource(completeImageArray);
        auditProgressViewTwo.setNotCompleteImageSource(notCompleteImageArray);
        auditProgressViewTwo.setTextSource(text);
    }
}
