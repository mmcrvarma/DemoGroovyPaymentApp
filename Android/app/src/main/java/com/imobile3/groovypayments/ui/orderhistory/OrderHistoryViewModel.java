package com.imobile3.groovypayments.ui.orderhistory;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.CartRepository;
import com.imobile3.groovypayments.data.Result;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.CartResponseModel;

import java.util.List;

/**
 * The ViewModel serves as an async bridge between the View (Activity, Fragment)
 * and our backing data repository (Database).
 */
public class OrderHistoryViewModel extends ViewModel {

    private int mCartClicks;
    private CartRepository mRepository;

    OrderHistoryViewModel(CartRepository repository) {
        mCartClicks = 0;
        mRepository = repository;
    }

    public void addCartClick() {
        mCartClicks++;
    }

    public int getCartClicks() {
        return mCartClicks;
    }

    public LiveData<CartResponseModel> getCarts() {
        // Caller should observe this object for changes. When the data has finished
        // async loading, the observer can react accordingly.
        final MutableLiveData<CartResponseModel> observable =
                new MutableLiveData<>();

        GroovyExecutors.getInstance().getDiskIo().execute(() -> {
            CartResponseModel cartResponseModel;
            Result<List<Cart>> result = mRepository.getDataSource().loadCarts();
            if (result instanceof Result.Success) {
                List<Cart> resultSet = ((Result.Success<List<Cart>>) result).getData();
                cartResponseModel = new CartResponseModel(resultSet,
                        MainApplication.getInstance()
                                .getString(R.string.order_history_retrieve_carts_success));
                observable.postValue(cartResponseModel);
            } else {
                cartResponseModel = new CartResponseModel(null,
                        MainApplication.getInstance()
                                .getString(R.string.order_history_retrieve_carts_failure));
                observable.postValue(cartResponseModel);
            }
        });

        return observable;
    }
}
