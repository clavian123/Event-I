package com.project.claviancandrian.event_i;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class ShowListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EventAdapter adapter;

    /**
     * TODO
     * 2. Get Event Data From Firebase
     * 3. Populate Event from Firebase
     * 4. Realtime Event List
     * 5. Create Activity and something for Event Organizer*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);


        recyclerView = (RecyclerView) findViewById(R.id.rvEvent);
        adapter = new EventAdapter(Data.eventList,getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
