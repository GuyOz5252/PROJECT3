package com.example.project2_rev2.data;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User {

    private static User user;

    private String username;
    private Map<String, Object> towerXP;
    private SaveData saveData;

    private User() {}

    public void createUserData(DocumentReference userDocument, String username) {
        this.username = username;

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("user_name", username);

        HashMap<String, Object> towerXPMap = new HashMap<>();
        for (TowerType towerType : TowerType.values()) {
            towerXPMap.put(towerType.name().toLowerCase(), 0);
        }
        userData.put("tower_xp", towerXPMap);

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
        towerXP = (Map<String, Object>) documentSnapshot.get("tower_xp");
        documentSnapshot.getReference()
                .collection("data_segment")
                .document("save_data")
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveData = task.getResult().toObject(SaveData.class);
                    }
                });
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

    public int getTowerXP(TowerType towerType) {
        return ((Long) towerXP.get(towerType.name().toLowerCase())).intValue();
    }

    public SaveData getSaveData() {
        return saveData;
    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }
}
