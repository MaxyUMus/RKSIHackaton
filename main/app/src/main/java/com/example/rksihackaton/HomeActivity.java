package com.example.rksihackaton;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity {
    private Button more_currencies_btn, back_btn, back_btn2, ag_btn, au_btn, pt_btn, pd_btn;
    private TextView investorDescription, dollarRate, euroRate, uanRate, bynRate;
    private ImageView investorLogo;
    private ImageButton gazprom, moex, sberbank, rosneft;
    private LinearLayout currenciesLayout, investorsLayout;
    private ScrollView currenciesScroll;
    private LineChart metalsChart, investorsChart;
    private ConstraintLayout majorContent, currenciesNgraphs, investorsRates;
    Map<String, String> flagMap = new HashMap<>();
    public OkHttpClient client;

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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        client = MainActivity.createHttpClient();

        more_currencies_btn = findViewById(R.id.more_currencies_btn);
        back_btn = findViewById(R.id.back_btn);
        back_btn2 = findViewById(R.id.back_btn2);

        //Отображение графиков металлов и списка валют
        currenciesLayout = findViewById(R.id.currenciesLayout);
        currenciesScroll = findViewById(R.id.currenciesScroll);

        dollarRate = findViewById(R.id.dollarRate);
        euroRate = findViewById(R.id.euroRate);
        uanRate = findViewById(R.id.uanRate);
        bynRate = findViewById(R.id.bynRate);

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

        majorContent.setVisibility(View.VISIBLE);
        currenciesNgraphs.setVisibility(View.GONE);
        investorsRates.setVisibility(View.GONE);

        //----------------------------------------------------------//

        Map<Integer, String> investorDescr = new HashMap<>();
        investorDescr.put(R.id.gazprom, "Газпром — российская энергетическая компания, одна из крупнейших нефтегазовых компаний мира.");
        investorDescr.put(R.id.moex, "MOEX — московская финансовая компания, организующая биржевые торги.");
        investorDescr.put(R.id.sberbank, "Сбер — крупнейший российский банк, предлагающий разнообразные финансовые услуги.");
        investorDescr.put(R.id.rosneft, "Роснефть — крупнейшая нефтяная компания России, занимающаяся добычей, переработкой и продажей нефти.");

        InitCurrencies();

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

        more_currencies_btn.setOnClickListener(view -> {
            String json = "{\"action\":\"get_currencies\"}";
            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url(Constants.server_url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                ParseCurrencies(jsonResponse.toString());
                                majorContent.setVisibility(View.GONE);
                                currenciesNgraphs.setVisibility(View.VISIBLE);
                                currenciesScroll.setVisibility(View.VISIBLE);
                                metalsChart.setVisibility(View.GONE);
                            } catch (JSONException e) {
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        back_btn.setOnClickListener(view -> {
            majorContent.setVisibility(View.VISIBLE);
            currenciesNgraphs.setVisibility(View.GONE);
        });

        ag_btn.setOnClickListener(view -> {
            String json = "{\"action\":\"get_metals\"}";
            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url(Constants.server_url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                majorContent.setVisibility(View.GONE);
                                currenciesNgraphs.setVisibility(View.VISIBLE);
                                currenciesScroll.setVisibility(View.GONE);
                                metalsChart.setVisibility(View.VISIBLE);
                                GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(jsonResponse.toString(), metalsChart)[1]);
                            } catch (JSONException e) {
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        au_btn.setOnClickListener(view -> {
            String json = "{\"action\":\"get_metals\"}";
            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url(Constants.server_url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                majorContent.setVisibility(View.GONE);
                                currenciesNgraphs.setVisibility(View.VISIBLE);
                                currenciesScroll.setVisibility(View.GONE);
                                metalsChart.setVisibility(View.VISIBLE);
                                GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(jsonResponse.toString(), metalsChart)[0]);
                            } catch (JSONException e) {
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        pt_btn.setOnClickListener(view -> {
            String json = "{\"action\":\"get_metals\"}";
            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url(Constants.server_url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                majorContent.setVisibility(View.GONE);
                                currenciesNgraphs.setVisibility(View.VISIBLE);
                                currenciesScroll.setVisibility(View.GONE);
                                metalsChart.setVisibility(View.VISIBLE);
                                GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(jsonResponse.toString(), metalsChart)[2]);
                            } catch (JSONException e) {
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        });

        pd_btn.setOnClickListener(view -> {
            String json = "{\"action\":\"get_metals\"}";
            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url(Constants.server_url)
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                majorContent.setVisibility(View.GONE);
                                currenciesNgraphs.setVisibility(View.VISIBLE);
                                currenciesScroll.setVisibility(View.GONE);
                                metalsChart.setVisibility(View.VISIBLE);
                                GraphBuilder.BuildLineGraph(metalsChart, SetupMetalGraph(jsonResponse.toString(), metalsChart)[3]);
                            } catch (JSONException e) {
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
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

                LineDataSet set = new LineDataSet(actions, "Инвестор");
                set.setColor(ContextCompat.getColor(HomeActivity.this, colorResId));

                GraphBuilder.BuildLineGraph(investorsChart, set);

                int logoID = getLogoId(loop_id);
                investorLogo.setImageResource(logoID);
            });
        }
    }

    public void InitCurrencies(){
        String json = "{\"action\":\"get_currencies\"}";
        new Thread(() -> {
            RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url(Constants.server_url)
                    .post(body)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            ParseCurrencies(jsonResponse.toString());
                        } catch (JSONException e) {

                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    public void ParseCurrencies(String currenciesJson){
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
                String currency = currencyObject.getString("name");
                String value = currencyObject.getString("value");

                TableRow.LayoutParams row_params = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        100);

                TableRow tableRow = new TableRow(HomeActivity.this);
                tableRow.setLayoutParams(row_params);

                switch (currency) {
                    case "USD":
                        dollarRate.setText(value);
                        break;
                    case "EUR":
                        euroRate.setText(value);
                        break;
                    case "CNY":
                        uanRate.setText(value);
                        break;
                    case "BYN":
                        bynRate.setText(value);
                        break;
                }

                CreateTextView(currency + " (" +flagMap.get(currency) + ")", tableRow, 1.5f);
                CreateTextView(value, tableRow, 1);

                currenciesLayout.addView(tableRow);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
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