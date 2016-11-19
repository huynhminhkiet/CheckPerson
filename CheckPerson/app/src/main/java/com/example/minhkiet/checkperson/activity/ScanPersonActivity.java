package com.example.minhkiet.checkperson.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.interfaces.ScanPersonActivityListener;
import com.example.minhkiet.checkperson.interfaces.ScanPersonFragmentListener;
import com.example.minhkiet.checkperson.base.BaseActivity;
import com.example.minhkiet.checkperson.fragment.ScanPersonFragment;
import com.example.minhkiet.checkperson.utils.AppUtils;
import com.example.minhkiet.checkperson.utils.Constants;
import com.example.minhkiet.checkperson.utils.MyImageUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScanPersonActivity extends BaseActivity implements ScanPersonFragmentListener {

    private ScanPersonActivityListener scanPersonActivityListener;


    @Override
    protected int getLayoutInstance() {
        return R.layout.activity_scan_person;
    }

    @Override
    protected void initControls() {
        ScanPersonFragment scanPersonFragment = ScanPersonFragment.newInstance();
        scanPersonFragment.setScanPersonListener(this);
        scanPersonActivityListener = scanPersonFragment;

        AppUtils.addNewFragment(this, R.id.container, scanPersonFragment);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ScanPersonActivity.class);
        return intent;
    }

    @Override
    public void onTakePicture() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, MyImageUtils.setImageUri());
        startActivityForResult(intent, Constants.PICK_PICTURE_CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.PICK_PICTURE_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap =  MyImageUtils.decodeFile(MyImageUtils.getImagePath());
            scanPersonActivityListener.onPreviewPicture(bitmap);
        }
    }



}
