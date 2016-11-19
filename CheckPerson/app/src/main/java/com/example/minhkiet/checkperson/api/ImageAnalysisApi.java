package com.example.minhkiet.checkperson.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by minhkiet on 20/11/2016
 */

public class ImageAnalysisApi {
    private VisionServiceRestClient mRestClient;
    private String caption, gender;
    int age;

    public ImageAnalysisApi(VisionServiceRestClient restClient) {
        mRestClient = restClient;
        caption = "";
        gender = "Unknown";
        age = 0;
    }

    public void analyzeImage(Bitmap bitmap) throws VisionServiceException, IOException {
        String[] features = {"categories", "faces", "description"};
        String[] detail = {};
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        AnalysisResult analysisResult = mRestClient.analyzeImage(inputStream, features, detail);
        caption = analysisResult.description.captions.get(0).text;
        if (!analysisResult.faces.isEmpty()) {
            gender = analysisResult.faces.get(0).gender.toString();
            age = analysisResult.faces.get(0).age;
        }
    }

    public String getCaption() {
        return caption;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
}
