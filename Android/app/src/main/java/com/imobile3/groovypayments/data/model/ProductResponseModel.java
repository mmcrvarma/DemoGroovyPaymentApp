package com.imobile3.groovypayments.data.model;

import java.util.List;

/**
 * Response model for the getProducts for Order entry page.
 */
public class ProductResponseModel {
    private List<Product> mProducts;
    private String mServerMessage;

    public ProductResponseModel(List<Product> products, String serverMessage)
    {
        mProducts = products;
        mServerMessage = serverMessage;
    }

    public List<Product> getProducts()
    {
        return mProducts;
    }

    public String getServerMessage()
    {
        return mServerMessage;
    }
}
