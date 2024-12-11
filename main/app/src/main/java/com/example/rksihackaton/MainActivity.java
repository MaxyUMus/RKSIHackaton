package com.example.rksihackaton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    Button aut;
    Button reg;
    OkHttpClient client;
    EditText email;
    EditText password;

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

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);

//            String emailText = email.getText().toString().trim();
//            String passwordText = password.getText().toString().trim();
//
//            if (emailText.isEmpty() || passwordText.isEmpty()) {
//                Toast.makeText(MainActivity.this, "Email and password cannot be empty", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            String json = "{\"email\":\"" + emailText + "\", \"password\":\"" + passwordText + "\", \"action\":\"login\"}";
//
//            new Thread(() -> {
//                RequestBody body = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));
//
//                Request request = new Request.Builder()
//                        .url("https://0a0d-94-141-124-197.ngrok-free.app/login")
//                        .post(body)
//                        .build();
//
//                try (Response response = client.newCall(request).execute()) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        String responseBody = response.body().string();
//                        runOnUiThread(() -> {
//                            try {
//                                JSONObject jsonResponse = new JSONObject(responseBody);
//                                String status = jsonResponse.getString("status");
//                                String message = jsonResponse.getString("message");
//
//
//                                if ("success".equals(status)) {
//                                    Toast.makeText(MainActivity.this, "Login successful: " + message, Toast.LENGTH_LONG).show();
//                                    // Переход на вторую активность
//                                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
//                                    startActivity(intent);
//                                } else {
//                                    Toast.makeText(MainActivity.this, "Login failed: " + message, Toast.LENGTH_LONG).show();
//                                }
//                            } catch (JSONException e) {
//                                Toast.makeText(MainActivity.this, "Error parsing response", Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    } else {
//                        runOnUiThread(() -> Toast.makeText(MainActivity.this, "Authorization failed. Server error.", Toast.LENGTH_LONG).show());
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    runOnUiThread(() -> {
//                        Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                    });
//                }
//            }).start();
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
