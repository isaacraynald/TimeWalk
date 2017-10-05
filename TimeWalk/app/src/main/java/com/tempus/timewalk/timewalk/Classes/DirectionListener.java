package com.tempus.timewalk.timewalk.Classes;

import com.tempus.timewalk.timewalk.Models.Points;

import java.util.List;

/**
 * An interface for directions
 * Created by Isaac on 5/9/17.
 */

public interface DirectionListener {
    void onDirectionStart();

    void onDirectionSuccess(List<Points> points);
}
