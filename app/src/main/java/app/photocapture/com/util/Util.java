package app.photocapture.com.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.photocapture.com.R;
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

    private static void overrideFile(File source, File destination) {
        InputStream in = null;
        try {
            in = new FileInputStream(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(destination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Copy the bits from instream to outstream
        byte[] buf = new byte[1024];
        int len = 0;
        while (true) {
            try {
                if (!((len = in.read(buf)) > 0)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.write(buf, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File timestampItAndSave(String path, File file) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        if(file.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            //        Bitmap src = BitmapFactory.decodeResource(); // the original file is cuty.jpg i added in resources
            Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTime = sdf.format(Calendar.getInstance().getTime()); // reading local time in the system
            dest.setHasAlpha(true);
            Canvas cs = new Canvas(dest);
            Paint tPaint = new Paint();
            tPaint.setTextSize(35);
            tPaint.setColor(Color.BLUE);
            tPaint.setStyle(Paint.Style.FILL);
            cs.drawBitmap(dest, 0f, 0f, null);
            float height = tPaint.measureText("yY");
            cs.drawText(dateTime, 20f, height + 15f, tPaint);
            try {
                dest.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return file;
    }
}
