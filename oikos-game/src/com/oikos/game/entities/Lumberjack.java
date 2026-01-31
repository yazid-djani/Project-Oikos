package com.oikos.game.entities;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Bûcheron - Coupe du bois.
 * TODO: Implémenter la coupe d'arbres.
 */
public class Lumberjack extends Villager {

    private int woodCollected = 0;

    public Lumberjack(float x, float y) {
        super(x, y);
        this.job = "lumberjack";
    }

    @Override
    public void update() {
        // TODO: IA du bûcheron (chercher arbres, couper)
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            super.draw(g2);
        } else {
            g2.setColor(new Color(139, 69, 19)); // Marron foncé
            g2.fillRect((int)position.x, (int)position.y, width, height);
            // Petite hache
            g2.setColor(Color.GRAY);
            g2.fillRect((int)position.x + width - 10, (int)position.y + 10, 8, 20);
        }
    }

    public int getWoodCollected() {
        return woodCollected;
    }
}