package com.fhmg.artosexchangeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MoneyCurrencyConverter extends AppCompatActivity {
    Spinner sp1,sp2;
    EditText ed1;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_currency_converter);
        ed1 = findViewById(R.id.txtamount);
        sp1 = findViewById(R.id.spfrom);
        sp2 = findViewById(R.id.spto);
        b1 = findViewById(R.id.btn1);

        String[] from = {"IDR"};
        ArrayAdapter ad = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,from);
        sp1.setAdapter(ad);
        String[] to = {"USD","Euro","Yen","Yuan","Rupee India"};
        ArrayAdapter ad1 = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,to);
        sp2.setAdapter(ad1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double tot;
                Double amount = Double.parseDouble(ed1.getText().toString());
                if(sp1.getSelectedItem().toString()== "IDR" && sp2.getSelectedItem().toString()== "USD"){
                    tot = amount * 0.000071;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();
                }
                else if (sp1.getSelectedItem().toString()== "IDR" && sp2.getSelectedItem().toString()== "Euro"){
                    tot = amount * 0.000061;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();

                }
                else if (sp1.getSelectedItem().toString()== "IDR" && sp2.getSelectedItem().toString()== "Yen"){
                    tot = amount * 0.0080;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();

                }
                else if (sp1.getSelectedItem().toString()== "IDR" && sp2.getSelectedItem().toString()== "Yuan"){
                    tot = amount * 0.00046;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();

                }
                else if (sp1.getSelectedItem().toString()== "IDR" && sp2.getSelectedItem().toString()== "Rupee India"){
                    tot = amount * 0.0053;
                    Toast.makeText(getApplicationContext(),tot.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });

    }

}