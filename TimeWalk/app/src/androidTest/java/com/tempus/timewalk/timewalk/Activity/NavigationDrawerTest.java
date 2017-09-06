package com.tempus.timewalk.timewalk.Activity;

import android.support.constraint.ConstraintLayout;
import android.support.test.espresso.Espresso;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import android.widget.RelativeLayout;

import com.tempus.timewalk.timewalk.Fragment.HomeFragment;
import com.tempus.timewalk.timewalk.R;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Isaac on 10/9/17.
 */
public class NavigationDrawerTest {
    @Rule
    public ActivityTestRule<NavigationDrawer> activityTestRule = new ActivityTestRule<NavigationDrawer>(NavigationDrawer.class);

    private NavigationDrawer navigationDrawer = null;

    @Before
    public void setUp() throws Exception {
        navigationDrawer = activityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){
        View view = navigationDrawer.findViewById(R.id.drawer_layout);

        assertNotNull(view);

    }

    @Test
    public void testDrawerItemClick(){
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))// Left Drawer should be closed.
                .perform(DrawerActions.open());
        Espresso.onView(ViewMatchers.withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_recommended));
        Espresso.onView(Matchers.allOf(ViewMatchers.withId(R.id.recommended_fragment), ViewMatchers.withId(R.id.recyclerView)
                ));

    }

    @Test
    public void TestDrawerClose(){
        Espresso.onView(ViewMatchers.withId(R.id.drawer_layout))// Left Drawer should be closed.
                .perform(DrawerActions.open());
    }


    @After
    public void tearDown() throws Exception {
        navigationDrawer = null;

    }

}