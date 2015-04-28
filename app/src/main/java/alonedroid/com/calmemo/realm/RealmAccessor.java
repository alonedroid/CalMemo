package alonedroid.com.calmemo.realm;

import org.androidannotations.annotations.App;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringRes;

import java.util.List;

import alonedroid.com.calmemo.CmApplication;
import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class RealmAccessor {

    @App
    CmApplication app;

    @StringRes
    String realmInstance;

    Realm realm;

    public List<CmPhoto> getPhotosByDate(String date) {
        this.realm = Realm.getInstance(this.app, this.realmInstance);

        RealmResults<CmPhoto> result = realm
                .where(CmPhoto.class)
                .equalTo(CmPhoto.CM_DATE, date)
                .notEqualTo(CmPhoto.CM_PHOTO, "")
                .findAll();

        if (result.size() > 0) {
            result.sort(CmPhoto.CM_DATE_TIME);
            return result;
        }

        return null;
    }

    public List<CmPhoto> getPhotosByDate(String yyyy, String mm) {
        this.realm = Realm.getInstance(this.app, this.realmInstance);

        RealmResults<CmPhoto> result = realm
                .where(CmPhoto.class)
                .beginsWith(CmPhoto.CM_DATE, yyyy + mm)
                .notEqualTo(CmPhoto.CM_PHOTO, "")
                .findAll();

        if (result.size() > 0) {
            return result.subList(0, result.size());
        }

        return null;
    }

    public void deleteCmPhoto(CmPhoto photo){
        this.realm.beginTransaction();
        photo.removeFromRealm();
        this.realm.commitTransaction();
    }

    public void close() {
        this.realm.close();
    }
}
