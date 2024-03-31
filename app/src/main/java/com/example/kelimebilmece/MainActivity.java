package com.example.kelimebilmece;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @SuppressLint("NonConstantResourceId")
    public void btnclick(View view) {
        int Id = view.getId();
        if(Id==R.id.oyna){
            Intent playIntent=new Intent(this, oyun2.class);
            finish();
            startActivity(playIntent);
            overridePendingTransition(R.anim.slide_out_up,R.anim.slide_in_down);
        }else if(Id==R.id.market){

        }else{
            finish();
        }

    }
}