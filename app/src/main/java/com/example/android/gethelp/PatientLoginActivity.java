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

public class PatientLoginActivity extends AppCompatActivity {
        private Button costumerLoginBtn;
        private Button costumerRegistrationButton;
        private TextView costumerRegistrationLink;
        private TextView costumerStatus;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private DatabaseReference CustomerDatabaseRef;
    private String OnlineCustID;

    private EditText EmailCostumer;
    private EditText PAsswordcostumer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);
        mAuth =FirebaseAuth.getInstance();



        costumerLoginBtn = (Button) findViewById(R.id.customer_login_btn);
        costumerRegistrationButton = (Button) findViewById(R.id.customer_register_btn);
        costumerRegistrationLink = (TextView) findViewById(R.id.customer_register_link);
        costumerStatus = (TextView) findViewById(R.id.customer_status);

        EmailCostumer = (EditText) findViewById(R.id.customer_email) ;
        PAsswordcostumer = (EditText) findViewById(R.id.customer_password) ;
        loadingBar= new ProgressDialog(this);


        costumerRegistrationButton.setEnabled(false);
        costumerRegistrationLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                costumerLoginBtn.setVisibility(View.INVISIBLE);
                costumerRegistrationLink.setVisibility(View.INVISIBLE);

                costumerStatus.setText("Register Customer");

                costumerRegistrationButton.setVisibility(View.VISIBLE);
                costumerRegistrationButton.setEnabled(true);
            }
        });

       costumerRegistrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email = EmailCostumer.getText().toString();
                String password=PAsswordcostumer.getText().toString();

                RegisterPatient(email,password);
            }
        });

       costumerLoginBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {
               String email = EmailCostumer.getText().toString();
               String password=PAsswordcostumer.getText().toString();

               SignInUSer(email,password);
           }
       });

    }

    private void SignInUSer(String email, String password)
    {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(PatientLoginActivity.this,"Please write email!!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(PatientLoginActivity.this,"Please write password!!",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Patient Login");
            loadingBar.setMessage("Please wait !!");
            loadingBar.show();
            mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Intent  customerIntent = new Intent(PatientLoginActivity.this,CustomerMapActivity.class);
                                startActivity(customerIntent);
                                Toast.makeText(PatientLoginActivity.this,"Patient Login succussfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                            else{
                                Toast.makeText(PatientLoginActivity.this," Login Failed",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }

    private void RegisterPatient(String email, String password)
    {
        if(TextUtils.isEmpty(email)){
            Toast.makeText(PatientLoginActivity.this,"Please write email!!",Toast.LENGTH_SHORT).show();
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(PatientLoginActivity.this,"Please write password!!",Toast.LENGTH_SHORT).show();
        }
        else{
            loadingBar.setTitle("Patient Registration");
            loadingBar.setMessage("Please wait !!");
            loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                OnlineCustID = mAuth.getCurrentUser().getUid();

                                CustomerDatabaseRef= FirebaseDatabase.getInstance().getReference()
                                        .child("Users").child("Patients").child(OnlineCustID);
                                CustomerDatabaseRef.setValue(true);
                                Intent driverIntent = new Intent(PatientLoginActivity.this,CustomerMapActivity.class);
                                startActivity(driverIntent);
                                Toast.makeText(PatientLoginActivity.this,"Patient Register succussfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                            else{
                                Toast.makeText(PatientLoginActivity.this," Register Failed",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }
}
