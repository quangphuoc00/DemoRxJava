package com.example.quangphuoc.demorxjava;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnBasic;
    private Button btnEmit;
    private Button btnSearch;
    private Button btnValid;
    private Button btnAvoidSpam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBasic = (Button) findViewById(R.id.btn_basic);
        btnAvoidSpam = (Button) findViewById(R.id.btn_avoid_spam);
        btnEmit = (Button) findViewById(R.id.btn_emit);
        btnSearch = (Button) findViewById(R.id.btn_search);
        btnValid = (Button) findViewById(R.id.btn_valid_form);
        btnBasic.setOnClickListener(v -> {
            DirectToActivity(BasicActivity.class);
        });
        btnEmit.setOnClickListener(v -> {
            DirectToActivity(CountDownActivity.class);
        });
        btnSearch.setOnClickListener(v -> {
            DirectToActivity(InstantSearchActivty.class);
        });
        btnValid.setOnClickListener(v -> {
            DirectToActivity(ValidFormActivity.class);
        });
        btnAvoidSpam.setOnClickListener(v -> {
            DirectToActivity(CountPerTwoSecondActivity.class);
        });
    }


    private void DirectToActivity(Class activity) {
        Intent mI = new Intent(this, activity);
        startActivity(mI);
    }
}
