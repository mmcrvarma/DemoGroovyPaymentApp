package com.imobile3.groovypayments.data.model;

public class ProductChartModel {
    private String mProductName;
    private long mProductId;
    private int mCount = 0;

    public ProductChartModel(long id, String name, int count)
    {
        mProductId = id;
        mProductName = name;
        mCount = count;
    }

    public String getProductName()
    {
        return mProductName;
    }

    public long getProductId()
    {
        return mProductId;
    }

    public int getCount()
    {
        return mCount;
    }

    public int setCount(int count)
    {
        mCount = count;
        return mCount;
    }
}
