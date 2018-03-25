package com.sdsmdg.vishwas.elanicassignment.models;

import java.util.ArrayList;
import java.util.List;


public class QuestionClass {

    private List<Items> items;

    public QuestionClass() {
        items = new ArrayList<>();
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public void addItems(List<Items> items) {
        this.items.addAll(items);
    }

    public int getSize() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }
}
