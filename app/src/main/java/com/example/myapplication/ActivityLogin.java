package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ActivityLogin extends AppCompatActivity {

    EditText mID, mPassword;
    Button mIdSignInButton, mIdSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the login form.
        mID = (EditText) findViewById(R.id.userid);
        mPassword = (EditText) findViewById(R.id.userpassword);

        // Button
        mIdSignInButton = (Button) findViewById(R.id.loginButton); // sign in button
        mIdSignUpButton = (Button) findViewById(R.id.signupButton); // sign up button


        // 로그인 버튼 클릭
        mIdSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = mID.getText().toString();
                String userPassword = mPassword.getText().toString();

                Response.Listener<String> responseListener = response -> {
                    try{
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if(success){
                            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                            String userID1 = jsonResponse.getString("userID");
                            String userPassword1 = jsonResponse.getString("userPassword");
                            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                            // 로그인 하면서 사용자 정보 넘기기
                            //intent.putExtra("userID", userID);
                            //intent.putExtra("userPassword", userPassword);
                            UserConnection.setUserId(userID1);
                            startActivity(intent);

                        } else {
                            Toast.makeText(getApplicationContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch(Exception e){
                        e.printStackTrace();
                    }
                };

                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ActivityLogin.this);
                queue.add(loginRequest);

            }
        });

        // 회원가입 버튼 클릭
        mIdSignUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_signup.class);
                startActivity(intent);
            }
        });

    }
}