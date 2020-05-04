package com.example.feddbackapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FeedBackActivity extends AppCompatActivity {

    MaterialEditText et_pnr_no, et_feedback;
    Spinner sp_feedback_title;
    Button btn_submit, btn_logout;
    TextView tv_no;
    private static String reference_no = null;
    private static String message = null;
    public static final String REFERENCE_KEY = "reference_no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        et_pnr_no = findViewById(R.id.et_pnr_no);
        et_feedback = findViewById(R.id.et_feedback);
        btn_logout = findViewById(R.id.btn_logout);
        btn_submit = findViewById(R.id.btn_submit);
        sp_feedback_title = findViewById(R.id.sp_feedback_title);
        tv_no = findViewById(R.id.tv_no);

        final SharedPreferences sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        final String username = sharedPreferences.getString("username", "username");
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.preLoginState), "Logged Out");
                editor.apply();
                startActivity(new Intent(FeedBackActivity.this, LoginActivity.class));
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pnr_number = Objects.requireNonNull(et_pnr_no.getText()).toString();
                String feedback = Objects.requireNonNull(et_feedback.getText()).toString();
                String feedback_heading = String.valueOf(sp_feedback_title.getSelectedItem());
                if(TextUtils.isEmpty(pnr_number) || TextUtils.isEmpty(feedback_heading) || TextUtils.isEmpty(feedback)){
                    Toast.makeText(getApplicationContext(), "All Fields are Required!", Toast.LENGTH_SHORT).show();
                } else {
                    submitFeedback(username, pnr_number, feedback_heading, feedback);
                }
            }
        });
    }

    private void submitFeedback(final String username, final String pnr_number, final String feedback_heading, final String feedback) {
        final ProgressDialog progressDialog = new ProgressDialog(FeedBackActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Submitting Feedback");
        progressDialog.show();
        String URL = "http://192.168.0.106/feedbackSystem/feedback.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String reference_no = jsonObject.getString("reference_no");
                    String message = jsonObject.getString("message");
                    if (message.equals("Successfully Submitted")) {
                        progressDialog.dismiss();
                        Intent intent = new Intent(FeedBackActivity.this, Pop.class);
                        intent.putExtra(REFERENCE_KEY, reference_no);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Feedback Not Submitted!", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                HashMap <String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("pnr_number", pnr_number);
                param.put("feedback_heading", feedback_heading);
                param.put("feedback", feedback);
                return param;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingelton.getmInstance(FeedBackActivity.this).addToRequestQueue(stringRequest);
    }
}