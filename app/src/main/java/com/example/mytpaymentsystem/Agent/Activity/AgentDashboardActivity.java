package com.example.mytpaymentsystem.Agent.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.mytpaymentsystem.Agent.Fragment.AgentDashboardFragment;
import com.example.mytpaymentsystem.Agent.Fragment.AgentNoticationFragment;
import com.example.mytpaymentsystem.Agent.Fragment.AgentOfferFragment;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.ActivityAgentDashboardBinding;
import com.example.mytpaymentsystem.personal.Fragment.DashBoardFragment;
import com.example.mytpaymentsystem.personal.Fragment.NotificationFragment;
import com.example.mytpaymentsystem.personal.Fragment.OfferFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AgentDashboardActivity extends AppCompatActivity {

    ActivityAgentDashboardBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAgentDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AgentDashboardFragment()).commit();

        binding.bottomBar.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.home:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AgentDashboardFragment()).commit();

                        break;

                    case R.id.offer:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new AgentOfferFragment()).commit();

                        break;

                    case R.id.notification:

                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new  AgentNoticationFragment()).commit();
                        break;


                }

                return false;
            }
        });



    }
}