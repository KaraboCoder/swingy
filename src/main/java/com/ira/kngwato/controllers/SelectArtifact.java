package com.ira.kngwato.controllers;

import java.util.Random;

public class SelectArtifact {
    private final String[] artifacts = {"HELM","WEAPON", "ARMOR"};
    private static SelectArtifact instance = null;
    private Random random = new Random();

    private SelectArtifact() {}

    public static SelectArtifact getInstance() {
        if (instance == null) {
            instance = new SelectArtifact();
        }
        return instance;
    }

    public String getArtifact() {
        return artifacts[random.nextInt(3)];
    }
}
