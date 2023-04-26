package com.example.test_log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;

    private HomeFragment homeFragment = new HomeFragment();
    private NewsFragment newsFragment = new NewsFragment();
    private ProfileFragment profileFragment = new ProfileFragment();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.menu_bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,homeFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               if(item.getItemId() == R.id.item_nv_home){
                   getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,homeFragment).commit();
                   return true;
               }else if(item.getItemId() == R.id.item_nv_news)
               {
                   getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,newsFragment).commit();
                   return true;
               }else
               {
                   getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,profileFragment).commit();
                   return true;
               }


            }
        });


    }


}




