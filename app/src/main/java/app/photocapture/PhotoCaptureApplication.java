package app.photocapture;

import android.app.Application;
import android.text.InputType;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.SharedPrefUtils;

public class PhotoCaptureApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPrefUtils.INSTANCE.init(getApplicationContext());
    }
}
