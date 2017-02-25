package com.example.vyas.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    ImageButton btinsert;
    ImageButton btsearch;
    ImageButton btnupdate;
    ImageButton btndelete;
    MyDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    public void init(){
        btinsert=(ImageButton)findViewById(R.id.btinsert);
        btsearch=(ImageButton)findViewById(R.id.btsearch);
        btnupdate=(ImageButton)findViewById(R.id.btnupdate);
        btndelete=(ImageButton)findViewById(R.id.btndelete);
        btnupdate.setOnClickListener(dbButtonsListener);
        btndelete.setOnClickListener(dbButtonsListener);
        btinsert.setOnClickListener(dbButtonsListener);
        btsearch.setOnClickListener(dbButtonsListener);
       // db= new MyDB(getApplicationContext());
    }

    public View.OnClickListener dbButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btinsert:
                    Intent i=new Intent(Menu.this,Insert.class);
                    startActivity(i);
                    //finish();
                    break;
                case R.id.btsearch:
                    Intent s=new Intent(Menu.this,Total.class);
                    startActivity(s);
                    //finish();
                    break;
                case R.id.btnupdate:
                    Intent u=new Intent(Menu.this,Update.class);
                    startActivity(u);
                    //finish();
                    break;
                case R.id.btndelete:
                    Intent d=new Intent(Menu.this,Delete.class);
                    startActivity(d);
                    //finish();
                    break;

            }
        }
    };
//    @Override
//    protected void onStart()  {
//        super.onStart();
//        db.openDB();
//    }
//
//    protected void onStop() {
//        super.onStop();
//        db.closeDB();
//    }

}
