package com.capulustech.myapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView titleTv=findViewById(R.id.titleTV);
        final EditText nameEt=findViewById(R.id.nameET);
        Button submitBtn=findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                String name =nameEt.getText().toString();
//                titleTv.setText(name);
                Intent intent=new Intent(MainActivity.this, StudentRegistrationActivity.class);
                startActivity(intent);


            }
        });


    }
}
