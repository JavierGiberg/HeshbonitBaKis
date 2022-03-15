package com.HeshbonitBeClick;

import androidx.annotation.NonNull;
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
import android.os.Build;
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

public class Order extends AppCompatActivity {

    String tel,id,name,toClient,adreesClient,telClient,DescriptionClient,
            DescriptionClient1,DescriptionClient2,priceClien,dateClient,dateDeliveri;
    Button createBtn;
    EditText editClientname,editClientAdrees,editClientNumPho,editClientDescription
            ,editClientDescription1,editClientDescription2,editClientPrice,editClientDate,editClientDateDeliveri;
    Bitmap bitmap,bitmap1,scaledbmp,scaledbmp1;
    TextView textViewHePa,txtDate;
    int pageWidht=1200,tax,num=1;
    float price,taxView;
    SharedPreferences setting,integer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        checkPermission();

        setting= getSharedPreferences("Details",MODE_PRIVATE);
        integer= getSharedPreferences("Integer",MODE_PRIVATE);



        createBtn = findViewById(R.id.btn_save_recive_order);
        editClientname = findViewById(R.id.ed_client_name_order);
        editClientAdrees = findViewById(R.id.ed_client_adress_order);
        editClientNumPho = findViewById(R.id.ed_client_tel_order);
        editClientDescription = findViewById(R.id.ed_client_Description_order);
        editClientDescription1 = findViewById(R.id.ed_client_Description1_order);
        editClientDescription2 = findViewById(R.id.ed_client_Description2_order);
        editClientPrice = findViewById(R.id.ed_client_price_order);
        editClientDate = findViewById(R.id.ed_client_date_order);
        editClientDateDeliveri=findViewById(R.id.ed_client_date_order_deliveri);

        Button home =findViewById(R.id.btn_go_home_order);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Order.this,MainActivity.class);
                startActivity(intent);
            }
        });

        num=integer.getInt("NumOrder",0);
        textViewHePa=findViewById(R.id.order_page);
        textViewHePa.setText("מספר: "+num);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.heshbonit);
        scaledbmp = Bitmap.createScaledBitmap(bitmap,1195,513,false);

        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.order);
        scaledbmp1 = Bitmap.createScaledBitmap(bitmap1,1195,860,false);


    //    ActivityCompat.requestPermissions(this,new String[]{
     //           Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        CreatePdf();

    }
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
                dateDeliveri=editClientDateDeliveri.getText().toString();




                PdfDocument myPdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint titlePaint =new Paint();


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
                canvas.drawText("הזמנת עבודה",pageWidht/2,500,titlePaint);
                canvas.drawText("מספר: "+num+"",pageWidht/2,620,titlePaint);

                canvas.drawText("לכבוד: ",1160,700,myPaint);
                canvas.drawText(toClient,1060,700,myPaint);

                canvas.drawText("כתובת: ",1160,800,myPaint);
                canvas.drawText(adreesClient,1060,800,myPaint);

                canvas.drawText("טל: ",250,700,myPaint);
                canvas.drawText(telClient,190,700,myPaint);

                canvas.drawText(DescriptionClient,1180,1000,myPaint);
                canvas.drawText(DescriptionClient1,1180,1200,myPaint);
                canvas.drawText(DescriptionClient2,1180,1400,myPaint);


                canvas.drawText(priceClien,190,1550,myPaint);
                canvas.drawText(tax+"",310,1620,myPaint);
                canvas.drawText(dateClient,1000,1560,myPaint);
                canvas.drawText(dateDeliveri,1000,1630,myPaint);



                if(priceClien.equals("")) {
                    price=0;
                    taxView=0;
                }
                else {
                    price = Float.parseFloat(priceClien);
                    taxView=(price * tax) / 100;
                    price += (price * tax) / 100;
                }

                canvas.drawText(String.format("%.02f", taxView), 210, 1615, myPaint);
                canvas.drawText(String.format("%.02f", price), 210, 1675, myPaint);

                myPdfDocument.finishPage(myPage);
                String pdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

                num++;

                SharedPreferences.Editor editor = integer.edit();
                editor.putInt("NumOrder", num);
                editor.commit();

                File file = new File(pdf, (num-1)+"_Order.pdf");


                try {
                   // myPdfDocument.writeTo(new FileOutputStream(file));
                    myPdfDocument.writeTo(new FileOutputStream(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPdfDocument.close();
                file.mkdir();

                Toast.makeText(Order.this,
                        "הנפקה בוצע בהצלחה ונשמר בתיקיה : Document", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(Order.this,MainActivity.class);
                startActivity(intent);
            }

        });
    }
    private void checkPermission() {
        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            }
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //    loadFileList();
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission not granted", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}