package com.example.minhkiet.checkperson.asynctask;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.api.ImageAnalysisApi;
import com.example.minhkiet.checkperson.api.RecognizeCelebritiesApi;
import com.example.minhkiet.checkperson.interfaces.ScanAsynctaskListener;
import com.microsoft.projectoxford.vision.VisionServiceRestClient;
import com.microsoft.projectoxford.vision.contract.Face;
import com.microsoft.projectoxford.vision.rest.VisionServiceException;

import java.io.IOException;
import java.util.List;

/**
 * Created by minhkiet on 19/11/2016
 */

public class ScanAsynctask extends AsyncTask<Void, String, List<Face>> {

    private Fragment fragment;
    private Bitmap bitmap;

    private VisionServiceRestClient mRestClient;

    private ScanAsynctaskListener scanAsynctaskListener;
    private RecognizeCelebirities mRecognizeCelebirities;

    public ScanAsynctask(Fragment fragment, Bitmap bitmap) {
        this.fragment = fragment;
        this.bitmap = bitmap;

        mRestClient = new VisionServiceRestClient(fragment.getActivity().getString(R.string.api_key));
        scanAsynctaskListener = (ScanAsynctaskListener) fragment;
        mRecognizeCelebirities = new RecognizeCelebirities();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        scanAsynctaskListener.onSetResultVisible(false);
        mRecognizeCelebirities.execute();
    }

    @Override
    protected List<Face> doInBackground(Void... voids) {
        String gender = null, age = null, caption = null;
        try {
            ImageAnalysisApi imageAnalysisApi = new ImageAnalysisApi(mRestClient);
            imageAnalysisApi.analyzeImage(bitmap);

            gender = imageAnalysisApi.getGender();
            publishProgress(gender, age, caption);

            age = imageAnalysisApi.getAge() + "";
            publishProgress(gender, age, caption);

            caption = imageAnalysisApi.getCaption();

            publishProgress(gender, age, caption);

            return imageAnalysisApi.getFaces();
        } catch (VisionServiceException | IOException e) {
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
            scanAsynctaskListener.onSetResultCaption(values[2]);
    }

    @Override
    protected void onPostExecute(List<Face> faces) {
        super.onPostExecute(faces);
        scanAsynctaskListener.onSetResultVisible(true);
        if (faces != null && !faces.isEmpty()) {
            Bitmap faceWithRectangle = createBitmapWithRectangleFace(bitmap, faces, 1);
            scanAsynctaskListener.onSetImageWithFace(faceWithRectangle);
        }
    }

    private class RecognizeCelebirities extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            RecognizeCelebritiesApi recognizeCelebritiesApi = new RecognizeCelebritiesApi(mRestClient);
            String name;
            try {
                name = recognizeCelebritiesApi.analyzeImageInDomain(bitmap);
                publishProgress(name);
            } catch (VisionServiceException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            if (values[0] != null)
                scanAsynctaskListener.onSetResultName(values[0]);
        }
    }

    private Bitmap createBitmapWithRectangleFace(Bitmap bitmap, List<Face> faces, double scale) {
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for (Face face : faces) {
            left = (float) (face.faceRectangle.left * scale);
            top = (float) (face.faceRectangle.top * scale);
            right = (float) scale * (face.faceRectangle.left + face.faceRectangle.width);
            bottom = (float) scale * (face.faceRectangle.top + face.faceRectangle.height);
            canvas.drawRect(left, top, right, bottom, paint);
        }
        return mutableBitmap;
    }
}
