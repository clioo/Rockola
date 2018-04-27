package com.example.jcarlos.hilos;

import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Hilos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilos);

        Button cmd = (Button) findViewById(R.id.button);
    }
    public void cmdOnClick (View v)
    {
        Time time = new Time();
        time.execute();
    }
    public void ejecutar()
    {
        Time time = new Time();
        time.execute();
    }
    private void hilo() {
        try{
                Thread.sleep(1000);

        }catch (Exception e)
        {
        e.printStackTrace();
        }
    }
    public class Time extends AsyncTask<Void, Integer, Boolean>
    {


        @Override
        protected Boolean doInBackground(Void... params) {
            for ( int i = 1; i <= 3; i ++ ) {
            hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            Toast.makeText(Hilos.this," cada dos segundos", Toast.LENGTH_SHORT).show();
        }
    }
}
