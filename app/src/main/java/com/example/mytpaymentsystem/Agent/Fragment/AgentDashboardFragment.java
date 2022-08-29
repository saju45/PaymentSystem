package com.example.mytpaymentsystem.Agent.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytpaymentsystem.Agent.Activity.AgentFlexiloadActivity;
import com.example.mytpaymentsystem.Agent.Activity.CashInActivity;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.WelcomeActivity;
import com.example.mytpaymentsystem.databinding.FragmentAgentDashboardBinding;
import com.example.mytpaymentsystem.personal.Activity.CashOutActivity;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AgentDashboardFragment extends Fragment {


    FragmentAgentDashboardBinding binding;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uId;
    DatabaseReference reference;
    public AgentDashboardFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentAgentDashboardBinding.inflate(inflater, container, false);


        firebaseAuth=FirebaseAuth.getInstance();
        reference= FirebaseDatabase.getInstance().getReference().child("payment");
        user=firebaseAuth.getCurrentUser();

        clickListener();



        fetchData();



        return binding.getRoot();
    }

    public void clickListener(){


        binding.recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Intent intent=new Intent(getContext(),AgentFlexiloadActivity.class);
             //   startActivity(intent);
                startActivity(new Intent(getContext(), AgentFlexiloadActivity.class));

            }
        });

        binding.cashIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), CashInActivity.class));
            }
        });

        binding.logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                startActivity(new Intent(getContext(), WelcomeActivity.class));
            }
        });

    }

    public void fetchData(){

        if (user!=null){
            uId=user.getUid();
        }
        reference.child("Agent").child(uId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if (snapshot.exists())
                {

                    UserModel model=snapshot.getValue(UserModel.class);

                    binding.name.setText(model.getName());
                    binding.number.setText(model.getPhone());
                    double balance=model.getBalance();
                    binding.balance.setText(balance+"");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

}