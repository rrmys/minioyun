package com.example.kelimebilmece;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class oyun2 extends AppCompatActivity {

    private TextView soru,cevap,can;
    private EditText tahmin;

    private SQLiteDatabase database;
    private Cursor cursor;
    private ArrayList<String> sorularlist;
    private ArrayList<String> sorularkodlist;
    private ArrayList<String> cevaplist;
    private Random rndsoru,rndharf;
    private int rndsoruno,rndharfno,harfsayisi,newcan,heal=50;
    private String rastsoru,rastsorukod,rastcevap,kelimebilgisi="",texttahmin;
    private ArrayList<Character> kelimeharfleri;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun2);
        database=this.openOrCreateDatabase("KelimeBilmece", MODE_PRIVATE,null);
        sorularlist=new ArrayList<>();
        sorularkodlist=new ArrayList<>();
        cevaplist=new ArrayList<>();
        rndsoru=new Random();
        soru=(TextView)findViewById(R.id.soru);
        cevap=(TextView)findViewById(R.id.cevap);
        kelimeharfleri=new ArrayList<>();
        rndharf=new Random();
        tahmin=(EditText)findViewById(R.id.tahmin);
        can=(TextView)findViewById(R.id.can);

        for(Map.Entry soru : SplashScreen.sorularList.entrySet()){
            sorularlist.add(String.valueOf(soru.getValue()));
            sorularkodlist.add(String.valueOf(soru.getKey()));
        }
        for(Map.Entry cevap : SplashScreen.cevaplarList.entrySet()){
            cevaplist.add(String.valueOf(cevap.getValue()));
        }

        rndsoruno=rndsoru.nextInt(sorularkodlist.size());
        rastsoru = sorularlist.get(rndsoruno);
        rastsorukod =sorularkodlist.get(rndsoruno);
        soru.setText(rastsoru);

        try {
            database=this.openOrCreateDatabase("KelimeBilmece",MODE_PRIVATE,null);
            cursor= database.rawQuery("SELECT * FROM Cevaplar WHERE cKod = ?", new String[]{rastsorukod});

            int cevapindex = cursor.getColumnIndex("cevap");


        }catch (Exception e){
            e.printStackTrace();
        }


        rastcevap=cevaplist.get(rndsoruno);
        for(int i=0;i<rastcevap.length();i++){
            if(i<rastcevap.length() - 1)
                kelimebilgisi += "_ ";
            else
                kelimebilgisi += "_";
        }

        cevap.setText(kelimebilgisi);
        for(char harf: rastcevap.toCharArray())
            kelimeharfleri.add(harf);
        if(rastcevap.length()>=5 && rastcevap.length()<=7)
            harfsayisi = 1;
        else if(rastcevap.length()>=8 && rastcevap.length()<=10)
            harfsayisi = 2;
        else if(rastcevap.length()>=11 && rastcevap.length()<=14)
            harfsayisi = 3;
        else if(rastcevap.length()>=15 )
            harfsayisi = 4;
        else
            harfsayisi = 0;
        for(int i=0; i<harfsayisi;i++){
            rastHarfAl();
        }
    }

    @SuppressLint("MissingSuperCall")
    public void onBackPressed(){
        Intent mainIntent=new Intent(this, MainActivity.class);
        finish();
        startActivity(mainIntent);
        overridePendingTransition(R.anim.slide_out_up,R.anim.slide_in_down);
    }

    public void btnHarf(View view) {
        if(heal > 0){
            rastHarfAl();
            heal-=5;
            can.setText(String.valueOf(heal));
        }else
            Toast.makeText(getApplicationContext(), "Yetersiz puan.Harf alamazsınız.", Toast.LENGTH_SHORT).show();

    }


    public void btnTahmin(View view) {
        texttahmin=tahmin.getText().toString();

        if(!TextUtils.isEmpty(texttahmin)){
            if(texttahmin.matches(rastcevap)){
                Toast.makeText(getApplicationContext(), "Doğru tahmin.", Toast.LENGTH_SHORT).show();
                //cevap.setText(rastcevap);
                heal+=20;
                can.setText(String.valueOf(heal));
                kelimeharfleri.clear();
                kelimebilgisi = "";
                tahmin.setText("");

                    rndsoruno=rndsoru.nextInt(sorularkodlist.size());
                    rastsoru = sorularlist.get(rndsoruno);
                    rastsorukod =sorularkodlist.get(rndsoruno);
                    soru.setText(rastsoru);

                    try {
                        database=this.openOrCreateDatabase("KelimeBilmece",MODE_PRIVATE,null);
                        cursor= database.rawQuery("SELECT * FROM Cevaplar WHERE cKod = ?", new String[]{rastsorukod});

                        int cevapindex = cursor.getColumnIndex("cevap");


                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    rastcevap=cevaplist.get(rndsoruno);
                    for(int i=0;i<rastcevap.length();i++){
                        if(i<rastcevap.length() - 1)
                            kelimebilgisi += "_ ";
                        else
                            kelimebilgisi += "_";
                    }

                    cevap.setText(kelimebilgisi);
                    for(char harf: rastcevap.toCharArray())
                        kelimeharfleri.add(harf);
                    if(rastcevap.length()>=5 && rastcevap.length()<=7)
                        harfsayisi = 1;
                    else if(rastcevap.length()>=8 && rastcevap.length()<=10)
                        harfsayisi = 2;
                    else if(rastcevap.length()>=11 && rastcevap.length()<=14)
                        harfsayisi = 3;
                    else if(rastcevap.length()>=15 )
                        harfsayisi = 4;
                    else
                        harfsayisi = 0;
                    for(int i=0; i<harfsayisi;i++){
                        rastHarfAl();
                    }


            }else{
                Toast.makeText(getApplicationContext(), "Yanlış tahmin.", Toast.LENGTH_SHORT).show();
                heal-=10;
                can.setText(String.valueOf(heal));}


        }else
            Toast.makeText(getApplicationContext(), "Tahmin değeri boş olamaz.", Toast.LENGTH_SHORT).show();

    }
    public void rastHarfAl(){
        if(kelimeharfleri.size()>0){
            rndharfno=rndharf.nextInt(kelimeharfleri.size());
            String[] txtharfler=cevap.getText().toString().split(" ");
            char[] kelimeharf=rastcevap.toCharArray();

            for(int i=0;i<rastcevap.length();i++){
                if(txtharfler[i].equals("_") && kelimeharf[i] == kelimeharfleri.get(rndharfno)){
                    txtharfler[i]=String.valueOf(kelimeharfleri.get(rndharfno));
                    kelimebilgisi="";

                    for(int j=0;j<txtharfler.length;j++){
                        if(j<txtharfler.length -1)
                            kelimebilgisi += txtharfler[j] +" ";
                        else
                            kelimebilgisi += txtharfler[j];
                    }
                    break;
                }
            }
            cevap.setText(kelimebilgisi);
            kelimeharfleri.remove(rndharfno);
        }

    }


}