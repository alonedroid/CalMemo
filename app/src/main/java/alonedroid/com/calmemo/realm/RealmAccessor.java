package alonedroid.com.calmemo.realm;

import org.androidannotations.annotations.EBean;

import java.util.List;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;
import hugo.weaving.DebugLog;
import io.realm.Realm;
import io.realm.RealmResults;

@EBean
public class RealmAccessor {

    public List<CmPhoto> getPhotosByDate(String date) {
        Realm realm = Realm.getInstance(CmApplication.getContext(), CmApplication.getResourceString(R.string.realm_instance));

        RealmResults<CmPhoto> result = realm.where(CmPhoto.class)
                .equalTo(CmPhoto.CM_DATE, date)
                .notEqualTo(CmPhoto.CM_PHOTO, "")
                .findAll();

        if (result.size() > 0) {
            RealmResults<CmPhoto> sortedAscending = result.sort(CmPhoto.CM_DATE_TIME);
            return sortedAscending;
        } else {
            return null;
        }

    }

}
