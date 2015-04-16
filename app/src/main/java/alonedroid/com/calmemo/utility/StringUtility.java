package alonedroid.com.calmemo.utility;

import org.androidannotations.annotations.EBean;

import hugo.weaving.DebugLog;

@EBean
public class StringUtility {

    public String format00(String base) {
        return String.format("%2s", base).replace(" ", "0");
    }
}