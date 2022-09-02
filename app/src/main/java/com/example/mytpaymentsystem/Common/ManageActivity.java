package com.example.mytpaymentsystem.Common;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mytpaymentsystem.databinding.ActivityManageBinding;
import com.example.mytpaymentsystem.personal.Activity.DashboardActivity;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class ManageActivity extends AppCompatActivity {

    ActivityManageBinding binding;
    String phoneNumber;
    String name;
    String pass;
    String type;
    FirebaseAuth mAuth;
    DatabaseReference database;
    String otpId;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityManageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        phoneNumber=getIntent().getStringExtra("mobile").toString();
        name=getIntent().getStringExtra("name").toString();
        pass=getIntent().getStringExtra("pass").toString();
        type=getIntent().getStringExtra("type").toString();
        mAuth=FirebaseAuth.getInstance();

      // initialOtp();

        binding.tvMobile.setText(phoneNumber);

         initiateotp();

        database=FirebaseDatabase.getInstance().getReference();

        binding.verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.otpEt.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                else if(binding.otpEt.getText().toString().length()!=6)
                    Toast.makeText(getApplicationContext(),"INvalid OTP",Toast.LENGTH_LONG).show();
                else
                {
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpId,binding.otpEt.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });

    }

    private void initialOtp(){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(120L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                //super.onCodeSent(s, forceResendingToken);
                                otpId=s;
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(ManageActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void initiateotp(){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        otpId = s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                             user = task.getResult().getUser();
                            String userId=user.getUid();
                            // Update UI

                            UserModel model=new UserModel(name,phoneNumber,type,userId,0.0);

                          /* HashMap<String,Object> hashMap=new HashMap<>();

                            hashMap.put("name",name);
                            hashMap.put("number",phoneNumber);
                            hashMap.put("balance",0);
                            hashMap.put("userId",userId);
                            hashMap.put("type",type);*/

                            database.child("payment")
                                    .child(type)
                                    .child(userId)
                                    .setValue(model)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){

                                        Toast.makeText(ManageActivity.this, "user added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ManageActivity.this,DashboardActivity.class));
                                        finish();

                                    }else {

                                        Log.e("TAG", "onComplete: "+task.getException());
                                        Toast.makeText(ManageActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


/*
        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            String userId=user.getUid();
                            // Update UI
                            HashMap<String,Object> hashMap=new HashMap<>();

                            hashMap.put("name",name);
                            hashMap.put("number",phoneNumber);
                            hashMap.put("balance",0);
                            hashMap.put("userId",userId);
                            hashMap.put("type",type);

                            database.child(type).child(userId).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(ManageActivity.this, "user added", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ManageActivity.this, DashboardActivity.class));
                                        finish();

                                    }else {
                                        Toast.makeText(ManageActivity.this, "something is wrong", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                            Toast.makeText(ManageActivity.this, "Code error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
*/


    @Override
    protected void onStart() {
        super.onStart();

    }
}