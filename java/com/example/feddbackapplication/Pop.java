package com.example.feddbackapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

public class Pop extends Activity {

    TextView tv_reference_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        tv_reference_no = findViewById(R.id.tv_reference_no);

        Intent intent = getIntent();
        String reference_no = intent.getStringExtra(FeedBackActivity.REFERENCE_KEY);
        tv_reference_no.setText(reference_no);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

    }
}
