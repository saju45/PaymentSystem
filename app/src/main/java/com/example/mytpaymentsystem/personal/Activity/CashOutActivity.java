package com.example.mytpaymentsystem.personal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mytpaymentsystem.Admin.Model.AdminBalance;
import com.example.mytpaymentsystem.Admin.Model.Discount;
import com.example.mytpaymentsystem.databinding.ActivityCashOutBinding;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CashOutActivity extends AppCompatActivity {

    ActivityCashOutBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String uId;
    double currentBalance;
    String  AgentNumber;
    String myNumber;
    String name;
    double agentPercent;
    double perPercent;
    double adminCurBln;
    String currentDate,currentTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCashOutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        uId=firebaseUser.getUid();

        reference= FirebaseDatabase.getInstance().getReference().child("payment");
        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        currentTime = new SimpleDateFormat("HH:mm a", Locale.getDefault()).format(new Date());


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
                }else if (amount.isEmpty()){
                    binding.amountEt.setError("Enter cash out amount");
                    binding.amountEt.requestFocus();
                }else if (cashOutAmount>=currentBalance)
                {
                    binding.amountEt.setError("Please check our balance and try");
                    binding.amountEt.requestFocus();
                }else {
                    binding.ccp.registerCarrierNumberEditText(binding.numberEt);
                    AgentNumber=binding.ccp.getFullNumberWithPlus().replace("","");
                    cashOut();
                    Toast.makeText(CashOutActivity.this, "click", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void fetchSingleData(){

        reference.child("Admin")
                .child("agentCashIn")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists())
                        {
                            Discount discount=snapshot.getValue(Discount.class);

                            agentPercent =discount.getDiscount();
                        }

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

                        if (snapshot.exists())
                        {
                            Discount discount=snapshot.getValue(Discount.class);

                            perPercent =discount.getDiscount();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        reference.child("Admin")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {


                                if (snapshot.exists())
                                {

                                    AdminBalance balance=snapshot.getValue(AdminBalance.class);
                                    adminCurBln=balance.getBalance();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });



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
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    public void cashOut(){


        reference.child("Agent").orderByChild("phone").equalTo(AgentNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot: snapshot.getChildren())
                    {


                        String opsiteId=dataSnapshot.getKey();

                        UserModel model=dataSnapshot.getValue(UserModel.class);
                        String amount=binding.amountEt.getText().toString();
                        double amoutEt=Double.parseDouble(amount);

                        double parsent=amoutEt/100* perPercent;
                        double agentPercentage=amoutEt/100* agentPercent;
                        double adminPercentage=amoutEt/100* perPercent;
                        double perBln=currentBalance-amoutEt-parsent;
                        double currentBalance =model.getBalance();

                        double updateBalance=currentBalance+amoutEt+agentPercentage;


                        double adminBln=adminCurBln+adminPercentage;
                        HashMap<String,Object> adminMap=new HashMap<>();
                        adminMap.put("balance",adminBln);
                        reference.child("Admin").updateChildren(adminMap);

                        HashMap<String,Object> perBlnMap=new HashMap<>();
                        perBlnMap.put("balance",perBln);

                        HashMap<String,Object> agentCashInMap=new HashMap<>();
                        agentCashInMap.put("amount",amoutEt);
                        agentCashInMap.put("number",AgentNumber);
                        agentCashInMap.put("name",name);
                        agentCashInMap.put("date",currentDate);
                        agentCashInMap.put("time",currentTime);

                        HashMap<String,Object> agentBalanceMap=new HashMap();
                        agentBalanceMap.put("balance",updateBalance);


                        reference.child("Personal").child("2ypO4J4PfWf6GvePM3Yfcjk31qC2").updateChildren(perBlnMap);
                        reference.child("Agent").child(opsiteId).updateChildren(agentBalanceMap);
                        reference.child("Agent").child(uId).child("cashIn").push().setValue(agentCashInMap);




                        reference.child("Admin")
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        if (snapshot.exists())
                                        {

                                            AdminBalance balance=snapshot.getValue(AdminBalance.class);
                                            double newbln=balance.getBalance();

                                            double adminNew= newbln-agentPercentage;
                                            HashMap<String,Object> newMap=new HashMap<>();
                                            newMap.put("balance",adminNew);
                                            reference.child("Admin").updateChildren(newMap);
                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                        binding.amountEt.setText("");
                        binding.numberEt.setText("");

                    }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




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