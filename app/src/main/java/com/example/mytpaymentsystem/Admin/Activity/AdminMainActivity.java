package com.example.mytpaymentsystem.Admin.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mytpaymentsystem.Admin.Fragment.AdminDashBoardFragment;
import com.example.mytpaymentsystem.Admin.Fragment.AdminNotification;
import com.example.mytpaymentsystem.Admin.Fragment.AdminOffer;
import com.example.mytpaymentsystem.Agent.Fragment.AgentDashboardFragment;
import com.example.mytpaymentsystem.Agent.Fragment.AgentNoticationFragment;
import com.example.mytpaymentsystem.Agent.Fragment.AgentOfferFragment;
import com.example.mytpaymentsystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminMainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        bottomNavigationView=findViewById(R.id.bottomBar);

        getSupportFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminDashBoardFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {

              switch (item.getItemId())
              {
                  case R.id.home:

                      getSupportFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminDashBoardFragment()).commit();

                      break;

                  case R.id.offer:

                      getSupportFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminOffer()).commit();

                      break;

                  case R.id.notification:

                      getSupportFragmentManager().beginTransaction().replace(R.id.adminFrame,new AdminNotification()).commit();
                      break;

              }

              return false;
          }
      });
    }
}