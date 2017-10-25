package com.tempus.timewalk.timewalk.GalleryView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tempus.timewalk.timewalk.R;

/**
 * Custom Adapter to create image sliding gallery, extends PageAdapter
 * Created by julientran on 4/9/17.
 */

public class SliderAdapter extends PagerAdapter{

    /**
     * Variables
     */
    private Context ctx;
    //private int[] locationImg = {R.drawable.location_image01, R.drawable.location_image02, R.drawable.location_image03};
    //private String[] captionList = {"Sea Breeze, Taken on January 12, 1961", "Taken on January 29, 1963", "Taken in August 1960"};
    private String[] locationImg;
    private String[] captionList;
    private String[] listDesc;
    private LayoutInflater inflater;

    public SliderAdapter(Context ctx, String[] locationImg, String[] captionList, String[] listDesc){
        this.ctx = ctx;
        this.locationImg = locationImg;
        this.captionList = captionList;
        this.listDesc = listDesc;

    }

    /**
     * Get the number of images within a gallery
     */
    @Override
    public int getCount() {
        return locationImg.length;
    }

    /**
     * Determines whether a page View is associated with a specific key object as returned by instantiateItem
     * @param view Page View to check for association with object
     * @param object Object to check for association with view
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

    /**
     * Create the page for the given position and set the image resources
     * @param container The parents view to set gallery view on
     * @param position The page position to be instantiated.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View sliderView = inflater.inflate(R.layout.swipe_gallery,container,false);
        ImageView img = (ImageView)sliderView.findViewById(R.id.locationImage);
        TextView tv = (TextView)sliderView.findViewById(R.id.imageCaption);
        //TextView tv2 = (TextView)sliderView.findViewById(R.id.location_name);
        TextView description = (TextView)sliderView.findViewById(R.id.location_desc);

        Picasso.with(ctx)
                .load(locationImg[position])
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .error(R.drawable.ic_menu_camera)
                .noFade()
                .into(img);

        tv.setText(captionList[position]);
        //tv2.setText(captionList[position]);
        //description.setText(listDesc[position]);
        container.addView(sliderView);
        return sliderView;
    }

    /**
     * Remove a page for the given position
     * @param container The parents view to destroy the gallery view from
     * @param position The page position to be removed.
     * @param object The same object that was returned by instantiateItem
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
