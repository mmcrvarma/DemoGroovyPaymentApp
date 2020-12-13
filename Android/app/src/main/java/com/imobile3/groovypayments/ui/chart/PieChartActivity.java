package com.imobile3.groovypayments.ui.chart;

import android.os.Bundle;
import android.util.Log;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.model.ProductChartModel;
import com.imobile3.groovypayments.ui.BaseActivity;
import com.imobile3.groovypayments.ui.dialog.ProgressDialog;

import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class PieChartActivity extends BaseActivity {

    private ProgressDialog mProgressDialog;
    private PieChartViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_common);
        setUpMainNavBar();
        mViewModel.getProductList().observe(this, PieChartActivity.this::setPieChart);
    }

    private void setPieChart(List<ProductChartModel> data) {
        // Update the pie chart view with data.
        AnyChartView chartView = findViewById(R.id.chartView);
        Pie pie = AnyChart.pie();

        List<DataEntry> chartData = new ArrayList<>();
        for(ProductChartModel chartModel: data)
        {
            chartData.add(new ValueDataEntry(chartModel.getProductName(), chartModel.getCount()));
        }
        chartView.setProgressBar(findViewById(R.id.progress_bar));


        pie.data(chartData);

        pie.labels().position("outside");

        pie.legend()
                .itemsLayout(LegendLayout.HORIZONTAL_EXPANDABLE)
                .align(Align.CENTER).padding(10d);

        chartView.setChart(pie);
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this, new PieChartViewModelFactory())
                .get(PieChartViewModel.class);
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showBackButton();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.daily_report_subtitle));
    }
}
