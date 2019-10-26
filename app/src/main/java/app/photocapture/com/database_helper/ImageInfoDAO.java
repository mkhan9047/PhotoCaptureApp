package app.photocapture.com.database_helper;

public class ImageInfoDAO {
    private String fileName;
    private String folderName;
    private String dateTime;
    private String latitude;
    private String longitude;
    private String userId;

    public ImageInfoDAO(String fileName, String folderName,
                        String dateTime, String latitude,
                        String longitude,
                        String userId) {
        this.fileName = fileName;
        this.folderName = folderName;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFolderName() {
        return folderName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getUserId() {
        return userId;
    }
}
