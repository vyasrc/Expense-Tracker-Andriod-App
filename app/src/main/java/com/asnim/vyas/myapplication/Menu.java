package com.asnim.vyas.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
    }

    private void init(){
        Button btinsert = (Button) findViewById(R.id.btinsert);
        Button btsearch = (Button) findViewById(R.id.btsearch);
        Button btnupdate = (Button) findViewById(R.id.btnupdate);
        Button btndelete = (Button) findViewById(R.id.btndelete);
        //ImageView view=(ImageView) findViewById(R.id.imageView);
        //.setOnClickListener(dbButtonsListener);
        btnupdate.setOnClickListener(dbButtonsListener);
        btndelete.setOnClickListener(dbButtonsListener);
        btinsert.setOnClickListener(dbButtonsListener);
        btsearch.setOnClickListener(dbButtonsListener);


    }

    private final View.OnClickListener dbButtonsListener = new View.OnClickListener() {
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


}
