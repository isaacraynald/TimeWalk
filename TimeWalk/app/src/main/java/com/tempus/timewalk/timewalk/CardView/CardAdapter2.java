package com.tempus.timewalk.timewalk.CardView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tempus.timewalk.timewalk.Activity.MapActivity;
import com.tempus.timewalk.timewalk.Activity.NavigationDrawer;
import com.tempus.timewalk.timewalk.Fragment.LocationFragment;
import com.tempus.timewalk.timewalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Isaac on 7/9/17.
 */

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.MyViewHolder>{
    private Context context;
    private String[] cardList2;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView venueName;
        public ImageButton images;

        public MyViewHolder(View itemView) {
            super(itemView);
            venueName = (TextView) itemView.findViewById(R.id.landmark_name);
            images = (ImageButton) itemView.findViewById(R.id.information);

        }

    }
    public CardAdapter2(Context context, String[] cardList){
        this.context = context;
        this.cardList2 = cardList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_layout_2, parent, false);
        return new CardAdapter2.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.venueName.setText(cardList2[position]);
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity mapActivity= (MapActivity) v.getContext();
                LocationFragment fragment = new LocationFragment();
                mapActivity.getSupportFragmentManager().beginTransaction().replace(R.id.maps_container, fragment,fragment.getTag()).
                        addToBackStack("location").commit();

            }
        });


    }


    @Override
    public int getItemCount() {
       return cardList2.length;
    }
}
