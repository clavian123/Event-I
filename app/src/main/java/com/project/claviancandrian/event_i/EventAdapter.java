package com.project.claviancandrian.event_i;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventAdapterViewHolder> {


    private ArrayList<Event> eventArrayList;
    private Context context;
    private String source;

    public EventAdapter(ArrayList<Event> eventArrayList, Context context,String source) {
        this.eventArrayList = eventArrayList;
        this.context = context;
        this.source = source;
    }

    @NonNull
    @Override
    public EventAdapter.EventAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_card_event, viewGroup, false);
        return new EventAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventAdapterViewHolder holder, final int i) {

//        Glide.with(context).load();
//        holder.imgEvent.setImageResource(Data.eventList.get(i).getImage());
        Glide.with(context)
                .load(eventArrayList.get(i).getImage())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgEvent);

        holder.txtNamaEvent.setText(eventArrayList.get(i).getName());
        holder.txtEventDate.setText(eventArrayList.get(i).getDate());
        holder.txtEventLocation.setText(eventArrayList.get(i).getCity());
//        holder.txtEventPrice.setText(String.format("%.2f",eventArrayList.get(i).getPrice().toString()));
        holder.txtEventPrice.setText(eventArrayList.get(i).getPrice());
        holder.txtEventType.setText(eventArrayList.get(i).getType());


        holder.cardEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailEventActivity.class);
                intent.putExtra("posisi",i);
                intent.putExtra("source",source);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(context, "Event pos "+i+" clicked", Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventArrayList.size();
    }

    public class EventAdapterViewHolder extends RecyclerView.ViewHolder {

        private CardView cardEvent;
        private ImageView imgEvent;
        private TextView txtNamaEvent;
        private TextView txtEventDate;
        private TextView txtEventPrice;
        private TextView txtEventLocation;
        private TextView txtEventType;

        public EventAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            cardEvent = itemView.findViewById(R.id.cardEvent);
            imgEvent = itemView.findViewById(R.id.imgEvent);
            txtNamaEvent = itemView.findViewById(R.id.tvEventName);
            txtEventDate = itemView.findViewById(R.id.tvEventDate);
            txtEventPrice = itemView.findViewById(R.id.tvEventPrice);
            txtEventLocation = itemView.findViewById(R.id.tvLocation);
            txtEventType = itemView.findViewById(R.id.tvEventType);

        }
    }
}
