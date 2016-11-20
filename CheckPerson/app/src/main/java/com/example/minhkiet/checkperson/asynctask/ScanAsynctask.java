package com.example.minhkiet.checkperson.asynctask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.api.ImageAnalysisApi;
import com.example.minhkiet.checkperson.api.RecognizeCelebritiesApi;
import com.example.minhkiet.checkperson.interfaces.ScanAsynctaskListener;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;
import java.io.IOException;

/**
 * Created by minhkiet on 19/11/2016
 */

public class ScanAsynctask extends AsyncTask<Void, String, Void> {

    private Fragment fragment;
    private Bitmap bitmap;

    private VisionServiceRestClient mRestClient;

    private ScanAsynctaskListener scanAsynctaskListener;

    public ScanAsynctask(Fragment fragment, Bitmap bitmap) {
        this.fragment = fragment;
        this.bitmap = bitmap;

        mRestClient = new VisionServiceRestClient(fragment.getActivity().getString(R.string.api_key));
        scanAsynctaskListener = (ScanAsynctaskListener) fragment;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        scanAsynctaskListener.onSetResultVisible(false);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String gender = null, age = null, name = null, caption = null;
        try {
            ImageAnalysisApi imageAnalysisApi = new ImageAnalysisApi(mRestClient);
            imageAnalysisApi.analyzeImage(bitmap);

            gender = imageAnalysisApi.getGender();
            publishProgress(gender, age, name, caption);

            age = imageAnalysisApi.getAge() + "";
            publishProgress(gender, age, name, caption);

            RecognizeCelebritiesApi recognizeCelebritiesApi = new RecognizeCelebritiesApi(mRestClient);
            name = recognizeCelebritiesApi.analyzeImageInDomain(bitmap);

            caption = imageAnalysisApi.getCaption();

            publishProgress(gender, age, name, caption);

        } catch (VisionServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        if (values[0] != null)
            scanAsynctaskListener.onSetResultGender(values[0]);
        if (values[1] != null)
            scanAsynctaskListener.onSetResultAge(values[1]);
        if (values[2] != null)
            scanAsynctaskListener.onSetResultName(values[2]);
        if (values[3] != null)
            scanAsynctaskListener.onSetResultCaption(values[3]);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        scanAsynctaskListener.onSetResultVisible(true);
    }
}
