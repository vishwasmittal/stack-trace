package com.sdsmdg.vishwas.elanicassignment;


public class Helpers {

    public int getMenuItemIDFromString(String itemString) {
        switch (itemString) {
            case "asc":
                return R.id.ascending;
            case "desc":
                return R.id.descending;
            case "activity":
                return R.id.activity;
            case "votes":
                return R.id.votes;
            case "creation":
                return R.id.creation;
            case "hot":
                return R.id.hot;
            case "week":
                return R.id.week;
            case "month":
                return R.id.month;
            default:
                return 0;
        }
    }
}
