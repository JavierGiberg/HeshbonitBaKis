package com.HeshbonitBeClick;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.HeshbonitBeClick.databinding.ActivityHeshbonitBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

public class Heshbonit extends AppCompatActivity {
    String tel,id,name,toClient,adreesClient,telClient,DescriptionClient,DescriptionClient1,DescriptionClient2,priceClien,dateClient,test;
    Button createBtn;
    EditText editClientname,editClientAdrees,editClientNumPho,editClientDescription,editClientDescription1,editClientDescription2,editClientPrice,editClientDate;
    Bitmap bitmap,bitmap1,scaledbmp,scaledbmp1;
    TextView textViewHePa,txtDate;
    int pageWidht=1200,tax,num=1;
    float price,taxView;
    SharedPreferences setting,integer;//save data in phone memory

    //new test
   // private LinearLayout mLlCanvas;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heshbonit);
        //new test
      //  mLlCanvas = (LinearLayout) findViewById(R.id.llCanvas);

       // mLlCanvas.addView(textViewHePa,0);


        setting= getSharedPreferences("Details",MODE_PRIVATE);
        integer= getSharedPreferences("Integer",MODE_PRIVATE);



        createBtn = findViewById(R.id.btn_save_recive);
        editClientname = findViewById(R.id.ed_client_name);
        editClientAdrees = findViewById(R.id.ed_client_adress);
        editClientNumPho = findViewById(R.id.ed_client_tel);
        editClientDescription = findViewById(R.id.ed_client_Description);
        editClientDescription1 = findViewById(R.id.ed_client_Description1);
        editClientDescription2 = findViewById(R.id.ed_client_Description2);
        editClientPrice = findViewById(R.id.ed_client_price);
        editClientDate = findViewById(R.id.ed_client_date);

        Button home =findViewById(R.id.btn_go_home_heshbonit);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Heshbonit.this,MainActivity.class);
                startActivity(intent);
            }
        });

        num=integer.getInt("NumRecive",0);
        textViewHePa=findViewById(R.id.heashbonit_page);
        textViewHePa.setText("מספר: "+num);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.heshbonit);
        scaledbmp = Bitmap.createScaledBitmap(bitmap,1195,513,false);

        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.heshbonit1);
        scaledbmp1 = Bitmap.createScaledBitmap(bitmap1,1195,860,false);//////////////////////////////////////////////////////////////


        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        CreatePdf();
    }
    // create pdf f()
    private void CreatePdf() {

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel= setting.getString("Tel","");
                id= setting.getString("Id","");
                name=setting.getString("Name","");


                toClient=editClientname.getText().toString();
                adreesClient = editClientAdrees.getText().toString();
                telClient=editClientNumPho.getText().toString();

                tax=integer.getInt("Tax",0);
                DescriptionClient=editClientDescription.getText().toString();
                DescriptionClient1=editClientDescription1.getText().toString();
                DescriptionClient2=editClientDescription2.getText().toString();
                priceClien= editClientPrice.getText().toString();
                dateClient =editClientDate.getText().toString();



                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint =new Paint();

                Paint singature = new Paint();
                singature.setColor(Color.rgb(000,000,255));
                singature.setTextSize(45f);
                singature.setTextAlign(Paint.Align.RIGHT);


                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(1200, 1750, 1).create();
                PdfDocument.Page myPage = myPdfDocument.startPage(myPageInfo);
                Canvas canvas = myPage.getCanvas();

                canvas.drawBitmap(scaledbmp,0,0,myPaint);
                canvas.drawBitmap(scaledbmp1,5,850,myPaint);



                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                titlePaint.setTextSize(70);
                canvas.drawText(name,pageWidht/2,350,titlePaint);

                myPaint.setColor(Color.rgb(000,000,000));
                myPaint.setTextSize(30f);
                myPaint.setTextAlign(Paint.Align.RIGHT);
                canvas.drawText("טל: ",1160,140,myPaint);
                canvas.drawText(tel,1100,140,myPaint);
                canvas.drawText("עוסק מורשה: ",1160,180,myPaint);
                canvas.drawText(id,990,180,myPaint);
                canvas.drawText("מקור",100,180,myPaint);

                titlePaint.setTextAlign(Paint.Align.CENTER);
                titlePaint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                titlePaint.setTextSize(70);
                canvas.drawText("חשבונית מס",pageWidht/2,500,titlePaint);
                canvas.drawText("מספר: "+num+"",pageWidht/2,620,titlePaint);

                canvas.drawText("לכבוד: ",1160,700,myPaint);
                canvas.drawText(toClient,1060,700,myPaint);

                canvas.drawText("כתובת: ",1160,800,myPaint);
                canvas.drawText(adreesClient,1060,800,myPaint);

                canvas.drawText("טל: ",250,700,myPaint);
                canvas.drawText(telClient,190,700,myPaint);

                canvas.drawText(DescriptionClient,1100,1000,myPaint);
                canvas.drawText(DescriptionClient1,1100,1100,myPaint);
                canvas.drawText(DescriptionClient2,1100,1200,myPaint);


                canvas.drawText(priceClien,190,1550,myPaint);
                canvas.drawText(tax+"",315,1620,myPaint);
                canvas.drawText(dateClient,1050,1570,myPaint);
                canvas.drawText(name,1080,1675,singature);//new test


                float price,taxView;
                if(priceClien.equals("")) {
                    price=0;
                    taxView=0;
                }
                else {
                    price = Float.parseFloat(priceClien);
                    taxView=(price * tax) / 100;
                    price += (price * tax) / 100;//)*17)/100)
                }

                canvas.drawText(String.format("%.02f", taxView), 210, 1615, myPaint);
                canvas.drawText(String.format("%.02f", price), 210, 1675, myPaint);

                myPdfDocument.finishPage(myPage);
                String pdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

                num++;

                SharedPreferences.Editor editor = integer.edit();
                editor.putInt("NumRecive", num);
                editor.commit();

                File file = new File(pdf, "_Heshbonit_makor"+(num-1)+".pdf");

                try {
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();
                file.mkdir();

                //--------------------------copyPDF-------------------------
                PdfDocument myPdfDocument1 = new PdfDocument();
                Paint myPaint1 = new Paint();
                Paint titlePaint1 =new Paint();

                PdfDocument.PageInfo myPageInfo2 = new PdfDocument.PageInfo.Builder(1200, 1750, 1).create();
                PdfDocument.Page myPage2 = myPdfDocument1.startPage(myPageInfo2);
                Canvas canvas1 = myPage2.getCanvas();

                canvas1.drawBitmap(scaledbmp,0,0,myPaint1);
                canvas1.drawBitmap(scaledbmp1,5,850,myPaint1);



                titlePaint1.setTextAlign(Paint.Align.CENTER);
                titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
                titlePaint1.setTextSize(70);
                canvas1.drawText(name,pageWidht/2,350,titlePaint1);

                myPaint1.setColor(Color.rgb(000,000,000));
                myPaint1.setTextSize(30f);
                myPaint1.setTextAlign(Paint.Align.RIGHT);
                canvas1.drawText("טל: ",1160,140,myPaint1);
                canvas1.drawText(tel,1100,140,myPaint1);
                canvas1.drawText("עוסק מורשה: ",1160,180,myPaint1);
                canvas1.drawText(id,990,180,myPaint1);
                canvas1.drawText("העתק",100,180,myPaint1);

                titlePaint1.setTextAlign(Paint.Align.CENTER);
                titlePaint1.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.ITALIC));
                titlePaint1.setTextSize(70);
                canvas1.drawText("חשבונית מס",pageWidht/2,500,titlePaint1);
                canvas1.drawText("מספר: "+num+"",pageWidht/2,620,titlePaint1);

                canvas1.drawText("לכבוד: ",1160,700,myPaint1);
                canvas1.drawText(toClient,1060,700,myPaint1);

                canvas1.drawText("כתובת: ",1160,800,myPaint1);
                canvas1.drawText(adreesClient,1060,800,myPaint1);

                canvas1.drawText("טל: ",250,700,myPaint1);
                canvas1.drawText(telClient,190,700,myPaint1);

                canvas1.drawText(DescriptionClient,1100,1000,myPaint1);
                canvas1.drawText(DescriptionClient1,1100,1100,myPaint1);
                canvas1.drawText(DescriptionClient2,1100,1200,myPaint1);


                canvas1.drawText(priceClien,190,1550,myPaint1);
                canvas1.drawText(tax+"",315,1620,myPaint1);
                canvas1.drawText(dateClient,1050,1570,myPaint1);
                canvas1.drawText(name,1080,1675,singature);//new test


                if(priceClien.equals("")) {
                    price=0;
                    taxView=0;
                }
                else {
                    price = Float.parseFloat(priceClien);
                    taxView=(price * tax) / 100;
                    price += (price * tax) / 100;//)*17)/100)
                }

                canvas1.drawText(String.format("%.02f", taxView), 210, 1615, myPaint1);
                canvas1.drawText(String.format("%.02f", price), 210, 1675, myPaint1);


                myPdfDocument1.finishPage(myPage2);
                String pdf1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

                File file1 = new File(pdf1, "_Heshbonit_copy"+(num-1)+".pdf");

                try {
                    myPdfDocument1.writeTo(new FileOutputStream(file1));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                myPdfDocument1.close();
                file1.mkdir();

                Toast.makeText(Heshbonit.this,
                        "הנפקה בוצע בהצלחה ונשמר בתיקיה : Document", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Heshbonit.this,MainActivity.class);
                startActivity(intent);
            }

        });
    }

}