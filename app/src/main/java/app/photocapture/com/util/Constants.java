package app.photocapture.com.util;

import android.text.InputType;

public class Constants {
    public static class Default {
        public static final String DEFAULT_STRING = "";
        public static final Integer DEFAULT_INTEGER = 0;
        public static final Integer INPUT_TYPE_DEFAULT = InputType.TYPE_CLASS_NUMBER;
        public static final Long DEFAULT_LONG = 0L;
        public static final Boolean DEFAULT_BOOLEAN = false;
    }

    public static class IntentKeys{
        public static final String QR_RESULT = "qr-result";
    }

    public static class PreferenceKeys {
        public static final String IS_PREVIEW_IMAGE_ON = "preview-image";
        public static final String REFERENCE_NUMBER_INPUT_TYPE = "reference-number";
    }
}
