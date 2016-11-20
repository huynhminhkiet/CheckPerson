//package com.example.minhkiet.checkperson;
//
//import android.content.Intent;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.minhkiet.checkperson.activity.ScanPersonActivity;
//import com.example.minhkiet.checkperson.base.BaseActivity;
//
//public class MainActivity extends BaseActivity {
//
//    private Button btnStart;
//
//    @Override
//    protected int getLayoutInstance() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void initControls() {
//        btnStart = (Button) findViewById(R.id.btn_start);
//        btnStart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = ScanPersonActivity.newIntent(getApplicationContext());
//                startActivity(intent);
//            }
//        });
//    }
//}
