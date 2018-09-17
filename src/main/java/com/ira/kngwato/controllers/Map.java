package com.ira.kngwato.controllers;

import com.ira.kngwato.models.Enemy;
import com.ira.kngwato.models.Hero;

public class Map {
    private int size;
    private static Map map = null;
    private String[][] mapArray;

    private Map() { }

    public static Map getMap() {
        if (map == null)
        {
            map = new Map();
        }
        return map;
    }

    public Map setSize(int level) {
        size = (level - 1) * 5 + 10 - (level % 2);
        return map;
    }

    public void initialiseMap () {
        mapArray = new String[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                mapArray[i][j] = "*";
            }
        }
    }

    public Map placeHero(Hero hero) {
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
             if (mapArray[i][j] == "H")
             {
                 mapArray[i][j] = "*";
             }
            }
        }
        mapArray[hero.getY()][hero.getX()] = "H";
        return map;
    }

    public Map placeEnemy(Enemy enemy) {
        mapArray[enemy.getY()][enemy.getX()] = "E";
        return map;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder mapBuilder = new StringBuilder();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                mapBuilder.append(mapArray[i][j] + " ");
            }
            mapBuilder.append("\n");
        }
        return mapBuilder.toString();
    }
}
