package com.cristian.controldepedidos.utils;

import com.cristian.controldepedidos.model.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QuickSort {

    public static SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

    public static void quickSort(List<Order> orders, int left, int right) throws ParseException {
        if(left < right){
            int index = partition(orders, left, right);
            quickSort(orders, left, index - 1);
            quickSort(orders, index + 1, right);
        }
    }

    private static int partition(List<Order> orders, int left, int right) throws ParseException {
        Date pivot = format.parse(orders.get(right).getDate());
        int index = left;

        for (int i = left; i < right; i++){
            Date temp = format.parse(orders.get(i).getDate());

            if(temp.before(pivot)){

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
}
