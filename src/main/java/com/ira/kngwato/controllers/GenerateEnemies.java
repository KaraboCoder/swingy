package com.ira.kngwato.controllers;

import com.ira.kngwato.models.Enemy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerateEnemies {
    private List<Enemy> enemies;
    private Random random = new Random();

    public GenerateEnemies(int mapSize) {
        int numberOfEnemies = random.nextInt(mapSize - 5);
        if(numberOfEnemies == 0) numberOfEnemies = 1;
        enemies = new ArrayList();
        for(int i = 0; i < numberOfEnemies; i++)
        {
            enemies.add(Enemy.builder()
                    .character("E")
                    .artifact(SelectArtifact.getInstance().getArtifact())
                    .x(random.nextInt(mapSize))
                    .y(random.nextInt(mapSize))
                    .build());
        }
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }
}
