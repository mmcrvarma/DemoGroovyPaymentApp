package com.imobile3.groovypayments.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.imobile3.groovypayments.MainApplication;
import com.imobile3.groovypayments.data.entities.CartEntity;
import com.imobile3.groovypayments.data.entities.CartProductEntity;
import com.imobile3.groovypayments.data.entities.CartTaxEntity;
import com.imobile3.groovypayments.data.entities.ProductEntity;
import com.imobile3.groovypayments.data.entities.ProductTaxJunctionEntity;
import com.imobile3.groovypayments.data.entities.TaxEntity;
import com.imobile3.groovypayments.data.entities.UserEntity;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.utils.CartBuilder;
import com.imobile3.groovypayments.data.utils.CartProductBuilder;
import com.imobile3.groovypayments.data.utils.CartTaxBuilder;
import com.imobile3.groovypayments.data.utils.ProductBuilder;
import com.imobile3.groovypayments.data.utils.TaxBuilder;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public final class GroovyDemoManager {

    private static final String TAG = GroovyDemoManager.class.getSimpleName();

    //region Singleton Implementation

    private static GroovyDemoManager sInstance;

    private GroovyDemoManager() {
    }

    @NonNull
    public static synchronized GroovyDemoManager getInstance() {
        if (sInstance == null) {
            sInstance = new GroovyDemoManager();
        }
        return sInstance;
    }

    //endregion

    public interface ResetDatabaseCallback {

        void onDatabaseReset();
    }

    /**
     * Delete the current database instance (potentially dangerous operation!)
     * and populate a new instance with fresh demo data.
     */
    public void resetDatabase(
            @NonNull final ResetDatabaseCallback callback) {
        new ResetDatabaseTask(MainApplication.getInstance(), callback)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private class ResetDatabaseTask extends AsyncTask<Void, Void, Void> {

        @NonNull
        private final Context mContext;
        @NonNull
        private final ResetDatabaseCallback mCallback;

        private ResetDatabaseTask(
                @NonNull final Context context,
                @NonNull final ResetDatabaseCallback callback) {
            mContext = context;
            mCallback = callback;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Blow away any existing database instance.
            DatabaseHelper.getInstance().eraseDatabase(mContext);

            // Initialize a new database instance.
            DatabaseHelper.getInstance().init(mContext);
            insertProducts();
            insertCartProducts();
            insertValidUserData();

            // All done!
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            mCallback.onDatabaseReset();
        }
    }

    // Method to insert some dummy products into the database
    public void insertProducts() {
        List<ProductEntity> productEntities = new ArrayList<>();

        // Add products, to retrieve from Order entry page
        productEntities.add(ProductBuilder.build(101L,
                "Tasty Chicken Sandwich",
                "Chicken, lettuce, tomato and mayo",
                750L, 200L,
                GroovyIcon.Sandwich, GroovyColor.Yellow));

        productEntities.add(ProductBuilder.build(102L,
                "10-Pack of AA Batteries",
                "Medium-quality batteries",
                899L, 125L,
                GroovyIcon.BatteryPack, GroovyColor.Gray));

        productEntities.add(ProductBuilder.build(103L,
                "Metal Folding Chair",
                "Weighs approximately 12 lbs.",
                2250L, 400L,
                GroovyIcon.WoodenChair, GroovyColor.Blue));

        //Add TaxEntity information.
        List<TaxEntity> taxEntities = new ArrayList<>();

        taxEntities.add(TaxBuilder.build(201L,
                "5% Federal Tax", "0.05"));

        taxEntities.add(TaxBuilder.build(202L,
                "7.5% State Tax", "0.075"));

        // Generate product-tax associations with junction entities.
        List<ProductTaxJunctionEntity> productTaxJunctionEntities = new ArrayList<>();
        for (ProductEntity product : productEntities) {
            // Associate all taxes with each product.
            productTaxJunctionEntities.addAll(TestDataRepository.getInstance()
                    .getProductTaxJunctions(product, taxEntities));
        }

        // Insert entities into database instance.
        DatabaseHelper.getInstance().getDatabase().getProductDao()
                .insertProducts(
                        productEntities.toArray(new ProductEntity[0]));
        DatabaseHelper.getInstance().getDatabase().getTaxDao()
                .insertTaxes(
                        taxEntities.toArray(new TaxEntity[0]));
        DatabaseHelper.getInstance().getDatabase().getProductTaxJunctionDao()
                .insertProductTaxJunctions(
                        productTaxJunctionEntities.toArray(new ProductTaxJunctionEntity[0]));
    }

    // Method to insert Cart products
    public void insertCartProducts() {
        //Create Cart Entities
        List<CartEntity> cartEntities = new ArrayList<>();
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        String date1 = "08/01/2020 21:30:15";
        String date2 = "08/02/2020 22:30:45";
        cartEntities.add(CartBuilder.build(101L,
                format.parse(date1, new ParsePosition(0)), 100L, 5L, 105L));
        cartEntities.add(CartBuilder.build(102L,
                format.parse(date2, new ParsePosition(0)), 105L, 10L, 115L));

        //Create Cart product Entity
        List<CartProductEntity> cartProductEntities = new ArrayList<>();

        cartProductEntities.add(CartProductBuilder.build(1001L, 101L,
                "Cotton Candy", 399L, 1));

        cartProductEntities.add(CartProductBuilder.build(1002L, 101L,
                "Giant Lollipop", 499L, 1));

        cartProductEntities.add(CartProductBuilder.build(1003L, 102L,
                "Cappuccino", 599L, 1));

        cartProductEntities.add(CartProductBuilder.build(1004L, 102L,
                "Caramel Macchiato", 750L, 2));

        List<CartTaxEntity> cartTaxEntities = new ArrayList<>();
        cartTaxEntities.add(CartTaxBuilder.build(1001L,
                101L,
                "5% Restaurant Tax", "0.05"));

        cartTaxEntities.add(CartTaxBuilder.build(1002L,
                101L,
                "7.5% Federal Tax", "0.075"));

        cartTaxEntities.add(CartTaxBuilder.build(1003L,
                102L,
                "5% Restaurant Tax", "0.05"));

        cartTaxEntities.add(CartTaxBuilder.build(1004L,
                102L,
                "7.5% Federal Tax", "0.075"));

        // Insert entities into database instance.
        DatabaseHelper.getInstance().getDatabase().getCartDao()
                .insertCarts(
                        cartEntities.toArray(new CartEntity[0]));
        DatabaseHelper.getInstance().getDatabase().getCartProductDao()
                .insertCartProducts(
                        cartProductEntities.toArray(new CartProductEntity[0]));
        DatabaseHelper.getInstance().getDatabase().getCartTaxDao()
                .insertCartTaxes(
                        cartTaxEntities.toArray(new CartTaxEntity[0]));
    }

    //Method to insert User data
    public void insertValidUserData() {
        List<UserEntity> userEntities = TestDataRepository.getInstance()
                .getUsers(TestDataRepository.Environment.GroovyDemo);

        // Insert entities into database instance.
        DatabaseHelper.getInstance().getDatabase()
                .getUserDao().insertUsers(userEntities.toArray(new UserEntity[0]));
    }
}
