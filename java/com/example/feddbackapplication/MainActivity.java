package com.example.feddbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_logout = findViewById(R.id.btn_logout);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.preLoginState), "Logged Out");
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    public void fileFeedback(View view){
        startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
    }

    public void seeStatus(View view) {
        startActivity(new Intent(MainActivity.this, StatusActivity.class));
    }
}