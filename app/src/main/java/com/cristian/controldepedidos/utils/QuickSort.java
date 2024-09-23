package com.cristian.controldepedidos.utils;

import com.cristian.controldepedidos.model.Order;
import com.cristian.controldepedidos.model.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuickSort {

    public static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public static void orderQuickSort(List<Order> orders, int left, int right) throws ParseException {
        if(left < right){
            int index = orderPartition(orders, left, right);
            orderQuickSort(orders, left, index - 1);
            orderQuickSort(orders, index + 1, right);
        }
    }

    private static int orderPartition(List<Order> orders, int left, int right) throws ParseException {
        Date pivot = format.parse(orders.get(right).getDate());
        int index = left;

        for (int i = left; i < right; i++){
            Date temp = format.parse(orders.get(i).getDate());

            assert temp != null;
            if(temp.after(pivot)){

                Order swapTemp = orders.get(index);
                // intercambio
                orders.set(index, orders.get(i));
                orders.set(i, swapTemp);
                index++;
            }
        }
        Order swapTemp = orders.get(index);
        // intercambio
        orders.set(index, orders.get(right));
        orders.set(right, swapTemp);

        return index;
    }
    public static void historyQuickSort(List<Transaction> transactions, int left, int right) throws ParseException {
        if(left < right){
            int index = historyPartition(transactions, left, right);
            historyQuickSort(transactions, left, index - 1);
            historyQuickSort(transactions, index + 1, right);
        }
    }
    private static int historyPartition(List<Transaction> transactions, int left, int right) throws ParseException {
        Date pivot = format.parse(transactions.get(right).getDate());
        int index = left;

        for (int i = left; i < right; i++){
            Date temp = format.parse(transactions.get(i).getDate());

            assert temp != null;
            if(temp.after(pivot)){

                Transaction swapTemp = transactions.get(index);
                // intercambio
                transactions.set(index, transactions.get(i));
                transactions.set(i, swapTemp);
                index++;
            }
        }
        Transaction swapTemp = transactions.get(index);
        // intercambio
        transactions.set(index, transactions.get(right));
        transactions.set(right, swapTemp);

        return index;
    }
}
