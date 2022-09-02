package com.example.mytpaymentsystem.Admin.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytpaymentsystem.Admin.Activity.CashIn_DF;
import com.example.mytpaymentsystem.Admin.Activity.SendMoneyDiscount;
import com.example.mytpaymentsystem.Admin.Model.AdminBalance;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.FragmentAdminDashBoardBinding;
import com.example.mytpaymentsystem.databinding.FragmentAgentDashboardBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AdminDashBoardFragment extends Fragment {


    // TODO: Rename and change types of parameters

    FragmentAdminDashBoardBinding binding;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    public AdminDashBoardFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentAdminDashBoardBinding.inflate(inflater,container, false);
        clickListener();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference();
        return  binding.getRoot();
    }
    public void clickListener(){


        binding.sendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), SendMoneyDiscount.class));
            }
        });

        binding.cashOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), CashIn_DF.class));
            }
        });

    }

    public void fetchData()
    {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    AdminBalance balance=dataSnapshot.getValue(AdminBalance.class);

                    binding.balance.setText(balance.getBalance()+"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}