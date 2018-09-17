package com.ira.kngwato.views.gui;

import com.ira.kngwato.controllers.Fight;
import com.ira.kngwato.controllers.GenerateEnemies;
import com.ira.kngwato.controllers.Map;
import com.ira.kngwato.controllers.Storage;
import com.ira.kngwato.models.Enemy;
import com.ira.kngwato.models.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

public class FightingGroundView extends JFrame implements WindowListener {
    private static final int FIGHT = 1;
    private static final int RUN = 0;
    private static final int WIN = 1;
    private Hero selectedHero;
    private JPanel container, leftContainer, rightContainer, controlsContainer;
    private JButton cancelButton, fightButton, runButton, leftButton, rightButton, upButton, downButton;
    private JTextArea fightingGround;
    private JTextPane heroStats;
    private List<Hero> heroes;
    private List<Enemy> enemies;

    public FightingGroundView(int selectedHeroIndex) {
        super("Fighting Ground");
        createComponents();
        setupComponents();
        addWindowListener(this);

        heroes = Storage.getStorage().getHeroes();
        updateStats(selectedHeroIndex);

        Map.getMap().setSize(selectedHero.getLevel()).initialiseMap();
        Map.getMap().placeHero(selectedHero);

        enemies = (new GenerateEnemies(Map.getMap().getSize())).getEnemies();
        placeEnemies();

        fightingGround.setText(Map.getMap().toString());

        setSize(800,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);

    }

    private void placeEnemies() {
        for(Enemy enemy : enemies) {
            Map.getMap().placeEnemy(enemy);
        }
    }

    private void createComponents() {
        container = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        container.setBackground(Color.WHITE);
        container.setPreferredSize(new Dimension(800,800));

        setupControlsContainer();

        leftContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 10,10));
        leftContainer.setBackground(Color.WHITE);
        leftContainer.setPreferredSize(new Dimension(300,800));
        leftContainer.add(new JLabel("Hero Stats"));
        heroStats = new JTextPane();
        heroStats.setPreferredSize(new Dimension(280, 500));
        heroStats.setBackground(Color.BLACK);
        heroStats.setForeground(Color.RED);
        heroStats.setEditable(false);
        JPanel bottomContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0,10));
        bottomContainer.setPreferredSize(new Dimension(280,70));
        bottomContainer.setBackground(Color.WHITE);
        fightButton = new JButton("Fight");
        fightButton.setPreferredSize(new Dimension(100,50));
        runButton = new JButton("Run");
        runButton.setPreferredSize(new Dimension(100,50));
//        bottomContainer.add(fightButton);
//        bottomContainer.add(runButton);
        leftContainer.add(heroStats);
        leftContainer.add(controlsContainer);
        leftContainer.add(bottomContainer);

        rightContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,10));
        rightContainer.setBackground(Color.WHITE);
        rightContainer.setPreferredSize(new Dimension(500,800));

        fightingGround = new JTextArea();
        fightingGround.setPreferredSize(new Dimension(480,720));
        fightingGround.setForeground(Color.RED);
        fightingGround.setBackground(Color.BLACK);
        fightingGround.setEditable(false);
        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100,50));
        rightContainer.add(fightingGround);
        rightContainer.add(cancelButton);

        container.add(leftContainer);
        container.add(rightContainer);
    }

    private void setupComponents() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Storage.getStorage().updateHeroes(heroes);
                new HomeView();
                dispose();
            }
        });

        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHero.moveRight();
                checkEnemyContact();
                Map.getMap().placeHero(selectedHero);
                fightingGround.setText(Map.getMap().toString());
                reachedTopBorder();
                updateStats();
            }
        });

        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHero.moveLeft();
                checkEnemyContact();
                Map.getMap().placeHero(selectedHero);
                fightingGround.setText(Map.getMap().toString());
                reachedTopBorder();
                updateStats();
            }
        });

        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHero.moveUp();
                checkEnemyContact();
                Map.getMap().placeHero(selectedHero);
                fightingGround.setText(Map.getMap().toString());
                reachedTopBorder();
                updateStats();
            }
        });

        downButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedHero.moveDown();
                checkEnemyContact();
                Map.getMap().placeHero(selectedHero);
                fightingGround.setText(Map.getMap().toString());
                reachedTopBorder();
                updateStats();
            }
        });

    }

    private void setupControlsContainer() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1;
        gbc.weighty = 1;

        controlsContainer = new JPanel(new GridBagLayout());
        controlsContainer.setPreferredSize(new Dimension(280,175));
        controlsContainer.setBackground(Color.WHITE);
        upButton = new JButton("UP");
        upButton.setPreferredSize(new Dimension(100,50));
        downButton = new JButton("DOWN");
        downButton.setPreferredSize(new Dimension(100,50));
        leftButton = new JButton("LEFT");
        leftButton.setPreferredSize(new Dimension(100,50));
        rightButton = new JButton("RIGHT");
        rightButton.setPreferredSize(new Dimension(100,50));
        gbc.gridx = 1;
        gbc.gridy = 0;
        controlsContainer.add(upButton, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlsContainer.add(leftButton, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        controlsContainer.add(rightButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        controlsContainer.add(downButton, gbc);
    }

    private void updateStats(int selectedHeroIndex) {
        if (selectedHeroIndex <= heroes.size())
        {
            selectedHero = heroes.get(selectedHeroIndex);
            heroStats.setText(String.format("Name: %s\nClass: %s\nLevel: %d\nXP: %d\nAttack: %d\nDefence: %d\nHit Points: %d\nX: %d\nY: %d",
                    selectedHero.getHeroName(), selectedHero.getHeroClass(), selectedHero.getLevel(),
                    selectedHero.getExp(), selectedHero.getAttack(), selectedHero.getDefense(), selectedHero.getHitPoints(),
                    selectedHero.getX(), selectedHero.getY()));
        }
    }

    private void updateStats() {
        heroStats.setText(String.format("Name: %s\nClass: %s\nLevel: %d\nXP: %d\nAttack: %d\nDefence: %d\nHit Points: %d\nX: %d\nY: %d",
                selectedHero.getHeroName(), selectedHero.getHeroClass(), selectedHero.getLevel(),
                selectedHero.getExp(), selectedHero.getAttack(), selectedHero.getDefense(), selectedHero.getHitPoints(),
                selectedHero.getX(), selectedHero.getY()));
    }

    private void checkEnemyContact() {
        Enemy foughtEnemy = null;
        int wannaFight = 0;
        JOptionPane optionPane = new JOptionPane("Wanna Fight?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Fight or Flight");
        for(Enemy enemy : enemies) {
            if (enemy.getX() == selectedHero.getX() && enemy.getY() == selectedHero.getY()) {
                foughtEnemy = enemy;
                dialog.setVisible(true);
                wannaFight = fightOrFlight(optionPane);
                break;
            }
        }
        if(foughtEnemy != null && wannaFight == FIGHT) {
            if(Fight.fight() == WIN) {
                JOptionPane.showMessageDialog(this, "You won!\nCollected Artifact: "+foughtEnemy.getArtifact());
                if(foughtEnemy.getArtifact() == "ARMOR") selectedHero.setDefense(selectedHero.getDefense() + 10);
                if(foughtEnemy.getArtifact() == "HELM") selectedHero.setHitPoints(selectedHero.getHitPoints() + 10);
                if(foughtEnemy.getArtifact() == "WEAPON") selectedHero.setAttack(selectedHero.getAttack() + 10);
                //Validation
                enemies.remove(foughtEnemy);
                if (enemies.size() == 0)
                {
                    selectedHero.levelUp();

                    Map.getMap().setSize(selectedHero.getLevel()).initialiseMap();
                    Map.getMap().placeHero(selectedHero);
                    enemies = (new GenerateEnemies(Map.getMap().getSize())).getEnemies();
                    placeEnemies();
                    fightingGround.setText(Map.getMap().toString());
                }
                updateStats();
            }else {
                Storage.getStorage().updateHeroes(heroes);
                JOptionPane.showMessageDialog(this, "You Lost!");
                new HomeView();
                dispose();
            }
        }
    }

    private void reachedTopBorder() {
        if(selectedHero.getY() == 0) {
            JOptionPane.showMessageDialog(this, "You won!");
            selectedHero.levelUp();
            int size = (selectedHero.getLevel() - 1) * 5 + 10 - (selectedHero.getLevel() % 2);
            selectedHero.setX(size / 2);
            selectedHero.setY(size / 2);
            Map.getMap().setSize(selectedHero.getLevel()).initialiseMap();
            Map.getMap().placeHero(selectedHero);
            enemies = (new GenerateEnemies(Map.getMap().getSize())).getEnemies();
            placeEnemies();
            fightingGround.setText(Map.getMap().toString());
            updateStats();
        }
    }

    private int fightOrFlight(JOptionPane optionPane) {
        if (optionPane.getValue() != null &&
                (Integer)optionPane.getValue() != 1)
        {
            return FIGHT;
        }else{
            selectedHero.moveUp();
            placeEnemies();
            return RUN;
        }
    }

    public void windowOpened(WindowEvent e) {}

    public void windowClosing(WindowEvent e) {
        Storage.getStorage().updateHeroes(heroes);
    }

    public void windowClosed(WindowEvent e) {}

    public void windowIconified(WindowEvent e) {}

    public void windowDeiconified(WindowEvent e) {}

    public void windowActivated(WindowEvent e) {}

    public void windowDeactivated(WindowEvent e) {}

}