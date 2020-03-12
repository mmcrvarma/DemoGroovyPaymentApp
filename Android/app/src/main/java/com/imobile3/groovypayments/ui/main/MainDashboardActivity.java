package com.imobile3.groovypayments.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.manager.ApiKeyManager;
import com.imobile3.groovypayments.network.WebServiceManager;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButton;
import com.imobile3.groovypayments.ui.adapter.MainDashboardButtonAdapter;
import com.imobile3.groovypayments.ui.misc.SecretFunctionsActivity;
import com.imobile3.groovypayments.ui.orderentry.OrderEntryActivity;
import com.imobile3.groovypayments.ui.orderhistory.OrderHistoryActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainDashboardActivity extends BaseActivity {

    private MainDashboardButtonAdapter mMainDashboardButtonAdapter;
    private RecyclerView mLaunchButtonsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_dashboard_activity);
        setUpMainNavBar();

        final List<MainDashboardButton> dashboardButtons = getDashboardButtons();
        mMainDashboardButtonAdapter = new MainDashboardButtonAdapter(this,
                dashboardButtons,
                new MainDashboardButtonAdapter.AdapterCallback() {
                    @Override
                    public void onButtonClick(MainDashboardButton button) {
                        handleDashboardButtonClick(button);
                    }
                });
        mLaunchButtonsRecyclerView = findViewById(R.id.grid_launch_buttons);
        mLaunchButtonsRecyclerView.setAdapter(mMainDashboardButtonAdapter);
        mLaunchButtonsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // TODO: Relocate this to another activity
        if (savedInstanceState == null) {
            // showFragmentNow(R.id.container, MainFragment.newInstance(), MainFragment.TAG);
        }

        // TODO: Temporarily initialize Stripe WebServices.
        WebServiceManager.getInstance().init(
                getApplicationContext(),
                "https://api.stripe.com",
                ApiKeyManager.getInstance().getStripeApiClientKey());
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
        mMainNavBar.showSubtitle(getString(R.string.main_dashboard_subtitle));
    }

    @Override
    protected void initViewModel() {
        // Nothing to do here, yet.
    }

    private void handleDashboardButtonClick(@NonNull MainDashboardButton button) {
        switch (button) {
            case OrderEntry:
                startActivity(new Intent(this, OrderEntryActivity.class));
                break;

            case OrderHistory:
                startActivity(new Intent(this, OrderHistoryActivity.class));
                break;

            case SecretFunctions:
                startActivity(new Intent(this, SecretFunctionsActivity.class));
                break;

            case Management:
            case TimeTracking:
            case Placeholder1:
            case Placeholder2:
                showAlertDialog(
                        R.string.common_under_construction,
                        R.string.under_construction_alert_message,
                        R.string.common_acknowledged);
                break;
        }
    }

    private List<MainDashboardButton> getDashboardButtons() {
        List<MainDashboardButton> dashboardButtons = new ArrayList<>();
        dashboardButtons.add(MainDashboardButton.OrderEntry);
        dashboardButtons.add(MainDashboardButton.OrderHistory);
        dashboardButtons.add(MainDashboardButton.SecretFunctions);
        dashboardButtons.add(MainDashboardButton.Management);
        dashboardButtons.add(MainDashboardButton.TimeTracking);
        dashboardButtons.add(MainDashboardButton.Placeholder1);
        dashboardButtons.add(MainDashboardButton.Placeholder2);
        return dashboardButtons;
    }
}
