package com.kingtech.currency_converter_android.ui.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kingtech.currency_converter_android.data.local.database.ConversionRatesDatabase;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;
import com.kingtech.currency_converter_android.data.repository.DataRepository;

import java.util.List;

public class AppViewModel extends ViewModel {

    private static final String TAG = "AppViewModel";
    private final DataRepository dataRepository;
    private Context ctx;
    private MutableLiveData<List<ConversionRate>> ratesMutableLiveData = new MutableLiveData<>();

    AppViewModel(Context context) {
        this.ctx = context;
        dataRepository = new DataRepository(context);
        dataRepository.fetchConversionRate();
    }


    public LiveData<List<ConversionRate>> getSavedRates() {
        LiveData<List<ConversionRate>> rates = ConversionRatesDatabase.getInstance(ctx).rateDao().getAllRates();
        if (rates.getValue() != null) {
            return rates;
        } else {
            ratesMutableLiveData.setValue(dataRepository.fetchConversionFromAssets());
            return ratesMutableLiveData;
        }
    }
}
