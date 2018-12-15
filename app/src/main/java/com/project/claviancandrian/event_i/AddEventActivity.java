package com.project.claviancandrian.event_i;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEventActivity extends AppCompatActivity {

    @BindView(R.id.addEventImage)
    ImageView addEventImage;
    @BindView(R.id.addEventName)
    EditText addEventName;
    @BindView(R.id.addEventDesc)
    EditText addEventDesc;
    @BindView(R.id.addEventDate)
    EditText addEventDate;
    @BindView(R.id.addEventPrice)
    EditText addEventPrice;
    @BindView(R.id.addEventLocation)
    EditText addEventLocation;
    @BindView(R.id.addEventType)
    EditText addEventType;
    @BindView(R.id.addEventPhone)
    EditText addEventPhone;
    @BindView(R.id.addEventEmail)
    EditText addEventEmail;
    @BindView(R.id.addEventAddress)
    EditText addEventAddress;
    @BindView(R.id.btnAddEvent)
    Button btnAddEvent;
//    map initialize

    DatabaseReference myRef;

    /**TODO
     * Harusnya tinggal ambil lokasi dari gMap trus push*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        myRef = FirebaseDatabase.getInstance().getReference("event-i");
    }

    @OnClick({R.id.addEventImage, R.id.btnAddEvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addEventImage:
                break;
            case R.id.btnAddEvent:
                addEvent();
                break;
        }
    }

    public void addEvent()
    {
      String name = addEventName.getText().toString();
      String desc = addEventDesc.getText().toString();
      String date = addEventDate.getText().toString();
      String location = addEventLocation.getText().toString();
      String type = addEventType.getText().toString();
      String phone = addEventPhone.getText().toString();
      String email = addEventEmail.getText().toString();
      String address = addEventAddress.getText().toString();

      Double price = Double.parseDouble(addEventPrice.getText().toString().trim());
        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = myRef.push().getKey();
            //creating an Event Object
            Event event = new Event(name, desc,address,location,type,date,email,phone,price);
            //Saving the Event
            myRef.child(id).setValue(event);

            //displaying a success toast
            Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }
}
