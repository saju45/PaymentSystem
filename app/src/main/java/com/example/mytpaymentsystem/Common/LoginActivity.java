package com.example.mytpaymentsystem.Common;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.mytpaymentsystem.Admin.Activity.AdminMainActivity;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityLoginBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthCredential;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    String[] who;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        who=getResources().getStringArray(R.array.spinner_item);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.simple_adapter,R.id.simple_textView,who);
        binding.spinner.setAdapter(adapter);

        auth=FirebaseAuth.getInstance();

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number="01773734512";
                String password="saju0011";

                if (binding.phoneEt.getText().toString().equals(number) && binding.passwordEt.getText().toString().equals(password))
                {

                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                    finish();
                }
            }
        });



    }
}