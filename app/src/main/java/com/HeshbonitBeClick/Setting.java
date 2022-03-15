package com.HeshbonitBeClick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.HeshbonitBeClick.databinding.ActivitySettingBinding;

public class Setting extends AppCompatActivity {
    SharedPreferences setting,integer;
    String tel,id,name,status1,to,adrees,telClient,NumRecive,TextTax,numKabala,numOrder;

    int numOfRecive,tax,numOfKabala,numOfOrder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setting=getSharedPreferences("Details",MODE_PRIVATE);
        integer=getSharedPreferences("Integer",MODE_PRIVATE);


        EditText editTextName = findViewById(R.id.ed_txt_name);
        EditText editTextId = findViewById(R.id.ed_txt_id);
        EditText editTextTel = findViewById(R.id.ed_txt_tel);
        EditText editTextNumRecive = findViewById(R.id.ed_txt_num_recive);
        EditText editTextNumkabala = findViewById(R.id.ed_txt_num_kabala);
        EditText editTextTax = findViewById(R.id.ed_txt_tax);
        EditText editTextNumOrder = findViewById(R.id.ed_txt_num_order);

        name="";
        id="";
        tel="";
        NumRecive="";
        numKabala="";
        TextTax="";
        tax=0;

        Button home =findViewById(R.id.btn_go_home_setting);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting.this,MainActivity.class);
                startActivity(intent);
            }
        });

        Button btnSaveAndFinish = findViewById(R.id.btn_save_finish);
        btnSaveAndFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NumRecive =editTextNumRecive.getText().toString();
                numKabala = editTextNumkabala.getText().toString();
                name = editTextName.getText().toString();
                id = editTextId.getText().toString();
                tel = editTextTel.getText().toString();
                TextTax = editTextTax.getText().toString();
                numOrder =editTextNumOrder.getText().toString();

                if (!(NumRecive.equals("")))
                    numOfRecive = Integer.parseInt(NumRecive);

                if (!(numKabala.equals("")))
                    numOfKabala = Integer.parseInt(numKabala);

                if(!(TextTax.equals("")))
                    tax=Integer.parseInt(TextTax);


                if(!(name.equals(""))) {
                    SharedPreferences.Editor editor1 = setting.edit();
                    editor1.putString("Name", name);
                    editor1.commit();
                }
                if(!(id.equals(""))) {
                    SharedPreferences.Editor editor2 = setting.edit();
                    editor2.putString("Id", id);
                    editor2.commit();
                }
                if(!(tel.equals(""))) {
                    SharedPreferences.Editor editor3 = setting.edit();
                    editor3.putString("Tel", tel);
                    editor3.commit();
                }
                if(!(NumRecive.equals(""))) {
                    SharedPreferences.Editor editor4 = integer.edit();
                    editor4.putInt("NumRecive", numOfRecive);
                    editor4.commit();
                }
                if(!(TextTax.equals(""))) {
                    SharedPreferences.Editor editor5 = integer.edit();
                    editor5.putInt("Tax", tax);
                    editor5.commit();
                }
                if(!(numKabala.equals(""))) {
                    SharedPreferences.Editor editor6 = integer.edit();
                    editor6.putInt("NumKabala", numOfKabala);
                    editor6.commit();
                }
                if(!(numOrder.equals(""))) {
                    numOfOrder=Integer.parseInt(numOrder);
                    SharedPreferences.Editor editor7 = integer.edit();
                    editor7.putInt("NumOrder", numOfOrder);
                    editor7.commit();
                }
                Toast.makeText(Setting.this,
                        "השינויים בוצעו בהצלחה", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Setting.this,MainActivity.class);
                startActivity(intent);
            }
        });



    }
}