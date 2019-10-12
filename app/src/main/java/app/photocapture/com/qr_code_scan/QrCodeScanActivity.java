package app.photocapture.com.qr_code_scan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import app.photocapture.com.R;
import app.photocapture.com.photo_capture_activity.PhotoCaptureActivity;
import app.photocapture.com.util.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanActivity extends AppCompatActivity implements
        ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
        zXingScannerView.setAutoFocus(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        zXingScannerView.startCamera();// Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();// Stop camera on pause
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(
                new Intent(
                        this,
                        PhotoCaptureActivity.class
                )
        );
        finish();
    }

    @Override
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            startActivity(
                    new Intent(
                            this,
                            PhotoCaptureActivity.class
                    ).putExtra(
                            Constants.IntentKeys.QR_RESULT,
                            rawResult.getText()
                    )
            );
            finish();
        }
    }
}
