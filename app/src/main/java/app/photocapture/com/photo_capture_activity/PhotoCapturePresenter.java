package app.photocapture.com.photo_capture_activity;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

class PhotoCapturePresenter {

    private PhotoCaptureMvpView photoCaptureMvpView;

    PhotoCapturePresenter(PhotoCaptureMvpView photoCaptureMvpView) {
        this.photoCaptureMvpView = photoCaptureMvpView;
    }

    void createRootFolder() {
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + app.photocapture.com.util.Constants.File.ROOT_FOLDER_NAME);
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

    public void saveVideo() {

    }

    public void saveImage() {

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

}
