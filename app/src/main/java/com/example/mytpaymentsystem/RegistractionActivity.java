package com.example.mytpaymentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.mytpaymentsystem.databinding.ActivityRegistractionBinding;

public class RegistractionActivity extends AppCompatActivity {

    ActivityRegistractionBinding binding;

    String[] who;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegistractionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.ccp.registerCarrierNumberEditText(binding.phoneEt);

        clickListener();
        who=getResources().getStringArray(R.array.spinner_item);
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,R.layout.simple_adapter,R.id.simple_textView,who);
        binding.spinner.setAdapter(adapter);

    }

    public void clickListener(){



        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=binding.nameEt.getText().toString();
                String password=binding.passwordEt.getText().toString();
                String type=binding.spinner.getSelectedItem().toString();

                Intent intent=new Intent(RegistractionActivity.this,ManageActivity.class);
                intent.putExtra("mobile",binding.ccp.getFullNumberWithPlus().replace("",""));
                intent.putExtra("name",name);
                intent.putExtra("pass",password);
                intent.putExtra("type",type);
                startActivity(intent);

            }
        });


        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistractionActivity.this,WelcomeActivity.class));
                finish();
            }
        });

    }
}