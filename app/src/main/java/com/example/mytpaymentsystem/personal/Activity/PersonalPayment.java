package com.example.mytpaymentsystem.personal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.mytpaymentsystem.Agent.Adapter.FlexiloadAdapter;
import com.example.mytpaymentsystem.Agent.Model.AgentFlexiLoadModel;
import com.example.mytpaymentsystem.databinding.ActivityPersonalPaymentBinding;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PersonalPayment extends AppCompatActivity {

    ActivityPersonalPaymentBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String uId;
    double currentBalance;
    String number;
    String myNumber;
    String name;
    double agentBln;
    String currentDate;
    String currentTime;
    FlexiloadAdapter adapter;
    ArrayList<AgentFlexiLoadModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityPersonalPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uId=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("payment");
        fetchData();
        clickListener();

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());

        list=new ArrayList<>();

        adapter=new FlexiloadAdapter(this,list);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(true);
        binding.paymentRv.setLayoutManager(layoutManager);
        binding.paymentRv.setHasFixedSize(true);
        binding.paymentRv.setAdapter(adapter);


    }

    public void clickListener(){

        binding.payBtn.setOnClickListener(new View.OnClickListener() {
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
                    Payment();
                }
            }
        });

    }


    public void fetchData(){

        reference.child("Personal")
                .child("2ypO4J4PfWf6GvePM3Yfcjk31qC2")
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

        reference.child("Personal")
                .child("2ypO4J4PfWf6GvePM3Yfcjk31qC2")
                .child("payment")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {

                            AgentFlexiLoadModel model=dataSnapshot.getValue(AgentFlexiLoadModel.class);
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });


    }


    public void Payment(){

        reference.child("Agent").orderByChild("phone").equalTo(number)

                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot dataSnapshot:snapshot.getChildren())
                        {
                            String key=dataSnapshot.getKey();
                            UserModel model=dataSnapshot.getValue(UserModel.class);

                            DecimalFormat formatter = new DecimalFormat("#0.00");

                            String amount=binding.amountEt.getText().toString();
                            double paymentAmount=Double.parseDouble(amount);
                            String userId=model.getUid();

                            double currentBalance=model.getBalance();
                            double updatebalance=currentBalance+paymentAmount;

                            HashMap<String,Object> balanceMap=new HashMap<>();
                            balanceMap.put("balance",updatebalance);

                            reference.child("Agent").child(userId).updateChildren(balanceMap);
                            double persoanlUpBln=agentBln-paymentAmount;
                            formatter.format(persoanlUpBln);


                            HashMap<String,Object> agentBln=new HashMap<>();
                            agentBln.put("balance",persoanlUpBln);
                            reference.child("Personal").child("2ypO4J4PfWf6GvePM3Yfcjk31qC2").updateChildren(agentBln);

                            HashMap<String,Object> personalCashMap=new HashMap<>();
                            personalCashMap.put("uId",userId);
                            personalCashMap.put("number",number);
                            personalCashMap.put("date",currentDate);
                            personalCashMap.put("time",currentTime);
                            personalCashMap.put("amount",paymentAmount);

                            reference.child("Personal").child("2ypO4J4PfWf6GvePM3Yfcjk31qC2").child("payment").push().setValue(personalCashMap);
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