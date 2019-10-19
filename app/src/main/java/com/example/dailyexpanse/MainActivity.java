package com.example.dailyexpanse;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText expenseamountET, expensedateET, expensetimeET;
    private ImageView documentimageView;
    private Button adddocumentBtn, addexpenseBtn;
    int selected=1;
    String nmselecteditm;
    String expanseammount,expansedate,expansetime;
    String imageurl;

    public static final int PICK_IMAGE = 1,CAMERA_REQUEST=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addinSpinner();
      otherfielddata();
      adddocumentBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              imageordocument();
          }
      });


    }

    private void imageordocument() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("please select your option");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }
private void takePhotoFromCamera(){

    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(cameraIntent, CAMERA_REQUEST);
    
}


    private void choosePhotoFromGallary() {
        Intent  intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==PICK_IMAGE){
            Uri uri=data.getData();
            imageurl=uri.toString();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                documentimageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this,imageurl,Toast.LENGTH_LONG).show();
        }
        if (requestCode==CAMERA_REQUEST){
            Uri uri=data.getData();
            imageurl=uri.toString();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                documentimageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this,imageurl,Toast.LENGTH_LONG).show();
        }
    }

    private void otherfielddata() {
        expanseammount=expenseamountET.getText().toString().trim();

        expensetimeET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar time = Calendar.getInstance();
                final int hour=time.get(Calendar.HOUR_OF_DAY);
                final int minute=time.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog=new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                String am_pm;
                                int mhour = 0;
                                if(hour > 12) {
                                    am_pm = "PM";
                                    mhour = hour - 12;
                                }
                                else
                                {
                                    am_pm="AM";
                                }
                                expensetimeET.setText(mhour+":"+minute+" "+am_pm);
                                // here time is ready for database
                                expansetime=hour+":"+minute;
                            }
                        },hour,minute,false);
                timePickerDialog.show();
            }
        });

        expensedateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar=Calendar.getInstance();
                final int year=calendar.get(Calendar.YEAR);
                final int month=calendar.get(Calendar.MONTH);
                final int day=calendar.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        expansedate=year+"/"+month+"/"+day;
                        expensedateET.setText(year+"/"+month+"/"+day);
                    }
                },year,month,day);

                datePickerDialog.show();
            }
        });

    }

    private void addinSpinner() {


        ArrayAdapter<CharSequence>snipperitem=ArrayAdapter.createFromResource(this,R.array.catagory,R.layout.support_simple_spinner_dropdown_item);
        snipperitem.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(snipperitem);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().equals("Select expense type"))
                {
                    selected=0;
                }else if (spinner.getSelectedItem().equals("Electricity Bill")){
                    nmselecteditm="Electricity Bill";
                    selected=1;

                }else if (spinner.getSelectedItem().equals("Lunch")){
                    nmselecteditm="Lunch";
                    selected=1;

                }else if (spinner.getSelectedItem().equals("Transport Cost")){
                    nmselecteditm="Transport Cost";
                    selected=1;

                }else if (spinner.getSelectedItem().equals("Breakfast")){
                    nmselecteditm="Breakfast";
                    selected=1;

                }else if (spinner.getSelectedItem().equals("Dinner")){
                    nmselecteditm="Dinner";
                    selected=1;

                }else if (spinner.getSelectedItem().equals("Net Bill")){
                    nmselecteditm="Net Bill";
                    selected=1;

                }
                    }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

