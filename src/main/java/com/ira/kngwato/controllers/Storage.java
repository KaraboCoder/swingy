package com.ira.kngwato.controllers;

import com.ira.kngwato.models.Hero;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static BufferedWriter writer = null;
    private static BufferedReader reader = null;

    public Storage(){}

    public static Storage getStorage() {
        if (writer == null)
        {
            try {
                writer = new BufferedWriter(new FileWriter("heroes.txt", true));
            } catch (Exception e) {
                System.out.println("Could not initialise storage.");
                System.exit(1);
            }
        }
        if (reader == null)
        {
            try {
                reader = new BufferedReader(new FileReader("heroes.txt"));
            }catch (Exception e) {
                System.out.println("Could not initialise storage");
                System.exit(1);
            }
        }
        return new Storage();
    }

    public void saveHero(Hero hero) {
        try {
            writer.write(hero.toString());
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println("Could not save hero.");
            System.exit(1);
        }
    }

    public void updateHeroes(List<Hero> heroes) {
        try {
            BufferedWriter writerUpdateHeroes = new BufferedWriter(new FileWriter("heroes.txt"));
            String heroesString = "";
            for (Hero hero: heroes) {
                heroesString += hero.toString() + "\n";
            }
            writerUpdateHeroes.write(heroesString);
            writerUpdateHeroes.flush();
            writerUpdateHeroes.close();
        } catch (Exception e) {
            System.out.println("Could not update heroes.");
            System.exit(1);
        }
    }

    public List<Hero> getHeroes() {
        String heroString = "";
        List<Hero> heroes = new ArrayList<Hero>();
        try {
            while ((heroString = reader.readLine()) != null)
            {
                if (heroString.split(",").length != 9) {
                    System.out.println("Invalid Hero data.");
                    System.exit(1);
                }
                heroes.add(Hero.builder()
                        .heroName(heroString.split(",")[0])
                        .heroClass(heroString.split(",")[1])
                        .level(Integer.parseInt(heroString.split(",")[2]))
                        .exp(Integer.parseInt(heroString.split(",")[3]))
                        .attack(Integer.parseInt(heroString.split(",")[4]))
                        .defense(Integer.parseInt(heroString.split(",")[5]))
                        .hitPoints(Integer.parseInt(heroString.split(",")[6]))
                        .x(Integer.parseInt(heroString.split(",")[7]))
                        .y(Integer.parseInt(heroString.split(",")[8]))
                        .build());
            }
            reader.close();
            reader = null;
        }catch (Exception e) {
            System.out.println("Could not get heroes.");
            System.exit(1);
        }
        return heroes;
    }
}
