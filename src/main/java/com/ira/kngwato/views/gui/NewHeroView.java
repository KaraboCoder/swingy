package com.ira.kngwato.views.gui;

import com.ira.kngwato.controllers.Storage;
import com.ira.kngwato.models.Hero;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewHeroView extends JFrame {
    private JPanel container, topContainer, bottomContainer;
    private JButton saveButton, cancelButton;
    private JTextField heroName, heroXP, heroAttack, heroDefence, heroHitPoints;
    private JComboBox heroClass, heroLevel;

    public NewHeroView() {
        super("New Hero");
        createComponents();
        setupComponents();

        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(this.container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createComponents() {
        container = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        container.setBackground(new Color(255,255,255));
        container.setPreferredSize(new Dimension(500,500));

        setupTopContainer();

        bottomContainer = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        bottomContainer.setBackground(new Color(255,255,255));
        bottomContainer.setPreferredSize(new Dimension(500,70));

        cancelButton = new JButton("Cancel");
        cancelButton.setPreferredSize(new Dimension(100,50));
        bottomContainer.add(cancelButton);

        saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(100,50));
        bottomContainer.add(saveButton);

        container.add(topContainer);
        container.add(bottomContainer);
    }

    private void setupComponents() {
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new HomeView();
                dispose();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int level = Integer.parseInt(heroLevel.getSelectedItem().toString());
                    int size = (level - 1) * 5 + 10 - (level % 2);
                    if (heroName.getText().trim().length() == 0)
                        throw new Exception("Hero name cannot be null");
                    int lvl = Integer.parseInt(heroLevel.getSelectedItem().toString());
                    int atck = Integer.parseInt(heroAttack.getText());
                    int def = Integer.parseInt(heroDefence.getText());
                    int hp =Integer.parseInt(heroHitPoints.getText());
                    int xp = Integer.parseInt(heroXP.getText());
                    if (lvl < 1 || lvl > 5)
                        throw new Exception("Level can only be between 1 and 5");
                    if (xp < 1000 || xp > 12200)
                        throw new Exception("XP can only be between 1000 and 12200");
                    if (atck < 500 || atck > 1000)
                        throw new Exception("Attack can only be between 500 and 1000");
                    if (def < 500 || def > 1000)
                        throw new Exception("Defence can only be between 500 and 1000");
                    if (hp < 500 || hp > 1000)
                        throw new Exception("HitPoints can only be between 500 and 1000");
                    Hero hero = Hero.builder()
                            .heroName(heroName.getText().trim())
                            .heroClass(heroClass.getSelectedItem().toString())
                            .level(lvl)
                            .attack(atck)
                            .defense(def)
                            .hitPoints(hp)
                            .exp(xp)
                            .x(size / 2)
                            .y(size / 2)
                            .build();
                    Storage.getStorage().saveHero(hero);
                    new HomeView();
                    dispose();
                }catch (Exception exc) {
                    JOptionPane.showMessageDialog(null,exc.getMessage(), "Invalid Input",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void setupTopContainer() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,10,10,10);


        topContainer = new JPanel(new GridBagLayout());
        topContainer.setBackground(new Color(255,255,255));
        topContainer.setPreferredSize(new Dimension(500,430));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Hero Name:"), gbc);
        heroName = new JTextField();
        heroName.setMinimumSize(new Dimension(390,30));
        heroName.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Hero Class:"), gbc);
        String[] heroClasses = {"Knight", "Martian", "Zombie"};
        heroClass = new JComboBox(heroClasses);
        heroClass.setMinimumSize(new Dimension(390,30));
        heroClass.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroClass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Level: "), gbc);
        String[] heroLevels = {"1", "2", "3", "4", "5"};
        heroLevel = new JComboBox(heroLevels);
        heroLevel.setMinimumSize(new Dimension(390,30));
        heroLevel.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroLevel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("XP: "), gbc);
        heroXP = new JTextField();
        heroXP.setMinimumSize(new Dimension(390,30));
        heroXP.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroXP, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Attack: "), gbc);
        heroAttack = new JTextField();
        heroAttack.setMinimumSize(new Dimension(390,30));
        heroAttack.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroAttack, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Defence: "), gbc);
        heroDefence = new JTextField();
        heroDefence.setMinimumSize(new Dimension(390,30));
        heroDefence.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroDefence, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 2;
        gbc.weighty = 1;
        topContainer.add(new JLabel("Hit Points: "), gbc);
        heroHitPoints = new JTextField();
        heroHitPoints.setMinimumSize(new Dimension(390,30));
        heroHitPoints.setPreferredSize(new Dimension(390,30));
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 10;
        gbc.weighty = 1;
        topContainer.add(heroHitPoints, gbc);
    }
}
