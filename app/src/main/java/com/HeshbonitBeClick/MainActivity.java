package com.HeshbonitBeClick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Button createBtn,kabalaBtn,btnCheck;
    TextView HomePage,txtDate;
    Bitmap bitmap,scaledbmp;
    int pageWidht=1200,num,num1=0;
    SharedPreferences sp;
    String sFolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        sp=getSharedPreferences("Details",MODE_PRIVATE);

        HomePage= findViewById(R.id.home_page);
        String name1 =sp.getString("Name","");
        HomePage.setText(name1);

        txtDate=findViewById(R.id.home_data);
        Calendar calendar =Calendar.getInstance();
        String currenDate = DateFormat.getDateInstance().format(calendar.getTime());
        txtDate.setText(currenDate);

        Button btnHeshbonit= findViewById(R.id.btn_heshbonit);
        btnHeshbonit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Heshbonit.class);
                startActivity(intent);
            }
        });

        btnCheck =findViewById(R.id.btn_check_tax);
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://taxinfo.taxes.gov.il/gmishurim/firstPage.aspx"));
                startActivity(browserIntent);
            }
        });
        kabalaBtn=findViewById(R.id.btn_kabala);
        kabalaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Kabala.class);
                startActivity(intent);
            }
        });
        Button orderBtn =findViewById(R.id.btn_order);
        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,Order.class);
                startActivity(intent);
            }
        });
        Button btnFace = findViewById(R.id.facebook);
        btnFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/omri.maimon.1"));
                startActivity(intent);
            }
        });
        Button btnMail = findViewById(R.id.mail);
        btnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:heshbonitbakis@gmail.com")));
            }
        });
        Button btnTax = findViewById(R.id.tax);
        btnTax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://drive.google.com/file/d/1pcwelE1FMk31pkLfTZMfAhw3mVfpFbao/view?usp=sharing"));
                startActivity(intent);
            }
        });
        Button btnOpenFolder = findViewById(R.id.btn_folder);
        btnOpenFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(Environment.getExternalStorageDirectory() + "/"+ "Documents"+"/");
                //open file manager
                startActivity(new Intent(Intent.ACTION_GET_CONTENT).setDataAndType(uri,"*/*"));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.action_bar_setting){
            Intent intent = new Intent(MainActivity.this,Setting.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.action_bar_help){
            Intent intent = new Intent(MainActivity.this,Help.class);
            startActivity(intent);
        }

        if(item.getItemId()==R.id.action_bar_if_use){
            Intent intent = new Intent(MainActivity.this,Condithion.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
