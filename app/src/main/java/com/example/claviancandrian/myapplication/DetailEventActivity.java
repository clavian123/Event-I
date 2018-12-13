package com.example.claviancandrian.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailEventActivity extends AppCompatActivity {

    /**
     * TODO
     * 1. Implementation Firebase
     * 2. Show Detail from Intent Extras*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null)
        {
            //Do Something here
        }


    }
}
