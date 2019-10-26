package app.photocapture.com.photo_capture_activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.photocapture.com.database_helper.DatabaseOperation;
import app.photocapture.com.database_helper.ImageInfoDAO;
import app.photocapture.com.util.Constants;

class PhotoCapturePresenter {

    Activity activity;

    private PhotoCaptureMvpView photoCaptureMvpView;

    PhotoCapturePresenter(PhotoCaptureMvpView photoCaptureMvpView,
                          Activity activity
    ) {
        this.activity = activity;
        this.photoCaptureMvpView = photoCaptureMvpView;
    }

    void createRootFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + app.photocapture.com.util.Constants.File.ROOT_FOLDER_NAME);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
    }

    void createSubFolder(String name) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + app.photocapture.com.util.Constants.File.ROOT_FOLDER_NAME
                + File.separator + name);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        if (success) {
            photoCaptureMvpView.onFolderCreateSuccess();
        } else {
            photoCaptureMvpView.onFolderCreateError();
        }
    }

    void checkIfFolderExists(String name) {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + app.photocapture.com.util.Constants.File.ROOT_FOLDER_NAME
                + File.separator + name);
        if (folder.exists()) {
            photoCaptureMvpView.onFolderFound();
        } else {
            photoCaptureMvpView.onFolderNotFound();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void openRootFolder(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()
                + File.separator + Constants.File.ROOT_FOLDER_NAME);
        intent.setDataAndType(uri, "text/csv");
        activity.startActivity(intent);
    }


    void saveImageInfo(ImageInfoDAO imageInfoDAO, Context context) {
        try {
            DatabaseOperation.saveImageInfo(context, imageInfoDAO);
            photoCaptureMvpView.onImageInfoSavedSuccess();
        } catch (SQLiteException e) {
            e.printStackTrace();
            photoCaptureMvpView.onImageInfoSavedError(e.getMessage());
        }
    }

}
