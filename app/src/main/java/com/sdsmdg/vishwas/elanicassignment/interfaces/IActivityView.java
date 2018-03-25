package com.sdsmdg.vishwas.elanicassignment.interfaces;

import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;


public interface IActivityView {
    void showSplash();

    void setDisplayHomeAsUpEnabled();

    void startMainActivity(String query);

    void showProgressBar();

    void hideProgressBar();

    void addItems(QuestionClass questions);

    void clearAdapter();

    void clearScreen();

    void noResultFound();

    void httpError();

    void clearImage();

    void connectionError();

    void unknownError();

    void setMenuItemText(String sort, String orderby);

    void setMenuItemSelected(int id);
}
