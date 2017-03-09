package com.example.vyas.myapplication;

import android.database.Cursor;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;





public class Update extends AppCompatActivity {

    private EditText upamount;
    private TextView curram;
    private ArrayList<String> lables = new ArrayList<>();
    private ArrayAdapter<String>
            dataAdapter;



    private TextInputLayout tilam;
     private String m;
     private String y;
    private String act;
    private Spinner spact;

    private Double curr;




    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);



    }

    private void init(){
        Spinner spm = (Spinner) findViewById(R.id.month);
        Spinner spy = (Spinner) findViewById(R.id.year);
        spact = (Spinner) findViewById(R.id.activity);
        curram=(TextView)findViewById(R.id.curram);
        upamount= (EditText)findViewById(R.id.upamount);
        Button btnupdate = (Button) findViewById(R.id.btnupdate);

        btnupdate.setOnClickListener(dbButtonsListener);
        tilam=(TextInputLayout)findViewById(R.id.input_upamount);


        spm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m=parent.getItemAtPosition(position).toString();
                lables = db.getAllLabels(m+"-"+y);
                curr=0.0;
                curram.setText(" "+curr+"");
                System.out.print(lables.size());
                for (int j = 0; j < lables.size(); j++) {
                    System.out.println(lables.get(j));
                }
                dataAdapter = new ArrayAdapter<>(Update.this,
                        R.layout.spinnerview, R.id.txt_name, lables);
                spact.setAdapter(dataAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

      spy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              y=parent.getItemAtPosition(position).toString();
              lables = db.getAllLabels(m+"-"+y);
              curr=0.0;
              curram.setText(" "+curr+"");
              System.out.print(lables.size());
              for (int j = 0; j < lables.size(); j++) {
                  System.out.println(lables.get(j));
              }
              dataAdapter = new ArrayAdapter<>(Update.this,
                      R.layout.spinnerview, R.id.txt_name, lables);


              spact.setAdapter(dataAdapter);


          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });


     //   ArrayAdapter<CharSequence>
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.month_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spm.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.year_array, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spy.setAdapter(adapter1);

        spact.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                act=parent.getItemAtPosition(position).toString();
                Cursor cursor1 = db.getCurrentAmount(act);
                //amount.clear();
                for(cursor1.moveToFirst(); !cursor1.isAfterLast(); cursor1.moveToNext()){
                    //System.out.println(cursor1.getDouble(0)+"");

                    curr=cursor1.getDouble(0);
                    System.out.println(curr);
//                    System.out.println(currview.getText());

                    //amount.add();
//                    for (int j = 0; j < amount.size(); j++) {
//                        System.out.println(amount.get(j));
//                    }
                }
                curram.setText(" "+curr+"");

                //  x=new ArrayAdapter<String>(Update.this,android.R.layout.simple_list_item_1,amount);
                //x.toString();

                //currview.setVisibility(View.VISIBLE);

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
                case R.id.btnupdate:
                    String am=getValue(upamount);
                    if (am.compareTo("")==0){
                        tilam.setErrorEnabled(true);
                        tilam.setError("Empty Input");
//                        Toast.makeText(Update.this, "Error: Amount not inserted",
//                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        long resultupdate = db.update(act, m + "-" + y,
                                Double.valueOf(am));
                        if (resultupdate == 0) {
                            Toast.makeText(Update.this, "Error", Toast.LENGTH_SHORT).show();

                        } else if (resultupdate == 1) {
                            Toast.makeText(Update.this, "Successfully Updated" , Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Update.this, "Multiple", Toast.LENGTH_SHORT).show();
                        }
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
