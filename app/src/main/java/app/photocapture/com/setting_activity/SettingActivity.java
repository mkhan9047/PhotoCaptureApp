package app.photocapture.com.setting_activity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import app.photocapture.com.R;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.SharedPrefUtils;

public class SettingActivity extends AppCompatActivity implements
        Switch.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    RadioGroup inputTypeRadioGroup;
    Switch aSwitchPreviewImage;
    CheckBox customImageQualityChecker;
    Spinner imageQualitySpinner;
    ConstraintLayout layoutImageQualityPercentage;
    SeekBar seekBarQuality;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        setUpToolbar();
        setListener();
        customImageQualityChecker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layoutImageQualityPercentage.setVisibility(View.VISIBLE);
                    imageQualitySpinner.setEnabled(false);
                    SharedPrefUtils.INSTANCE.write(Constants.PreferenceKeys.IMAGE_QUALITY_TYPE_IS_CUSTOM,
                            true);
                } else {
                    SharedPrefUtils.INSTANCE.write(Constants.PreferenceKeys.IMAGE_QUALITY_TYPE_IS_CUSTOM,
                            false);
                    layoutImageQualityPercentage.setVisibility(View.GONE);
                    imageQualitySpinner.setEnabled(true);
                }
            }
        });
    }

    private void setUpToolbar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.txt_setting));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }
    }

    private void setListener() {
        inputTypeRadioGroup.setOnCheckedChangeListener(this);
        aSwitchPreviewImage.setOnCheckedChangeListener(this);
    }


    private void initViews() {
        customImageQualityChecker = findViewById(R.id.checkbox_custom);
        imageQualitySpinner = findViewById(R.id.spinner_quality);
        layoutImageQualityPercentage = findViewById(R.id.layout_custom_image_quality);
        inputTypeRadioGroup = findViewById(R.id.radio_group);
        aSwitchPreviewImage = findViewById(R.id.switch_image_preview);
        seekBarQuality = findViewById(R.id.seek_bar_image_quality);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,
                                 boolean isChecked) {
        setPreViewImageState(isChecked);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSavedInputType();
        showSavedPreviewOption();
    }

    private void setPreViewImageState(boolean state) {
        SharedPrefUtils.INSTANCE.writePreviewImageStatus(
                Constants.PreferenceKeys.IS_PREVIEW_IMAGE_ON,
                state
        );
    }


    private void showSavedInputType() {
        if (SharedPrefUtils.INSTANCE.readInputType(
                Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE
        ) == InputType.TYPE_CLASS_NUMBER) {
            inputTypeRadioGroup.check(R.id.radio_numric);
        } else {
            inputTypeRadioGroup.check(R.id.radio_alpha_numric);
        }
    }

    private void showSavedPreviewOption() {
        aSwitchPreviewImage.setChecked(
                SharedPrefUtils.INSTANCE.readPreViewImageStatus(
                        Constants.PreferenceKeys.IS_PREVIEW_IMAGE_ON
                ));
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        inputTypeMethod(checkedId);
    }

    private void inputTypeMethod(int checkId) {
        switch (checkId) {
            case R.id.radio_numric:
                SharedPrefUtils.INSTANCE.writeInputType(
                        Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE,
                        InputType.TYPE_CLASS_NUMBER
                );
                break;
            case R.id.radio_alpha_numric:
                SharedPrefUtils.INSTANCE.writeInputType(
                        Constants.PreferenceKeys.REFERENCE_NUMBER_INPUT_TYPE,
                        InputType.TYPE_CLASS_TEXT
                );
                break;
        }
    }
}
