package com.ira.kngwato.views.gui;

import com.ira.kngwato.controllers.Storage;
import com.ira.kngwato.models.Hero;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HomeView extends JFrame {
    private JPanel container, leftContainer, rightContainer;
    private JButton selectHero, newHero;
    private JList heroesList;
    private JTextArea heroDetails;
    List<String> heroNames;
    List<Hero> heroes;
    private int selectedHeroIndex = 0;

    public HomeView() {
        super("Swingy");

        createComponents();
        setupComponents();

        heroes = Storage.getStorage().getHeroes();
        heroNames = new ArrayList<String>();
        for(Hero h : heroes) {
            heroNames.add(h.getHeroName());
        }

        heroesList.setListData(heroNames.toArray());
        heroesList.setSelectedIndex(0);

        setSize(500,500);
        setLocationRelativeTo(null);
        setResizable(false);
        setContentPane(this.container);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private void createComponents() {
        container = new JPanel(new GridLayout(1,2));
        container.setBackground(new Color(255,255,255));
        container.setPreferredSize(new Dimension(500,500));

        leftContainer = new JPanel(new FlowLayout());
        leftContainer.setPreferredSize(new Dimension(250,500));
        leftContainer.setBackground(new Color(255,255,255));

        leftContainer.add(new JLabel("Available Heroes"));
        heroesList = new JList();
        heroesList.setPreferredSize(new Dimension(230,410));

        JScrollPane listScroll = new JScrollPane(heroesList);
        listScroll.setPreferredSize(new Dimension(230,410));
        leftContainer.add(listScroll);

        selectHero = new JButton("Select Hero");
        newHero = new JButton("New Hero");

        JPanel buttonsContainer = new JPanel(new GridLayout(1,2,10,0));
        buttonsContainer.setPreferredSize(new Dimension(230,50));
        buttonsContainer.setBackground(new Color(255,255,255));
        buttonsContainer.add(selectHero);
        buttonsContainer.add(newHero);
        leftContainer.add(buttonsContainer);

        rightContainer = new JPanel(new FlowLayout());
        rightContainer.setPreferredSize(new Dimension(250,500));
        rightContainer.setBackground(new Color(255,255,255));
        rightContainer.add(new JLabel("Hero Details"));

        heroDetails = new JTextArea();
        heroDetails.setPreferredSize(new Dimension(230, 440));
        rightContainer.add(heroDetails);

        container.add(leftContainer);
        container.add(rightContainer);
    }

    private void setupComponents() {
        newHero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                System.out.println("New Hero");
                new NewHeroView();
                dispose();
            }
        });

        selectHero.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Hero hero = Hero.builder()
                        .heroName("Karabo")
                        .heroClass("Alien")
                        .attack(1000)
                        .defense(1000)
                        .exp(1000)
                        .hitPoints(1000)
                        .level(5)
                        .build();
                new FightingGroundView(selectedHeroIndex);
                dispose();
            }
        });

        heroesList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting())
                {
                    JList list = (JList) e.getSource();
                    int index = list.getSelectedIndex();
                    Hero selectedHero = heroes.get(index);
                    heroDetails.setText(String.format("Name: %s\nClass: %s\nLevel: %d\nXP: %d\nAttack: %d\nDefence: %d\nHit Points: %d",
                            selectedHero.getHeroName(), selectedHero.getHeroClass(), selectedHero.getLevel(),
                            selectedHero.getExp(), selectedHero.getAttack(), selectedHero.getDefense(), selectedHero.getHitPoints()));
                    setSelectedHero(index);
                }
            }
        });
    }

    private void setSelectedHero(int index) {
        selectedHeroIndex = index;
    }
}
