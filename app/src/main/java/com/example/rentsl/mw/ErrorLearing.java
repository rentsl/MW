package com.example.rentsl.mw;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ErrorLearing extends AppCompatActivity {
    int Tx;
    String truewords;
    String tureexplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_learing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**状态栏透明*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }

        Intent intent = getIntent();
        Tx = intent.getIntExtra("number", -1);
        truewords = intent.getStringExtra("truewords");
        tureexplain = intent.getStringExtra("trueexplain");
        TextView ewords = (TextView) findViewById(R.id.errorwords);
        TextView explain = (TextView) findViewById(R.id.errorexplain);

        ewords.setText(truewords);
        explain.setText(tureexplain);

    }
    /**监听函数*/
    public void exitbutton3(View v) {
        this.finish();
        Data.setLearningfinish(0);
    }

}
