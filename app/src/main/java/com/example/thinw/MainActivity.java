package com.example.thinw;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private EditText edtSearch;
    private RecyclerView rvList;
    private MyAdapter myAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Example> exampleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        exampleList = new ArrayList<>();
        myAdapter = new MyAdapter(MainActivity.this,exampleList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(myAdapter);
        getEmail();
        
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String s) {
        List<Example> userListFilter = new ArrayList<>();

        for (Example example : exampleList) {
            if (example.getEmail().toLowerCase().contains(s.toLowerCase())) {
                userListFilter.add(example);
            }

        }
        myAdapter.filterList(userListFilter, MainActivity.this);
        myAdapter.notifyDataSetChanged();
    }

    private void getEmail() {
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
        edtSearch = (EditText) findViewById(R.id.tvSearch);
        rvList = (RecyclerView) findViewById(R.id.rvList);
        swipeRefreshLayout = findViewById(R.id.sw);
    }
}