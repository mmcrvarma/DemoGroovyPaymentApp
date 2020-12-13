package com.imobile3.groovypayments.ui.checkout;

import android.content.Context;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.concurrent.GroovyExecutors;
import com.imobile3.groovypayments.data.PaymentTypeRepository;
import com.imobile3.groovypayments.data.model.ClientSecretKeyResponse;
import com.imobile3.groovypayments.data.model.PaymentType;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.network.WebServiceManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel serves as an async bridge between the View (Activity, Fragment)
 * and our backing data repository (Database).
 */
public class CheckoutViewModel extends ViewModel {

    private PaymentTypeRepository mRepository;

    CheckoutViewModel(PaymentTypeRepository repository) {
        mRepository = repository;
    }

    public LiveData<List<PaymentType>> getPaymentTypes(@NonNull final Context context) {
        // Caller should observe this object for changes. When the data has finished
        // async loading, the observer can react accordingly.
        final MutableLiveData<List<PaymentType>> observable =
                new MutableLiveData<>(new ArrayList<>());

        GroovyExecutors.getInstance().getDiskIo().execute(() -> {
            List<PaymentType> resultSet = mRepository.getPaymentTypes(context);
            observable.postValue(resultSet);
        });

        return observable;
    }

    public LiveData<ClientSecretKeyResponse> getClientSecretKey()
    {
        final MutableLiveData<ClientSecretKeyResponse> observable = new MutableLiveData<>();
        WebServiceManager.getInstance().generateClientSecret(MainApplication.getInstance(),
                CartManager.getInstance().getCart().getGrandTotal(), new WebServiceManager.ClientSecretCallback() {
                    @Override
                    public void onClientSecretError(@NonNull String message) {
                        observable.postValue(new ClientSecretKeyResponse(message, null));
                    }

                    @Override
                    public void onClientSecretGenerated(@NonNull String clientSecret) {
                        observable.postValue(new ClientSecretKeyResponse(null, clientSecret));
                    }
                });

        return observable;
    }
}
