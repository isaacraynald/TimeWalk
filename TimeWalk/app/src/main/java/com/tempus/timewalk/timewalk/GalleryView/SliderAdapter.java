package com.tempus.timewalk.timewalk.GalleryView;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tempus.timewalk.timewalk.R;

/**
 * Created by julientran on 4/9/17.
 */

public class SliderAdapter extends PagerAdapter{

    private Context ctx;
    private int[] locationImg = {R.drawable.location_image01, R.drawable.location_image02, R.drawable.location_image03};
    private String[] captionList = {"Sea Breeze, Taken on January 12, 1961", "Taken on January 29, 1963", "Taken in August 1960"};
    private LayoutInflater inflater;

    public SliderAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return locationImg.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view==(LinearLayout)object);
    }

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

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout)object);
    }
}
