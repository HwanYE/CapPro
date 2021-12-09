package com.example.myapplication;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {

    final static private String URL = "http://10.0.2.2/UserInfo.php";
    private Map<String, String> parameters;

    public RegisterRequest(String userID, String userPassword, String S_NAME, String S_LOACTION, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("userPassword", userPassword);
        parameters.put("S_NAME", S_NAME);
        parameters.put("S_LOCATION", S_LOACTION);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
