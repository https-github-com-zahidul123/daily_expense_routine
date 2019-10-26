package com.example.dailyexpanse.All_sub_class;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dailyexpanse.Fragment_classes.Dashboard_frag;
import com.example.dailyexpanse.Fragment_classes.Show_expanse_frag;
import com.example.dailyexpanse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Show_total_expense extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_total_expense);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        spinner = findViewById(R.id.spinner);

        Fragment fragment=new Dashboard_frag();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutId,fragment);
        fragmentTransaction.commit();

        //navigation item selected action
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        replaceFragment(new Dashboard_frag());
                        return true;
                    case R.id.expense:
                        replaceFragment(new Show_expanse_frag());
                        return true;
                }
                return false;
            }
        });
    }


    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutId,fragment);
        fragmentTransaction.commit();
    }
}
