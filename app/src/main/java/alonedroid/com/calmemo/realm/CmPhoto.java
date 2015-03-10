package alonedroid.com.calmemo.realm;


import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class CmPhoto extends RealmObject {
    @Ignore
    public static final String DATE = "date";
    @Ignore
    public static final String TIME = "time";
    @Ignore
    public static final String PHOTO = "photo";

    private String date;
    private String time;
    private String photo;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}