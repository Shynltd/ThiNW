package com.example.thinw;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String LINK = "https://jsonplaceholder.typicode.com/comments";
    private TextView tvSearch;
    private RecyclerView rvList;
    private MyAdapter myAdapter;
    private List<Example> exampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        exampleList = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this,exampleList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(myAdapter);
        getEmail();

    }

    private void getEmail() {
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        AndroidNetworking.get(LINK)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                Integer postId = response.getJSONObject(i).getInt("postId");
                                Integer id = response.getJSONObject(i).getInt("id");
                                String name = response.getJSONObject(i).getString("name");
                                String email = response.getJSONObject(i).getString("email");
                                String body = response.getJSONObject(i).getString("body");
                                exampleList.add(new Example(postId,id,name,email,body));
                                myAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            Log.e("onResponse: ", e.getMessage() );
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error: ", error.getMessage() );
                    }
                });
    }

    private void initView() {
        tvSearch = (TextView) findViewById(R.id.tvSearch);
        rvList = (RecyclerView) findViewById(R.id.rvList);
    }
}