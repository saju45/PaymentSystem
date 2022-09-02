package com.example.mytpaymentsystem.Agent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytpaymentsystem.Agent.Model.PayBillModel;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.SimpleElectricityRvBinding;

import java.util.ArrayList;

public class PayBillAdapter extends RecyclerView.Adapter<PayBillAdapter.viewHolder> {

    Context context;
    ArrayList<PayBillModel> list;

    public PayBillAdapter(Context context, ArrayList<PayBillModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_electricity_rv,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        PayBillModel model=list.get(position);

        holder.binding.image.setImageResource(model.getImageView());
        holder.binding.text1.setText(model.getText1());
        holder.binding.text2.setText(model.getText2());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you click item 1", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleElectricityRvBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleElectricityRvBinding.bind(itemView);
        }
    }
}
