package app.photocapture.com.util;

import android.content.Context;
import android.content.res.Resources;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;

public class Util {
    /**
     * This method converts dp to pixels
     *
     * @param dp desired amount of dp
     * @return amount in pixels
     */
    public static float dpToPx(int dp) {
        return Math.round(Resources.getSystem().getDisplayMetrics().density * dp);
    }

    public static File getCompressedFile(File file,
                                         Context context) {
        File compressedFile = null;
        try {
            if (SharedPrefUtils.INSTANCE.readBoolean(
                    Constants.PreferenceKeys.IMAGE_QUALITY_TYPE_IS_CUSTOM
            )) {
                compressedFile = new Compressor(context)
                        .setQuality(SharedPrefUtils.INSTANCE.readInt(
                                Constants.PreferenceKeys.QUALITY_PERCENTENGE
                        ))
                        .compressToFile(file);
            } else {
                switch (SharedPrefUtils.INSTANCE.readInt(
                        Constants.PreferenceKeys.QUALITY_SELECTED
                )) {
                    case Constants.ImageQuality.HIGH:
                        compressedFile = new Compressor(context)
                                .setQuality(100)
                                .compressToFile(file);
                        break;
                    case Constants.ImageQuality.LOW:
                        compressedFile = new Compressor(context)
                                .setQuality(30)
                                .compressToFile(file);
                        break;
                    case Constants.ImageQuality.MEDIUM:
                        compressedFile = new Compressor(context)
                                .setQuality(50)
                                .compressToFile(file);
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return compressedFile;
    }
}
