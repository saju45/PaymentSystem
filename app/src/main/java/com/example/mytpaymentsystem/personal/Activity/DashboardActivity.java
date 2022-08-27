package com.example.mytpaymentsystem.personal.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityDashboardBinding;
import com.example.mytpaymentsystem.personal.Fragment.DashBoardFragment;
import com.example.mytpaymentsystem.personal.Fragment.NotificationFragment;
import com.example.mytpaymentsystem.personal.Fragment.OfferFragment;
import com.example.mytpaymentsystem.personal.Model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new DashBoardFragment()).commit();

       binding.bottomBar.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {

               switch (item.getItemId())
               {
                   case R.id.home:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new DashBoardFragment()).commit();

                       break;

                   case R.id.offer:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new OfferFragment()).commit();

                       break;

                   case R.id.notification:

                       getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new NotificationFragment()).commit();
                       break;


               }

               return false;
           }
       });

    }
}