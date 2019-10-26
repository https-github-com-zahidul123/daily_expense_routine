package com.example.dailyexpanse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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

import com.example.dailyexpanse.All_sub_class.Show_total_expense;
import com.example.dailyexpanse.Db_modelClass.Dbhelper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText expenseamountET, expensedateET, expensetimeET;
    private ImageView documentimageView;
    private Button adddocumentBtn, addexpenseBtn;
    int selected=1;
    String nmselecteditm;
    String expanseammount,expansedate,expansetime;
    String imageurl;
    String currentPhotoPath;
    Dbhelper dbhelper;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    public static final int PICK_IMAGE = 1,CAMERA_REQUEST=2;
    int resultup=1;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper=new Dbhelper(this);
        init();
        addinSpinner();
        otherfielddata();
         uid=getIntent().getStringExtra("EXPENSE_ID");
        resultup=getIntent().getIntExtra("result",1);

        if (resultup==100){
            addexpenseBtn.setText("Update File");
        }
      adddocumentBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              imageordocument();
          }
      });

      addexpenseBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (resultup==1){
                  addexpenseBtn.setText("Update File");
                  long result= dbhelper.insertData(expenseamountET.getText().toString(),expensedateET.getText().toString(),
                          expensetimeET.getText().toString(),imageurl,nmselecteditm);
                  if (result==-1){
                      Toast.makeText(MainActivity.this,"sorry to insert",Toast.LENGTH_LONG).show();
                  }else {
                      addexpenseBtn.setText("Update File");
                      Intent intent=new Intent(MainActivity.this, Show_total_expense.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);

                  }
              }else if (resultup==100){
                  boolean result= dbhelper.updatedata(expanseammount,expansedate,expansetime,uid);
                  if (!result){
                      resultup=1;
                      addexpenseBtn.setText("Add expense");
                      Toast.makeText(MainActivity.this,"sorry to update",Toast.LENGTH_LONG).show();
                  }else {

                      resultup=1;
                      addexpenseBtn.setText("Add expense");
                      Intent intent=new Intent(MainActivity.this, Show_total_expense.class);
                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                      startActivity(intent);
                  }
              }


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
                    @RequiresApi(api = Build.VERSION_CODES.M)
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

@RequiresApi(api = Build.VERSION_CODES.M)
private void takePhotoFromCamera(){
    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
    {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
    }else{

       // Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(cameraIntent, CAMERA_REQUEST);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST);
            }
        }
    }


}

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
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

            imageurl=currentPhotoPath;
           // Bitmap photo = (Bitmap) data.getExtras().get("data");
           // documentimageView.setImageBitmap(photo);

            documentimageView.setImageURI(Uri.parse(imageurl));
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

