package com.fhmg.artosexchangeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class MoneyCurrencyConverter extends AppCompatActivity {
    SharedPref sharedpref;
    private static final String TAG = MainActivity.class.getSimpleName();
    Spinner sp1,sp2;
    EditText ed1;
    Button b1;
    TextView result1;
    ProgressBar progressBar;
    int amount = 16000;
    String symbols="IDR";
    String tow="USD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        sharedpref = new SharedPref(this);
        if(sharedpref.loadNightModeState()==true) {
            setTheme(R.style.darktheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_currency_converter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ed1 = findViewById(R.id.txtamount);
        sp1 = findViewById(R.id.spfrom);
        sp2 = findViewById(R.id.spto);
        b1 = findViewById(R.id.btn1);
        result1 = findViewById(R.id.result1);
        progressBar = findViewById(R.id.progressBar);

        String[] from = {"IDR"};
        ArrayAdapter ad = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,from);
        sp1.setAdapter(ad);
        String[] to = {"USD","EUR","JPY","CNY","CLP","AED"};
        ArrayAdapter ad1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,to);
        sp2.setAdapter(ad1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "USD") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "USD";
                    getConverter();

                } else if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "EUR") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "EUR";
                    getConverter();

                } else if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "JPY") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "JPY";
                    getConverter();

                } else if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "CNY") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "CNY";
                    getConverter();

                } else if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "CLP") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "CLP";
                    getConverter();

                }
                else if (sp1.getSelectedItem().toString() == "IDR" && sp2.getSelectedItem().toString() == "AED") {
                    amount = Integer.parseInt(ed1.getText().toString());
                    symbols = "IDR";
                    tow = "AED";
                    getConverter();

                }

            }

            private void getConverter() {
                progressBar.setVisibility(View.VISIBLE);
                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://fcsapi.com/api-v2/forex/converter?pair1="+symbols+"&pair2="+tow+"&amount="+amount+"&access_key=tjxrYbivu5ItvtzOf6Fb";
                client.get(url, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        // Jika koneksi berhasil
                        progressBar.setVisibility(View.INVISIBLE);
                        String result = new String(responseBody);
                        Log.d(TAG, result);
                        try {
                            JSONObject responseObject = new JSONObject(result);
                            JSONObject rate = responseObject.getJSONObject("response");



                                String total = rate.getString("total");


                                HashMap<String, String> symbolx = new HashMap<>();

                                symbolx.put("currency_name", total);

                                result1.setText("Result = "+total.toString());
                            Toast.makeText(getApplicationContext(), total.toString(), Toast.LENGTH_LONG).show();


                    } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.toString()+"   You have reached maximum 3 limit per minute in free account, Please stop extra hits or upgrade your account. Restriction remove after 1 minute.", Toast.LENGTH_LONG).show();

                        }


                }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        progressBar.setVisibility(View.VISIBLE);                        String errorMessage;
                        switch (statusCode) {
                            case 401:
                                errorMessage = statusCode + " : Bad Request";
                                break;
                            case 403:
                                errorMessage = statusCode + " : Forbidden";
                                break;
                            case 404:
                                errorMessage = statusCode + " : Not Found";
                                break;
                            default:
                                errorMessage = statusCode + " : " + error.getMessage();
                                break;
                        }
                        Toast.makeText(MoneyCurrencyConverter.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                    });


            }

        });

    }
    public void restartApp() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        restartApp();

        return true;
    }
}