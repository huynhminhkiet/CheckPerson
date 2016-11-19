package com.example.minhkiet.checkperson.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.api.ImageAnalysisApi;
import com.example.minhkiet.checkperson.api.RecognizeCelebritiesApi;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import java.io.IOException;

/**
 * Created by minhkiet on 19/11/2016
 */

public class ScanAsynctask extends AsyncTask<Void, String, Void> {

    private Activity activity;
    private Bitmap bitmap;

    private VisionServiceRestClient mRestClient;

    public ScanAsynctask(Activity activity, Bitmap bitmap) {
        this.activity = activity;
        this.bitmap = bitmap;
        mRestClient = new VisionServiceRestClient(activity.getString(R.string.api_key));
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... voids) {
//        ImageAnalysisApi imageAnalysisApi = new ImageAnalysisApi(mRestClient);
        RecognizeCelebritiesApi recognizeCelebritiesApi = new RecognizeCelebritiesApi(mRestClient);
        try {
//            imageAnalysisApi.analyzeImage(bitmap);
            Log.d(getClass().getSimpleName(), recognizeCelebritiesApi.analyzeImageInDomain(bitmap));
        } catch (VisionServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



}
