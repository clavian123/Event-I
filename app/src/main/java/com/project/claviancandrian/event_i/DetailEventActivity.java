package com.project.claviancandrian.event_i;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailEventActivity extends AppCompatActivity {

    @BindView(R.id.detailEventImage)
    ImageView detailEventImage;
    @BindView(R.id.detailEventName)
    TextView detailEventName;
    @BindView(R.id.detailEventDetail)
    TextView detailEventDetail;
    @BindView(R.id.tvDetailDate)
    TextView tvDetailDate;
    @BindView(R.id.tvDetailPrice)
    TextView tvDetailPrice;
    @BindView(R.id.tvDetailLocation)
    TextView tvDetailLocation;
    @BindView(R.id.tvDetailType)
    TextView tvDetailType;
    @BindView(R.id.tvDetailContact)
    TextView tvDetailContact;
    @BindView(R.id.tvDetailEmail)
    TextView tvDetailEmail;
    @BindView(R.id.detailEventAddress)
    EditText detailEventAddress;
    @BindView(R.id.mapDetail)
    MapView mapDetail;

    /**
     * TODO
     * 1. Ambil lokasi gMap trus tampilin di Map
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Integer pos = bundle.getInt("posisi");
//            detailEventImage.setImageResource(Data.eventList.get(pos).getImage());
            detailEventName.setText(Data.eventList.get(pos).getName());
            detailEventDetail.setText(Data.eventList.get(pos).getDesc());
            tvDetailDate.setText(Data.eventList.get(pos).getDate());
            tvDetailPrice.setText(Data.eventList.get(pos).getPrice().toString());
            tvDetailLocation.setText(Data.eventList.get(pos).getLocation());
            tvDetailType.setText(Data.eventList.get(pos).getType());
            tvDetailContact.setText(Data.eventList.get(pos).getCpTelp());
            tvDetailEmail.setText(Data.eventList.get(pos).getCpEmail());
            detailEventAddress.setText(Data.eventList.get(pos).getCpEmail());
        }
    }
}
