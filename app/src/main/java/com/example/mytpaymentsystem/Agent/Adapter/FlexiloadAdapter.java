package com.example.mytpaymentsystem.Agent.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytpaymentsystem.Agent.Model.AgentFlexiLoadModel;
import com.example.mytpaymentsystem.R;
import com.example.mytpaymentsystem.databinding.SimpleFlexiloadRvBinding;

import java.util.ArrayList;

public class FlexiloadAdapter extends RecyclerView.Adapter<FlexiloadAdapter.viewHolder>{


    Context context;
    ArrayList<AgentFlexiLoadModel> list;

    public FlexiloadAdapter(Context context, ArrayList<AgentFlexiLoadModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.simple_flexiload_rv,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {


        AgentFlexiLoadModel model=list.get(position);

        holder.binding.amout.setText(model.getAmount()+"");
        holder.binding.number.setText(model.getNumber());
        holder.binding.time.setText(model.getTime());
        holder.binding.date.setText(model.getDate());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        SimpleFlexiloadRvBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding=SimpleFlexiloadRvBinding.bind(itemView);
        }
    }
}
