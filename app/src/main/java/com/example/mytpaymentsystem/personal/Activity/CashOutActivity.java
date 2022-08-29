package com.example.mytpaymentsystem.personal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityCashOutBinding;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CashOutActivity extends AppCompatActivity {

    ActivityCashOutBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String uId;
    double currentBalance;
    String number;
    String myNumber;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCashOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uId=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("payment");
         binding.ccp.registerCarrierNumberEditText(binding.numberEt);
         number=binding.ccp.getFullNumberWithPlus().replace("","");

        fetchSingleData();
        clickListener();

    }

    public void clickListener(){


        binding.cashOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=binding.numberEt.getText().toString();
                String amount=binding.amountEt.getText().toString();
                double cashOutAmount=Double.parseDouble(amount);

                if (number.isEmpty()){
                    binding.numberEt.setError("enter agent number");
                    binding.numberEt.requestFocus();
                }/*else if (number.length()10){

                    binding.numberEt.setError("please enter valid number");
                    binding.numberEt.requestFocus();
                }*/else if (amount.isEmpty()){
                    binding.amountEt.setError("Enter cash out amount");
                    binding.amountEt.requestFocus();
                }else if (cashOutAmount>=currentBalance)
                {
                    binding.amountEt.setError("Please check our balance and try");
                    binding.amountEt.requestFocus();
                }else {

                    cashOut();
                }
            }
        });

    }

    public void fetchSingleData(){

        reference.child("Personal")
                .child(uId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){

                            UserModel model=snapshot.getValue(UserModel.class);
                            currentBalance=model.getBalance();
                            myNumber=model.getPhone();
                            name=model.getName();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void cashOut(){


        reference.child("Agent").orderByChild("phone").equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists()){


                    String opsiteId=snapshot.getKey();

                    UserModel model=snapshot.getValue(UserModel.class);

                    double currentBalance =model.getBalance();
                    String amount=binding.amountEt.getText().toString();
                    double amoutEt=Double.parseDouble(amount);
                    double updateBalance=currentBalance+amoutEt;


                    HashMap<String,Object> agentCashInMap=new HashMap<>();
                    agentCashInMap.put("amount",amoutEt);
                    agentCashInMap.put("number",myNumber);
                    agentCashInMap.put("name",name);

                    HashMap<String,Object> agentBalanceMap=new HashMap();
                    agentBalanceMap.put("balance",updateBalance);
                    reference.child(opsiteId).updateChildren(agentBalanceMap);
                    reference.child("cashIn").child(opsiteId).setValue(agentBalanceMap);



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



       /* Query query=FirebaseDatabase
                .getInstance()
                .getReference()
                .child("payment")
                .child("Agent")
                .orderByChild("phone")
                .equalTo(number)
                .addListenerForSingleValueEvent("");*/


        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

    }
}