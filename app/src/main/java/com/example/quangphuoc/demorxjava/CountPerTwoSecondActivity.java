package com.example.quangphuoc.demorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class CountPerTwoSecondActivity extends AppCompatActivity {
    Button btnTap;
    TextView tvNumber;
    private Subscription _subscription;

    @Override
    public void onResume() {
        super.onResume();
        _subscription = _getBufferedSubscription();
    }

    @Override
    public void onPause() {
        super.onPause();
        _subscription.unsubscribe();
    }

    private Subscription _getBufferedSubscription() {
        return RxView.clicks(btnTap) //void
                .map(aVoid -> {      //void => int
                    return 4;
                })
                .buffer(2, TimeUnit.SECONDS) //int => int[]
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integers -> {
                    if (integers.size() > 0) {
                        tvNumber.setText(String.valueOf(integers.size()));
                    } else {
                        Log.d("Tag","--------- No taps received ");
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avoid_spam);
        btnTap = (Button)findViewById(R.id.btn_tap);
        tvNumber = (TextView)findViewById(R.id.tv_text);
    }
}
