package com.tempus.timewalk.timewalk.CardView;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tempus.timewalk.timewalk.Activity.MapActivity;
import com.tempus.timewalk.timewalk.Activity.NavigationDrawer;
import com.tempus.timewalk.timewalk.Classes.ImageListener;
import com.tempus.timewalk.timewalk.Classes.ImagesOperations;
import com.tempus.timewalk.timewalk.Fragment.LocationFragment;
import com.tempus.timewalk.timewalk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Destination card for each landmark which is dislayed below the map on Map Fragment
 * Created by Isaac on 7/9/17.
 */

public class CardAdapter2 extends RecyclerView.Adapter<CardAdapter2.MyViewHolder>{

    /**
     * Variables
     */
    private Context context;
    private String[] cardList2;
    private String[] ID;
    private String[] description;
    private ImageListener imageListener;

    /**
     * Values for card contents
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView venueName;
        public ImageButton images;

        public MyViewHolder(View itemView) {
            super(itemView);
            venueName = (TextView) itemView.findViewById(R.id.landmark_name);
            images = (ImageButton) itemView.findViewById(R.id.information);
        }
    }

    /**
     * Constructor method to create a card adapter
     * @param context set context
     * @param cardList List of cards of all profiled landmarks in a tour
     */
    public CardAdapter2(Context context, String[] cardList, String[] ID, String[] description, ImageListener imageListener){
        this.context = context;
        this.cardList2 = cardList;
        this.ID = ID;
        this.imageListener = imageListener;
        this.description = description;
    }

    /**
     * Create new ViewHolder to display items of the card adapter
     * @param parent get the ViewGroup to add the card view onto it
     *               after it is bound to an adapter position
     * @param viewType The view type of the new View
     * @return MyViewHolder A new ViewHolder that holds a Card View of the given view type.
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.card_layout_2, parent, false);
        return new CardAdapter2.MyViewHolder(view);
    }

    /**
     * Create new ViewHolder to display items of the card adapter
     * @param holder The ViewHolder which should be updated to represent the contents of the item at
     *              the given position in the data set.
     * @param position The position of the item within the adapter's data set
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.venueName.setText(cardList2[position]);
        holder.images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity mapActivity= (MapActivity) v.getContext();
                LocationFragment fragment = new LocationFragment();
                Bundle b = new Bundle();
                b.putString("ID", ID[position]);
                b.putString("Name", cardList2[position]);
                b.putString("Description", description[position]);
                fragment.setArguments(b);
                mapActivity.getSupportFragmentManager().beginTransaction().replace(R.id.maps_container, fragment,fragment.getTag()).
                        addToBackStack("location").commit();
            }
        });
        holder.venueName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity mapActivity= (MapActivity) v.getContext();
                LocationFragment fragment = new LocationFragment();
                Bundle b = new Bundle();
                b.putString("ID", ID[position]);
                b.putString("Name", cardList2[position]);
                b.putString("Description", description[position]);
                fragment.setArguments(b);
                mapActivity.getSupportFragmentManager().beginTransaction().replace(R.id.maps_container, fragment,fragment.getTag()).
                        addToBackStack("location").commit();
            }
        });
    }

    /**
     * Get the total number of profiled cards in the cardList
     * @return number of cards item.
     */
    @Override
    public int getItemCount() {
       return cardList2.length;
    }
}
