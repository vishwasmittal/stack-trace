package com.sdsmdg.vishwas.elanicassignment.models;

import java.util.ArrayList;
import java.util.List;


public class QuestionClass {

    public class Items {

        public class User {
            private String profile_image;
            private String display_name;
            private String link;

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getDisplay_name() {
                return display_name;
            }

            public void setDisplay_name(String display_name) {
                this.display_name = display_name;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }
        }

        private List<String> tags;
        private boolean is_answered;
        private User owner;
        private String link;
        private String title;
        private String body;

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

        public boolean isIs_answered() {
            return is_answered;
        }

        public void setIs_answered(boolean is_answered) {
            this.is_answered = is_answered;
        }

        public User getOwner() {
            return owner;
        }

        public void setOwner(User owner) {
            this.owner = owner;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    private List<Items> items;

    public QuestionClass(){
        items = new ArrayList<>();
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public void addItems(List<Items> items) {
//        this.items = items;
        this.items.addAll(items);
    }

    public int getSize() {
        return items.size();
    }

    public void clear(){
        items.clear();
    }
}
