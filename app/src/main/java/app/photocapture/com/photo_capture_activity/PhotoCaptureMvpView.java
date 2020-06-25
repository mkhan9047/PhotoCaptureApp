package app.photocapture.com.photo_capture_activity;

interface PhotoCaptureMvpView {
    void onFolderFound();
    void onFolderNotFound();
    void onFolderCreateSuccess();
    void onFolderCreateError();
    void onImageInfoSavedSuccess();
    void onImageInfoSavedError(String message);
}
