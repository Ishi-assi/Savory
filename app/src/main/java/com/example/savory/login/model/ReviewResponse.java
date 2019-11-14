package com.example.savory.login.model;


import java.util.List;

public class ReviewResponse {
    public List<Reviews> reviews;

    public class Reviews {
        public String id;

        public String text;
    }

}
