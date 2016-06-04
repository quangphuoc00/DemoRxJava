package com.example.quangphuoc.demorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CountDownActivity extends AppCompatActivity {

    TextView tvText;
    private Subscription subCountDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emit);
        tvText = (TextView) findViewById(R.id.tv_text);

        //Create observable
        Observable<String> countDownObservable = CreateObservable(5);

        //consumes
        subCountDown = countDownObservable
                .subscribeOn(Schedulers.newThread())
                .map(s -> s+="aaa")
                .subscribeOn(Schedulers.io())
                .map(s -> s+="map")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    tvText.setText(s);
                });
    }

    private Observable<String> CreateObservable(int number){
        return Observable.create(sub -> {
            for (int i = number; i >= 0; i--) {
                try {
                    sub.onNext(i+"");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sub.onCompleted();}
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subCountDown != null && !subCountDown.isUnsubscribed())
            subCountDown.unsubscribe();
    }
}
