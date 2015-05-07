package alonedroid.com.calmemo.realm;


import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

public class CmPhoto extends RealmObject implements Serializable {

    @Ignore
    public static final String CM_DATE_TIME = "cmDateTime";
    @Ignore
    public static final String CM_DATE = "cmDate";
    @Ignore
    public static final String CM_TIME = "cmTime";
    @Ignore
    public static final String CM_PHOTO = "cmPhoto";
    @Ignore
    public static final String CM_ACTION = "cmAction";
    @Ignore
    public static final String CM_DATE_FORMAT = "yyyyMMdd";
    @Ignore
    public static final String CM_TIME_FORMAT = "HHmmss";

    private Date cmDateTime;
    private String cmDate;
    private String cmTime;
    private String cmPhoto;
    private String cmAction;

    public Date getCmDateTime() {
        return cmDateTime;
    }

    public void setCmDateTime(Date cmDateTime) {
        this.cmDateTime = cmDateTime;
    }

    public String getCmDate() {
        return cmDate;
    }

    public void setCmDate(String cmDate) {
        this.cmDate = cmDate;
    }

    public String getCmTime() {
        return cmTime;
    }

    public void setCmTime(String cmTime) {
        this.cmTime = cmTime;
    }

    public String getCmPhoto() {
        return cmPhoto;
    }

    public void setCmPhoto(String cmPhoto) {
        this.cmPhoto = cmPhoto;
    }

    public String getCmAction() {
        return cmAction;
    }

    public void setCmAction(String cmAction) {
        this.cmAction = cmAction;
    }
}