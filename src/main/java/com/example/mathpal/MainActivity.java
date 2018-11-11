package com.example.mathpal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toSeq(View view){
        startActivity(new Intent(MainActivity.this, SeqIdent.class));
    }

    public void toTrig(View view){
        startActivity(new Intent(MainActivity.this, TrigoCheck.class));
    }
}
