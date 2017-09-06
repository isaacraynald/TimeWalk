package com.tempus.timewalk.timewalk.CardView;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tempus.timewalk.timewalk.Activity.MapActivity;
import com.tempus.timewalk.timewalk.Models.DataModel;
import com.tempus.timewalk.timewalk.R;

import java.util.List;

/**
 * Created by Isaac on 23/8/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {
    private Context context;
    private List<DataModel> cardList;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView venue, details, description;
        public ImageView images;

        public MyViewHolder(View itemView) {
            super(itemView);
            images = (ImageView)itemView.findViewById(R.id.icon_images);
            venue = (TextView) itemView.findViewById(R.id.title_content);
            details = (TextView) itemView.findViewById(R.id.pages);
            description = (TextView) itemView.findViewById(R.id.description);

        }
    }

    public CardAdapter(Context context, List cardList){
        this.context = context;
        this.cardList = cardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final DataModel dataModel = cardList.get(position);
        holder.venue.setText(dataModel.getVenue());
        holder.details.setText(dataModel.getDetail());
        holder.description.setText(dataModel.getDescription());
        holder.images.setImageResource(dataModel.getImages());
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("places", dataModel.getVenue());
                context.startActivity(intent);
            }
        });

    }

    public int getItemCount() {
        return cardList.size();
    }

}
