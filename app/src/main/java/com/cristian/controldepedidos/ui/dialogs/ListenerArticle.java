package com.cristian.controldepedidos.ui.dialogs;

import android.widget.Button;

import com.cristian.controldepedidos.model.Article;

public interface ListenerArticle {
    void onClickConfirm(Article article);
    void onClickCancel(Button button);
}
