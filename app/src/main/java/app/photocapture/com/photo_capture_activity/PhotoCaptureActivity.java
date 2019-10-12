package app.photocapture.com.photo_capture_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import app.photocapture.com.R;
import app.photocapture.com.setting_activity.SettingActivity;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.SharedPrefUtils;

public class PhotoCaptureActivity extends AppCompatActivity
        implements View.OnClickListener {

    EditText edtReferenceNumber;
    ImageButton btnSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        initViews();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViewOption();
    }

    private void setListener() {
        btnSetting.setOnClickListener(this);
    }

    private void initViews() {
        edtReferenceNumber = findViewById(R.id.edit_text_reference);
        btnSetting = findViewById(R.id.btn_setting);
    }

    private void setUpViewOption() {
        edtReferenceNumber.setInputType(SharedPrefUtils.INSTANCE.readInputType(
                Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE
        ));
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
