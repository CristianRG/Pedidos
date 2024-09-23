package com.cristian.controldepedidos.ui.fragments;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.controller.adapters.HistoryAdapter;
import com.cristian.controldepedidos.controller.database.HistoryController;
import com.cristian.controldepedidos.databinding.FragmentHistoryBinding;
import com.cristian.controldepedidos.model.DatabaseHelper;
import com.cristian.controldepedidos.model.Transaction;
import com.cristian.controldepedidos.utils.QuickSort;

import java.text.ParseException;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private DatabaseHelper dbh;
    private SQLiteDatabase db;
    private HistoryAdapter adapter;

    public HistoryFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.dbh = new DatabaseHelper(getContext());
        this.db = dbh.getWritableDatabase();
        ArrayList<Transaction> transactions = HistoryController.getHistory(db);
        // order by date
        try {
            QuickSort.historyQuickSort(transactions, 0, transactions.size() - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // init recyclerView
        binding.historyRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.adapter = new HistoryAdapter(getContext(), transactions);
        binding.historyRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<Transaction> transactions = HistoryController.getHistory(db);
        // order by date
        try {
            QuickSort.historyQuickSort(transactions, 0, transactions.size() - 1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        adapter.updatedData(transactions);
        adapter.notifyDataSetChanged();
    }
}
