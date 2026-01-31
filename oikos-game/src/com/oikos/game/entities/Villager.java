package com.oikos.game.entities;

import com.oikos.engine.core.GameObject;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Villageois - Humain générique.
 * TODO: Implémenter les métiers et comportements.
 */
public class Villager extends GameObject {

    protected float speed = 2f;
    protected int health = 100;
    protected String job = "villager";

    public Villager(float x, float y) {
        super(x, y, 64, 64);
    }

    @Override
    public void update() {
        // TODO: IA du villageois
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            super.draw(g2);
        } else {
            g2.setColor(new Color(139, 90, 43)); // Marron
            g2.fillRect((int)position.x, (int)position.y, width, height);
        }
    }
}