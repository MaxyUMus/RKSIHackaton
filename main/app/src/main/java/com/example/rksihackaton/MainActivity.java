package com.example.rksihackaton;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
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
                        .url("http://10.0.2.2:8080/login")
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
                                    // Intent intent = new Intent(MainActivity.this, activity_home.class);
                                    // startActivity(intent);
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
                        .url("http://10.0.2.2:8080/register")
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

        lineChart = findViewById(R.id.graph);
        showLineChart();

        barChart = findViewById(R.id.barChart);
        showBarChart();
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
        ArrayList<Entry> entries = new ArrayList<>();
        ArrayList<Entry> entries1 = new ArrayList<>();
        // Пример данных
        entries.add(new Entry(0, 1));
        entries.add(new Entry(1, 3));
        entries.add(new Entry(2, 2));
        entries.add(new Entry(3, 5));
        entries.add(new Entry(4, 4));

        entries1.add(new Entry(0, 2));
        entries1.add(new Entry(1, 8));
        entries1.add(new Entry(2, 1));
        entries1.add(new Entry(3, 2));
        entries1.add(new Entry(4, 12));

        LineDataSet dataSet = new LineDataSet(entries, "Label"); // Название графика
        LineDataSet dataSet1 = new LineDataSet(entries1, "Label1"); // Название графика
        dataSet.setColor(ColorTemplate.rgb("FFD700"));
        GraphBuilder.BuildLineGraph(lineChart, dataSet, dataSet1);
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
