package com.kingtech.currency_converter_android.ui.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kingtech.currency_converter_android.R;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<ConversionRate> {

    public SpinnerAdapter(@NonNull Context context, List<ConversionRate> rateList) {
        super(context, 0, rateList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int pos, View view, ViewGroup viewGroup) {
        ConversionRate rate = getItem(pos);

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_layout, viewGroup, false
            );
        }
        //Declaring and initializing the widgets in custom layout
        ImageView imageView = view.findViewById(R.id.img_flag);
        TextView textView = view.findViewById(R.id.tvRate);

        if (rate != null) {
            textView.setText(rate.getCountry());

            if (rate.getCountry().equals("PLN")) {
                imageView.setImageResource(R.drawable.poland_flag);
            }
            if (rate.getCountry().equals("EAU")) {
                imageView.setImageResource(R.drawable.eu_logo);
            }
        }

        return view;
    }
}
