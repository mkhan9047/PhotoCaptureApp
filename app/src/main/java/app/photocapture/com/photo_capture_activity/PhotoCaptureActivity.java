package app.photocapture.com.photo_capture_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.photocapture.com.R;
import app.photocapture.com.qr_code_scan.QrCodeScanActivity;
import app.photocapture.com.setting_activity.SettingActivity;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.GlideUtils;
import app.photocapture.com.util.PermissionUtils;
import app.photocapture.com.util.SharedPrefUtils;

public class PhotoCaptureActivity extends AppCompatActivity
        implements View.OnClickListener, PhotoCaptureMvpView {

    PhotoCapturePresenter photoCapturePresenter;
    EditText edtReferenceNumber;
    ImageButton btnSetting;
    AppCompatButton btnViewPhotos, btnExitApp;
    CardView cardViewQrCode,
            cardViewNewFolder,
            cardCaptureImage,
            cardCaptureVideo;
    boolean isGranted = true;
    String pictureImagePath;
    File pickedImageFile = null;
    String videoPath;
    boolean isImageRequest = false;
    File pickedVideoFile = null;
    private final int REQUEST_CAMERA = 1;
    private final int REQUEST_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_capture);
        photoCapturePresenter = new PhotoCapturePresenter(this,
                this);
        initViews();
        setListener();
        askPermission();
        searchForExtra();
        createInitialFolder();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpViewOption();
    }

    private void askPermission() {
        PermissionUtils.INSTANCE.requestPermission(this,
                PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        );
    }

    private void searchForExtra() {
        if (getIntent() != null) {
            String qrResult = getIntent().getStringExtra(
                    Constants.IntentKeys.QR_RESULT
            );
            edtReferenceNumber.setText(qrResult);
        }
    }

    private void startCameraForImage(String s) {
        photoCapturePresenter.checkIfFolderExists(s);
    }

    private void startCameraForVideo(String s) {
        photoCapturePresenter.checkIfFolderExists(s);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                pickedImageFile = new File(pictureImagePath);
                if (pickedImageFile.exists()) {
                    if (SharedPrefUtils.INSTANCE.readPreViewImageStatus(
                            Constants.PreferenceKeys.IS_PREVIEW_IMAGE_ON
                    )) {
                        showImageViewerDialog(pickedImageFile);
                    } else {
                        cardCaptureImage.performClick();
                        Toast.makeText(this, getResources()
                                        .getString(R.string.saved_successfully),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (requestCode == REQUEST_VIDEO) {
                pickedVideoFile = new File(videoPath);
                if (pickedVideoFile.exists()) {
                    if (SharedPrefUtils.INSTANCE.readPreViewImageStatus(
                            Constants.PreferenceKeys.IS_PREVIEW_IMAGE_ON
                    )) {
                        showVideoPreviewDialog(pickedVideoFile);
                    } else {
                        cardCaptureVideo.performClick();
                        Toast.makeText(this, getResources()
                                        .getString(R.string.saved_successfully),
                                Toast.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }

    private void setListener() {
        cardViewNewFolder.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        cardViewQrCode.setOnClickListener(this);
        btnViewPhotos.setOnClickListener(this);
        cardCaptureImage.setOnClickListener(this);
        cardCaptureVideo.setOnClickListener(this);
        btnExitApp.setOnClickListener(this);
    }

    private void initViews() {
        cardCaptureVideo = findViewById(R.id.card_view_capture_video);
        cardCaptureImage = findViewById(R.id.card_view_capture_photo);
        cardViewNewFolder = findViewById(R.id.card_view_new_folder);
        btnViewPhotos = findViewById(R.id.btn_view_photos);
        cardViewQrCode = findViewById(R.id.card_view_qr_code);
        edtReferenceNumber = findViewById(R.id.edit_text_reference);
        btnSetting = findViewById(R.id.btn_setting);
        btnExitApp = findViewById(R.id.btn_exit);
    }

    private void setUpViewOption() {
        edtReferenceNumber.setInputType(SharedPrefUtils.INSTANCE.readInputType(
                Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE
        ));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PermissionUtils.REQUEST_CODE_PERMISSION_LOCATION_AND_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                isGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    public void saveImage(String folderName) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = edtReferenceNumber.getText().toString() + "_" + timeStamp + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator +
                Constants.File.ROOT_FOLDER_NAME + File.separator + folderName);
        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(pictureImagePath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    public void saveVideo(String folderName) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = edtReferenceNumber.getText().toString() + "_" + timeStamp + ".mp4";
        File storageDir = new File(Environment.getExternalStorageDirectory() + File.separator +
                Constants.File.ROOT_FOLDER_NAME + File.separator + folderName);
        videoPath = storageDir.getAbsolutePath() + "/" + imageFileName;
        File file = new File(videoPath);
        Uri outputFileUri = Uri.fromFile(file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        startActivityForResult(cameraIntent, REQUEST_VIDEO);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_view_capture_video:
                if (edtReferenceNumber.getText().toString().length() != 0) {
                    if (isGranted) {
                        isImageRequest = false;
                        startCameraForVideo(edtReferenceNumber.getText().toString());
                    } else {
                        askPermission();
                    }
                } else {
                    Toast.makeText(this, getResources()
                                    .getString(R.string.empty_reference),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.card_view_capture_photo:
                if (edtReferenceNumber.getText().toString().length() != 0) {
                    if (isGranted) {
                        isImageRequest = true;
                        startCameraForImage(edtReferenceNumber.getText().toString());
                    } else {
                        askPermission();
                    }
                } else {
                    Toast.makeText(this, getResources()
                                    .getString(R.string.empty_reference),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_view_photos:
                Toast.makeText(this, "Under construction!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.btn_setting:
                startActivity(
                        new Intent(
                                PhotoCaptureActivity.this,
                                SettingActivity.class
                        )
                );
                break;
            case R.id.card_view_qr_code:
                if (isGranted) {
                    startActivity(
                            new Intent(
                                    PhotoCaptureActivity.this,
                                    QrCodeScanActivity.class
                            )
                    );
                    finish();
                } else {
                    askPermission();
                    Toast.makeText(this,
                            getResources().getString(R.string.required_permission),
                            Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_exit:
                AlertDialog.Builder exitDialog = new AlertDialog.Builder(
                        this
                ).setMessage("Do you wants to exit?")
                        .setTitle("Exit")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                exitDialog.show();
                break;

            case R.id.card_view_new_folder:
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInputFromWindow(
                        edtReferenceNumber.getApplicationWindowToken(),
                        InputMethodManager.SHOW_FORCED, InputMethodManager.SHOW_FORCED);
                edtReferenceNumber.requestFocus();
                edtReferenceNumber.setText("");
                break;
        }
    }

    private void createInitialFolder() {
        photoCapturePresenter.createRootFolder();
    }

    @Override
    public void onFolderFound() {
        if (isImageRequest) {
            saveImage(edtReferenceNumber.getText().toString());
        } else {
            saveVideo(edtReferenceNumber.getText().toString());
        }
    }

    @Override
    public void onFolderNotFound() {
        photoCapturePresenter.createSubFolder(edtReferenceNumber.getText().toString());
    }

    @Override
    public void onFolderCreateSuccess() {
        if (isImageRequest) {
            saveImage(edtReferenceNumber.getText().toString());
        } else {
            saveVideo(edtReferenceNumber.getText().toString());
        }
    }

    @Override
    public void onFolderCreateError() {
        Toast.makeText(this, getResources().
                        getString(R.string.something_went_wrong),
                Toast.LENGTH_SHORT).show();
    }

    private void showImageViewerDialog(final File imageFile) {
        final Dialog dialog = new Dialog(this,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_photo_viewer_dialog);
        ImageView imageViewPickedImage = dialog.findViewById(R.id.image_view_show);
        Button btnDiscard = dialog.findViewById(R.id.btn_discard);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageFile.exists()) {
                    if (imageFile.delete()) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(PhotoCaptureActivity.this,
                                getResources().getString(
                                        R.string.something_went_wrong
                                ),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                cardCaptureImage.performClick();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhotoCaptureActivity.this, getResources()
                                .getString(R.string.saved_successfully),
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                cardCaptureImage.performClick();
            }
        });
        GlideUtils.Companion.normal(
                imageViewPickedImage,
                imageFile
        );
        dialog.show();
    }

    private void showVideoPreviewDialog(final File videoFile) {
        final Dialog dialog = new Dialog(this,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.layout_video_viewer_dialog);
        VideoView videoViewCapturePreview = dialog.findViewById(R.id.video_view_capture);
        Button btnDiscard = dialog.findViewById(R.id.btn_discard);
        Button btnSave = dialog.findViewById(R.id.btn_save);
        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoFile.exists()) {
                    if (videoFile.delete()) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(PhotoCaptureActivity.this,
                                getResources().getString(
                                        R.string.something_went_wrong
                                ),
                                Toast.LENGTH_SHORT).show();
                    }
                    cardCaptureVideo.performClick();
                }

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PhotoCaptureActivity.this, getResources()
                                .getString(R.string.saved_successfully),
                        Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                cardCaptureVideo.performClick();

            }
        });
        videoViewCapturePreview.setVideoURI(Uri.parse(videoFile.getPath()));
        videoViewCapturePreview.setMediaController(new MediaController(this));
        videoViewCapturePreview.requestFocus();
        videoViewCapturePreview.start();
        dialog.show();
    }

}

