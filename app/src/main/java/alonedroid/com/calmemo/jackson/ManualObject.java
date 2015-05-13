package alonedroid.com.calmemo.jackson;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManualObject {
    public List<Manual> manual = new ArrayList<Manual>();

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Manual {
        public String title;
        public String description;
    }
}
