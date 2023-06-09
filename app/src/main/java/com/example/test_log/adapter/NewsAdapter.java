package com.example.test_log.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.test_log.MainActivity;
import com.example.test_log.R;
import com.example.test_log.enity.News;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<News> {
    Context context;
    TextView tv_name;
    ArrayList<News> list;
    News dao;
    View v_del;
    MainActivity main;
    FragmentActivity homeFragment;
    public NewsAdapter(@NonNull Context context, FragmentActivity activity, ArrayList<News> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
        this.homeFragment = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_title_main, null);
        }
        final News item = list.get(position);
        if (v != null){
            tv_name = v.findViewById(R.id.tv_name);
            v_del = v.findViewById(R.id.v_del);
            tv_name.setText(item.getName());
        }

        return v;
    }
}
