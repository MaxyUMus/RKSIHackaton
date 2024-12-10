package com.example.rksihackaton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private Button more_currencies_btn, back_btn, ag_btn, au_btn, pt_btn, pd_btn;
    private LinearLayout currenciesLayout;
    private ScrollView currenciesScroll;
    private LineChart metalsChart;
    private ConstraintLayout currenciesNgraphs, majorContent;


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
        more_currencies_btn = findViewById(R.id.more_currencies_btn);
        back_btn = findViewById(R.id.back_btn);
        currenciesLayout = findViewById(R.id.currenciesLayout);
        currenciesScroll = findViewById(R.id.currenciesScroll);

        metalsChart = findViewById(R.id.metalsChart);
        ag_btn = findViewById(R.id.ag_btn);
        au_btn = findViewById(R.id.au_btn);
        pt_btn = findViewById(R.id.pt_btn);
        pd_btn = findViewById(R.id.pd_btn);

        currenciesNgraphs = findViewById(R.id.currenciesNgraphs);
        majorContent = findViewById(R.id.majorContent);



        String currenciesJson = "{ \"currencies\": [ {\"currency\": \"AUD\", \"value\": \"63\"}, {\"currency\": \"AZN\", \"value\": \"58\"}, {\"currency\": \"AMD\", \"value\": \"25\"}, {\"currency\": \"BYN\", \"value\": \"29\"}, {\"currency\": \"BGN\", \"value\": \"54\"}, {\"currency\": \"BRL\", \"value\": \"16\"}, {\"currency\": \"HUF\", \"value\": \"25\"}, {\"currency\": \"KRW\", \"value\": \"69\"}, {\"currency\": \"VND\", \"value\": \"41\"}, {\"currency\": \"HKD\", \"value\": \"12\"}, {\"currency\": \"GEL\", \"value\": \"35\"}, {\"currency\": \"DKK\", \"value\": \"14\"}, {\"currency\": \"AED\", \"value\": \"27\"}, {\"currency\": \"USD\", \"value\": \"100\"}, {\"currency\": \"EUR\", \"value\": \"106\"}, {\"currency\": \"EGP\", \"value\": \"19\"}, {\"currency\": \"INR\", \"value\": \"11\"}, {\"currency\": \"IDR\", \"value\": \"63\"}, {\"currency\": \"KZT\", \"value\": \"19\"}, {\"currency\": \"CAD\", \"value\": \"70\"}, {\"currency\": \"QAR\", \"value\": \"27\"}, {\"currency\": \"KGS\", \"value\": \"11\"}, {\"currency\": \"CNY\", \"value\": \"13\"}, {\"currency\": \"MDL\", \"value\": \"54\"}, {\"currency\": \"NZD\", \"value\": \"58\"}, {\"currency\": \"TMT\", \"value\": \"28\"}, {\"currency\": \"NOK\", \"value\": \"90\"}, {\"currency\": \"PLN\", \"value\": \"24\"}, {\"currency\": \"RON\", \"value\": \"21\"}, {\"currency\": \"XDR\", \"value\": \"131\"}, {\"currency\": \"RSD\", \"value\": \"90\"}, {\"currency\": \"SGD\", \"value\": \"74\"}, {\"currency\": \"TJS\", \"value\": \"91\"}, {\"currency\": \"THB\", \"value\": \"29\"}, {\"currency\": \"TRY\", \"value\": \"28\"}, {\"currency\": \"UZS\", \"value\": \"77\"}, {\"currency\": \"UAH\", \"value\": \"24\"},\n {\"currency\": \"GBP\", \"value\": \"128\"},\n {\"currency\": \"CZK\", \"value\": \"42\"},\n {\"currency\": \"SEK\", \"value\": \"91\"}, {\"currency\": \"CHF\", \"value\": \"113\"}, {\"currency\": \"ZAR\", \"value\": \"56\"}, {\"currency\": \"JPY\", \"value\": \"66\"} ] }";

        try {
            JSONObject currenciesJsonObject = new JSONObject(currenciesJson);
            JSONArray currencies = currenciesJsonObject.getJSONArray("currencies");

            for (int i = 0; i < currencies.length(); i++) {
                JSONObject currencyObject = currencies.getJSONObject(i);
                String currency = currencyObject.getString("currency");
                String value = currencyObject.getString("value");

                TableRow.LayoutParams row_params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        100);

                TableRow tableRow = new TableRow(HomeActivity.this);
                tableRow.setLayoutParams(row_params);

                CreateTextView(currency, tableRow);
                CreateTextView(value, tableRow);

                currenciesLayout.addView(tableRow);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        String metalsJson = "{\"metals\":{\"metal\":[{\"date\":\"11.12.2024\",\"gold_price\":\"8593.1299999999992\",\"silver_price\":\"101.73\",\"platinum_price\":\"3058.52\",\"palladium_price\":\"3161.4400000000001\"},{\"date\":\"10.12.2024\",\"gold_price\":\"8426.1900000000005\",\"silver_price\":\"99.400000000000006\",\"platinum_price\":\"2990.5300000000002\",\"palladium_price\":\"3089.5700000000002\"},{\"date\":\"07.12.2024\",\"gold_price\":\"8439.1700000000001\",\"silver_price\":\"100.19\",\"platinum_price\":\"3011.0799999999999\",\"palladium_price\":\"3116.5599999999999\"},{\"date\":\"06.12.2024\",\"gold_price\":\"8803.7399999999998\",\"silver_price\":\"102.48999999999999\",\"platinum_price\":\"3131.0799999999999\",\"palladium_price\":\"3250.7399999999998\"},{\"date\":\"05.12.2024\",\"gold_price\":\"8849.5200000000004\",\"silver_price\":\"103.56999999999999\",\"platinum_price\":\"3200.46\",\"palladium_price\":\"3327.8099999999999\"},{\"date\":\"04.12.2024\",\"gold_price\":\"9020.3400000000001\",\"silver_price\":\"103.97\",\"platinum_price\":\"3216\",\"palladium_price\":\"3369.6300000000001\"},{\"date\":\"03.12.2024\",\"gold_price\":\"9134.9300000000003\",\"silver_price\":\"105.79000000000001\",\"platinum_price\":\"3239.0300000000002\",\"palladium_price\":\"3387.1999999999998\"}]}}";

        // Используем Gson для разбора JSON
        Gson gson = new Gson();
        JsonObject metalsJsonObject = gson.fromJson(metalsJson, JsonObject.class);
        JsonObject metals = metalsJsonObject.getAsJsonObject("metals");
        JsonArray metalArray = metals.getAsJsonArray("metal");

        // Создаем списки для хранения данных графика
        List<Entry> goldPrices = new ArrayList<>();
        List<Entry> silverPrices = new ArrayList<>();
        List<Entry> platinumPrices = new ArrayList<>();
        List<Entry> palladiumPrices = new ArrayList<>();

        // Заполняем списки данными
        for (int i = 0; i < metalArray.size(); i++) {
            JsonObject metalData = metalArray.get(i).getAsJsonObject();
            String date = metalData.get("date").getAsString();
            float goldPrice = metalData.get("gold_price").getAsFloat();
            float silverPrice = metalData.get("silver_price").getAsFloat();
            float platinumPrice = metalData.get("platinum_price").getAsFloat();
            float palladiumPrice = metalData.get("palladium_price").getAsFloat();

            // Добавляем данные в соответствующий список
            goldPrices.add(new Entry(i, goldPrice));
            silverPrices.add(new Entry(i, silverPrice));
            platinumPrices.add(new Entry(i, platinumPrice));
            palladiumPrices.add(new Entry(i, palladiumPrice));
        }

        LineDataSet gold_set = new LineDataSet(goldPrices, "Золото"); // Название графика
        LineDataSet silver_set = new LineDataSet(silverPrices, "Серебро"); // Название графика
        LineDataSet platinum_set = new LineDataSet(platinumPrices, "Платина"); // Название графика
        LineDataSet palladium_set = new LineDataSet(palladiumPrices, "Палладий"); // Название графика
        gold_set.setColor(ColorTemplate.rgb("FFD700"));
        silver_set.setColor(ColorTemplate.rgb("C0C0C0"));
        platinum_set.setColor(ColorTemplate.rgb("e5e4e2"));
        palladium_set.setColor(ColorTemplate.rgb("b1b1b1"));

        more_currencies_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.VISIBLE);
            metalsChart.setVisibility(View.GONE);
        });

        back_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.VISIBLE);
            currenciesNgraphs.setVisibility(View.GONE);
        });

        ag_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, silver_set);

        });

        au_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, gold_set);

        });

        pt_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, platinum_set);

        });

        pd_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, palladium_set);

        });

    }

    void CreateTextView(String text, TableRow TR){
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = 1;

        TextView textView = new TextView(HomeActivity.this);
        textView.setLayoutParams(params);
        textView.setPadding(10, 10, 10, 10);
        textView.setText(text);
        textView.setTextColor(Color.WHITE);
        textView.setMaxEms(2);
        textView.setTextSize(15);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        TR.addView(textView);
    }
}