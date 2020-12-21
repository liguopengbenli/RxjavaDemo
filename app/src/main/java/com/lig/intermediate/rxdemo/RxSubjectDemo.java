package com.lig.intermediate.rxdemo;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.AsyncSubject;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.ReplaySubject;


public class RxSubjectDemo extends AppCompatActivity {

    private final static String TAG = "myApp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //asyncSubjectDemo2();
        //behaviourSubjectDemo2();
        //publishSubjectDemo2();
        replaySubjectDemo2();
    }

    void asyncSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        observable.subscribe(asyncSubject); //asyncSubject only emit the last value of the observable
        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.subscribe(getThirdObserver());
    }

    void asyncSubjectDemo2(){
        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        asyncSubject.subscribe(getFirstObserver());
        asyncSubject.onNext("JAVA"); // because can act like observable
        asyncSubject.onNext("KOTLIN");
        asyncSubject.onNext("XML");

        asyncSubject.subscribe(getSecondObserver());
        asyncSubject.onNext("KOTLIN");
        asyncSubject.onComplete();

        asyncSubject.subscribe(getThirdObserver());
    }

    void behaviourSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        observable.subscribe(behaviorSubject); //asyncSubject only emit the last value of the observable
        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.subscribe(getSecondObserver());
        behaviorSubject.subscribe(getThirdObserver());
    }

    void behaviourSubjectDemo2(){
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();

        behaviorSubject.subscribe(getFirstObserver());
        behaviorSubject.onNext("JAVA"); // because can act like observable
        behaviorSubject.onNext("KOTLIN");
        behaviorSubject.onNext("XML");

        behaviorSubject.subscribe(getSecondObserver()); //behaviourSubject emits the most recent emit item
        behaviorSubject.onNext("KOTLIN");
        behaviorSubject.onComplete();

        behaviorSubject.subscribe(getThirdObserver());
    }

    void publishSubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        PublishSubject<String> publishSubject = PublishSubject.create();

        //PublishSubject emits all the subsequent items at the time of subscription
        observable.subscribe(publishSubject);
        publishSubject.subscribe(getFirstObserver());
        publishSubject.subscribe(getSecondObserver());
        publishSubject.subscribe(getThirdObserver());
    }

    void publishSubjectDemo2(){
        PublishSubject<String> publishSubject = PublishSubject.create();

        publishSubject.subscribe(getFirstObserver());
        publishSubject.onNext("JAVA"); // because can act like observable
        publishSubject.onNext("KOTLIN");
        publishSubject.onNext("XML");

        publishSubject.subscribe(getSecondObserver());
        publishSubject.onNext("KOTLIN");
        publishSubject.onComplete();

        publishSubject.subscribe(getThirdObserver());
    }
    void replaySubjectDemo1(){
        Observable<String> observable = Observable.just("JAVA", "KOTLIN", "XML", "JSON");
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        ReplaySubject<String> replaySubject = ReplaySubject.create();

        observable.subscribe(replaySubject); //ReplaySubject emit all the items, all of them
        replaySubject.subscribe(getFirstObserver());
        replaySubject.subscribe(getSecondObserver());
        replaySubject.subscribe(getThirdObserver());
    }

    void replaySubjectDemo2(){
        ReplaySubject<String> replaySubject = ReplaySubject.create();

        replaySubject.subscribe(getFirstObserver());
        replaySubject.onNext("JAVA"); // because can act like observable
        replaySubject.onNext("KOTLIN");
        replaySubject.onNext("XML");

        replaySubject.subscribe(getSecondObserver());
        replaySubject.onNext("KOTLIN");
        replaySubject.onComplete();

        replaySubject.subscribe(getThirdObserver());
    }




    private Observer<String> getFirstObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " First Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " First Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " First Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " First Observer onComplete ");

            }
        };

        return observer;
    }

    private Observer<String> getSecondObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " Second Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " Second Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " Second Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " Second Observer onComplete ");

            }
        };

        return observer;
    }

    private Observer<String> getThirdObserver() {

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {


                Log.i(TAG, " Third Observer onSubscribe ");
            }

            @Override
            public void onNext(String s) {

                Log.i(TAG, " Third Observer Received " + s);

            }

            @Override
            public void onError(Throwable e) {

                Log.i(TAG, " Third Observer onError ");
            }

            @Override
            public void onComplete() {

                Log.i(TAG, " Third Observer onComplete ");

            }
        };

        return observer;
    }

}