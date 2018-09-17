package com.ira.kngwato.controllers;

import java.util.Random;

public class Fight {
    public static int fight() {
        Random random = new Random();
        return random.nextInt(2);
    }
}
