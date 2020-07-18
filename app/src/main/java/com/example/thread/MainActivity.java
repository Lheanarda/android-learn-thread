package com.example.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button btnStartThread;
    private Handler mainHandler = new Handler();
    private int j = 0;

    private volatile boolean stopThread = false; //1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStartThread = findViewById(R.id.button_start_thread);
    }

    public void startThread(View view){
        stopThread = false; //2
        //method 1
//        ExampleThread thread = new ExampleThread(10);
//        thread.start();

        //method 2
        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();

        //method 3
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
            }
        }).start();
         */

    }

    public void stopThread (View view){
        stopThread = true; //3
    }

    class ExampleThread extends Thread{
        int seconds;
        ExampleThread(int seconds){
            this.seconds = seconds;
        }
        @Override
        public void run() {
            for(int i = 0; i<seconds ;i++){

                if(i==5)
                    Log.d(TAG, "startThread: "+i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } //method1

    class ExampleRunnable implements Runnable{
        int seconds;
        ExampleRunnable(int seconds){
            this.seconds = seconds;
        }

        @Override
        public void run() {
            Log.i("see_i",j+" before loop");
            for(int i = j; i<seconds ;i++){
                j=i;
                Log.i("see_i",i+"");
                if(stopThread) //4
                    return;
                Log.d(TAG, "startThread: "+i);
                if(i==5){
                    //method 1. edit ui while thread running
                    /*
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            btnStartThread.setText("50%");
                        }
                    });*/
                    //method 2
                    /*
                    btnStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            btnStartThread.setText("50%");
                        }
                    });*/
                    //method e3
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnStartThread.setText("50%");
                        }
                    });
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    } //method 2
}
