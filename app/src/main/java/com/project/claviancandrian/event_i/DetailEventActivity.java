package com.project.claviancandrian.event_i;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.maps.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @BindView(R.id.btnDetailEditt)
    Button btnDetailEditt;

    Integer pos = 0;
    String source = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            pos = bundle.getInt("posisi");
            source = bundle.getString("source");

            if (source.equals("none"))
            {
                btnDetailEditt.setVisibility(View.GONE);
            }

            Log.d("EXTRAS", String.valueOf(pos));
            Log.d("SIZE", String.valueOf(Data.eventList.size()));

            Glide.with(this)
                    .load(Data.eventList.get(pos).getImage())
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(detailEventImage);

            detailEventName.setText(Data.eventList.get(pos).getName());
            detailEventDetail.setText(Data.eventList.get(pos).getDesc());
            tvDetailDate.setText(Data.eventList.get(pos).getDate());
            tvDetailPrice.setText(Data.eventList.get(pos).getPrice().toString());
            tvDetailLocation.setText(Data.eventList.get(pos).getLocation());
            tvDetailType.setText(Data.eventList.get(pos).getType());
            tvDetailContact.setText(Data.eventList.get(pos).getCpTelp());
            tvDetailEmail.setText(Data.eventList.get(pos).getCpEmail());
            detailEventAddress.setText(Data.eventList.get(pos).getCpEmail());
//            detailEventImage.setImageResource(Data.eventList.get(pos).getImage());
        }
    }

    @OnClick(R.id.btnDetailEditt)
    public void onViewClicked() {
        Intent intent = new Intent(this,ModifyEventActivity.class);
        intent.putExtra("pos",pos);
        startActivity(intent);
    }
}
