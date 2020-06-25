package app.photocapture.com.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import app.photocapture.com.R;
import app.photocapture.com.photo_capture_activity.PhotoCaptureActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        launchMainActivity();
    }

    private void launchMainActivity() {
        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(
                                        SplashActivity.this,
                                        PhotoCaptureActivity.class
                                ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                        );
                        finish();
                    }
                }, 4000);
    }
}
