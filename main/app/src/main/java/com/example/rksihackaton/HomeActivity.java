package com.example.rksihackaton;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    private Button more_currencies_btn, back_btn, back_btn2, ag_btn, au_btn, pt_btn, pd_btn;
    private TextView investorDescription;
    private ImageView investorLogo;
    private ImageButton gazprom, moex, sberbank, rosneft;
    private LinearLayout currenciesLayout, investorsLayout;
    private ScrollView currenciesScroll;
    private LineChart metalsChart, investorsChart;
    private ConstraintLayout majorContent, currenciesNgraphs, investorsRates;


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
        back_btn2 = findViewById(R.id.back_btn2);

        //Отображение графиков металлов и списка валют
        currenciesLayout = findViewById(R.id.currenciesLayout);
        currenciesScroll = findViewById(R.id.currenciesScroll);

        //Драг. металлы
        metalsChart = findViewById(R.id.metalsChart);
        ag_btn = findViewById(R.id.ag_btn);
        au_btn = findViewById(R.id.au_btn);
        pt_btn = findViewById(R.id.pt_btn);
        pd_btn = findViewById(R.id.pd_btn);

        //Инвесторы
        gazprom = findViewById(R.id.gazprom);
        moex = findViewById(R.id.moex);
        sberbank = findViewById(R.id.sberbank);
        rosneft = findViewById(R.id.rosneft);

        //Инфа об инвесторах
        investorDescription = findViewById(R.id.investorDescription);
        investorLogo = findViewById(R.id.investorLogo);
        investorsChart = findViewById(R.id.investorsChart);
        investorsLayout = findViewById(R.id.investorsLayout);

        currenciesNgraphs = findViewById(R.id.currenciesNgraphs);
        majorContent = findViewById(R.id.majorContent);
        investorsRates = findViewById(R.id.investorsRates);
        //----------------------------------------------------------//

        Map<Integer, String> investorDescr = new HashMap<>();
        investorDescr.put(R.id.gazprom, "«Газпром» — российская энергетическая компания, одна из крупнейших нефтегазовых компаний мира. ");
        investorDescr.put(R.id.moex, "Московская биржа — финансовая компания, основной деятельностью которой является организация биржевых торгов.");
        investorDescr.put(R.id.sberbank, "Сбер — крупнейший российский банк, предлагающий разнообразные финансовые услуги.");
        investorDescr.put(R.id.rosneft, "Роснефть — крупнейшая нефтяная компания России, занимающаяся добычей, переработкой и продажей нефти.");


        String currenciesJson = "{ \"currencies\": [ {\"currency\": \"AUD\", \"value\": \"63\"}, {\"currency\": \"AZN\", \"value\": \"58\"}, {\"currency\": \"AMD\", \"value\": \"25\"}, {\"currency\": \"BYN\", \"value\": \"29\"}, {\"currency\": \"BGN\", \"value\": \"54\"}, {\"currency\": \"BRL\", \"value\": \"16\"}, {\"currency\": \"HUF\", \"value\": \"25\"}, {\"currency\": \"KRW\", \"value\": \"69\"}, {\"currency\": \"VND\", \"value\": \"41\"}, {\"currency\": \"HKD\", \"value\": \"12\"}, {\"currency\": \"GEL\", \"value\": \"35\"}, {\"currency\": \"DKK\", \"value\": \"14\"}, {\"currency\": \"AED\", \"value\": \"27\"}, {\"currency\": \"USD\", \"value\": \"100\"}, {\"currency\": \"EUR\", \"value\": \"106\"}, {\"currency\": \"EGP\", \"value\": \"19\"}, {\"currency\": \"INR\", \"value\": \"11\"}, {\"currency\": \"IDR\", \"value\": \"63\"}, {\"currency\": \"KZT\", \"value\": \"19\"}, {\"currency\": \"CAD\", \"value\": \"70\"}, {\"currency\": \"QAR\", \"value\": \"27\"}, {\"currency\": \"KGS\", \"value\": \"11\"}, {\"currency\": \"CNY\", \"value\": \"13\"}, {\"currency\": \"MDL\", \"value\": \"54\"}, {\"currency\": \"NZD\", \"value\": \"58\"}, {\"currency\": \"TMT\", \"value\": \"28\"}, {\"currency\": \"NOK\", \"value\": \"90\"}, {\"currency\": \"PLN\", \"value\": \"24\"}, {\"currency\": \"RON\", \"value\": \"21\"}, {\"currency\": \"XDR\", \"value\": \"131\"}, {\"currency\": \"RSD\", \"value\": \"90\"}, {\"currency\": \"SGD\", \"value\": \"74\"}, {\"currency\": \"TJS\", \"value\": \"91\"}, {\"currency\": \"THB\", \"value\": \"29\"}, {\"currency\": \"TRY\", \"value\": \"28\"}, {\"currency\": \"UZS\", \"value\": \"77\"}, {\"currency\": \"UAH\", \"value\": \"24\"},\n {\"currency\": \"GBP\", \"value\": \"128\"},\n {\"currency\": \"CZK\", \"value\": \"42\"},\n {\"currency\": \"SEK\", \"value\": \"91\"}, {\"currency\": \"CHF\", \"value\": \"113\"}, {\"currency\": \"ZAR\", \"value\": \"56\"}, {\"currency\": \"JPY\", \"value\": \"66\"} ] }";

        Map<String, String> flagMap = new HashMap<>();

          flagMap.put("AUD",	"Австралия 🇦🇺");
          flagMap.put("AZN",	"Азербайджан	🇦🇿");
          flagMap.put("AMD",	"Армения	🇦🇲");
          flagMap.put("BYN",	"Беларусь 🇧🇾");
          flagMap.put("BGN",	"Болгария 🇧🇬");
          flagMap.put("BRL",	"Бразилия 🇧🇷");
          flagMap.put("HUF",	"Венгрия	🇭🇺");
          flagMap.put("KRW",	"Южная Корея	🇰🇷");
          flagMap.put("VND",	"Вьетнам	🇻🇳");
          flagMap.put("HKD",	"Гонконг	🇭🇰");
          flagMap.put("GEL",	"Грузия	🇬🇪");
          flagMap.put("DKK",	"Дания 🇩🇰");
          flagMap.put("AED",	"ОАЭ	🇦🇪");
          flagMap.put("USD",	"США	🇺🇸");
          flagMap.put("EUR",	"Еврозона 🇪🇺");
          flagMap.put("EGP",	"Египет 🇪🇬");
          flagMap.put("INR",	"Индия 🇮🇳");
          flagMap.put("IDR",	"Индонезия 🇮🇩");
          flagMap.put("KZT",	"Казахстан 🇰🇿");
          flagMap.put("CAD",	"Канада 🇨🇦");
          flagMap.put("QAR",	"Катар 🇶🇦");
          flagMap.put("KGS",	"Кыргызстан 🇰🇬");
          flagMap.put("CNY",	"Китай 🇨🇳");
          flagMap.put("MDL",	"Молдова	🇲🇩");
          flagMap.put("NZD",	"Новая Зеландия 🇳🇿");
          flagMap.put("TMT",	"Туркменистан 🇹🇲");
          flagMap.put("NOK",	"Норвегия 🇳🇴");
          flagMap.put("PLN",	"Польша	🇵🇱");
          flagMap.put("RON",	"Румыния	🇷🇴");
          flagMap.put("XDR",	"МВФ");
          flagMap.put("RSD",	"Сербия 🇷🇸");
          flagMap.put("SGD",	"Сингапур 🇸🇬");
          flagMap.put("TJS",	"Таджикистан	🇹🇯");
          flagMap.put("THB",	"Таиланд	🇹🇭");
          flagMap.put("TRY",	"Турция 🇹🇷");
          flagMap.put("UZS",	"Узбекистан 🇺🇿");
          flagMap.put("UAH",	"Украина	🇺🇦");
          flagMap.put("GBP",	"Великобритания 🇬🇧");
          flagMap.put("CZK",	"Чехия 🇨🇿");
          flagMap.put("SEK",	"Швеция 🇸🇪");
          flagMap.put("CHF",	"Швейцария 🇨🇭");
          flagMap.put("ZAR",	"Южноафриканская Республика	🇿🇦");
          flagMap.put("JPY",	"Япония 🇯🇵");

        try {
            JSONObject currenciesJsonObject = new JSONObject(currenciesJson);
            JSONArray currencies = currenciesJsonObject.getJSONArray("currencies");


            TableRow.LayoutParams row_p = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    100);

            TableRow row = new TableRow(HomeActivity.this);
            row.setLayoutParams(row_p);

            CreateTextView("Валюты", row, 1.5f);
            CreateTextView("Стоимость в рублях", row, 1);

            currenciesLayout.addView(row);

            for (int i = 0; i < currencies.length(); i++) {
                JSONObject currencyObject = currencies.getJSONObject(i);
                String currency = currencyObject.getString("currency");
                String value = currencyObject.getString("value");

                TableRow.LayoutParams row_params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        100);

                TableRow tableRow = new TableRow(HomeActivity.this);
                tableRow.setLayoutParams(row_params);

                CreateTextView(currency + " (" +flagMap.get(currency) + ")", tableRow, 1.5f);
                CreateTextView(value, tableRow, 1);

                currenciesLayout.addView(tableRow);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //----------------------------------------------------------//

        String metalsJson = "{\"metals\":{\"metal\":[{\"date\":\"11.12.2024\",\"gold_price\":\"8593.1299999999992\",\"silver_price\":\"101.73\",\"platinum_price\":\"3058.52\",\"palladium_price\":\"3161.4400000000001\"},{\"date\":\"10.12.2024\",\"gold_price\":\"8426.1900000000005\",\"silver_price\":\"99.400000000000006\",\"platinum_price\":\"2990.5300000000002\",\"palladium_price\":\"3089.5700000000002\"},{\"date\":\"07.12.2024\",\"gold_price\":\"8439.1700000000001\",\"silver_price\":\"100.19\",\"platinum_price\":\"3011.0799999999999\",\"palladium_price\":\"3116.5599999999999\"},{\"date\":\"06.12.2024\",\"gold_price\":\"8803.7399999999998\",\"silver_price\":\"102.48999999999999\",\"platinum_price\":\"3131.0799999999999\",\"palladium_price\":\"3250.7399999999998\"},{\"date\":\"05.12.2024\",\"gold_price\":\"8849.5200000000004\",\"silver_price\":\"103.56999999999999\",\"platinum_price\":\"3200.46\",\"palladium_price\":\"3327.8099999999999\"},{\"date\":\"04.12.2024\",\"gold_price\":\"9020.3400000000001\",\"silver_price\":\"103.97\",\"platinum_price\":\"3216\",\"palladium_price\":\"3369.6300000000001\"},{\"date\":\"03.12.2024\",\"gold_price\":\"9134.9300000000003\",\"silver_price\":\"105.79000000000001\",\"platinum_price\":\"3239.0300000000002\",\"palladium_price\":\"3387.1999999999998\"}]}}";


        //----------------------------------------------------------//


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
            GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(metalsJson, metalsChart)[1]);

        });

        au_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(metalsJson, metalsChart)[0]);

        });

        pt_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(metalsJson, metalsChart)[2]);

        });


        pd_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.GONE);
            currenciesNgraphs.setVisibility(View.VISIBLE);
            currenciesScroll.setVisibility(View.GONE);
            metalsChart.setVisibility(View.VISIBLE);
            GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(metalsJson, metalsChart)[3]);
        });

        back_btn2.setOnClickListener(view -> {
            investorsRates.setVisibility(View.GONE);
            majorContent.setVisibility(View.VISIBLE);
        });

        //Установка OnClickListener при нажатии на акцию компании
        for(int i = 0; i < investorsLayout.getChildCount(); i++){
            int loop_id = i;
            investorsLayout.getChildAt(i).setOnClickListener(view -> {
                investorsRates.setVisibility(View.VISIBLE);
                majorContent.setVisibility(View.GONE);

                String inv = investorDescr.get(investorsLayout.getChildAt(loop_id).getId());
                investorDescription.setText(inv);
                int colorResId = getColorId(loop_id);

                investorDescription.setTextColor(ContextCompat.getColor(HomeActivity.this, colorResId));
                List<Entry> actions = new ArrayList<>();
                Random rand = new Random();
                actions.add(new Entry(0, rand.nextFloat() * (40 - 12) + 12));
                actions.add(new Entry(1, rand.nextFloat() * (40 - 12) + 12));
                actions.add(new Entry(2, rand.nextFloat() * (40 - 12) + 12));
                actions.add(new Entry(3, rand.nextFloat() * (40 - 12) + 12));

                LineDataSet set = new LineDataSet(actions, inv);
                set.setColor(ContextCompat.getColor(HomeActivity.this, colorResId));

                GraphBuilder.BuildLineGraph(investorsChart, set);

                int logoID = getLogoId(loop_id);
                investorLogo.setImageResource(logoID);
            });
        }


    }

public LineDataSet[] SetupMetalGraph(String json, LineChart lineChart){
    Gson gson = new Gson();
    JsonObject metalsJsonObject = gson.fromJson(json, JsonObject.class);
    JsonObject metals = metalsJsonObject.getAsJsonObject("metals");
    JsonArray metalArray = metals.getAsJsonArray("metal");

    List<Entry> goldPrices = new ArrayList<>();
    List<Entry> silverPrices = new ArrayList<>();
    List<Entry> platinumPrices = new ArrayList<>();
    List<Entry> palladiumPrices = new ArrayList<>();

    List<String> dates = new ArrayList<>();

    for (int i = 0; i < metalArray.size(); i++) {
        JsonObject metalData = metalArray.get(i).getAsJsonObject();
        dates.add(metalData.get("date").getAsString());
        float goldPrice = metalData.get("gold_price").getAsFloat();
        float silverPrice = metalData.get("silver_price").getAsFloat();
        float platinumPrice = metalData.get("platinum_price").getAsFloat();
        float palladiumPrice = metalData.get("palladium_price").getAsFloat();

        goldPrices.add(new Entry(i, goldPrice));
        silverPrices.add(new Entry(i, silverPrice));
        platinumPrices.add(new Entry(i, platinumPrice));
        palladiumPrices.add(new Entry(i, palladiumPrice));
    }

    LineDataSet[] sets = new LineDataSet[4];
    sets[0] = new LineDataSet(goldPrices, "Золото");
    sets[1] = new LineDataSet(silverPrices, "Серебро");
    sets[2] = new LineDataSet(platinumPrices, "Платина");
    sets[3] = new LineDataSet(palladiumPrices, "Палладий");
    sets[0].setColor(ColorTemplate.rgb("FFD700"));
    sets[1].setColor(ColorTemplate.rgb("C0C0C0"));
    sets[2].setColor(ColorTemplate.rgb("e5e4e2"));
    sets[3].setColor(ColorTemplate.rgb("b1b1b1"));

    XAxis xAxis = lineChart.getXAxis();
    xAxis.setValueFormatter(new IndexAxisValueFormatter(dates)); // dateValues - ваш список дат
    xAxis.setGranularity(1f); // шаг генерации меток
    xAxis.setPosition(XAxis.XAxisPosition.TOP); // позиция меток оси X
    xAxis.setLabelRotationAngle(45); // угол поворота меток для улучшения читаемости
    xAxis.setTextSize(12); // размер шрифта меток

    return sets;
}

    private int getColorId(int id) {
        switch (id) {
            case 0:
                return R.color.gazprom;
            case 1:
                return R.color.moex;
            case 2:
                return R.color.sber;
            case 3:
                return R.color.rosneft;
            default:
                return R.color.btns_color;
        }
    }


    private int getLogoId(int id) {
        switch (id) {
            case 0:
                return R.drawable.gprom;
            case 1:
                return R.drawable.mel;
            case 2:
                return R.drawable.sber;
            default:
                return R.drawable.rneft;

        }
    }


    void CreateTextView(String text, TableRow TR, float weight){
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        params.weight = weight;

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