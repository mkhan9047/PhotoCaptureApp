package app.photocapture.com.photo_capture_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import app.photocapture.com.R;
import app.photocapture.com.setting_activity.SettingActivity;

public class PhotoCaptureActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        initViews();
        setListener();
    }

    private void setListener() {
        btnSetting.setOnClickListener(this);
    }

    private void initViews() {
        btnSetting = findViewById(R.id.btn_setting);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:
                startActivity(
                        new Intent(
                                PhotoCaptureActivity.this,
                                SettingActivity.class
                        )
                );
                break;
        }
    }
}
