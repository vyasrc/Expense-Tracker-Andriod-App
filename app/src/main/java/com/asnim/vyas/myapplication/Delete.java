package com.asnim.vyas.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Delete extends AppCompatActivity {


    private ArrayList<String> lables = new ArrayList<>();
    private ArrayAdapter<String>
            dataAdapter;


    private String month,year, act;

    private Spinner spact;


    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);


    }

    private void init(){
        Spinner spm = (Spinner) findViewById(R.id.month);
        Spinner spy = (Spinner) findViewById(R.id.year);
        spact = (Spinner) findViewById(R.id.activity);
        Button btndelete = (Button) findViewById(R.id.btndelete);
        btndelete.setOnClickListener(dbButtonsListener);
        spm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month=parent.getItemAtPosition(position).toString();
                lables = db.getAllLabels(month+"-"+year);
                System.out.print(lables.size());
                for (int j = 0; j < lables.size(); j++) {
                    System.out.println(lables.get(j));
                }



                //dasda

                // ArrayAdapter<String>
//                        dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                        android.R.layout.simple_spinner_item, lables);
                dataAdapter = new ArrayAdapter<>(Delete.this,
                        R.layout.spinnerview, R.id.txt_name, lables);

//                dataAdapter
//                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spact.setAdapter(dataAdapter);
                //  spact.setVisibility(View.VISIBLE);
                //  spact.setOnItemSelectedListener(this);
                //   spact.setSelection(0);
//                act=spact.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                year=parent.getItemAtPosition(position).toString();
                lables = db.getAllLabels(month+"-"+year);

                System.out.print(lables.size());
                for (int j = 0; j < lables.size(); j++) {
                    System.out.println(lables.get(j));
                }
                //  System.out.println(y);
                //lables = new ArrayList<String>();

                // ArrayAdapter<String>
//              dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                      android.R.layout.simple_spinner_item, lables);

                dataAdapter = new ArrayAdapter<>(Delete.this,
                        R.layout.spinnerview, R.id.txt_name, lables);

//              dataAdapter
//                      .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spact.setAdapter(dataAdapter);
                // spact.setVisibility(View.VISIBLE);
                // spact.setOnItemSelectedListener(this);
                //    spact.setSelection(0);

                //        act=spact.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       // ArrayAdapter<CharSequence>
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spm.setAdapter(adapter);

        //ArrayAdapter<CharSequence>
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spy.setAdapter(adapter1);
        spact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                act=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

//    String text;
//    String text1;

    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btndelete:
//                    text = spinner.getSelectedItem().toString();
//                    text1=spinner1.getSelectedItem().toString();
                    long resultdelete = db.delete(act,month+"-"+year);
                    if (resultdelete == 0) {
                        Toast.makeText(Delete.this, "Error ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Delete.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };
    @Override
    protected void onStart()  {
        super.onStart();
        db= new MyDB(getApplicationContext());
        db.openDB();
        init();
    }

    protected void onStop() {
        super.onStop();
        db.closeDB();
    }

}
