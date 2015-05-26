package alonedroid.com.calmemo.utility;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import lombok.Cleanup;
import lombok.SneakyThrows;

@EBean
public class AssetsUtility {

    private static final String COPYRIGHTS_PATH = "copyrights";

    @SneakyThrows(Exception.class)
    public static List<String> getCopyrightsFileList(Context context) throws IOException {
        String[] list = context.getAssets().list(COPYRIGHTS_PATH);
        List<String> convertedList = new ArrayList<>();
        for (String path : list) {
            convertedList.add(COPYRIGHTS_PATH + "/" + path);
        }
        return convertedList;
    }

    @SneakyThrows(IOException.class)
    public static String readFile(Context context, String fileName) throws IOException {
        @Cleanup BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                context.getResources().getAssets().open(fileName)));

        String lineString;
        StringBuilder stringBuilder = new StringBuilder();
        while ((lineString = bufferedReader.readLine()) != null) {
            stringBuilder.append(lineString);
            stringBuilder.append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}