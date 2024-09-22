package com.cristian.controldepedidos.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.cristian.controldepedidos.R;
import com.cristian.controldepedidos.databinding.DialogPayArticleBinding;
import com.cristian.controldepedidos.model.Article;

import java.util.Objects;

public class PayArticleDialog extends Dialog {
    private DialogPayArticleBinding binding;
    private Article article;
    private Context context;
    private DialogPayInterface payInterface;
    public PayArticleDialog(@NonNull Context context, Article article) {
        super(context);
        this.context = context;
        this.article = article;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_pay_article, null, false);
        setContentView(binding.getRoot());
        Objects.requireNonNull(getWindow()).setLayout(680, 600);

        // set properties
        binding.txtProductName.setText(article.getProduct().getName());
        binding.txtCustomerName.setText(article.getCustomer().getName());
        binding.txtDebtProduct.setText(String.valueOf(article.getDebt()));
        binding.txtPriceProduct.setText(String.valueOf(article.getTotal()));

        // listener from buttons
        binding.btnConfirmPay.setOnClickListener(view -> {

            // get amount
            double amount = Double.parseDouble(binding.editTextAmountToPay.getText().toString());
            // get debt
            double debt = article.getDebt();
            // check if amount is most than debt
            if(amount <= debt){
                article.setPayment(article.getPayment() + amount);
                article.setDebt(article.getTotal() - article.getPayment());

                if(payInterface!=null){
                    payInterface.onConfirmPay(article);
                }
                dismiss();
            }
            else {
                binding.editTextAmountToPay.setFocusable(true);
                binding.editTextAmountToPay.setTextColor(context.getColor(R.color.danger));
            }
        });

        binding.btnCancelPay.setOnClickListener(view -> dismiss());

    }
    // set listener to listen when click button
    public void setListener(DialogPayInterface payInterface){
        this.payInterface = payInterface;
    }

    public interface DialogPayInterface {
        void onConfirmPay(Article article);
    }
}
