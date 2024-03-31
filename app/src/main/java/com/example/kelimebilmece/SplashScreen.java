package com.example.kelimebilmece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {

    /*private String[] sorulist={"Takım çalışmalarında ekip elemanlarının sorumluluk ve yükümlülükleri paylaşması","Hastane,ev,fabrika gibi yerlerden kullanılmış ve kullanıcının artık işine yaramayan maddelerin tümü","Birden ortaya çıkan,aşırı ve normalin dışına taşmış korku hali"};
    private String[] sorukodlist={"s1","s2","s3"};
    private String[] cevaplist={"iş bölümü","atık","panik"};
    private String[] cevapkodist={"c1","c2","c3"};*/
    private ProgressBar mProgress;
    //private TextView mTextView;
    private SQLiteDatabase database;
    private Cursor cursor;
    private float maksprogres= 100f, artacakprogres, progresmiktar=0;
    static public HashMap<String,String> sorularList;
    static public HashMap<String,String> cevaplarList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mProgress= (ProgressBar)findViewById(R.id.progressBar);
        //mTextView= (TextView) findViewById(R.id.textview);
        sorularList=new HashMap<>();
        cevaplarList=new HashMap<>();



        try {
            database = this.openOrCreateDatabase("KelimeBilmece",MODE_PRIVATE,null);
            database.execSQL("CREATE TABLE IF NOT EXISTS Sorular(id INTEGER PRIMARY KEY,sKod VARCHAR UNIQUE,soru VARCHAR)");
            database.execSQL("DELETE FROM Sorular");
            database.execSQL("CREATE TABLE IF NOT EXISTS Cevaplar(cKod VARCHAR ,cevap VARCHAR, FOREIGN KEY (cKod) REFERENCES Sorular (sKod))");
            database.execSQL("DELETE FROM Cevaplar");

            sqlInsertData();
            sqlInsertData2();

            cursor=database.rawQuery("SELECT * FROM Sorular",null);

            artacakprogres=maksprogres/cursor.getCount();

            int sKodIndex=cursor.getColumnIndex("sKod");
            int soruIndex=cursor.getColumnIndex("soru");

            while(cursor.moveToNext()){
                sorularList.put(cursor.getString(sKodIndex),cursor.getString(soruIndex));
                progresmiktar += artacakprogres;
                mProgress.setProgress((int)progresmiktar);
            }

            cursor=database.rawQuery("SELECT * FROM Cevaplar",null);

            int cKodIndex=cursor.getColumnIndex("cKod");
            int cevapIndex=cursor.getColumnIndex("cevap");

            while(cursor.moveToNext()){
                cevaplarList.put(cursor.getString(cKodIndex),cursor.getString(cevapIndex));
            }
            cursor.close();
            new CountDownTimer(1100,1000){
                public void onTick(long l) {
                    progresmiktar+=artacakprogres;
                    mProgress.setProgress((int)progresmiktar);
                }

                @Override
                public void onFinish() {
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    finish();
                    startActivity(mainIntent);

                }

            }.start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void sqlInsertData(){
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s1','Mesaj sözcüğünün türkçe karşılığı')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s2','Suç mahalli')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s3','Birden ortaya çıkan,aşırı ve normalin dışına taşmış korku hali')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s4',' Durum,ortam,çevre,atmosfer anlamlarında kullanılan bir sözcük')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s5','Eskiden yüksek makamdaki kadınlara ve hakan eşlerine verilen unvan')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s6','Deniz ve okyanusların canlı mücevher kutusu')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s7','Fransızca kökenli -karizmatik- sözünün türkçe karşılığı')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s8','Sabahın güneş doğmadan önceki zamanı')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s9','Bir erkeğin sadece evlenirse sahip olabileceği bir akrabalık ünvanı')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s10','İç Anadolunun,özellikle de Ankara yöresinin başlıca oyun havalarından olan bir türkü ve oyunu')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s11','-Yabani- karşıtı')");
        /*database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s12','')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s13','')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s14','')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s15','')");
        database.execSQL("INSERT INTO Sorular (sKod,soru) VALUES ('s16','')");*/

    }
    private void sqlInsertData2(){
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s1','ileti')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s2','olayyeri')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s3','panik')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s4','ambiyans')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s5','hatun')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s6','istiridye')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s7','etkileyici')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s8','seher')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s9','eniste')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s10','fidayda')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s11','ehli')");
        /*database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s12','')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s13','')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s14','')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s15','')");
        database.execSQL("INSERT INTO Cevaplar(cKod,cevap) VALUES ('s16','')");*/

    }
}