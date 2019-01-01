package com.project.claviancandrian.event_i;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.MapView;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyEventActivity extends AppCompatActivity {

    @BindView(R.id.modifEventImage)
    ImageView modifEventImage;
    @BindView(R.id.modifEventName)
    EditText modifEventName;
    @BindView(R.id.modifEventDesc)
    EditText modifEventDesc;
    @BindView(R.id.modifEventDate)
    EditText modifEventDate;
    @BindView(R.id.modifEventPrice)
    EditText modifEventPrice;
    @BindView(R.id.modifEvenLocation)
    EditText modifEvenLocation;
    @BindView(R.id.modifEventType)
    EditText modifEventType;
    @BindView(R.id.modifEventPhone)
    EditText modifEventPhone;
    @BindView(R.id.modifEventMail)
    EditText modifEventMail;
    @BindView(R.id.modifEventAddress)
    EditText modifEventAddress;
    @BindView(R.id.modifMap)
    MapView modifMap;
    @BindView(R.id.btnModifEvent)
    Button btnModifEvent;
    @BindView(R.id.btnRemoveEvent)
    Button btnRemoveEvent;

    Integer pos = 0;

    private final int PICK_IMAGE_REQUEST = 71;


    private Uri filePath;

    DatabaseReference myRef;
    FirebaseStorage storage;
    StorageReference storageReference;

    AlertDialog dialog;
    LayoutInflater inflater;
    View dialogView;

    Uri downloadUrl;

    Boolean sucesss = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_event);
        ButterKnife.bind(this);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        myRef = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            pos = bundle.getInt("pos");
        }
        Glide.with(this)
                .load(Data.eventList.get(pos).getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(modifEventImage);

        modifEventName.setText(Data.eventList.get(pos).getName());
        modifEventDesc.setText(Data.eventList.get(pos).getDesc());
        modifEventDate.setText(Data.eventList.get(pos).getDate());
        modifEventPrice.setText(Data.eventList.get(pos).getPrice().toString());
        modifEvenLocation.setText(Data.eventList.get(pos).getLocation());
        modifEventType.setText(Data.eventList.get(pos).getType());
        modifEventPhone.setText(Data.eventList.get(pos).getCpTelp());
        modifEventMail.setText(Data.eventList.get(pos).getCpEmail());
        modifEventAddress.setText(Data.eventList.get(pos).getCpEmail());
    }

    @OnClick({R.id.modifEventImage, R.id.btnModifEvent, R.id.btnRemoveEvent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.modifEventImage:
                //change image
                changeEventImage();
                break;
            case R.id.btnModifEvent:
                //modify / update Event
                uploadImage();
                break;
            case R.id.btnRemoveEvent:
                //remove Event
                DialogRemove();
                break;
        }
    }

    public void changeEventImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void modifEvent() {
        String name = modifEventName.getText().toString();
        String desc = modifEventDesc.getText().toString();
        String date = modifEventDate.getText().toString();
        String location = modifEvenLocation.getText().toString();
        String type = modifEventType.getText().toString();
        String phone = modifEventPhone.getText().toString();
        String email = modifEventMail.getText().toString();
        String address = modifEventAddress.getText().toString();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userMail = user.getEmail();

        Double price = Double.parseDouble(modifEventPrice.getText().toString().trim());
        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
//            String id = myRef.push().getKey();

            Event event2 = new Event(Data.eventList.get(pos).getId(), name, desc, address, location, type, date, email, phone, price, userMail, downloadUrl.toString());

            //akses parent index, ibaratnya seperti nama tabel
            myRef.child(event2.getId()) //select barang berdasarkan key
                    .setValue(event2) //set value barang yang baru
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ModifyEventActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
                        }
                    });


            Data.eventList.set(pos, new Event(event2.getId(), name, desc, address, location, type, date, email, phone, price, userMail, downloadUrl.toString()));
            //displaying a success toast
            Toast.makeText(this, "Event Changed", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ShowListActivity.class);
            finish();
            startActivity(intent);
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
        }
    }

    public void removeEvent() {
        myRef.child(Data.eventList.get(pos).getId()).removeValue();
        Data.eventList.remove(pos);
        Toast.makeText(this, "Event Deleted", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ShowListActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                modifEventImage.setImageBitmap(bitmap);
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
                            modifEvent();
//                            Log.d("GAJELAS","URL : "+downloadUrl.toString());
                            sucesss = true;
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ModifyEventActivity.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void DialogRemove() {
        dialog = new AlertDialog.Builder(ModifyEventActivity.this).create();
        inflater = getLayoutInflater();
        dialogView = inflater.inflate(R.layout.item_alert_delete, null);
        dialog.setView(dialogView);
        dialog.setCancelable(true);

        Button btnAlertYes = dialogView.findViewById(R.id.btnAlertYes);
        Button btnAlertNo = dialogView.findViewById(R.id.btnAlertNo);

        btnAlertYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeEvent();
            }
        });

        btnAlertNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
