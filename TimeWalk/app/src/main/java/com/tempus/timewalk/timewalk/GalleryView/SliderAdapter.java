package com.tempus.timewalk.timewalk.GalleryView;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
    private int[] locationImg = {R.drawable.location_image01, R.drawable.location_image02, R.drawable.location_image03};
    private String[] captionList = {"Sea Breeze, Taken on January 12, 1961", "Taken on January 29, 1963", "Taken in August 1960"};
    private LayoutInflater inflater;

    public SliderAdapter(Context ctx){
        this.ctx = ctx;
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
        img.setImageResource(locationImg[position]);
        tv.setText(captionList[position]);
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
