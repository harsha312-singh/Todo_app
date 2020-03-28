package com.example.todo_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class otp_page extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private PinView pinView;
    String phoneNo;
    Details phone = new Details();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mRootReference = firebaseDatabase.getReference();
    private DatabaseReference phoneNoReference = mRootReference.child("Details");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_page);
        pinView = findViewById(R.id.pinid);
        mAuth = FirebaseAuth.getInstance();
        phoneNo = getIntent().getStringExtra("ph");
        sendVerificationCode(phoneNo);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = pinView.getText().toString();
                //String code="123456";
                if (code.isEmpty() || code.length() < 6) {
                    Toast.makeText(otp_page.this, "Enter OTP...", Toast.LENGTH_LONG).show();
                    pinView.requestFocus();
                    return;
                }
                Toast.makeText(otp_page.this,code,Toast.LENGTH_SHORT).show();
                verifyCode(code);
                store_in_db();
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        Toast.makeText(this,code,Toast.LENGTH_LONG).show();
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    store_in_db();
                } else {
                    Toast.makeText(otp_page.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendVerificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
        mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken){
            super.onCodeSent(s,forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(otp_page.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    public void store_in_db(){
        phoneNoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int check =1;
                //for(DataSnapshot phSnapshot: dataSnapshot.getChildren()){
                    //Toast.makeText(otp_page.this,phSnapshot.child("number").getValue().toString(),Toast.LENGTH_SHORT).show();
                    //if(phoneNo.equals(phSnapshot.child("number").getValue().toString())){
                     //   check=0;
                     //   break;
                    //}
                //}
                if(check == 1) {
                    phone.setName(phoneNo);
                    phoneNoReference.push().setValue(phone);
                }
                Intent i= new Intent(otp_page.this, homepage.class);
                Toast.makeText(otp_page.this,phoneNo,Toast.LENGTH_SHORT).show();
                i.putExtra("ph", phoneNo);
                startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(otp_page.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

