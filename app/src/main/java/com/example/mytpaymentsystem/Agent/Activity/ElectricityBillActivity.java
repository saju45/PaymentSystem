package com.example.mytpaymentsystem.Agent.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mytpaymentsystem.Agent.Adapter.PayBillAdapter;
import com.example.mytpaymentsystem.Agent.Model.PayBillModel;
import com.example.mytpaymentsystem.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ElectricityBillActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ImageView backImg,image;
    TextView textView;

    ArrayList<PayBillModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill);

        toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.electricityRv);
        backImg=findViewById(R.id.toolBackBtn);
        image=findViewById(R.id.toolBarImage);
        textView=findViewById(R.id.tollName);

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ElectricityBillActivity.this,AgentDashboardActivity.class));
                finish();
            }
        });

        textView.setText("Electricity Bill");
        image.setImageResource(R.drawable.palli_bidgut);

        list=new ArrayList<>();
        addData();
        PayBillAdapter adapter=new PayBillAdapter(this,list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);



    }

    public void addData(){

        list.add(new PayBillModel(R.drawable.palli_bidgut,"Palli Bidyut(prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.palli_bidgut,"Palli Bidgut(Postpaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.desco,"DESCO(Prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.desco,"DESCO(PostPaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.nesco,"NESCO(Prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.nesco,"NESCO(Postpaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.dpdc,"DPDC(Prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.dpdc,"DPDC(Postpaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.dpdc,"DPDB(Prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.dpdc,"DPDB(Postpaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.westzone,"Westzone(Prepaid)","Electricity "));
        list.add(new PayBillModel(R.drawable.westzone,"Westzone(Postpaid)","Electricity "));

    }

}