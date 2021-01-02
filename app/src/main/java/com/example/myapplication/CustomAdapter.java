package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
        int id = queries.get(position).getId();
        String kegiatan = queries.get(position).getKegiatan();
        final boolean[] penting = {queries.get(position).isPenting()};
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.adapterview,parent,false);
        ImageButton btnStatus = convertView.findViewById(R.id.statsuButton);
        TextView tvKegiatan = convertView.findViewById(R.id.kegiatan);
        Button btnPenting = convertView.findViewById(R.id.pentingButton);
        Log.d("Status", "Status : " + status[0]);
        if (!status[0]){
            btnStatus.setImageResource(R.drawable.unchecked_list);
        }else {
            btnStatus.setImageResource(R.drawable.checkedl_list);
            tvKegiatan.setPaintFlags(tvKegiatan.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        if (!penting[0]){
            btnPenting.setBackgroundColor(Color.RED);
            btnPenting.setText("BIASA");
        }else{
            btnPenting.setBackgroundColor(Color.BLUE);
            btnPenting.setText("PENTING");
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditActivity.class);
                intent.putExtra("id",queries.get(position).getId());
                intent.putExtra("activity",queries.get(position).getKegiatan());
                context.startActivity(intent);
            }
        });
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Clicked1","true");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DatabaseHandler db = new DatabaseHandler(context);
                        Handler bst = new Handler(Looper.getMainLooper());
                        bst.post(new Runnable() {
                            @Override
                            public void run() {
                                if (!status[0]){
                                    try {
                                        btnStatus.setImageResource(R.drawable.checkedl_list);
                                        tvKegiatan.setPaintFlags(tvKegiatan.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                        db.updateData(id, true,kegiatan,penting[0]);
                                        status[0] = true;
                                        Log.d("change", "Changed to true");
                                    }catch (SQLiteException err){
                                        Log.d("Error", "Gagal Diubah");
                                    }
                                }else {
                                    try {
                                        btnStatus.setImageResource(R.drawable.unchecked_list);
                                        tvKegiatan.setPaintFlags(tvKegiatan.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                                        db.updateData(id, false,kegiatan,penting[0]);
                                        status[0] = false;
                                        Log.d("change", "Changed to false");
                                    }catch (SQLiteException err){
                                        Log.d("Error", "Gagal Diubah");
                                    }

                                }
                            }
                        });
                        db.close();
                    }
                }).start();
            }
        });
        tvKegiatan.setText(kegiatan);
        return convertView;
    }
}
