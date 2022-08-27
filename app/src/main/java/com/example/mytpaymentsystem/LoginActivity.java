package com.example.mytpaymentsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.widget.ArrayAdapter;

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



    }
}