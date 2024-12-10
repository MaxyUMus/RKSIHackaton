package com.example.rksihackaton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;

public class HomeActivity extends AppCompatActivity {

    private Button main, rate_tapes, profile, exchange, metal, stocks;
    private LinearLayout main_layout, rates_layout, profile_layout;
    private LineChart chart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//
//        main = findViewById(R.id.main_btn);
//        rate_tapes = findViewById(R.id.rate_tapes_btn);
//        profile = findViewById(R.id.profile_btn);
//
//        exchange = findViewById(R.id.exchange_rate);
//        metal = findViewById(R.id.metal_rate);
//        stocks = findViewById(R.id.stocks_rate);
//
//        main_layout = findViewById(R.id.main_layout);
//        rates_layout = findViewById(R.id.rates_layout);
//        profile_layout = findViewById(R.id.profile_layout);
//
//        chart = findViewById(R.id.)


    }
}