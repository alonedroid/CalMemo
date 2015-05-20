package alonedroid.com.calmemo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

public class JacksonUtility {

    public static Object getInstance(Class cls) {
        try {
            ObjectMapper map = new ObjectMapper();
            if (cls == ManualObject.class) {
                return map.readValue(CmApplication.openRawResource(R.raw.manual), cls);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}