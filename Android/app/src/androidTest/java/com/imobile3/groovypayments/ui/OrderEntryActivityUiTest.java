package com.imobile3.groovypayments.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.DatabaseHelper;
import com.imobile3.groovypayments.data.GroovyDemoManager;
import com.imobile3.groovypayments.ui.orderentry.OrderEntryActivity;

import junit.framework.AssertionFailedError;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class OrderEntryActivityUiTest {

    @Rule
    public ActivityTestRule<OrderEntryActivity> mActivityRule =
            new ActivityTestRule(OrderEntryActivity.class, true, false);

    @Before
    public void init()
    {
        // Initialize a new database instance.
        DatabaseHelper.getInstance().init(MainApplication.getInstance());

        //insert products
        GroovyDemoManager.getInstance().insertProducts();

    }

    @After
    public void cleanDatabase()
    {
        // Blow away any existing database instance.
        DatabaseHelper.getInstance().eraseDatabase(MainApplication.getInstance());
    }

    @Test
    public void testActivityLaunched()
    {
        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.list_products)).check(matches(isDisplayed()));
    }

    /**
     * Custom matcher to find the itemView inside RecyclerView.ViewHolder that matches the
     * view being tested, and then confirm that the element in itemView is ImageView or not.
     * @param position
     * @param itemMatcher
     * @return
     */
    public static Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(Object item) {
                final RecyclerView.ViewHolder viewHolder = ((RecyclerView)item).findViewHolderForAdapterPosition(position);
                if(viewHolder != null && itemMatcher.matches(viewHolder.itemView)
                        && viewHolder.itemView.findViewById(R.id.icon) instanceof ImageView)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
        };
    }

    @Test
    public void testImageViewDisplayed()
    {
        mActivityRule.launchActivity(new Intent());
        //If view is not visible, scroll to the position, before checking the view elements.
        onView(withId(R.id.list_products))
                .perform(RecyclerViewActions.scrollToPosition(1));
        onView(withId(R.id.list_products))
                .check(matches(withViewAtPosition(1,
                        hasDescendant(allOf(withId(R.id.icon), isDisplayed())))));
    }


}
