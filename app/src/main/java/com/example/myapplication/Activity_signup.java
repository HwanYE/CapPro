package com.example.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Activity_signup extends Activity {
    private static String IP_ADDRESS = "10.0.2.2";
    private static String TAG = "REGISTER";

    private EditText mEditTextID;
    private EditText mEditTextPASSWORD;
    private EditText mEditTextSNAME;
    private EditText mEditTextSLOCATION;
    private TextView mTextViewResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup);

        mEditTextID = (EditText)findViewById(R.id.editText_main_id);
        mEditTextPASSWORD = (EditText)findViewById(R.id.editText_main_pw);
        mEditTextSNAME = (EditText)findViewById(R.id.editText_main_sname);
        mEditTextSLOCATION = (EditText)findViewById(R.id.editText_main_slocation);

        mTextViewResult = (TextView)findViewById(R.id.textView_main_result);

        mTextViewResult.setMovementMethod(new ScrollingMovementMethod());


        Button buttonInsert = (Button)findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = mEditTextID.getText().toString();
                String userPassword = mEditTextPASSWORD.getText().toString();
                String S_NAME = mEditTextSNAME.getText().toString();
                String S_LOCATION = mEditTextSLOCATION.getText().toString();

                InsertData task = new InsertData();
                task.execute("http://" + IP_ADDRESS + "/Register.php", userID,userPassword,S_NAME,S_LOCATION);


                //mEditTextID.setText("");
                //mEditTextPASSWORD.setText("");

            }
        });

    }



    class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Activity_signup.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "POST response  - " + result);
        }


        @Override
        protected String doInBackground(String... params) {

            String userID = (String)params[1];
            String userPassword = (String)params[2];
            String S_NAME = (String)params[3];
            String S_LOCATION = (String)params[4];

            String serverURL = (String)params[0];
            String postParameters = "userID=" + userID + "&userPassword=" + userPassword
                    + "&S_NAME=" + S_NAME + "&S_LOCATION=" + S_LOCATION;


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "POST response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);

                return new String("Error: " + e.getMessage());
            }

        }
    }


}