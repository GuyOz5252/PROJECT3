package com.example.project2_rev2.data;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

public class User {

    private static User user = null;

    private String username = "";
    private HashMap<String, Object> towerXPData;

    private User() {}

    public void initUser(DocumentSnapshot documentSnapshot) {
        this.username = documentSnapshot.getString("user_name");
    }

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public String getUsername() {
        return username;
    }
}
