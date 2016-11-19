package com.example.minhkiet.checkperson.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by minhkiet on 19/11/2016
 */

public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutInstance());
        initControls();
    }

    protected abstract int getLayoutInstance();
    protected abstract void initControls();
}
