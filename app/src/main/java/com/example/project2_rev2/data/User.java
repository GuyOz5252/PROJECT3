package com.example.project2_rev2.data;

import com.example.project2_rev2.gameComponents.abstractComponents.Tower;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static User user;

    private String username;
    private Map<String, Object> towerXPData;

    private User() {
        this.username = null;
        this.towerXPData = null;
    }

    public void createUserData(DocumentReference userDocument, String username) {
        this.username = username;

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("user_name", username);

        HashMap<String, Object> towers = new HashMap<>();
        for (Tower.TowerType towerType : Tower.TowerType.values()) {
            towers.put(towerType.name().toLowerCase(), 0);
        }
        userData.put("tower_xp", towers);

        userDocument.set(userData);

        userDocument.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                setUserData(task.getResult());
            }
        });
    }

    @SuppressWarnings("all")
    public void setUserData(DocumentSnapshot documentSnapshot) {
        username = documentSnapshot.getString("user_name");
        towerXPData = (Map<String, Object>) documentSnapshot.get("tower_xp");
        System.out.println(towerXPData);
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

    public int getTowerXP(Tower.TowerType towerType) {
        return ((Long)towerXPData.get(towerType.name().toLowerCase())).intValue();
    }
}
