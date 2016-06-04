package com.example.quangphuoc.demorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SimpleAdapter;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class InstantSearchActivty extends AppCompatActivity {
    AutoCompleteTextView atcSearchBox;
    String[] allData = {"javascript", "java", "c", "c#", "c++", "android", "objective-c", "swift"};
    ArrayAdapter<String> adapter;
    ArrayList<String> lstData = new ArrayList<>();
    private Subscription _subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instant_search_activty);
        atcSearchBox = (AutoCompleteTextView) findViewById(R.id.atc_search_box);

        atcSearchBox.setThreshold(0);
        _subscription = RxTextView.textChangeEvents(atcSearchBox)//
                .debounce(400, TimeUnit.MILLISECONDS)// default Scheduler is Computation
                .filter(textViewTextChangeEvent -> {
                    return !TextUtils.isEmpty(textViewTextChangeEvent.text());
                })
                .map(textViewTextChangeEvent1 -> {
                    ArrayList<String> filterStrings = new ArrayList<String>();
                    for (String str: allData) {
                        if(str.contains(textViewTextChangeEvent1.text()))
                            filterStrings.add(str);
                    }
                    return filterStrings;
                })
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(strings -> {
                    if(strings.size() == 0)
                        return;

                    lstData.clear();
                    lstData.addAll(strings);
                    adapter = new ArrayAdapter<String>
                            (this, android.R.layout.simple_dropdown_item_1line, lstData);
                    atcSearchBox.setAdapter(adapter);
                    atcSearchBox.showDropDown();
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(_subscription!=null)
            _subscription.unsubscribe();
    }
}
