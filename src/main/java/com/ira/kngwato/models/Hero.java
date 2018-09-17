package com.ira.kngwato.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Builder
@Setter
@Getter
public class Hero {
    @NotNull(message = "Hero name cannot be null")
    private String heroName;
    @NotNull(message = "Hero class cannot be null")
    private String heroClass;

    @Min(value = 1, message = "Level can only be between 1 and 5")
    @Max(value = 5, message = "Level can only be between 1 and 5")
    private int level;
    @Min(value = 1000, message = "XP can only be between 1000 and 12200")
    @Max(value = 12200, message = "XP can only be between 1000 and 12200")
    private int exp;
    @Min(value = 500, message = "Attack can only be between 500 and 1000")
    @Max(value = 1000, message = "Attack can only be between 500 and 1000")
    private int attack;
    @Min(value = 500, message = "Defence can only be between 500 and 1000")
    @Max(value = 1000, message = "Defence can only be between 500 and 1000")
    private int defense;
    @Min(value = 500, message = "HitPoints can only be between 500 and 1000")
    @Max(value = 1000, message = "HitPoints can only be between 500 and 1000")
    private int hitPoints;

    private int x;
    private int y;

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%d,%d,%d,%d,%d",
                heroName,heroClass,level,exp,attack,defense,hitPoints,x,y);
    }

    public void moveLeft() {
        x--;
        if (x < 0) x = 0;
    }

    public void moveRight() {
        x++;
        if (x >= (level - 1) * 5 + 10 - (level % 2))
            x--;
    }

    public void moveUp() {
        y--;
        if (y < 0) y = 0;
    }

    public void moveDown() {
        y++;
        if (y >= (level - 1) * 5 + 10 - (level % 2))
            y--;
    }

    public void levelUp() {
        if (level < 5)
            level++;
        exp = level * 1000 + (level - 1) * (level - 1) * 450;
    }
}
