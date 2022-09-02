package com.example.mytpaymentsystem.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytpaymentsystem.Admin.Model.Discount;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.LayoutDiscoutBinding;

import java.util.ArrayList;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.viewHolder>{

    Context context;
    ArrayList<Discount> list;

    public DiscountAdapter(Context context, ArrayList<Discount> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_discout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Discount discount=list.get(position);

        holder.binding.personal.setText(discount.getName());
        holder.binding.discount.setText(discount.getDiscount()+"");
        holder.binding.date.setText(discount.getDate());
        holder.binding.time.setText(discount.getTime());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        LayoutDiscoutBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=LayoutDiscoutBinding.bind(itemView);
        }
    }
}
