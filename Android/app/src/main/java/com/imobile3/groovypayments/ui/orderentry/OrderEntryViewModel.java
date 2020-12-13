package com.imobile3.groovypayments.ui.orderentry;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.ProductRepository;
import com.imobile3.groovypayments.data.Result;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.data.model.ProductResponseModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel serves as an async bridge between the View (Activity, Fragment)
 * and our backing data repository (Database).
 */
public class OrderEntryViewModel extends ViewModel {

    private ProductRepository mRepository;

    OrderEntryViewModel(ProductRepository repository) {
        mRepository = repository;
    }

    public LiveData<ProductResponseModel> getProducts() {
        // Caller should observe this object for changes. When the data has finished
        // async loading, the observer can react accordingly.
        final MutableLiveData<ProductResponseModel> observable =
                new MutableLiveData<>();
        GroovyExecutors.getInstance().getDiskIo().execute(() -> {
            ProductResponseModel responseModel;
            Result<List<Product>> result = mRepository.getDataSource().loadProducts();
            if (result instanceof Result.Success) {
                List<Product> resultSet = ((Result.Success<List<Product>>)result).getData();
                responseModel =
                        new ProductResponseModel(resultSet,
                                MainApplication.getInstance().getString(R.string.order_entry_retrieve_products_success));
                observable.postValue(responseModel);
            } else {
                responseModel =
                        new ProductResponseModel(null,
                                MainApplication.getInstance().getString(R.string.order_entry_retrieve_products_failure));
                observable.postValue(responseModel);
            }
        });

        return observable;
    }
}
