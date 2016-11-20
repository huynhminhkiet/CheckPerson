package com.example.minhkiet.checkperson.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.interfaces.ScanPersonActivityListener;
import com.example.minhkiet.checkperson.interfaces.ScanPersonFragmentListener;
import com.example.minhkiet.checkperson.base.BaseActivity;
import com.example.minhkiet.checkperson.fragment.ScanPersonFragment;
import com.example.minhkiet.checkperson.utils.AppUtils;
import com.example.minhkiet.checkperson.utils.Constants;
import com.example.minhkiet.checkperson.utils.MyImageUtils;

import java.io.IOException;

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

    @Override
    public void onTakePicture() {
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, MyImageUtils.setNewImageUri());
        startActivityForResult(intent, Constants.TAKE_PICTURE_CAMERA_REQUEST);
    }

    @Override
    public void onPickPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                Constants.PICK_PICTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.TAKE_PICTURE_CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap bitmap = MyImageUtils.decodeFile(MyImageUtils.getImagePathCurrentTake());
            scanPersonActivityListener.onPreviewPicture(bitmap);
        }

        if (requestCode == Constants.PICK_PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri selectedPicture = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedPicture);
                scanPersonActivityListener.onPreviewPicture(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
