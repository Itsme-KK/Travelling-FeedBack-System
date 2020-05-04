package com.example.feddbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class StatusActivity extends AppCompatActivity {

    Button btn_logout, btn_check;
    MaterialEditText et_ref_no;
    TextView tv_status;
    private static final String status1 = "Action will be taken soon";
    private static final String status2 = "Your Request is under process";
    private static final String status3 = "Action has been taken against your feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        et_ref_no = findViewById(R.id.et_ref_no);
        btn_logout = findViewById(R.id.btn_logout);
        btn_check = findViewById(R.id.btn_submit);
        tv_status = findViewById(R.id.tv_status);
        tv_status.setText("");
        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.preLoginState), "Logged Out");
                editor.apply();
                startActivity(new Intent(StatusActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reference_number  = et_ref_no.getText().toString();
                if(TextUtils.isEmpty(reference_number)){
                    Toast.makeText(getApplicationContext(), "All Fields Are Required", Toast.LENGTH_SHORT).show();
                } else {
                    check_status(reference_number);
                }
            }
        });
    }

    private void check_status(final String reference_number) {
        final ProgressDialog progressDialog = new ProgressDialog(StatusActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Submitting Feedback");
        progressDialog.show();
        String URL = "http://192.168.0.106/feedbackSystem/checkStatus.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("1")) {
                    progressDialog.dismiss();
                    tv_status.setText(status1);
                } else if(response.equals("2")){
                    progressDialog.dismiss();
                    tv_status.setText(status2);
                } else if(response.equals("3")){
                    progressDialog.dismiss();
                    tv_status.setText(status3);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(StatusActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap <String, String> param = new HashMap<>();
                param.put("reference_number",reference_number);
                return param;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingelton.getmInstance(StatusActivity.this).addToRequestQueue(stringRequest);
    }
}
