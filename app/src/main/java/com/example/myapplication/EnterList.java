package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class EnterList extends AppCompatActivity {
    boolean pentingKah;
    ImageButton backButton;
    EditText activityInput;
    Switch isPenting;
    Button inputButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_list);

        backButton = findViewById(R.id.backButton);
        activityInput = findViewById(R.id.enterActivity);
        isPenting = findViewById(R.id.pentingTidak);
        inputButton = findViewById(R.id.inputEntryData);
        onclickHandler();
        
    }
    void onclickHandler(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterList.this,MainActivity.class);
                startActivity(intent);
            }
        });
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Queries query = inputHandler();
               try {
                   DatabaseHandler db = new DatabaseHandler(EnterList.this);
                   db.insertData(query.isStatus(),query.getKegiatan(),query.isPenting());
                   Toast.makeText(EnterList.this,"Data Berhasil Masuk",Toast.LENGTH_SHORT).show();
                   Log.d("Database","Database Berhasil Masuk");
                   db.close();
               }catch (SQLiteException err){
                   Log.d("Error",err.toString());
                   Toast.makeText(EnterList.this,"Data Gagal Dimasukkan",Toast.LENGTH_SHORT).show();
               }
            }
        });
        isPenting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    pentingKah = true;
                }else{
                    pentingKah = false;
                }
            }
        });
    }
    Queries inputHandler(){
        String input = activityInput.getText().toString();
        Queries query = new Queries();
        query.setStatus(true);
        query.setKegiatan(input);
        query.setPenting(pentingKah);

        return query;
    }
}