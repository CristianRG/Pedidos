package com.cristian.controldepedidos.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.model.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private ArrayList<Customer> customers;
    private Context context;

    public CustomerAdapter(Context context, ArrayList<Customer> customers) {
        this.context = context;
        this.customers = customers;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer = this.customers.get(position);
        holder.name.setText(customer.getName());
        Spannable phone = new SpannableString(customer.getPhone().toString());
        phone.setSpan(new UnderlineSpan(), 0, phone.length(), 0);
        holder.phone.setText(phone);

        String status = customer.getStatus() != 0 ? "Activo" : "Inactivo";

        holder.status.setText(status);

        holder.phone.setOnClickListener(view -> {
            try {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(String.format("https://wa.me/%s?text=%s", customer.getPhone(), "¡Hola!")));
                context.startActivity(browserIntent);
            } catch (RuntimeException e){
                Toast.makeText(this.context, "Ha ocurrido un error...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public void updateData(ArrayList<Customer> customers){
        this.customers = customers;
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView phone;
        public TextView status;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.customer_name);
            phone = itemView.findViewById(R.id.customer_phone);
            status = itemView.findViewById(R.id.customer_status);
        }
    }
}
