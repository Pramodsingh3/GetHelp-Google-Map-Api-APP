package com.example.android.gethelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
   private Button welcomeDriverButton;
    private Button welcomePatientButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        welcomeDriverButton =(Button)findViewById(R.id.welcome_driver);
        welcomePatientButton=(Button) findViewById(R.id.welcome_patient);

        welcomePatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent loginRegisterPatientIntent= new Intent(MainActivity.this,PatientLoginActivity.class);
                startActivity(loginRegisterPatientIntent);
            }
        });

       welcomeDriverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent loginRegisterPatientIntent= new Intent(MainActivity.this,driverLoginActivity.class);
                startActivity(loginRegisterPatientIntent);
            }
        });
    }
}
