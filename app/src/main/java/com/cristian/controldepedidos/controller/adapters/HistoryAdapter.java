package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.BR;
import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.databinding.HistoryItemBinding;
import com.cristian.controldepedidos.model.Transaction;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{
    private HistoryItemBinding itemBinding;
    private Context context;
    private ArrayList<Transaction> transactions;

    public HistoryAdapter(Context context, ArrayList<Transaction> transactions) {
        this.context = context;
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.history_item, parent, false);
        return new HistoryViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);

        // set properties
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void updatedData(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private HistoryItemBinding itemBinding;
        public HistoryViewHolder(@NonNull HistoryItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        public void bind(Object object){
            itemBinding.setVariable(BR.transaction, object);
            itemBinding.executePendingBindings();
        }
    }
}
