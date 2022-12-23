package com.example.project2_rev2.data;

import com.example.project2_rev2.R;
import com.example.project2_rev2.utils.Size;

public enum EnemyType {
    DEMO_ENEMY(R.drawable.ic_bug, 4, new Size(100, 100), 30, 10, 7),
    DEMO_BOSS(R.drawable.ic_bug, 2, new Size(250, 250), 3100, 200, 100);

    public int resourceId;
    public int speed;
    public Size size;
    public int health;
    public int damage;
    public int value;

    EnemyType(int resourceId, int speed, Size size, int health, int damage, int value) {
        this.resourceId = resourceId;
        this.speed = speed;
        this.size = size;
        this.health = health;
        this.damage = damage;
        this.value = value;
    }
}
