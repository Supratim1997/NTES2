package com.example.supratim.ntes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class training_second extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_second);
        Button button=(Button)findViewById(R.id.but1tr);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                next_page(v);
            }
        } );
        Button button2=(Button)findViewById(R.id.trbackmain);
        button2.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                prev_page(v);
            }
        } );
    }
    public void next_page(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
    public void prev_page(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}

