package com.project.claviancandrian.event_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowListActivity extends AppCompatActivity {

    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    private RecyclerView recyclerView;
    private EventAdapter adapter;

    DatabaseReference myRef;

    /**
     * TODO
     * 1. Setting fab Button, kalau Event Organizer button muncul, klo pengguna biasa, Button Hilang
     */

    ArrayList<Event> arrayEvent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        ButterKnife.bind(this);

        recyclerView = (RecyclerView) findViewById(R.id.rvEvent);
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayEvent.clear();
                Data.eventList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
                    arrayEvent.add(event);
                    Data.eventList.add(event);
//                    Log.d("UYUH", Data.eventList.get(0).getCity());
                }
                adapter = new EventAdapter(arrayEvent, getApplicationContext());
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ShowListActivity.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
//                Event value = dataSnapshot.getChildren().getv(Event.class);
//                Data.eventList.add(value);
////                Log.d("CAPEK", "Value is: " + value.getName());
//                Toast.makeText(ShowListActivity.this, "Size is "+Data.eventList.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("", "Failed to read value.", error.toException());
                Toast.makeText(ShowListActivity.this, "" + error.toException(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        Intent intent = new Intent(this,AddEventActivity.class);
        startActivity(intent);
    }
}
