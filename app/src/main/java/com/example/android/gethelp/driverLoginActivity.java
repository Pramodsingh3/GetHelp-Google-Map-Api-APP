package com.example.android.gethelp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class driverLoginActivity extends AppCompatActivity {

    private Button driverLoginBtn;
    private Button driverRegistrationButton;
    private TextView driverRegistrationLink;
    private TextView driverStatus;
    private EditText EmailDriver;
    private EditText PAsswordDriver;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    private DatabaseReference driverDatabaseRef;
    private String OnlineDriverID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_login);
        mAuth=FirebaseAuth.getInstance();



        driverLoginBtn = (Button) findViewById(R.id.login_driver_btn);
        driverRegistrationButton = (Button) findViewById(R.id.register_driver_btn);
        driverRegistrationLink = (TextView) findViewById(R.id.create_driver_account);
        driverStatus = (TextView) findViewById(R.id.title_driver);
        EmailDriver = (EditText) findViewById(R.id.driver_email) ;
        PAsswordDriver = (EditText) findViewById(R.id.driver_password) ;
        loadingBar= new ProgressDialog(this);


        driverRegistrationButton.setEnabled(false);
     driverRegistrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverLoginBtn.setVisibility(View.INVISIBLE);
                driverRegistrationLink.setVisibility(View.INVISIBLE);

                driverStatus.setText("Register Driver");

             driverRegistrationButton.setVisibility(View.VISIBLE);
                driverRegistrationButton.setEnabled(true);
            }
        });
        driverRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = EmailDriver.getText().toString();
                String password=PAsswordDriver.getText().toString();

                RegisterDriver(email,password);
            }
        });

        driverLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = EmailDriver.getText().toString();
                String password=PAsswordDriver.getText().toString();
                SignInDriver(email,password);
            }
        });


    }

    private void SignInDriver(String email, String password)
    {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(driverLoginActivity.this,"Please write email!!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(driverLoginActivity.this,"Please write password!!",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Driver login");
            loadingBar.setMessage("Please wait !!");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(driverLoginActivity.this,"Driver Login succussfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent= new Intent(driverLoginActivity.this,DriverMapsActivity.class);
                                startActivity(driverIntent);
                            }
                            else{
                                Toast.makeText(driverLoginActivity.this," Login Failed",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


    private void RegisterDriver(String email, String password)
    {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(driverLoginActivity.this,"Please write email!!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(driverLoginActivity.this,"Please write password!!",Toast.LENGTH_SHORT).show();
        }
         else{
             loadingBar.setTitle("Driver Registration");
             loadingBar.setMessage("Please wait !!");
             loadingBar.show();
              mAuth.createUserWithEmailAndPassword(email,password)
                      .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                          @Override
                          public void onComplete(@NonNull Task<AuthResult> task)
                          {
                              if(task.isSuccessful())
                              {

                                  OnlineDriverID = mAuth.getCurrentUser().getUid();

                            driverDatabaseRef= FirebaseDatabase.getInstance().getReference()
                                          .child("Users").child("Drivers").child(OnlineDriverID);
                                  driverDatabaseRef.setValue(true);

                                  Intent  DriverIntent = new Intent(driverLoginActivity.this,DriverMapsActivity.class);
                                  startActivity(DriverIntent);
                                  Toast.makeText(driverLoginActivity.this,"Driver Register succussfully",Toast.LENGTH_SHORT).show();
                                  loadingBar.dismiss();
                              }
                              else{
                                  Toast.makeText(driverLoginActivity.this," Register Failed",Toast.LENGTH_SHORT).show();
                                  loadingBar.dismiss();
                              }
                          }
                      });
        }
    }
}
