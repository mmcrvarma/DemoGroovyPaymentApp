package com.imobile3.groovypayments.data.model;

import java.util.List;

/**
 * Response model for the getCarts for Order history page.
 */
public class CartResponseModel {
    private List<Cart> mCarts;
    private String mServerMessage;

    public CartResponseModel(List<Cart> carts, String serverMessage)
    {
        mCarts = carts;
        mServerMessage = serverMessage;
    }

    public List<Cart> getCarts()
    {
        return mCarts;
    }

    public String getServerMessage()
    {
        return mServerMessage;
    }
}
