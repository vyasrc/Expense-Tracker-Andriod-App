package com.example.vyas.expensetracker;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Insert extends AppCompatActivity {

    private EditText etactivity;
    private EditText etamount;
    private TextInputLayout tilac;
    private TextInputLayout tilam;


    private MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        init();
    }

    private void init(){
        etactivity= (EditText)findViewById(R.id.etactivity);
        etamount= (EditText)findViewById(R.id.etamount);
        Button btninsert = (Button) findViewById(R.id.btninsert);
        btninsert.setOnClickListener(dbButtonsListener);
        tilac=(TextInputLayout)findViewById(R.id.input_etactivity);
        tilam=(TextInputLayout)findViewById(R.id.input_etamount);



        db= new MyDB(getApplicationContext());
    }

    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btninsert:
                        String act=getValue(etactivity);
                        String am=getValue(etamount);
                        int flag = 0;
                        if (act.compareTo("")==0 && am.compareTo("")!=0){
                            tilac.setErrorEnabled(true);
                            tilac.setError("Empty Input");
                            flag =1;
                        }if (act.compareTo("")!=0 && am.compareTo("")==0){
                            tilam.setErrorEnabled(true);
                            tilam.setError("Empty Input");
                            flag =1;
                        }if (act.compareTo("")==0 && am.compareTo("")==0){
                            tilac.setErrorEnabled(true);
                            tilac.setError("Empty Input");
                        tilam.setErrorEnabled(true);
                        tilam.setError("Empty Input");
                            flag =1;
                        }
                        if (flag ==0) {
                            long resultinsert = db.insert(act, Double.valueOf(am));
                            if (resultinsert == -1) {
                                Toast.makeText(Insert.this, "Error : Activity name already exist",
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Insert.this, "Successfully Inserted" ,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
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



