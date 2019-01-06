package com.project.claviancandrian.event_i;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowListActivity extends AppCompatActivity {

    @BindView(R.id.btnAdd)
    FloatingActionButton btnAdd;
    @BindView(R.id.btnAcc)
    FloatingActionButton btnAcc;
    private RecyclerView recyclerView;
    private EventAdapter adapter;

    String source = "";
    String user = "";
    DatabaseReference myRef;

    /**
     * TODO
     * 1. Setting fab Button, kalau Event Organizer button muncul, klo pengguna biasa, Button Hilang
     */

    ArrayList<Event> arrayEvent = new ArrayList<>();

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);
        ButterKnife.bind(this);


        getSupportActionBar();

        recyclerView = (RecyclerView) findViewById(R.id.rvEvent);
        myRef = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            source = bundle.getString("source");
            if (source.equals("google")) {
                user = bundle.getString("user");
                btnAdd.setVisibility(View.VISIBLE);
                btnAcc.setVisibility(View.GONE);
            } else if (source.equals("mail")) {
                user = bundle.getString("user");
                btnAdd.setVisibility(View.VISIBLE);
                btnAcc.setVisibility(View.GONE);
            }
        } else {

            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();
            }

            btnAdd.setVisibility(View.GONE);
            btnAcc.setVisibility(View.VISIBLE);
            source = "none";
        }

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                arrayEvent.clear();
                Data.eventList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Event event = dataSnapshot1.getValue(Event.class);
                    if (!user.isEmpty()) {
                        if (event.getOwner().equals(user)) {
                            arrayEvent.add(event);
                            Data.eventList.add(event);
                        }
                    } else {
                        arrayEvent.add(event);
                        Data.eventList.add(event);
                    }
//                    Log.d("UYUH", Data.eventList.get(0).getCity());
                }
                adapter = new EventAdapter(arrayEvent, getApplicationContext(), source);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,ShowListActivity.class);
                finish();
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick({R.id.btnAdd, R.id.btnAcc})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                Intent intent = new Intent(this, AddEventActivity.class);
                startActivity(intent);
                break;
            case R.id.btnAcc:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                break;
        }
    }

}
