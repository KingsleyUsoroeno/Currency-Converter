package com.kingtech.currency_converter_android.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;

import java.util.List;

@Dao
public interface RateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertConversionRate(ConversionRate rate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertConversionRate(List<ConversionRate> rate);

    @Query("SELECT * FROM conversionrate ORDER BY id ASC")
    LiveData<List<ConversionRate>> getAllRates();
}
