package com.cristian.controldepedidos.controller.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cristian.controldepedidos.model.ContractDB;
import com.cristian.controldepedidos.model.Transaction;

import java.util.ArrayList;

public class HistoryController {

    public static long addHistory(SQLiteDatabase db, Transaction transaction){
        ContentValues values = new ContentValues();
        values.put(ContractDB.HISTORY_TYPE, transaction.getType());
        values.put(ContractDB.HISTORY_DETAILS, transaction.getDetails());
        values.put(ContractDB.HISTORY_DATE, transaction.getDate());
        return db.insert(ContractDB.HISTORY_TABLE_NAME, null, values);
    }

    public static ArrayList<Transaction> getHistory(SQLiteDatabase db){
        ArrayList<Transaction> transactions = new ArrayList<>();
        Cursor cursor = db.query(ContractDB.HISTORY_TABLE_NAME, null, null, null, null, null, null);

        if(cursor.moveToFirst()){
            do {
                transactions.add(new Transaction(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return transactions;
    }
}
