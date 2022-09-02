package com.example.mytpaymentsystem.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation move= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        binding.image.startAnimation(move);


        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,RegistractionActivity.class));
            }
        });


        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
/*
        if (FirebaseAuth.getInstance().getCurrentUser().getUid()!=null)
        {
            startActivity(new Intent(WelcomeActivity.this, DashboardActivity.class));
        }*/
    }
}