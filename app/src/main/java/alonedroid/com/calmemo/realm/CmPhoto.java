package alonedroid.com.calmemo.realm;


import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class CmPhoto extends RealmObject implements Serializable {

    @Ignore
    public static final String CM_DATE_TIME = "cm_date_time";
    @Ignore
    public static final String CM_DATE = "cm_date";
    @Ignore
    public static final String CM_TIME = "cm_time";
    @Ignore
    public static final String CM_PHOTO = "cm_photo";
    @Ignore
    public static final String CM_ACTION = "cm_action";

    private Date cm_date_time;
    private String cm_date;
    private String cm_time;
    private String cm_photo;
    private String cm_action;

    public Date getCm_date_time() {
        return cm_date_time;
    }

    public void setCm_date_time(Date cm_date_time) {
        this.cm_date_time = cm_date_time;
    }

    public String getCm_date() {
        return cm_date;
    }

    public void setCm_date(String cm_date) {
        this.cm_date = cm_date;
    }

    public String getCm_time() {
        return cm_time;
    }

    public void setCm_time(String cm_time) {
        this.cm_time = cm_time;
    }

    public String getCm_photo() {
        return cm_photo;
    }

    public void setCm_photo(String cm_photo) {
        this.cm_photo = cm_photo;
    }

    public String getCm_action() {
        return cm_action;
    }

    public void setCm_action(String cm_action) {
        this.cm_action = cm_action;
    }
}