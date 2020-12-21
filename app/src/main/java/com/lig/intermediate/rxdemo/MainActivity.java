package com.lig.intermediate.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity {
    private String[] greetings= {"Hello A", "Hello B", "Hello C"};
    private Integer[] nums={1,2,3,4,5};
    private Observable<Student> myObservable;
    private DisposableObserver<Student> myObserver;

    private TextView viewText;
    private EditText inputText;
    private Button clearButton;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = findViewById(R.id.etInputField);
        viewText = findViewById(R.id.tvInput);
        clearButton = findViewById(R.id.btnClear);

        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewText.setText(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        clearButton.setOnClickListener(v -> {
            inputText.setText("");
            viewText.setText(" ");
        });


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
                        /*.map(new Function<Student, Student>() {
                            @Override
                            public Student apply(Student student) throws Throwable {
                                student.setName(student.getName().toUpperCase());
                                return student;
                            }
                        })*/
                        .concatMap(new Function<Student, ObservableSource<Student>>() {
                            @Override
                            public ObservableSource<Student> apply(Student student) throws Throwable {
                                Student student1 = new Student();
                                student1.setName("new Member1"+ student.getName());

                                Student student2 = new Student();
                                student2.setName("new Member2"+ student.getName());

                                student.setName(student.getName().toUpperCase());
                                return Observable.just(student, student1, student2); //merges items emitted by multiple Observables and returns a single Observable
                            }
                        }) // flatMap to return a new observable, cancatMap do the same and maintient order
                        .subscribeWith(getObserver()) // return a observer
        );


        //demonstration of bundle operator
        //Observable<Integer> myObservable2 = Observable.range(1,20);
        Observable<Integer> myObservable2 = Observable.just(1,2,3,7,5,3,5,5,4,4);
        myObservable2.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //.buffer(4)
                /*.filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Throwable {
                        return integer%3==0;
                    }
                })*/
                //.distinct() // use to return only distinct value
                //.skip(3) // to skip nb items
                .skipLast(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.i("RXdemo", "onNext" + integer);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

        private DisposableObserver getObserver() {
            myObserver = new DisposableObserver<Student>() {
                @Override
                public void onNext(Student student) {
                    Log.i("RXdemo", "onNext" + student.getName());
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

