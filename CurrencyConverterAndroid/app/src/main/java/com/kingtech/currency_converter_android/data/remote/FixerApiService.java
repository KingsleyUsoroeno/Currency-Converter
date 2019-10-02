package com.kingtech.currency_converter_android.data.remote;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface FixerApiService {

    @GET("latest")
    Call<ResponseBody> getConversionRate(@Query("access_key") String accessKey);

    @GET
    Call<ResponseBody> getConversion(@Url String url);
}
