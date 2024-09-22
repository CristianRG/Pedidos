package com.cristian.controldepedidos.ui.dialogs;

import com.cristian.controldepedidos.model.Article;

public interface ListenerArticle {
    void onClickConfirm(Article article);
    void onClickCancel();
}
