package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class Activity_signup extends Activity {

    private Button mSubmitButton;

    private EditText mName, mID, mPassword, mBirth;

    private static String userName, userId, userPassword, userBirth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // 값 가져오기
        mName = (EditText) findViewById(R.id.name);
        mID = (EditText) findViewById(R.id.ID);
        mPassword = (EditText) findViewById(R.id.password);
        mBirth = (EditText) findViewById(R.id.birth);
        mSubmitButton = (Button) findViewById(R.id.registerButton);


        // 회원 가입 버튼이 눌렸을때
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 현재 입력된 정보를 string으로 가져오기
                userName = mName.getText().toString();
                userId = mID.getText().toString();
                userPassword = mPassword.getText().toString();
                userBirth = mBirth.getText().toString();

                // 회원가입 절차 시작
                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        try{
                            // String으로 그냥 못 보냄으로 JSON Object 형태로 변형하여 전송
                            // 서버 통신하여 회원가입 성공 여부를 jsonResponse로 받음
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){ // 회원가입이 가능한다면
                                Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Activity_signup.this, ActivityLogin.class );
                                startActivity(intent);
                                finish();//액티비티를 종료시킴(회원등록 창을 닫음)

                            }else{// 회원가입이 안된다면
                                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다. 다시 한 번 확인해 주세요.", Toast.LENGTH_SHORT).show();
                                return;
                            }

                        }
                        catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                };

                // Volley 라이브러리를 이용해서 실제 서버와 통신을 구현하는 부분
                RegisterRequest registerRequest = new RegisterRequest(userId, userPassword, userName, userBirth, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Activity_signup.this);
                queue.add(registerRequest);
            }
        });
    }
}