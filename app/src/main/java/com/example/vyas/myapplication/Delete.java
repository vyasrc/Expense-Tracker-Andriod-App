package com.example.vyas.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Delete extends AppCompatActivity {

    EditText delactivity;
    //EditText delmonth;
    Button btndelete;


    ArrayList<String> lables = new ArrayList<String>();
    ArrayAdapter<CharSequence> adapter;
    ArrayAdapter<CharSequence> adapter1;
    ArrayAdapter<String>
            dataAdapter;


    String m;
    String y;
    String act;
    Spinner spm;
    Spinner spy;
    Spinner spact;

    ArrayList<String> view=null;
    ArrayAdapter<String> x = null;
    ArrayList<String> view1=null;
    ArrayAdapter<String> x1 = null;

    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        view = new ArrayList<String>();
        x = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,view);
        view1 = new ArrayList<String>();
        x1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,view1);

    }

    public void init(){
        spm = (Spinner) findViewById(R.id.month);
        spy = (Spinner) findViewById(R.id.year);
        spact = (Spinner) findViewById(R.id.activity);
        btndelete=(Button)findViewById(R.id.btndelete);
        btndelete.setOnClickListener(dbButtonsListener);
        spm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m=parent.getItemAtPosition(position).toString();
                lables = db.getAllLabels(m+"-"+y);
                System.out.print(lables.size());
                for (int j = 0; j < lables.size(); j++) {
                    System.out.println(lables.get(j));
                }
                // System.out.println(m);
                //lables = new ArrayList<String>();

                // ArrayAdapter<String>
//                        dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                        android.R.layout.simple_spinner_item, lables);
                dataAdapter = new ArrayAdapter<String>(Delete.this,
                        R.layout.spinnerview,R.id.txt_name,lables);

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
                return;

            }
        });

        spy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                y=parent.getItemAtPosition(position).toString();
                lables = db.getAllLabels(m+"-"+y);

                System.out.print(lables.size());
                for (int j = 0; j < lables.size(); j++) {
                    System.out.println(lables.get(j));
                }
                //  System.out.println(y);
                //lables = new ArrayList<String>();

                // ArrayAdapter<String>
//              dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
//                      android.R.layout.simple_spinner_item, lables);

                dataAdapter = new ArrayAdapter<String>(Delete.this,
                        R.layout.spinnerview,R.id.txt_name,lables);

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
                return;
            }
        });

       // ArrayAdapter<CharSequence>
                adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spm.setAdapter(adapter);

        //ArrayAdapter<CharSequence>
                adapter1 = ArrayAdapter.createFromResource(this,
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
                return;
            }
        });
    }

//    String text;
//    String text1;

    public View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btndelete:
//                    text = spinner.getSelectedItem().toString();
//                    text1=spinner1.getSelectedItem().toString();
                    long resultdelete = db.delete(act,m+"-"+y);
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
    private String getValue(EditText etfname) {
        return etfname.getText().toString().trim();
    }

}
