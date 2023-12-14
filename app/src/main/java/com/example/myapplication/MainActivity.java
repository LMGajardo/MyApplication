package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends AppCompatActivity {

    private static final String THINGSPEAK_API_KEY = "SXABYBTHUASWY5GE";
    private static final String THINGSPEAK_UPDATE_URL = "https://api.thingspeak.com/update";
    private static final String FIELD_PARAM = "field3";

    private Button btnToggleBalizaBuzzer;
    private boolean lightsAndBuzzerActivated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnToggleBalizaBuzzer = findViewById(R.id.btnToggleBalizaBuzzer);
    }

    public void toggleBalizaBuzzer(View view) {
        lightsAndBuzzerActivated = !lightsAndBuzzerActivated;
        sendDataToThingSpeak(lightsAndBuzzerActivated ? "1" : "0");
    }

    private void sendDataToThingSpeak(String value) {
        if (!isNetworkAvailable()) {
            Log.e("MainActivity", "Error: No hay conexión a Internet");
            return;
        }

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put(FIELD_PARAM, value);
        params.put("api_key", THINGSPEAK_API_KEY);

        client.post(THINGSPEAK_UPDATE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody) {
                Log.d("MainActivity", "Data sent to ThingSpeak. Response: " + new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("MainActivity", "Error al enviar datos a ThingSpeak. StatusCode: " + statusCode, error);
            }
        });
    }

    private boolean isNetworkAvailable() {
        // Agrega lógica para verificar la disponibilidad de la red
        return true;
    }
}