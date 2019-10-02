package com.kingtech.currency_converter_android.ui.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.kingtech.currency_converter_android.R;
import com.kingtech.currency_converter_android.data.local.entity.ConversionRate;
import com.kingtech.currency_converter_android.databinding.ActivityMainBinding;
import com.kingtech.currency_converter_android.ui.viewmodel.AppViewModel;
import com.kingtech.currency_converter_android.ui.viewmodel.ViewModelFactory;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ActivityMainBinding viewBinding;
    private ConversionRate fromConversionRates;
    private ConversionRate toConversionRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        ViewModelFactory viewModelFactory = new ViewModelFactory(this);
        AppViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(AppViewModel.class);

        viewModel.getSavedRates().observe(this, new Observer<List<ConversionRate>>() {
            @Override
            public void onChanged(List<ConversionRate> conversionRates) {
                if (!conversionRates.isEmpty()) {
                    setUpSpinnerAdapter(conversionRates);
                }
            }
        });

        viewBinding.tvLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")));
            }
        });

        viewBinding.btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateRate(toConversionRate);
            }
        });
    }

    private void calculateRate(ConversionRate conversionRate) {
        // Check if our EditText is Not Null before making a Conversion
        String amount = viewBinding.textInputAmount.getEditText().getText().toString().trim();
        if (!amount.isEmpty()) {
            double rate = Double.parseDouble(amount) * conversionRate.getRate();
            // Set our Result on our Result TextView
            viewBinding.tvConversionRate.setText(String.valueOf(rate));
            Log.i("MainActivity", "Conversion Amount " + rate);
        } else {
            viewBinding.textInputAmount.getEditText().setError("Field is Required");
        }
    }

    private void setUpSpinnerAdapter(List<ConversionRate> rates) {
        SpinnerAdapter adapter = new SpinnerAdapter(this, rates);
        // Apply the adapter to the spinner
        viewBinding.fromSpinner.setAdapter(adapter);
        viewBinding.toSpinner.setAdapter(adapter);
        viewBinding.fromSpinner.setOnItemSelectedListener(this);

        // Check if our Spinner has a Selected value before casting it to of type ConversionRate
        if (viewBinding.fromSpinner.getSelectedItem() != null && viewBinding.toSpinner.getSelectedItem() != null) {
            fromConversionRates = (ConversionRate) viewBinding.fromSpinner.getSelectedItem();
            toConversionRate = (ConversionRate) viewBinding.toSpinner.getSelectedItem();
            viewBinding.textInputAmount.setHint(fromConversionRates.getCountry());
            viewBinding.tvCountry.setText(toConversionRate.getCountry());
        }

        viewBinding.toSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                toConversionRate = (ConversionRate) adapterView.getItemAtPosition(i);
                viewBinding.tvCountry.setText(String.valueOf(toConversionRate.getRate()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //handle when no item selected
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        fromConversionRates = (ConversionRate) adapterView.getItemAtPosition(i);
        viewBinding.textInputAmount.setHint(fromConversionRates.getCountry());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
