package com.example.thinw;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private String LINK = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        AndroidNetworking.get(LINK)
                .addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                    }
                    @Override
                    public void onError(ANError error) {

                    }
                });
    }
}