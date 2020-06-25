package app.photocapture.com.database_helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PhotoLibrarianDatabaseHelper extends SQLiteOpenHelper {

    private static final String databaseName = "PhotoLibrarian";
    private static final int databaseVersion = 1;

    public PhotoLibrarianDatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String image_info = "CREATE TABLE image_info (\n" +

                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +

                "file_name TEXT, \n" +

                "folder_name TEXT,\n" +

                "date_time TEXT, \n" +

                "latitude TEXT, \n" +

                "longitude TEXT, \n" +

                "device_id TEXT \n" +

                ");";

        db.execSQL(image_info);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
