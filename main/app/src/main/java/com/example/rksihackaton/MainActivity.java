package com.example.rksihackaton;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class MainActivity extends AppCompatActivity {
    Button aut;
    Button reg;
    OkHttpClient client;
    EditText email;
    EditText password;


    private LineChart lineChart;
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);

        client = createHttpClient();

        aut = findViewById(R.id.btn_login);
        reg = findViewById(R.id.btn_register);

        aut.setOnClickListener(view -> {

            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                Toast.makeText(MainActivity.this, "Email and password cannot be empty", Toast.LENGTH_LONG).show();
                return;
            }

            String json = "{\"email\":\"" + emailText + "\", \"password\":\"" + passwordText + "\", \"action\":\"login\"}";

            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url("https://0a0d-94-141-124-197.ngrok-free.app/login")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                String status = jsonResponse.getString("status");
                                String message = jsonResponse.getString("message");


                                if ("success".equals(status)) {
                                    Toast.makeText(MainActivity.this, "Login successful: " + message, Toast.LENGTH_LONG).show();
                                    // Переход на вторую активность
                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Login failed: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Authorization failed. Server error.", Toast.LENGTH_LONG).show());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }).start();
        });



        reg.setOnClickListener(view -> {

            String emailText = email.getText().toString().trim();
            String passwordText = password.getText().toString().trim();
            String json = "{\"email\":\"" + emailText + "\", \"password\":\"" + passwordText + "\", \"action\":\"register\"}";

            new Thread(() -> {
                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

                Request request = new Request.Builder()
                        .url("https://0a0d-94-141-124-197.ngrok-free.app/register")
                        .post(body)
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful() && response.body() != null) {
                        String responseBody = response.body().string();
                        runOnUiThread(() -> {
                            try {
                                JSONObject jsonResponse = new JSONObject(responseBody);
                                String status = jsonResponse.getString("status");
                                String message = jsonResponse.getString("message");

                                if ("success".equals(status)) {
                                    Toast.makeText(MainActivity.this, "Registration successful: " + message, Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MainActivity.this, "Registration failed: " + message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(MainActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Registration failed. Server error.", Toast.LENGTH_LONG).show());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> {
                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
                }
            }).start();
        });

        lineChart = findViewById(R.id.lineChart);


        showLineChart();
//
//        barChart = findViewById(R.id.barChart);
//        showBarChart();
    }

    private void showBarChart() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<BarEntry> entries1 = new ArrayList<>();

        // Пример данных
        entries.add(new BarEntry(0, 4f));
        entries.add(new BarEntry(1, 6f));
        entries.add(new BarEntry(2, 2f));
        entries.add(new BarEntry(3, 8f));
        entries.add(new BarEntry(4, 5f));
        entries.add(new BarEntry(5, 2f));
        entries.add(new BarEntry(6, 3f));
        entries.add(new BarEntry(7, 3f));

        entries1.add(new BarEntry(0, 3f));
        entries1.add(new BarEntry(1, 8f));
        entries1.add(new BarEntry(2, 5f));
        entries1.add(new BarEntry(3, 12f));
        entries1.add(new BarEntry(4, 0f));
        entries1.add(new BarEntry(5, 3f));
        entries1.add(new BarEntry(6, 3f));
        entries1.add(new BarEntry(7, 9f));

        BarDataSet dataSet = new BarDataSet(entries, "Label"); // Название графика
        BarDataSet dataSet1 = new BarDataSet(entries1, "Label2"); // Название графика
        dataSet1.setColor(ColorTemplate.rgb("FFD700"));

        float groupSpace = 0.06f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset

        GraphBuilder.BuildBarGraph(barChart, groupSpace, barSpace, barWidth, dataSet, dataSet1);
    }

    private void showLineChart() {
        String jsonString = "{\"metals\":{\"metal\":[{\"date\":\"11.12.2024\",\"gold_price\":\"8593.1299999999992\",\"silver_price\":\"101.73\",\"platinum_price\":\"3058.52\",\"palladium_price\":\"3161.4400000000001\"},{\"date\":\"10.12.2024\",\"gold_price\":\"8426.1900000000005\",\"silver_price\":\"99.400000000000006\",\"platinum_price\":\"2990.5300000000002\",\"palladium_price\":\"3089.5700000000002\"},{\"date\":\"07.12.2024\",\"gold_price\":\"8439.1700000000001\",\"silver_price\":\"100.19\",\"platinum_price\":\"3011.0799999999999\",\"palladium_price\":\"3116.5599999999999\"},{\"date\":\"06.12.2024\",\"gold_price\":\"8803.7399999999998\",\"silver_price\":\"102.48999999999999\",\"platinum_price\":\"3131.0799999999999\",\"palladium_price\":\"3250.7399999999998\"},{\"date\":\"05.12.2024\",\"gold_price\":\"8849.5200000000004\",\"silver_price\":\"103.56999999999999\",\"platinum_price\":\"3200.46\",\"palladium_price\":\"3327.8099999999999\"},{\"date\":\"04.12.2024\",\"gold_price\":\"9020.3400000000001\",\"silver_price\":\"103.97\",\"platinum_price\":\"3216\",\"palladium_price\":\"3369.6300000000001\"},{\"date\":\"03.12.2024\",\"gold_price\":\"9134.9300000000003\",\"silver_price\":\"105.79000000000001\",\"platinum_price\":\"3239.0300000000002\",\"palladium_price\":\"3387.1999999999998\"}]}}";

        // Используем Gson для разбора JSON
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonString, JsonObject.class);
        JsonObject metals = jsonObject.getAsJsonObject("metals");
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
        GraphBuilder.BuildLineGraph(lineChart, gold_set, silver_set, platinum_set, palladium_set);
    }


    private OkHttpClient createHttpClient() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((java.security.KeyStore) null);
            X509TrustManager trustManager = (X509TrustManager) trustManagerFactory.getTrustManagers()[0];

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

            // Логирование запросов
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            return new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .sslSocketFactory(sslContext.getSocketFactory(), trustManager)
                    .hostnameVerifier((hostname, session) -> true)
                    .addInterceptor(loggingInterceptor)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
