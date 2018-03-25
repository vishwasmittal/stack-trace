package com.sdsmdg.vishwas.elanicassignment.interfaces;

import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;


public interface TaskCompleteListener {
    void startMainActivity();

    void setMenuItemSelected(String sort, String orderby);

    void noResultFound();

    void clearImage();

    void addItems(QuestionClass questions);

    void clearAdapter();

    void httpError();

    void hideProgressBar();

    void connectionError();

    void unknownError();
}
