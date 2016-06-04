package com.example.quangphuoc.demorxjava;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscription;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;


import static android.text.TextUtils.isEmpty;
import static android.util.Patterns.EMAIL_ADDRESS;

public class ValidFormActivity extends AppCompatActivity {
    TextView btnValidIndicator;
    EditText etEmail;
    EditText etPassword;
    EditText etNumber;

    private Observable<CharSequence> _emailChangeObservable;
    private Observable<CharSequence> _passwordChangeObservable;
    private Observable<CharSequence> _numberChangeObservable;

    private Subscription _subscription = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_form);
        etEmail = (EditText) findViewById(R.id.et_email);
        etPassword = (EditText) findViewById(R.id.et_password);
        etNumber = (EditText) findViewById(R.id.et_number);
        btnValidIndicator = (TextView)findViewById(R.id.btn_demo_form_valid);

        _emailChangeObservable = RxTextView.textChanges(etEmail).skip(1);
        _passwordChangeObservable = RxTextView.textChanges(etPassword).skip(1);
        _numberChangeObservable = RxTextView.textChanges(etNumber).skip(1);

        validForm();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _subscription.unsubscribe();
    }

    private void validForm() {
        _subscription = Observable.combineLatest(_emailChangeObservable,
                _passwordChangeObservable,
                _numberChangeObservable,
                (newEmail, newPassword, newNumber) -> {
                    boolean emailValid = !isEmpty(newEmail) &&
                            EMAIL_ADDRESS.matcher(newEmail).matches();
                    if (!emailValid) {
                        etEmail.setError("Invalid Email!");
                    }

                    boolean passValid = !isEmpty(newPassword) && newPassword.length() > 8;
                    if (!passValid) {
                        etPassword.setError("Invalid Password!");
                    }

                    boolean numValid = !isEmpty(newNumber);
                    if (numValid) {
                        int num = Integer.parseInt(newNumber.toString());
                        numValid = num > 0 && num <= 100;
                    }
                    if (!numValid) {
                        etNumber.setError("Invalid Number!");
                    }

                    return emailValid && passValid && numValid;
                })
                .subscribe(formValid -> {
                    if (formValid) {
                        btnValidIndicator.setBackgroundColor(Color.BLUE);
                    } else {
                        btnValidIndicator.setBackgroundColor(Color.GRAY);
                    }
                });
    }
}
