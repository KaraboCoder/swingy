package com.ira.kngwato;

import com.ira.kngwato.views.gui.HomeView;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1)
        {
            if (args[0].equalsIgnoreCase("console"))
            {
                new com.ira.kngwato.views.console.HomeView().run();
            }
            else if (args[0].equalsIgnoreCase("gui"))
            {
                new HomeView();
            }
            else
            {
                System.out.println("Error");
            }
        }
        else
        {
            System.out.println("Error");
        }
    }
}
