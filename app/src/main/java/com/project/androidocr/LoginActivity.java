package com.project.androidocr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent myIntent = new Intent(LoginActivity.this, MainActivity.class);
                // myIntent.putExtra("key", value); //Optional

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email", "1rdggkysioroyulwshd@sdvrecft.com");
                    jsonBody.put("password", "password");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = jsonBody.toString();

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url ="https://qstphpparbtbjvknnkdm.supabase.co/auth/v1/token?grant_type=password";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
//                                LoginActivity.this.startActivity(myIntent);
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.d("TAG", "Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + new String(error.networkResponse.data, StandardCharsets.UTF_8)
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage());
//                        Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                    }

                }) {
                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return mRequestBody.getBytes(StandardCharsets.UTF_8);
                    }
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("apikey", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlIjoic2VydmljZV9yb2xlIiwiaWF0IjoxNjM0MzI0NTk4LCJleHAiOjE5NDk5MDA1OTh9.e-N9TZ5IDQGpYIPIm0N43M5qT_bP6btKj9TKjwa1qOM");
                        params.put("Content-Type", "application/json");
                        return params;

                    }
                };

                queue.add(stringRequest);
            }
        });
    }
}