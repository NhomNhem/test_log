package com.example.test_log;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    //
    private ListView lv_main;
    private View view_add;
    private Dialog dialog;
    private TextInputEditText ed_name, ed_link;
    private Button btn_add, btn_del, btn_cancel;
    private NewsAdapter adapter;
    private NewsDAO dao;
    private News news;
    private ArrayList<News> list;
    private View view;
    //
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        lv_main = findViewById(R.id.lv_main);
        view_add = findViewById(R.id.view_add);

        dao = new NewsDAO(MainActivity.this);
        UpdateLV();



        deleteCache(getApplicationContext()); //xóa cache

        view_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                openDialog();
            }
        });
        lv_main.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (checkNetwork()){
                    String link = list.get(i).getLink();
                    if (!link.isEmpty()){
                        Intent intent = new Intent(MainActivity.this, NewsActivity.class);
                        intent.putExtra("link", link);
                        startActivity(intent);
                    }
                }else {
                    NoInternetToast();
                }
            }
        });

        lv_main.setOnItemLongClickListener((adapterView, view, i, l) -> {
            delete(list.get(i).getId());
            return true;
        });

        // Inflate the layout for this fragment
        return view;
    }
}