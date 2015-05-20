package alonedroid.com.calmemo.jackson;

import java.util.ArrayList;
import java.util.List;

public class ManualObject {
    public List<Manual> manual = new ArrayList<>();

    public static class Manual {
        public String title;
        public String description;
    }
}
