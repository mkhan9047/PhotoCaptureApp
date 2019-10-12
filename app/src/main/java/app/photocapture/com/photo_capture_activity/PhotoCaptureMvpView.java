package app.photocapture.com.photo_capture_activity;

interface PhotoCaptureMvpView {
    void onImageSaveSuccess(String message);
    void onImageSaveError(String message);
    void onVideoSaveSuccess(String message);
    void onVideoSaveError(String message);
    void onFolderFound();
    void onFolderNotFound();
    void onFolderCreateSuccess();
    void onFolderCreateError();
}
