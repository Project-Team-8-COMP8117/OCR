package com.project.androidocr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
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
                String email = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();

                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email", email);
                    //Get Password from Text
//                    Toast.makeText(LoginActivity.this, SHA256_Hash.Convert("password"), Toast.LENGTH_SHORT).show();


                    jsonBody.put("password", SHA256_Hash.Convert("password"));
                } catch (JSONException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = jsonBody.toString();

                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                String url ="https://qstphpparbtbjvknnkdm.supabase.co/auth/v1/token?grant_type=password";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject resJson = null;
                                try {
                                    resJson = new JSONObject(response);
                                    String uuid = resJson.getJSONObject("user").getString("id").toString();
//                                    Toast.makeText(LoginActivity.this, uuid, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(LoginActivity.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("UUID", uuid);
                                    editor.commit();
                                    LoginActivity.this.startActivity(myIntent);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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

    public void GoToSignUp(View view) {
        final Intent myIntent = new Intent(LoginActivity.this, SignUpActivity.class);
        LoginActivity.this.startActivity(myIntent);
    }
}