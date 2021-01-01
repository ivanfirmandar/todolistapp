package com.example.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private ArrayList<Queries> queries;
    private Context context;
    public CustomAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        this.queries = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final boolean[] status = {queries.get(position).isStatus()};
        String kegiatan = queries.get(position).getKegiatan();
        final boolean[] penting = {queries.get(position).isPenting()};
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.adapterview,parent,false);
        Button btnStatus = convertView.findViewById(R.id.statsuButton);
        TextView tvKegiatan = convertView.findViewById(R.id.kegiatan);
        Button btnPenting = convertView.findViewById(R.id.pentingButton);
        if (!status[0]){
            btnStatus.setBackgroundColor(Color.RED);
            btnPenting.setText(String.valueOf(status[0]));
        }else {
            btnStatus.setBackgroundColor(Color.BLUE);
            btnPenting.setText(String.valueOf(status[0]));
        }
        if (!penting[0]){
            btnPenting.setBackgroundColor(Color.RED);
            btnPenting.setText(String.valueOf(penting[0]));
        }else{
            btnPenting.setBackgroundColor(Color.BLUE);
            btnPenting.setText(String.valueOf(penting[0]));
        }

        tvKegiatan.setText(kegiatan);
        btnPenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    Handler handler = new Handler();
                    @Override
                    public void run() {
                        Log.d("Thread", "Thread is Running");
                        if (!penting[0]){
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("penting?",String.valueOf(penting));
                                    btnPenting.setBackgroundColor(Color.BLACK);
                                    penting[0] = true;
                                }
                            });
                        }else {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("penting?",String.valueOf(penting));
                                    Log.d("status","Berhasil");
                                    btnPenting.setBackgroundColor(Color.RED);
                                    penting[0] = false;
                                }
                            });
                        }
                    }
                }).start();
            }
        });
        return convertView;
    }
}
