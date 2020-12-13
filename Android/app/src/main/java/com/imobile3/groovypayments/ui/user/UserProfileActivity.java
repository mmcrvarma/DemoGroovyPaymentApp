package com.imobile3.groovypayments.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.utils.SharedPreferencesHelper;

public class UserProfileActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_activity);

        setUpMainNavBar();
        setUpViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        if(SharedPreferencesHelper.getInstance().getLoggedInStatus())
        {
            mMainNavBar.showSubtitle(getString(R.string.user_profile_subtitle_format,
                    SharedPreferencesHelper.getInstance().getDisplayName()));
        }
    }

    @Override
    protected void initViewModel() {
        // No view model needed.
    }

    private void setUpViews() {
        TextView lblUsername = findViewById(R.id.label_username);
        TextView lblEmail = findViewById(R.id.label_email);
        TextView lblHoursWeek = findViewById(R.id.label_hours_week);
        if(SharedPreferencesHelper.getInstance().getLoggedInStatus())
        {
            lblUsername.setText(SharedPreferencesHelper.getInstance().getUserName());
            lblEmail.setText(SharedPreferencesHelper.getInstance().getEmail());
            lblHoursWeek.setText("35.42");
            findViewById(R.id.hours_week_container).setVisibility(View.VISIBLE);
            findViewById(R.id.email_container).setVisibility(View.VISIBLE);
            findViewById(R.id.username_container).setVisibility(View.VISIBLE);
        }
    }
}
