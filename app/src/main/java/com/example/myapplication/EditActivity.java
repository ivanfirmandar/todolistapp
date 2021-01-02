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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    Button backButton;
    EditText inputActivity;
    Switch pentingTidak;
    Button inputButton;
    Boolean pentingKah = false;
    TextView nameSpace;
    Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        backButton = findViewById(R.id.backButton2);
        inputActivity = findViewById(R.id.inputActivity);
        pentingTidak = findViewById(R.id.pentingTidak2);
        inputButton = findViewById(R.id.inputEntryData2);
        nameSpace = findViewById(R.id.nameSpace);

        onClickHandler();

        Intent getIntent = getIntent();
        id = getIntent.getIntExtra("id",0);
        nameSpace.setText("Edit List " + id);
        inputActivity.setText(getIntent.getStringExtra("activity"));


    }
    private void onClickHandler(){
        pentingTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    pentingKah = true;
                }else{
                    pentingKah = false;
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Queries query = inputActivity();
                try {
                    DatabaseHandler db = new DatabaseHandler(EditActivity.this);
                    db.updateData(id,query.isStatus(),query.getKegiatan(),query.isPenting());
                    Toast.makeText(EditActivity.this,"Data Berhasil Diubah",Toast.LENGTH_SHORT);
                    startActivity(new Intent(EditActivity.this,MainActivity.class));
                }catch (SQLiteException err){
                    Toast.makeText(EditActivity.this,"Data Gagal Diubah",Toast.LENGTH_SHORT);
                }

            }
        });
    }
    private Queries inputActivity(){
        String activity = inputActivity.getText().toString();
        Queries query = new Queries();
        query.setStatus(false);
        query.setKegiatan(activity);
        query.setPenting(pentingKah);
        Log.d("PentingKah",""+pentingKah);
        return query;
    }
}