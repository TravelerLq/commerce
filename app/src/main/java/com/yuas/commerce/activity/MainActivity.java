package com.yuas.commerce.activity;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yuas.commerce.R;


//商会通主页

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =(TextView) findViewById(R.id.tv_test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyAsyncTask().execute("stingtask");
            }
        });


        //AsyncTask<Params,Progress,Result>  AsyncTask  d o in backgr
    }


    class MyAsyncTask extends AsyncTask<String, Integer, String> {


        //do in background 转子线程  Asytask 是串行执行的 perform backggrounf operation and publish rsult obthe UIthread w
        @Override
        protected String doInBackground(String... strings) {
            for (int i = 0; i < 10; i++) {
                publishProgress(i);//提交之后，会执行onProcessUpdate方法
            }
            Log.i(TAG, "doInBackground out");
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.i(TAG, "onProgressUpdate --" + values);
            super.onProgressUpdate(values);
        }



        @Override
        protected void onPostExecute(String s) {

            Log.i(TAG, "onPostExecute --s=" + s);
            super.onPostExecute(s);
        }

        //
    }


}
