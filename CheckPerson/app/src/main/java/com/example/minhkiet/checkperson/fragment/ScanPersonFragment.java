package com.example.minhkiet.checkperson.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhkiet.checkperson.R;
import com.example.minhkiet.checkperson.asynctask.ScanAsynctask;
import com.example.minhkiet.checkperson.interfaces.ScanPersonActivityListener;
import com.example.minhkiet.checkperson.interfaces.ScanPersonFragmentListener;
import com.example.minhkiet.checkperson.base.BaseFragment;

/**
 * Created by minhkiet on 19/11/2016
 */

public class ScanPersonFragment extends BaseFragment implements ScanPersonActivityListener {

    private Button btnTakePicture, btnChoosePicture;

    private TextView tvPerson;

    private ProgressBar progressBar;

    private ImageView ivPreview;

    private ScanPersonFragmentListener scanPersonFragmentListener;

    public ScanPersonFragment() {}

    public static ScanPersonFragment newInstance() {
        ScanPersonFragment scanPersonFragment = new ScanPersonFragment();
        return scanPersonFragment;
    }

    @Override
    protected int getLayoutInstance() {
        return R.layout.fragment_scan_person;
    }

    @Override
    protected void initControls(View v) {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "abc.ttf");

        btnTakePicture = (Button) v.findViewById(R.id.btn_take_picture);
        btnTakePicture.setTypeface(font);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scanPersonFragmentListener.onTakePicture();
            }
        });

        btnChoosePicture = (Button) v.findViewById(R.id.btn_choose_picture);
        btnChoosePicture.setTypeface(font);

        tvPerson = (TextView) v.findViewById(R.id.tv_person);
        tvPerson.setTypeface(font);
        tvPerson.setVisibility(View.VISIBLE);

        ivPreview = (ImageView) v.findViewById(R.id.iv_preview);
        ivPreview.setVisibility(View.INVISIBLE);

        progressBar = (ProgressBar) v.findViewById(R.id.pr_scanning);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#219187"),
                PorterDuff.Mode.MULTIPLY);
    }

    public void setScanPersonListener(ScanPersonFragmentListener listener) {
        scanPersonFragmentListener = listener;
    }

    @Override
    public void onPreviewPicture(Bitmap bitmap) {
        if (bitmap != null) {
            tvPerson.setVisibility(View.INVISIBLE);
            ivPreview.setVisibility(View.VISIBLE);
            ivPreview.setImageBitmap(bitmap);
            ScanAsynctask imageDescribeAsynctask = new ScanAsynctask(getActivity(), bitmap);
            imageDescribeAsynctask.execute();
        } else {
            Toast.makeText(getActivity(), "Bitmap null!", Toast.LENGTH_SHORT).show();
        }
    }
}
