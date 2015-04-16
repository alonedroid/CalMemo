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

    public List<CmPhoto> getPhotosByDate(String date) {
        Realm realm = Realm.getInstance(this.app, this.realmInstance);

        try {
            RealmResults<CmPhoto> result = realm
                    .where(CmPhoto.class)
                    .equalTo(CmPhoto.CM_DATE, date)
                    .notEqualTo(CmPhoto.CM_PHOTO, "")
                    .findAll();

            if (result.size() > 0) {
                result.sort(CmPhoto.CM_DATE_TIME);
                return result;
            }
        } finally {
            realm.close();
        }

        return null;
    }
}
