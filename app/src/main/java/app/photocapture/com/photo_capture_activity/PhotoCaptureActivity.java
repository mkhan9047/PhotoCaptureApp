package app.photocapture.com.photo_capture_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import app.photocapture.com.R;
import app.photocapture.com.qr_code_scan.QrCodeScanActivity;
import app.photocapture.com.setting_activity.SettingActivity;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.PermissionUtils;
import app.photocapture.com.util.SharedPrefUtils;

public class PhotoCaptureActivity extends AppCompatActivity
        implements View.OnClickListener {

    EditText edtReferenceNumber;
    ImageButton btnSetting;
    CardView cardViewQrCode;
    boolean isGranted = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        initViews();
        setListener();
        askPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViewOption();
    }

    private void askPermission() {
        PermissionUtils.INSTANCE.requestPermission(this,
                PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }

    private void setListener() {
        btnSetting.setOnClickListener(this);
        cardViewQrCode.setOnClickListener(this);
    }

    private void initViews() {
        cardViewQrCode = findViewById(R.id.card_view_qr_code);
        edtReferenceNumber = findViewById(R.id.edit_text_reference);
        btnSetting = findViewById(R.id.btn_setting);
    }

    private void setUpViewOption() {
        edtReferenceNumber.setInputType(SharedPrefUtils.INSTANCE.readInputType(
                Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE
        ));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                isGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
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
            case R.id.card_view_qr_code:
                if (isGranted) {
                    startActivity(
                            new Intent(
                                    PhotoCaptureActivity.this,
                                    QrCodeScanActivity.class
                            )
                    );
                } else {
                    Toast.makeText(this, getResources().getString(R.string.required_permission), Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
