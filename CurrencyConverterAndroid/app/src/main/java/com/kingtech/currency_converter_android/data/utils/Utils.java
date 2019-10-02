package com.kingtech.currency_converter_android.data.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    public static List<ConversionRate> convertStringToList(String s) {
        Gson gson = new Gson();
        List<ConversionRate> rate = new ArrayList<>();
        if (s.isEmpty()) {
            return null;
        }
        HashMap<String, Double> ratesMap = gson.fromJson(s, new TypeToken<HashMap<String, Double>>() {
        }.getType());

        for (Map.Entry<String, Double> entry : ratesMap.entrySet()) {
            rate.add(new ConversionRate(entry.getKey(), entry.getValue()));
        }
        return rate;
    }

    public static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            while (is.read(buffer) > 0) {
                json = new String(buffer);
            }
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }
}
