package com.kingtech.currency_converter_android.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FixerApi {

    private static final String BASE_URL = "http://data.fixer.io/api/";
    private static Retrofit retrofit;

    private static synchronized Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }

    public static FixerApiService getService() {
        return getRetrofit().create(FixerApiService.class);
    }
}


