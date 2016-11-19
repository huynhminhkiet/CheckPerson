package com.example.minhkiet.checkperson.api;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.Gson;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.AnalysisInDomainResult;
import com.microsoft.projectoxford.vision.contract.AnalysisResult;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by minhkiet on 20/11/2016
 */

public class RecognizeCelebritiesApi {
    private VisionServiceRestClient mRestClient;

    public RecognizeCelebritiesApi(VisionServiceRestClient restClient) {
        mRestClient = restClient;
    }

    public String analyzeImageInDomain(Bitmap bitmap) throws VisionServiceException, IOException {
        String model = "celebrities";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        AnalysisInDomainResult analysisResult = mRestClient.analyzeImageInDomain(inputStream, model);
//        String result = mGson.toJson(analysisResult);
//        Log.d(TAG, "processVisionService: " + result);
        if (analysisResult.result.get("celebrities").getAsJsonArray().size() > 0)
           return analysisResult.result.get("celebrities").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
        return "Unknow";
    }
}
