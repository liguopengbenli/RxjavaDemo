package com.lig.intermediate.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private String greeting="Hello from RxJava";
    private Observable<String> myObservable;

    private DisposableObserver<String> myObserver;
    private DisposableObserver<String> myObserver2;

    //private Disposable disposable;
    private TextView textView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGretting);

        myObservable = Observable.just(greeting);

        myObserver = new DisposableObserver<String>() {
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

        compositeDisposable.add(
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(myObserver) // return a observer
        );

        myObserver2 = new DisposableObserver<String>() {
            @Override
            public void onNext(@NonNull String s) {
                Log.i("RXdemo", "onNext");
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

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
        compositeDisposable.add(
                myObservable
                        .subscribeWith(myObserver2) // return a observer
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // clear the disposables held, if use dispose will destroy the composite
    }
}