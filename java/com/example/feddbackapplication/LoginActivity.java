package com.example.feddbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    MaterialEditText et_username, et_password;
    Button btn_login;
    SharedPreferences sharedPreferences;
    CheckBox loginState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        loginState = findViewById(R.id.cb_loginstate);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
                    et_password.setError("This Field cannot be Empty!");
                    et_password.requestFocus();
                    et_username.setError("This Field cannot be Empty!");
                    et_username.requestFocus();
                    return;
                } else {
                    login(username, password);
                }
            }
        });

        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.preLoginState), "");
        if(loginStatus.equals("Logged In")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

    public void register(View view){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private void login (final String username, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Logging In");
        progressDialog.show();
        String URL = "http://192.168.0.106/feedbackSystem/login.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Login Successful")){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if(loginState.isChecked()){
                        editor.putString(getResources().getString(R.string.preLoginState), "Logged In");
                        editor.putString("username", username);
                    } else {
                        editor.putString(getResources().getString(R.string.preLoginState), "Logged Out");
                    }
                    editor.apply();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("password", password);
                return param;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingelton.getmInstance(LoginActivity.this).addToRequestQueue(stringRequest);
    }
}