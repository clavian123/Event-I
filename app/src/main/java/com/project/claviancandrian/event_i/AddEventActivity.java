package com.project.claviancandrian.event_i;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddEventActivity extends AppCompatActivity implements OnMapReadyCallback {

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

    private Uri filePath;

    Uri downloadUrl;

    private String imgUrl;
    private GoogleMap mMap;

    Boolean sucesss = false;

    private final int PICK_IMAGE_REQUEST = 71;

    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;

    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        addEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddEventActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        addEventDate.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick({R.id.addEventImage, R.id.btnAddEvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.addEventImage:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                break;
            case R.id.btnAddEvent:
                uploadImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                addEventImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {

        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = storageReference.child("images/" + System.currentTimeMillis() + "." + getFileExtension(filePath));
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
//                            imgUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful()) ;
                            downloadUrl = urlTask.getResult();
//                                String uri = taskSnapshot.getStorage().getDownloadUrl().toString();
//                            Toast.makeText(AddEventActivity.this, "url : "+downloadUrl.toString(), Toast.LENGTH_SHORT).show();
                            addEvent();
//                            Log.d("GAJELAS","URL : "+downloadUrl.toString());
                            sucesss = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddEventActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            sucesss = false;
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    public void addEvent() {
        String name = addEventName.getText().toString();
        String desc = addEventDesc.getText().toString();
        String date = addEventDate.getText().toString();
        String location = addEventLocation.getText().toString();
        String type = addEventType.getText().toString();
        String phone = addEventPhone.getText().toString();
        String email = addEventEmail.getText().toString();
        String address = addEventAddress.getText().toString();
        String price = addEventPrice.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userMail = user.getEmail();

//        Double price = Double.parseDouble(addEventPrice.getText().toString().trim());
        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = myRef.push().getKey();
            //creating an Event Object
            Event event = new Event(id, name, desc, address, location, type, date, email, phone, price, userMail, downloadUrl.toString());
            Data.eventList.add(new Event(id, name, desc, address, location, type, date, email, phone, price, userMail, downloadUrl.toString()));
            //Saving the Event
            myRef.child(id).setValue(event);
            //displaying a success toast
            Toast.makeText(this, "Event added", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ShowListActivity.class);
            startActivity(intent);
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
