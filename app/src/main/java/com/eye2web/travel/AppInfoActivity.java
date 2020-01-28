package com.eye2web.travel;

import android.os.Bundle;
import android.view.View;

public class AppInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    public void onGobackBtnClicked(View v) {
        super.onBackPressed();
    }
}
