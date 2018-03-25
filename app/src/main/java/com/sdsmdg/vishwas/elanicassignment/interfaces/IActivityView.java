package com.sdsmdg.vishwas.elanicassignment.interfaces;

import com.sdsmdg.vishwas.elanicassignment.models.QuestionClass;


public interface IActivityView {
    public void showSplash();

    public void setDisplayHomeAsUpEnabled();

    public void startMainActivity(String query);

    public void showProgressBar();

    public void hideProgressBar();

    public void addItems(QuestionClass questions);

    public void clearAdapter();

    public void clearScreen();

    public void noResultFound();

    public void httpError();

    public void clearImage();

    public void connectionError();

    public void unknownError();

    public void setMenuItemText(String sort, String orderby);

    public void setMenuItemSelected(int id);
}
