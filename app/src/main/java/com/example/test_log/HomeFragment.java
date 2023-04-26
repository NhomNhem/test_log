package com.example.test_log;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test_log.adapter.NewsAdapter;
import com.example.test_log.dao.NewsDAO;
import com.example.test_log.enity.News;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
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

        lv_main = (ListView) view.findViewById(R.id.lv_homefrag);
        view_add = (View) view.findViewById(R.id.view_add);

        dao = new NewsDAO(getActivity());
        UpdateLV();



        deleteCache(getActivity().getApplicationContext()); //xóa cache

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
                        Intent intent = new Intent(getActivity(), NewsFragment.class);
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
    public void openDialog(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_add);
        ed_name = dialog.findViewById(R.id.ed_name);
        ed_link = dialog.findViewById(R.id.ed_link);
        btn_add = dialog.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = ed_name.getText().toString().trim();
                String link = ed_link.getText().toString().trim();
                if (name.isEmpty() || link.isEmpty()){
                    Toast.makeText(getActivity().getApplicationContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    News news = new News();
                    news.setName(name);
                    news.setLink(link);
                    if (dao.insert(news)){
                        Toast.makeText(getActivity().getApplicationContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                        UpdateLV();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(), "lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
        dialog.show();
    }
    public void NoInternetToast(){
        LayoutInflater inflater = getLayoutInflater();
        View v = inflater.inflate(R.layout.no_internet_toast, null);
        Toast toast = new Toast(getActivity().getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(v);
        toast.show();
    }
    private boolean checkNetwork() {
        boolean wifiAvailable = false;
        boolean mobileAvailable = false;
        ConnectivityManager conManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfo = conManager.getAllNetworkInfo();
        for (NetworkInfo netInfo : networkInfo) {
            if (netInfo.getTypeName().equalsIgnoreCase("WIFI"))
                if (netInfo.isConnected())
                    wifiAvailable = true;
            if (netInfo.getTypeName().equalsIgnoreCase("MOBILE"))
                if (netInfo.isConnected())
                    mobileAvailable = true;
        }
        return wifiAvailable || mobileAvailable;
    }

    public void delete(int id){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_del);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_del = dialog.findViewById(R.id.btn_del);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dao.delete(id)){
                    Toast.makeText(getActivity().getApplicationContext(), "xóa thành công", Toast.LENGTH_SHORT).show();
                    UpdateLV();
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "lỗi", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });dialog.show();
        UpdateLV();
    }
    public void UpdateLV(){
        list = (ArrayList<News>) dao.getALL();
        adapter = new NewsAdapter(getActivity().getApplicationContext(),getActivity(), list);
        lv_main.setAdapter(adapter);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
            Toast.makeText(context.getApplicationContext(), "clear cache success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "clear cache failed", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }
}