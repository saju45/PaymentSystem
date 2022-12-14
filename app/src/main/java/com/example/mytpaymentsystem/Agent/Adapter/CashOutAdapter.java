package com.example.mytpaymentsystem.Agent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytpaymentsystem.Agent.Fragment.CashOutModel;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.SimpleCashoutLayoutBinding;

import java.util.ArrayList;

public class CashOutAdapter extends RecyclerView.Adapter<CashOutAdapter.viewHolder> {

    Context context;
    ArrayList<CashOutModel> list;


    public CashOutAdapter(Context context, ArrayList<CashOutModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_cashout_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        CashOutModel model=list.get(position);

        holder.binding.number.setText(model.getNumber());
        holder.binding.amout.setText(model.getAmount()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleCashoutLayoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding=SimpleCashoutLayoutBinding.bind(itemView);
        }
    }
}
