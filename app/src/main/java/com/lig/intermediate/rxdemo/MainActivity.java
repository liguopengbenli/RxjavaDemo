package com.lig.intermediate.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private String greeting="Hello from RxJava";
    private Observable<String> myObservable;
    private Observer<String> myObserver;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGretting);

        myObservable = Observable.just(greeting);

        myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.i("RXdemo", "onSubscribe");


            }

            @Override
            public void onNext(@NonNull String s) {
                Log.i("RXdemo", "onNext");
                textView.setText(s);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.i("RXdemo", "onError");

            }

            @Override
            public void onComplete() {
                Log.i("RXdemo", "onComplete");

            }
        };

        myObservable.subscribe(myObserver);

    }
}