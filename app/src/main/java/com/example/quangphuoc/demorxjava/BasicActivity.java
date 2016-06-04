package com.example.quangphuoc.demorxjava;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BasicActivity extends AppCompatActivity {
    
    private static final int TYPE_ONE = 1;
    private static final int TYPE_TWO = 2;
    TextView tvText;
    private Subscription subHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        tvText = (TextView) findViewById(R.id.tv_text);

        //Create observable
        Observable<String> helloWorldObservable = CreateObservable(TYPE_ONE);
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                tvText.setText(s);
            }
        };
        //consumes
        subHelloWorld = helloWorldObservable
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map(m -> m + " Phuoc")
                .subscribe(subscriber);

    }

    private Observable<String> CreateObservable(int type){
        if(type == TYPE_ONE)
            return CreateBasicObservable();
        else
            return Observable.just("(type 2) Hello");
    }

    @NonNull
    private Observable<String> CreateBasicObservable() {
        return Observable.create(sub -> {
            sub.onNext("(type 1) Hello ");
            sub.onCompleted();}
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subHelloWorld != null && !subHelloWorld.isUnsubscribed())
            subHelloWorld.unsubscribe();
    }
}
