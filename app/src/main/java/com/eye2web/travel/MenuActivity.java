package com.eye2web.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent menuIntent = getIntent();
        String gu = "";
        gu = menuIntent.getStringExtra("gu");

        if(null != gu && !"".equalsIgnoreCase(gu)) {
            if("travel".equalsIgnoreCase(gu)) {
                setContentView(R.layout.activity_travel);
            } else if("festival".equalsIgnoreCase(gu)) {
                setContentView(R.layout.activity_festival);
            }
        } else {
            setContentView(R.layout.activity_travel);
        }




    }
}
