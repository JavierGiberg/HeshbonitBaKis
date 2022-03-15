package com.HeshbonitBeClick;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Kabala extends AppCompatActivity {
    String tel, id, name, toClient, adreesClient, telClient, DescriptionClient, DescriptionClient1, DescriptionClient2, priceClien, dateClient, test;
    Button createBtn;
    EditText editClientname, editClientAdrees, editClientNumPho, editClientDescription, editClientDescription1, editClientDescription2, editClientPrice, editClientDate;
    Bitmap bitmap, bitmap1, scaledbmp, scaledbmp1;
    TextView textViewHePa, txtDate;
    int pageWidht = 1200,  num = 1;
    float price, taxView;
    SharedPreferences setting, integer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kabala);


        setting = getSharedPreferences("Details", MODE_PRIVATE);
        integer = getSharedPreferences("Integer", MODE_PRIVATE);

        createBtn = findViewById(R.id.btn_save_recive_kabala);
        editClientname = findViewById(R.id.ed_client_name_kabala);
        editClientAdrees = findViewById(R.id.ed_client_adress_kabala);
        editClientNumPho = findViewById(R.id.ed_client_tel_kabala);
        editClientDescription = findViewById(R.id.ed_client_Description_kabala);
        editClientDescription1 = findViewById(R.id.ed_client_Description1_kabala);
        editClientDescription2 = findViewById(R.id.ed_client_Description2_kabala);
        editClientPrice = findViewById(R.id.ed_client_price_kabala);
        editClientDate = findViewById(R.id.ed_client_date_kabala);

        Button home =findViewById(R.id.btn_go_home_kabala);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Kabala.this,MainActivity.class);
                startActivity(intent);
            }
        });

        num = integer.getInt("NumKabala", 0);
        textViewHePa = findViewById(R.id.kabala_page);
        textViewHePa.setText("מספר: " + num);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.heshbonit);
        scaledbmp = Bitmap.createScaledBitmap(bitmap, 1195, 513, false);

        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.kabala);
        scaledbmp1 = Bitmap.createScaledBitmap(bitmap1, 1195, 860, false);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        CreatePdf();
    }

    private void CreatePdf() {

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel = setting.getString("Tel", "");
                id = setting.getString("Id", "");
                name = setting.getString("Name", "");


                toClient = editClientname.getText().toString();
                adreesClient = editClientAdrees.getText().toString();
                telClient = editClientNumPho.getText().toString();


                DescriptionClient = editClientDescription.getText().toString();
                DescriptionClient1 = editClientDescription1.getText().toString();
                DescriptionClient2 = editClientDescription2.getText().toString();
                priceClien = editClientPrice.getText().toString();
                dateClient = editClientDate.getText().toString();


                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint = new Paint();


                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 1750, 1).create();
                PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();

                canvas.drawBitmap(scaledbmp, 0, 0, myPaint);
                canvas.drawBitmap(scaledbmp1, 5, 850, myPaint);


                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                titlePaint.setTextSize(70);
                canvas.drawText(name, pageWidht / 2, 350, titlePaint);

                myPaint.setColor(Color.rgb(000, 000, 000));
                myPaint.setTextSize(30f);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("טל: ", 1160, 140, myPaint);
                canvas.drawText(tel, 1100, 140, myPaint);
                canvas.drawText("עוסק מורשה: ", 1160, 180, myPaint);
                canvas.drawText(id, 990, 180, myPaint);
                canvas.drawText("מקור", 100, 180, myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                titlePaint.setTextSize(70);
                canvas.drawText("קבלה מס", pageWidht / 2, 500, titlePaint);
                canvas.drawText("מספר: " + num + "", pageWidht / 2, 620, titlePaint);

                canvas.drawText("לכבוד: ", 1160, 700, myPaint);
                canvas.drawText(toClient, 1060, 700, myPaint);

                canvas.drawText("כתובת: ", 1160, 800, myPaint);
                canvas.drawText(adreesClient, 1060, 800, myPaint);

                canvas.drawText("טל: ", 250, 700, myPaint);
                canvas.drawText(telClient, 190, 700, myPaint);

                canvas.drawText(DescriptionClient, 1100, 1000, myPaint);
                canvas.drawText(DescriptionClient1, 1100, 1100, myPaint);
                canvas.drawText(DescriptionClient2, 1100, 1200, myPaint);

                canvas.drawText(dateClient, 1050, 1570, myPaint);

                if(priceClien.equals("")) {
                    price=0;

                }
                else {
                    price = Float.parseFloat(priceClien);
                }
                canvas.drawText(String.format("%.02f", price), 210, 1675, myPaint);

                myPdfDocument.finishPage(myPage);
                String pdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

                num++;

                SharedPreferences.Editor editor = integer.edit();
                editor.putInt("NumKabala", num);
                editor.commit();

                File file = new File(pdf, (num-1)+"_Kabala.pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();
                file.mkdir();

                Toast.makeText(Kabala.this,
                        "הנפקה בוצע בהצלחה ונשמר בתיקיה : Document", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Kabala.this, MainActivity.class);
                startActivity(intent);
            }

        });
    }
}