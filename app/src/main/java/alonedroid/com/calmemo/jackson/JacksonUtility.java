package alonedroid.com.calmemo.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import alonedroid.com.calmemo.CmApplication;
import alonedroid.com.calmemo.R;

public class JacksonUtility {

    public static Object getInstance(Class cls) throws IOException {

        ObjectMapper map = new ObjectMapper();
        if (cls == ManualObject.class) {
            return map.readValue(CmApplication.openRawResource(R.raw.manual), cls);
        }
        return null;
    }
}