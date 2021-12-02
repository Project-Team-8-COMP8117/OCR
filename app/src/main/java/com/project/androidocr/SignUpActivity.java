package com.project.androidocr;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class SignUpActivity extends AppCompatActivity {
    Button signUp_btn;
    ProgressBar progressBar;
    EditText m_password;
    EditText m_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUp_btn = findViewById(R.id.signUp_btn);
        progressBar = findViewById(R.id.loading);
        m_password = findViewById(R.id.password);
        m_password2 = findViewById(R.id.password2);


        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignUpActivity.this, "123", Toast.LENGTH_SHORT).show();

//                String name = findViewById(R.id.name).toString();
                String email = ((EditText)findViewById(R.id.username)).getText().toString();
                String password = ((EditText)findViewById(R.id.password)).getText().toString();
                String confirmPassword = ((EditText)findViewById(R.id.password2)).getText().toString();

//                Toast.makeText(SignUpActivity.this, email, Toast.LENGTH_SHORT).show();
//                Toast.makeText(SignUpActivity.this, password, Toast.LENGTH_SHORT).show();
//                Toast.makeText(SignUpActivity.this, confirmPassword, Toast.LENGTH_SHORT).show();

                if(password.equals(confirmPassword) == false) {
                    m_password.setError("Password does not match");
                    return;
                }

                if(password.length() < 6) {
                    m_password.setError("Password must be greater than 6 characters");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                JSONObject jsonBody = new JSONObject();
                try {
                    jsonBody.put("email", email);
                    //Get Password from Text
//                    Toast.makeText(SignUpActivity.this, SHA256_Hash.Convert(password), Toast.LENGTH_SHORT).show();


                    jsonBody.put("password", SHA256_Hash.Convert(password));
                } catch (JSONException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                final String mRequestBody = jsonBody.toString();

                RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
                String url ="https://qstphpparbtbjvknnkdm.supabase.co/auth/v1/signup";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject resJson = null;
                                try {
                                    resJson = new JSONObject(response);
//                                    String uuid = resJson.getJSONObject("user").getString("id").toString();
//                                    Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();

//                                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//                                    SharedPreferences.Editor editor = sharedPref.edit();
//                                    editor.putString("UUID", uuid);
//                                    editor.commit();
                                    final Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
                                    SignUpActivity.this.startActivity(myIntent);
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
//                        params.put("Content-Type", "application/json");
                        return params;

                    }
                };

                queue.add(stringRequest);

            }
        });
    }

    public void GoToLogin(View view) {
        final Intent myIntent = new Intent(SignUpActivity.this, LoginActivity.class);
        SignUpActivity.this.startActivity(myIntent);
    }
}