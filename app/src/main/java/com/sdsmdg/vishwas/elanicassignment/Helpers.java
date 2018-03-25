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

//    public String[] getStringFromMenuItemID(int id){
//        String [] result = new String[2];
//        //        String orderby = null;
//        String sort = null;
//        switch (item.getItemId()) {
//            case R.id.search:
//                Log.e("onOptionsItemSelected", "Search option chosen");
//                return true;
//            case R.id.activity:
//                sort = "activity";
//                break;
//            case R.id.votes:
//                sort = "votes";
//                break;
//            case R.id.creation:
//                sort = "creation";
//                break;
//            case R.id.hot:
//                sort = "hot";
//                break;
//            case R.id.week:
//                sort = "week";
//                break;
//            case R.id.month:
//                sort = "month";
//                break;
//            case R.id.ascending:
//                orderby = "asc";
//                break;
//            case R.id.descending:
//                orderby = "desc";
//                break;
//            default:
//                return false;
//        }
//        return
//    }


}
