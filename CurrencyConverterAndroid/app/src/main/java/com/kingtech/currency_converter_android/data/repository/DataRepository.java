package com.kingtech.currency_converter_android.data.repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.kingtech.currency_converter_android.data.local.dao.RateDao;
import com.kingtech.currency_converter_android.data.local.database.ConversionRatesDatabase;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;
import com.kingtech.currency_converter_android.data.remote.FixerApi;
import com.kingtech.currency_converter_android.data.utils.AppExecutor;
import com.kingtech.currency_converter_android.data.utils.Constant;
import com.kingtech.currency_converter_android.data.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {

    private static final String TAG = "DataRepository";
    private RateDao rateDao;
    private Context context;

    public DataRepository(Context context) {
        this.context = context;
        rateDao = ConversionRatesDatabase.getInstance(context).rateDao();
    }

    public void fetchConversionRate() {
        Call<ResponseBody> conversionResponse = FixerApi.getService().getConversionRate(Constant.API_KEY);
        conversionResponse.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        saveToDb(response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i(TAG, "onFailure: Failed to Fetch response due to " + t.getMessage());
                t.printStackTrace();
            }
        });
    }


    private String parseJson(String res) {
        String rates = "";
        try {
            JSONObject responseObject = new JSONObject(res);
            rates = responseObject.getJSONObject("rates").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rates;
    }

    private void saveToDb(String responseBody) {
        final List<ConversionRate> rates = Utils.convertStringToList(responseBody);
        AppExecutor.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                rateDao.insertConversionRate(rates);
            }
        });
    }

    public List<ConversionRate> fetchConversionFromAssets() {
        String res = Utils.loadJSONFromAsset(context, "rates.json");
        String result = parseJson(res);
        return Utils.convertStringToList(result);
    }
}
