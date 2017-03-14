package com.example.vyas.expensetracker;

import android.content.Context;

import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePass extends AppCompatActivity {

    private EditText currpass, newpass;
    private TextInputLayout tilc,tiln;
    private static final String MyPREFERENCES = "MyPrefs";
    private static final String Password = "password";
    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        currpass = (EditText) findViewById(R.id.currpass);
        newpass = (EditText) findViewById(R.id.newpass);
        Button btnchange = (Button) findViewById(R.id.btnchange);
        btnchange.setOnClickListener(dbButtonsListener);
        tilc = (TextInputLayout)findViewById(R.id.input_currpass);
        tiln = (TextInputLayout)findViewById(R.id.input_newpass);

    }

    private String getValue(EditText etfname) {
        return etfname.getText().toString().trim();
    }

    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnchange:
                    String cp=getValue(currpass);
                    String np = getValue(newpass);
                    int flag = 0;
                    if (np.compareTo("")==0 && cp.compareTo("")==0){
                        tilc.setErrorEnabled(true);
                        tilc.setError("Empty Input");
                        tiln.setErrorEnabled(true);
                        tiln.setError("Empty Input");
                        flag =1;
//                        Toast.makeText(ChangePass.this, "Error: Current Password not inserted",
//                                Toast.LENGTH_SHORT).show();
                    }
                    if (np.compareTo("")!=0 && cp.compareTo("")==0){
                        tilc.setErrorEnabled(true);
                        tilc.setError("Empty Input");
                        flag =1;
//                        Toast.makeText(ChangePass.this, "Error: Current Password not inserted",
//                                Toast.LENGTH_SHORT).show();
                    }
                    if (np.compareTo("")==0 && cp.compareTo("")!=0){
                        tiln.setErrorEnabled(true);
                        tiln.setError("Empty Input");
                        flag =1;
//                            Toast.makeText(ChangePass.this, "Error: New Password not inserted",
//                                    Toast.LENGTH_SHORT).show();
                    }

                     if (sharedpreferences.getString(Password, "").compareTo(cp) != 0 &&
                             np.compareTo("")==0 && cp.compareTo("")!=0 ) {
                         tilc.setErrorEnabled(true);
                         tilc.setError("Wrong password");
                         tiln.setErrorEnabled(true);
                         tiln.setError("Empty Input");
                         flag =1;

                    }if (sharedpreferences.getString(Password, "").compareTo(cp) != 0 &&
                             np.compareTo("")!=0 && cp.compareTo("")!=0) {
                         tilc.setErrorEnabled(true);
                         tilc.setError("Wrong password");
                         flag =1;

                    }

                    if (flag ==0) {
                         SharedPreferences.Editor editor = sharedpreferences.edit();
                         editor.putString(Password, np);
                         editor.apply();
                         Toast.makeText(ChangePass.this, "Changed Password Successfully", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(ChangePass.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
            }
        }


    };
}

