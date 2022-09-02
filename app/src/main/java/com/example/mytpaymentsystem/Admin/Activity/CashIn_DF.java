package com.example.mytpaymentsystem.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytpaymentsystem.Admin.Adapter.DiscountAdapter;
import com.example.mytpaymentsystem.Admin.Model.Discount;
import com.example.mytpaymentsystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Locale;

public class CashIn_DF extends AppCompatActivity {

    Toolbar toolbar;
    EditText personalEt,agentEt;
    Button personalSend,agentSent;
    RecyclerView agentRv,personalRv;
    ImageView back,image;
    TextView name;

    String currentDate;
    String currentTime;

    String agent,personal;
    double value,perValue;
    DatabaseReference reference;
    ArrayList<Discount> list;
    ArrayList<Discount> list2;
    DiscountAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_in_df);

        init();
        clickListener();
        fetchData();

        adapter=new DiscountAdapter(this,list);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        agentRv.setLayoutManager(layoutManager);
        agentRv.setHasFixedSize(true);
        agentRv.setAdapter(adapter);


        DiscountAdapter adapter1=new DiscountAdapter(this,list2);
        LinearLayoutManager layoutManager1=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        personalRv.setLayoutManager(layoutManager1);
        personalRv.setHasFixedSize(true);
        personalRv.setAdapter(adapter1);


    }

    public void init(){

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        back=findViewById(R.id.toolBackBtn);
        image=findViewById(R.id.toolBarImage);
        name=findViewById(R.id.tollName);

        image.setImageResource(R.drawable.send_money);
        name.setText("Cash In percentage");

        personalEt=findViewById(R.id.personalEt);
        agentEt=findViewById(R.id.agentBenefitEt);
        agentSent=findViewById(R.id.agentSend);
        personalSend=findViewById(R.id.personalSend);
        agentRv=findViewById(R.id.agentRv);
        personalRv=findViewById(R.id.personalRv);

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());
        list=new ArrayList<>();
        list2=new ArrayList<>();
        reference= FirebaseDatabase.getInstance().getReference().child("payment");

    }

    public void clickListener(){

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CashIn_DF.this,AdminMainActivity.class));
                finish();
            }
        });

        agentSent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                agent= agentEt.getText().toString();
                value=Double.parseDouble(agent);
                if (agent.isEmpty()){

                    agentEt.setError("please enter any value");
                    agentEt.requestFocus();
                }else {

                    agentData();
                }
            }
        });

        personalSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personal= personalEt.getText().toString();
                perValue=Double.parseDouble(personal);
                if (personal.isEmpty()){

                    personalEt.setError("please enter any value");
                    personalEt.requestFocus();
                }else {

                    personalData();
                }

            }
        });

    }

    public void agentData(){

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("discount",value);
        hashMap.put("name","agent");
        hashMap.put("date",currentDate);
        hashMap.put("time",currentTime);

        reference.child("Admin").child("agentCashIn").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(CashIn_DF.this, "value id added", Toast.LENGTH_SHORT).show();
                    agentEt.setText("");
                }else {
                    Toast.makeText(CashIn_DF.this, "Something is wrong please check ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void personalData(){

        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("discount",perValue);
        hashMap.put("name","personal");
        hashMap.put("date",currentDate);
        hashMap.put("time",currentTime);

        reference.child("Admin").child("personalCashIn").setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    Toast.makeText(CashIn_DF.this, "value id added", Toast.LENGTH_SHORT).show();
                    personalEt.setText("");
                }else{
                    Toast.makeText(CashIn_DF.this, "Something is wrong please check ", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void  fetchData(){

        reference.child("Admin")
                .child("agentCashIn")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){

                    Discount discount=snapshot.getValue(Discount.class);

                    list.add(discount);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.child("Admin")
                .child("personalCashIn")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if (snapshot.exists()){

                            Discount discount=snapshot.getValue(Discount.class);

                            list2.add(discount);

                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}