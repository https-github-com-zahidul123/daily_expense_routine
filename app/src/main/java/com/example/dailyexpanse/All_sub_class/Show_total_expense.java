package com.example.dailyexpanse.All_sub_class;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dailyexpanse.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Show_total_expense extends AppCompatActivity {
    private Spinner spinner;
    private TextView fromdateTV, todateTV;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_total_expense);
        init();
    }


    private void init() {

        spinner = findViewById(R.id.spinner);
        fromdateTV = findViewById(R.id.fromdateTV);
        todateTV = findViewById(R.id.todateTV);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}
