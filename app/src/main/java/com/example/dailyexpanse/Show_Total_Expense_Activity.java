package com.example.dailyexpanse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Show_Total_Expense_Activity extends AppCompatActivity {
    
    private Spinner spinner;
    private  TextView fromdateTV, todateTV;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__total__expense_);
        
        init();
        
    }
    
     private void init() {

        spinner = findViewById(R.id.spinner);
        fromdateTV = findViewById(R.id.fromdateTV);
        todateTV = findViewById(R.id.todateTV);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
    }
}
