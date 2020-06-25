package app.photocapture.com.database_helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DatabaseOperation {
    public static void saveImageInfo(
            Context context,
            ImageInfoDAO imageInfo)
            throws SQLiteException {
        ContentValues contentValues = new ContentValues();
        contentValues.put("file_name", imageInfo.getFileName());
        contentValues.put("folder_name", imageInfo.getFolderName());
        contentValues.put("date_time", imageInfo.getDateTime());
        contentValues.put("latitude", imageInfo.getLatitude());
        contentValues.put("longitude", imageInfo.getLongitude());
        contentValues.put("device_id", imageInfo.getUserId());
        SQLiteOpenHelper databaseHelper = new PhotoLibrarianDatabaseHelper(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insertOrThrow("image_info", null, contentValues);
    }
}
