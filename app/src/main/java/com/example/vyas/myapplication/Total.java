package com.example.vyas.myapplication;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.GridView;

import android.widget.Spinner;



import java.util.ArrayList;


public class Total extends AppCompatActivity {


;

    private GridView totalvalue;
    private GridView viewall;
    private String month;
    private String year;

    private ArrayList<String> viewdata=null;
    private ArrayAdapter<String> x = null;
    private ArrayList<String> viewtotal=null;
    private ArrayAdapter<String> x1 = null;





    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total);
        viewdata = new ArrayList<>();
        x = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewdata);
        viewtotal = new ArrayList<>();
        x1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, viewtotal);
        init();



    }

    private void init(){
        Spinner spinnermonth = (Spinner) findViewById(R.id.month);
        Spinner spinneryear = (Spinner) findViewById(R.id.year);
        Button btnsearch = (Button) findViewById(R.id.btnsearch);
        Button btntotal = (Button) findViewById(R.id.btntotal);

        viewall= (GridView)findViewById(R.id.viewall);
        totalvalue= (GridView)findViewById(R.id.totalvalue);
        btnsearch.setOnClickListener(dbButtonsListener);
        btntotal.setOnClickListener(dbButtonsListener);

        db= new MyDB(getApplicationContext());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnermonth.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinneryear.setAdapter(adapter1);

        spinnermonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month=parent.getItemAtPosition(position).toString();
                viewdata.clear();
                viewtotal.clear();
                totalvalue.setAdapter(x1);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinneryear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year=parent.getItemAtPosition(position).toString();
                viewdata.clear();
                viewtotal.clear();
                totalvalue.setAdapter(x1);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }




    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnsearch:
                    Cursor cursor = db.getAllRecords(month+"-"+year);
                    viewdata.clear();
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        viewdata.add(cursor.getString(cursor.getColumnIndex(MyDB.ACTIVITY)) + "");
                        viewdata.add(cursor.getString(cursor.getColumnIndex(MyDB.DATE)) + "");
                        viewdata.add(cursor.getDouble(cursor.getColumnIndex(MyDB.AMOUNT)) + "");
                    }
                    viewall.setAdapter(x);
                    break;
                case R.id.btntotal:
                    Cursor cursor1 = db.getTotalSum(month+"-"+year);
                    viewtotal.clear();
                    for(cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()){
                        viewtotal.add(cursor1.getDouble(0)+"");
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

}
