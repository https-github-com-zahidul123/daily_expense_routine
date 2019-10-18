package com.example.dailyexpanse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText expenseamountET, expensedateET, expensetimeET;
    private ImageView documentimageView;
    private Button adddocumentBtn, addexpenseBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addinSpinner();

    }

    private void addinSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(0, "Select expense type");
        categories.add("Electricity Bill");
        categories.add("Lunch");
        categories.add("Transport Cost");
        categories.add("Breakfast");
        categories.add("Dinner");
        categories.add("Net Bill");
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(dataAdapter);

        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position).equals("Select expense type"))
                {
                    //do nothing
                }
                else
                {
                    String item = parent.getItemAtPosition(position).toString();
                    Toast.makeText(MainActivity.this, "Selected"+item, Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void init() {

        spinner = findViewById(R.id.spinner);
        expenseamountET = findViewById(R.id.expenseamountET);
        expensedateET = findViewById(R.id.expensedateET);
        expensetimeET = findViewById(R.id.expensetimetET);
        documentimageView = findViewById(R.id.documentimageView);
        adddocumentBtn = findViewById(R.id.adddocumentBtn);
        addexpenseBtn = findViewById(R.id.addexpenseBtn);

    }
}

