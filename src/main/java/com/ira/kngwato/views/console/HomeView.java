package com.ira.kngwato.views.console;

import com.ira.kngwato.controllers.GenerateEnemies;
import com.ira.kngwato.controllers.Map;
import com.ira.kngwato.controllers.Storage;
import com.ira.kngwato.models.Enemy;
import com.ira.kngwato.models.Hero;

import java.util.List;
import java.util.Scanner;

public class HomeView {
    private List<Hero> heroes;
    private Hero selectedHero;
    private List<Enemy> enemies;

    public HomeView() {
        heroes = Storage.getStorage().getHeroes();
    }
    public void run() {
        String selection = null;
        while(true){
            if (selection != null && selection.equalsIgnoreCase("n"))
            {
                createNewHero();
            }else{
                if (selection != null) {
                    try{
                        int selectedHeroIndex = Integer.parseInt(selection) - 1;
                        if (selectedHeroIndex >= 0 && selectedHeroIndex < heroes.size()) {
                            selectedHero = heroes.get(selectedHeroIndex);
                            fightingGround();
                        }
                    }catch (Exception e){
                        System.out.println("Error input. Try again.");
                    }
                }
            }
            printHeader();
            printListOfHeroes();
            selection = selectHero();
            if (selection.equalsIgnoreCase("q")) {
                Storage.getStorage().updateHeroes(heroes);
                break;
            }
        }
    }

    private void createNewHero() {
        String heroName;
        String heroClass;
        Hero hero;
        System.out.println("############# CREATE NEW HERO #############\n");
        System.out.print("Enter Hero name: ");
        heroName = getUserInput();
        System.out.print("Enter Hero class: ");
        heroClass = getUserInput();

        hero = Hero.builder()
                .heroName(heroName)
                .heroClass(heroClass)
                .level(1)
                .exp(500)
                .attack(500)
                .defense(500)
                .hitPoints(500)
                .x(4)
                .y(4)
                .build();
        Storage.getStorage().saveHero(hero);
        heroes = Storage.getStorage().getHeroes();
    }

    private void printListOfHeroes() {
        int i = 1;
        System.out.println("#\tHEROES");
        for(Hero hero : heroes) {
            System.out.println(i + ".\t" + hero.getHeroName());
            i++;
        }
    }

    private void printHeader() {
        System.out.println("##############################################");
        System.out.println("################### SWINGY ###################");
        System.out.println("##############################################");
    }

    private String selectHero() {
        Scanner input = new Scanner(System.in);
        String selection;
        System.out.println("Select a Hero or enter q to exit or n to create new Hero.");
        selection = input.nextLine();
        return selection;
    }

    private String getUserInput() {
        Scanner userInput = new Scanner(System.in);
        String input = userInput.nextLine();
        while (true) {
            if (input.trim().length() == 0)
            {
                System.out.print("Input cannot be null. Try again: ");
                input = userInput.nextLine();
            }else {
                break;
            }
        }
        return input;
    }

    private void fightingGround() {
        Map.getMap().setSize(selectedHero.getLevel()).initialiseMap();
        Map.getMap().placeHero(selectedHero);
        enemies = (new GenerateEnemies(Map.getMap().getSize())).getEnemies();
        placeEnemies();
        printMap();
        String move;
        while(!(move = promptMovements()).equalsIgnoreCase("E")) {
            if (move.equalsIgnoreCase("U")) {
                selectedHero.moveUp();
                Map.getMap().placeHero(selectedHero);
            }
            if (move.equalsIgnoreCase("D")) {
                selectedHero.moveDown();
                Map.getMap().placeHero(selectedHero);
            }
            if (move.equalsIgnoreCase("L")) {
                selectedHero.moveLeft();
                Map.getMap().placeHero(selectedHero);
            }
            if (move.equalsIgnoreCase("R")) {
                selectedHero.moveRight();
                Map.getMap().placeHero(selectedHero);
            }
            if (move.equalsIgnoreCase("E")) {
                Storage.getStorage().updateHeroes(heroes);
                break;
            }
            printMap();
        }
    }

    private String promptMovements() {
        Scanner input = new Scanner(System.in);
        String selection;
        System.out.println("############# Select Move #############");
        System.out.println("U: UP | D: DOWN | L: LEFT | R: RIGHT | E: EXIT");
        selection = input.nextLine();
        return selection;
    }

    private void printMap() {
        System.out.println(Map.getMap().toString());
    }

    private void placeEnemies() {
        for(Enemy enemy : enemies) {
            Map.getMap().placeEnemy(enemy);
        }
    }
}