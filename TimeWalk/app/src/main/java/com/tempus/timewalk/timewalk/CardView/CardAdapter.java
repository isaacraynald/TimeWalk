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
 * Display card for each tour on the recommended route page
 * Created by Isaac on 23/8/17.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyViewHolder> {

    /**
     * Variables
     */
    private Context context;
    private List<DataModel> cardList;

    /**
     * Values for card contents
     */
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

    /**
     * Constructor method to create a card adapter
     * @param context set context
     * @param cardList List of cards of all profiled tours in the recommended route
     */
    public CardAdapter(Context context, List cardList){
        this.context = context;
        this.cardList = cardList;
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
                R.layout.card_layout, parent, false);
        return new MyViewHolder(view);
    }

    /**
     * Create new ViewHolder to display items of the card adapter
     * @param holder The ViewHolder which should be updated to represent the contents of the item at
     *              the given position in the data set.
     * @param position The position of the item within the adapter's data set
     */
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

    /**
     * Get the total number of profiled cards in the cardList
     * @return number of cards item.
     */
    public int getItemCount() {
        return cardList.size();
    }

}
