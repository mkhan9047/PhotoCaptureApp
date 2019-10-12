package app.photocapture.com.qr_code_scan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import app.photocapture.com.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScanActivity extends AppCompatActivity implements
        ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);
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
    public void handleResult(Result rawResult) {
        if (rawResult != null) {
            Toast.makeText(this, rawResult.getText(), Toast.LENGTH_SHORT).show();
        }
    }
}
