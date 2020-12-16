package com.lig.intermediate.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
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
    private Observable<Student> myObservable;
    private DisposableObserver<Student> myObserver;

    private TextView textView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGretting);

        //myObservable = Observable.just("Hello 1", "Hello 2", "Hello 3");
        //myObservable = Observable.fromArray(getStudents());
        //myObservable = Observable.range(1, 20);

        myObservable = Observable.create(emitter -> {
            ArrayList<Student> students = getStudents();
            for (Student student : students) {
                emitter.onNext(student);
            }
            emitter.onComplete();
        });

        compositeDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(getObserver()) // return a observer
        );
    }

        private DisposableObserver getObserver() {
            myObserver = new DisposableObserver<Student>() {
                @Override
                public void onNext(Student student) {
                    Log.i("RXdemo", "onNext" + student.getEmail());
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

    private ArrayList<Student> getStudents() {

        ArrayList<Student> students = new ArrayList<>();

        Student student1 = new Student();
        student1.setName(" student 1");
        student1.setEmail(" student1@gmail.com ");
        student1.setAge(27);
        students.add(student1);

        Student student2 = new Student();
        student2.setName(" student 2");
        student2.setEmail(" student2@gmail.com ");
        student2.setAge(20);
        students.add(student2);

        Student student3 = new Student();
        student3.setName(" student 3");
        student3.setEmail(" student3@gmail.com ");
        student3.setAge(20);
        students.add(student3);

        Student student4 = new Student();
        student4.setName(" student 4");
        student4.setEmail(" student4@gmail.com ");
        student4.setAge(20);
        students.add(student4);

        Student student5 = new Student();
        student5.setName(" student 5");
        student5.setEmail(" student5@gmail.com ");
        student5.setAge(20);
        students.add(student5);

        return students;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear(); // clear the disposables held, if use dispose will destroy the composite
    }

}

