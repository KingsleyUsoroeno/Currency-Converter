package com.kingtech.currency_converter_android.data.local.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kingtech.currency_converter_android.data.local.dao.RateDao;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;

@Database(entities = {ConversionRate.class}, version = 1, exportSchema = false)
public abstract class ConversionRatesDatabase extends RoomDatabase {

    private static final String DB_NAME = "conversion_rate.db";
    private static ConversionRatesDatabase dbInstance;

    public static synchronized ConversionRatesDatabase getInstance(Context ctx) {
        if (dbInstance == null) {
            dbInstance = Room.databaseBuilder(ctx.getApplicationContext(),
                    ConversionRatesDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return dbInstance;
    }

    public abstract RateDao rateDao();
}
