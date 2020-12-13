package com.imobile3.groovypayments.ui.chart;

import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.CartRepository;
import com.imobile3.groovypayments.data.Result;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.data.model.ProductChartModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PieChartViewModel extends ViewModel {

    private CartRepository mCartRepository;

    PieChartViewModel(CartRepository repository) {
        mCartRepository = repository;
    }

    LiveData<List<ProductChartModel>> getProductList() {
        final MutableLiveData<List<ProductChartModel>> observable =
                new MutableLiveData<>();
        GroovyExecutors.getInstance().getDiskIo().execute(() -> {
            Result<List<Cart>> result = mCartRepository.getDataSource().loadCarts();
            if (result instanceof Result.Success) {
                List<Cart> resultSet = ((Result.Success<List<Cart>>)result).getData();
                HashMap<Long, ProductChartModel> productMap = new HashMap<>();

                //Go through all the Cart, and make HashMap of product Id, ProductModel
                for(Cart cart: resultSet)
                {
                    for(CartProductEntity product: cart.getProducts())
                    {
                        Long productId = Long.valueOf(product.getId());
                        if(productMap.containsKey(productId))
                        {
                            int countOfProducts = productMap.get(productId).getCount();
                            productMap.get(productId).setCount(countOfProducts + product.getQuantity());
                        }
                        else
                        {
                            productMap.put(productId, new ProductChartModel
                                    (productId, product.getName(), product.getQuantity()));
                        }
                    }
                }
                observable.postValue(new ArrayList<>(productMap.values()));
            } else {
                // TODO: Return an error message appropriate for the UI.
                observable.postValue(new ArrayList<>());
            }
        });

        return observable;
    }
}
