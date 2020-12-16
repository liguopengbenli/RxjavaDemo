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
    private String[] greetings= {"Hello A", "Hello B", "Hello C"};
    private Integer[] nums={1,2,3,4,5};
    private Observable<Integer> myObservable;

    private DisposableObserver<Integer> myObserver;
    private DisposableObserver<Integer> myObserver2;


    //private Disposable disposable;
    private TextView textView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGretting);

        //myObservable = Observable.just("Hello 1", "Hello 2", "Hello 3");
        //myObservable = Observable.fromArray(nums);
        myObservable = Observable.range(1, 20);

        compositeDisposable.add(
        myObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver()) // return a observer
        );

        myObserver2 = new DisposableObserver<Integer>() {

            @Override
            public void onNext(Integer string) {
                Log.i("RXdemo", "onNext" + string);
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
                myObservable.subscribeWith(myObserver2) // return a observer
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // clear the disposables held, if use dispose will destroy the composite
    }

    private DisposableObserver getObserver(){
        myObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer string) {
                Log.i("RXdemo", "onNext" + string);
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

        return myObserver;
    }
}