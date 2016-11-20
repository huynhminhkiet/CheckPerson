package com.example.minhkiet.checkperson.base;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by minhkiet on 19/11/2016
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutInstance());
        checkPermission();
        initControls();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            finish();
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET}, 101);
        }
    }

    protected abstract int getLayoutInstance();
    protected abstract void initControls();
}
