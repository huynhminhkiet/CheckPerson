package com.example.minhkiet.checkperson.interfaces;

import android.graphics.Bitmap;

/**
 * Created by minhkiet on 20/11/2016
 */

public interface ScanAsynctaskListener {
    void onSetResultVisible(boolean b);

    void onSetResultName(String name);

    void onSetResultGender(String gender);

    void onSetResultAge(String age);

    void onSetResultCaption(String caption);

    void onSetImageWithFace(Bitmap bitmap);
}
