package com.example.dailyexpanse.Fragment_classes;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.dailyexpanse.Db_modelClass.Dbhelper;


import com.example.dailyexpanse.R;

import java.util.Calendar;


public class Dashboard_frag extends Fragment {

    public Dashboard_frag() {
        // Required empty public constructor
    }
    private DatePickerDialog.OnDateSetListener mFromDateSetListener;
    private DatePickerDialog.OnDateSetListener mToDateSetListener;
    private Spinner spinner;
    private int selected;
    private String nmselecteditm=null;
    private ImageView fromdate,todate;
    private TextView expense,fromdateshow,todateshow;
    String expansedateto=null,expansedatefrom=null;
    int initial=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_frag, container, false);
        spinner=view.findViewById(R.id.selectExpenseTypeSpinnerId);
        fromdate=view.findViewById(R.id.fromDateIVId);
        todate=view.findViewById(R.id.toDateIVId);
        expense=view.findViewById(R.id.totalExpenseTVId);
        fromdateshow=view.findViewById(R.id.fromDateTVId);
        todateshow=view.findViewById(R.id.toDateTVId);
        addspinner();





        fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mFromDateSetListener,
                        year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setTitle("Please select date");
                datePickerDialog.show();
            }
        });
        mFromDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                expansedatefrom = year + "/" + month + "/" + dayOfMonth;
                fromdateshow.setText(expansedatefrom);
            }
        };

        todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mToDateSetListener,
                        year, month, day
                );
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setTitle("Please select date");
                datePickerDialog.show();
            }
        });

        mToDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                expansedateto = year + "/" + month + "/" + dayOfMonth;
                todateshow.setText(expansedateto);
                Dbhelper dbhelper=new Dbhelper(getActivity());
                int amount=dbhelper.getamountbydate(expansedatefrom,expansedateto);
                expense.setText(String.valueOf(amount));
            }
        };




        return view;
    }




    private void addspinner() {

        ArrayAdapter<CharSequence> snipperitem=ArrayAdapter.createFromResource(getActivity(),R.array.catagory,R.layout.support_simple_spinner_dropdown_item);
        snipperitem.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(snipperitem);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner.getSelectedItem().equals("Select expense type"))
                {
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                   int amount=dbhelper.getallamount();
                    expense.setText(String.valueOf(amount));

                }else if (spinner.getSelectedItem().equals("Electricity Bill")){
                    nmselecteditm="Electricity Bill";

                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),expansedatefrom+" "+expansedateto+" "+nmselecteditm,Toast.LENGTH_LONG).show();

                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));

                }else if (spinner.getSelectedItem().equals("Lunch")){
                    nmselecteditm="Lunch";
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),expansedatefrom+" "+expansedateto+" "+nmselecteditm,Toast.LENGTH_LONG).show();
                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));

                }else if (spinner.getSelectedItem().equals("Transport Cost")){
                    nmselecteditm="Transport Cost";
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),"the amount is count",Toast.LENGTH_LONG).show();
                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));
                }else if (spinner.getSelectedItem().equals("Breakfast")){
                    nmselecteditm="Breakfast";
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),"the amount is count",Toast.LENGTH_LONG).show();
                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));
                }else if (spinner.getSelectedItem().equals("Dinner")){
                    nmselecteditm="Dinner";
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),"the amount is count",Toast.LENGTH_LONG).show();
                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));
                }else if (spinner.getSelectedItem().equals("Net Bill")){
                    nmselecteditm="Net Bill";
                    Dbhelper dbhelper=new Dbhelper(getActivity());
                    Toast.makeText(getActivity(),"the amount is count",Toast.LENGTH_LONG).show();
                    int amount=dbhelper.getamountbyitem(nmselecteditm);
                    expense.setText(String.valueOf(amount));
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}
