package com.android.project3.data;

import com.android.project3.R;
import com.android.project3.utils.Size;

public enum EnemyType {
    DEMO_ENEMY(R.drawable.ic_bug, 4, new Size(100, 100), 30, 10, 7),
    CAMO_DEMO_ENEMY(R.drawable.ic_camo_bug, 8, new Size(100, 100), 30, 10, 9),
    ARMOR_DEMO_ENEMY(R.drawable.ic_armor_bug, 5, new Size(110, 110), 20, 17, 8),
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
