package com.asnim.vyas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private EditText user;
    private EditText pass;
    private TextInputLayout tilu;
    private TextInputLayout tilp;
    private static final String MyPREFERENCES = "MyPrefs" ;
    private static final String Username = "username";
    private static final String Password = "password";
    private SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        Button btncreate = (Button) findViewById(R.id.btncreate);
        Button btnlogin = (Button) findViewById(R.id.btnlogin);
        Button btnchange = (Button) findViewById(R.id.btnchange);
        btncreate.setOnClickListener(dbButtonsListener);
        btnlogin.setOnClickListener(dbButtonsListener);
        btnchange.setOnClickListener(dbButtonsListener);
        tilu = (TextInputLayout)findViewById(R.id.input_username);
        tilp = (TextInputLayout)findViewById(R.id.input_password);

//        til.setErrorEnabled(true);
//        til.setError("some error..");
        if (sharedpreferences.getString(Username,"").compareTo("") == 0){
                btnlogin.setVisibility(View.GONE);
                btnchange.setVisibility(View.GONE);
        }
        else{
            btncreate.setVisibility(View.GONE);

        }
    }

    private String getValue(EditText etfname) {
        return etfname.getText().toString().trim();
    }

    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btncreate:
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    String sc = user.getText().toString();
                    String pc = pass.getText().toString();
                    int flag=0;
                    if (sc.compareTo("")==0) {
                        tilu.setErrorEnabled(true);
                        tilu.setError("Empty Input");
                        flag=1;
                    }
                     if (pc.compareTo("")==0){
                        tilp.setErrorEnabled(true);
                        tilp.setError("Empty Input");
                         flag=1;
                    }
                    if (flag==0) {
                        editor.putString(Username, sc);
                        editor.putString(Password, pc);
                        editor.apply();
                        Intent c = new Intent(MainActivity.this, Menu.class);
                        startActivity(c);
                        finish();
                    }

                     break;
                case R.id.btnlogin:
//                    String j=sharedpreferences.getString(Password,"");
//                    System.out.println(j);
                    String sl = getValue(user);
                    String pl = getValue(pass);
                     flag=0;
                    if (sharedpreferences.getString(Password, "").compareTo(pl) == 0
                            && sl.compareTo("")==0) {
                        tilu.setErrorEnabled(true);
                        tilu.setError("Empty Input");
                        flag=1;
                    }
                     if(sharedpreferences.getString(Username, "").compareTo(sl) == 0
                             && pl.compareTo("")==0){
                        tilp.setErrorEnabled(true);
                        tilp.setError("Empty Input");
                         flag=1;

                }
                        if(sl.compareTo("")==0 && pl.compareTo("")==0){
                            tilu.setErrorEnabled(true);
                            tilu.setError("Empty Input");
                            tilp.setErrorEnabled(true);
                            tilp.setError("Empty Input");

                            flag=1;
                        }
                        if (sharedpreferences.getString(Username, "").compareTo(sl) != 0
                                && sl.compareTo("")!=0){
                            tilu.setErrorEnabled(true);
                            tilu.setError("Invalid Username");
                            flag=1;
                        }
                         if(sharedpreferences.getString(Password, "").compareTo(pl) != 0
                                 && pl.compareTo("")!=0) {
                            tilp.setErrorEnabled(true);
                            tilp.setError("Wrong Password");
                             flag=1;

                        }
                        if (flag==0) {
                            Intent l = new Intent(MainActivity.this, Menu.class);
                            startActivity(l);
                            finish();
                    }


                    break;
                case R.id.btnchange:
                    String us= getValue(user);
                    if (sharedpreferences.getString(Username, "").compareTo(us) == 0
                    && us.compareTo("")!=0) {
                        Intent f = new Intent(MainActivity.this, ChangePass.class);
                        startActivity(f);
                    }
                    //finish();
                    break;

            }
        }
    };
}



