package com.example.mytpaymentsystem.Agent.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mytpaymentsystem.Agent.Adapter.CashOutAdapter;
import com.example.mytpaymentsystem.Agent.Fragment.CashOutModel;
import com.example.mytpaymentsystem.MainActivity;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityCashInBinding;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class CashInActivity extends AppCompatActivity {

    ActivityCashInBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String uId;
    double currentBalance;
    String number;
    String myNumber;
    String name;
    double agentBln;
    ArrayList<CashOutModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCashInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uId=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("payment");
        fetchSingleData();
        clickListener();


        list=new ArrayList<>();

        CashOutAdapter adapter=new CashOutAdapter(CashInActivity.this,list);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        binding.cashInRv.setLayoutManager(layoutManager);
        binding.cashInRv.setHasFixedSize(true);
        binding.cashInRv.setAdapter(adapter);


        reference.child("Agent").child(uId).child("cashOut").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren()){

                    CashOutModel model=snapshot.getValue(CashOutModel.class);

                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void clickListener(){


        binding.cashInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.numberEt.getText().toString().isEmpty()){
                    binding.numberEt.setError("enter agent number");
                    binding.numberEt.requestFocus();
                }else if (binding.amountEt.getText().toString().isEmpty()){
                    binding.amountEt.setError("Enter cash out amount");
                    binding.amountEt.requestFocus();
                }else {

                    binding.ccp.registerCarrierNumberEditText(binding.numberEt);
                    number=binding.ccp.getFullNumberWithPlus().replace("","");
                    cashIn();
                }
            }
        });

    }

    public void fetchSingleData(){

        reference.child("Agent")
                .child(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){


                            UserModel model=snapshot.getValue(UserModel.class);
                            currentBalance=model.getBalance();
                            myNumber=model.getPhone();
                            name=model.getName();
                            agentBln=model.getBalance();

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    public void cashIn(){

        reference.child("Personal").orderByChild("phone").equalTo(number)

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            String key=dataSnapshot.getKey();
                            UserModel model=dataSnapshot.getValue(UserModel.class);

                            DecimalFormat formatter = new DecimalFormat("#0.00");

                            String amount=binding.amountEt.getText().toString();
                           double cashInAmount=Double.parseDouble(amount);
                            String userId=model.getUid();

                            double parcentage=cashInAmount/100*5;
                            double currentBalance=model.getBalance();
                            double updatebalance=currentBalance+cashInAmount;

                            HashMap<String,Object> balanceMap=new HashMap<>();
                            balanceMap.put("balance",updatebalance);

                            HashMap<String,Object> cashInHap=new HashMap<>();
                            cashInHap.put("key",uId);
                            cashInHap.put("number",myNumber);
                            cashInHap.put("balance",cashInAmount);

                            reference.child("Personal").child(userId).updateChildren(balanceMap);
                            reference.child("Personal").child(userId).child("cashIn").push().updateChildren(cashInHap);

                            double agentUpBln=agentBln-cashInAmount+parcentage;
                            formatter.format(agentUpBln);


                            HashMap<String,Object> agentBln=new HashMap<>();
                            agentBln.put("balance",agentUpBln);
                            reference.child("Agent").child(uId).updateChildren(agentBln);

                            HashMap<String,Object> agentCashMap=new HashMap<>();
                            agentCashMap.put("uId",userId);
                            agentCashMap.put("amount",cashInAmount);

                            reference.child("Agent").child(uId).child("cashOut").push().setValue(agentCashMap);

                            binding.numberEt.setText("");
                            binding.amountEt.setText("");
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }



}