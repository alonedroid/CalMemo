package alonedroid.com.calmemo.utility;

import org.androidannotations.annotations.EBean;

@EBean
public class StringUtility {

    public String format00(int base) {
        return format00(String.valueOf(base));
    }

    public String format00(String base) {
        return String.format("%2s", base).replace(" ", "0");
    }
}