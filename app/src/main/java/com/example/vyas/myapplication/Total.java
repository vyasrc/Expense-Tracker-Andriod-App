package com.example.vyas.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Total extends AppCompatActivity {


;
    Button btntotal;
    Button btnsearch;

    GridView totalvalue;
    GridView viewall;
    Spinner spinner;
    Spinner spinner1;
    String text;
    String text1;

    ArrayList<String> view=null;
    ArrayAdapter<String> x = null;
    ArrayList<String> view1=null;
    ArrayAdapter<String> x1 = null;

    private ArrayList<HashMap<String, String>> list;



    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        view = new ArrayList<String>();
        x = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,view);
        view1 = new ArrayList<String>();
        x1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,view1);
        init();
    }

    public void init(){
        spinner = (Spinner) findViewById(R.id.month);
        spinner1 = (Spinner) findViewById(R.id.year);
        btnsearch=(Button)findViewById(R.id.btnsearch);
        btntotal=(Button)findViewById(R.id.btntotal);

        viewall= (GridView)findViewById(R.id.viewall);
        totalvalue= (GridView)findViewById(R.id.totalvalue);
        btnsearch.setOnClickListener(dbButtonsListener);
        btntotal.setOnClickListener(dbButtonsListener);

        db= new MyDB(getApplicationContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);


    }




    public View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnsearch:
                    text = spinner.getSelectedItem().toString();
                    text1=spinner1.getSelectedItem().toString();
                    Cursor cursor = db.getAllRecords(text+"-"+text1);
                    view.clear();
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        view.add(cursor.getString(cursor.getColumnIndex(MyDB.ACTIVITY)) + "");
                        view.add(cursor.getString(cursor.getColumnIndex(MyDB.DATE)) + "");
                        view.add(cursor.getDouble(cursor.getColumnIndex(MyDB.AMOUNT)) + "");
                    }
                    viewall.setAdapter(x);
                    break;
                case R.id.btntotal:
                    Cursor cursor1 = db.getTotalSum(text+"-"+text1);
                    view1.clear();
                    for(cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()){
                        view1.add(cursor1.getDouble(0)+"");
                    }
                    totalvalue.setAdapter(x1);
                    break;

            }
        }
    };
    @Override
    protected void onStart()  {
        super.onStart();
        db.openDB();
    }

    protected void onStop() {
        super.onStop();
        db.closeDB();
    }
    private String getValue(EditText etfname) {
        return etfname.getText().toString().trim();
    }

}
