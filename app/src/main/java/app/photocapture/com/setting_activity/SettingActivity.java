package app.photocapture.com.setting_activity;

import android.os.Bundle;
import android.text.InputType;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import app.photocapture.com.R;
import app.photocapture.com.util.Constants;
import app.photocapture.com.util.SharedPrefUtils;

public class SettingActivity extends AppCompatActivity implements
        Switch.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener {

    RadioGroup inputTypeRadioGroup;
    Switch aSwitchPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        setUpToolbar();
        setListener();
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
        inputTypeRadioGroup = findViewById(R.id.radio_group);
        aSwitchPreviewImage = findViewById(R.id.switch_image_preview);
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
