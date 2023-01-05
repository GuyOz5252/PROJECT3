package com.example.project2_rev2.data;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class User {

    private static User user;

    private String username;
    private int userLevel;
    private int userXP;
    private Map<String, Object> towerXP;
    private SaveData saveData;
    private PlayerStats playerStats;

    private User() {}

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void createUserData(DocumentReference userDocument, String username) {
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("user_name", username);
        userData.put("user_level", 1);
        userData.put("user_xp", 0);

        HashMap<String, Object> towerXPMap = new HashMap<>();
        for (TowerType towerType : TowerType.values()) {
            towerXPMap.put(towerType.name().toLowerCase(), 0);
        }
        userData.put("tower_xp", towerXPMap);

        userDocument.set(userData);

        userDocument.collection("data_segment")
                .document("save_data")
                .set(new SaveData());

        userDocument.collection("data_segment")
                .document("player_stats")
                .set(new PlayerStats());

        this.username = username;
        this.userLevel = 1;
        this.userXP = 0;
        this.towerXP = towerXPMap;
        userDocument.collection("data_segment")
                .document("save_data")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveData = task.getResult().toObject(SaveData.class);
                    }
                });
        userDocument.collection("data_segment")
                .document("player_stats")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        playerStats = task.getResult().toObject(PlayerStats.class);
                    }
                });
    }

    @SuppressWarnings("all")
    public void setUserData(DocumentSnapshot documentSnapshot) {
        username = documentSnapshot.getString("user_name");
        userLevel = ((Long)documentSnapshot.get("user_level")).intValue();
        userXP = ((Long)documentSnapshot.get("user_xp")).intValue();
        towerXP = (Map<String, Object>) documentSnapshot.get("tower_xp");
        documentSnapshot.getReference()
                .collection("data_segment")
                .document("save_data")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveData = task.getResult().toObject(SaveData.class);
                    }
                });
        documentSnapshot.getReference()
                .collection("data_segment")
                .document("player_stats")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        playerStats = task.getResult().toObject(PlayerStats.class);
                    }
                });
    }

    public void updateFirestoreUserData() {
        DocumentReference userDocument = FirebaseFirestore.getInstance().collection("users")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid());

        userDocument.update("user_level", getInstance().getUserLevel());
        userDocument.update("user_xp", getInstance().getUserXP());
        userDocument.update("tower_xp", towerXP);

        userDocument.collection("data_segment").document("save_data")
                .set(getInstance().getSaveData(), SetOptions.merge());
        userDocument.collection("data_segment").document("player_stats")
                .set(getInstance().getPlayerStats(), SetOptions.merge());
    }

    public String getUsername() {
        return username;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public int getUserXP() {
        return userXP;
    }

    public void addUserXP(int xp) {
        userXP += xp;
        getPlayerStats().setXpEarned(getPlayerStats().getXpEarned() + xp);
        if (userXP >= userLevel*1800) {
            userXP -= userLevel*1800;
            userLevel++;
        }
    }

    public int getTowerXP(TowerType towerType) {
        if (towerXP.get(towerType.name().toLowerCase()).getClass().equals(Long.class)) {
            return ((Long) towerXP.get(towerType.name().toLowerCase())).intValue();
        } else {
            return (int) towerXP.get(towerType.name().toLowerCase());
        }
    }

    public void setTowerXP(TowerType towerType, int xp) {
        towerXP.replace(towerType.name().toLowerCase(), xp);
    }

    public SaveData getSaveData() {
        return saveData;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }
}
