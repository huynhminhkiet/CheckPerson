package com.example.minhkiet.checkperson.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by minhkiet on 19/11/2016
 */

public abstract class BaseFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutInstance(), null);
        initControls(v);
        return v;
    }

    protected abstract int getLayoutInstance();
    protected abstract void initControls(View v);
}
