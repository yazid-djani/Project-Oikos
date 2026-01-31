package com.oikos.game.entities;

import com.oikos.engine.core.GameObject;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Mouton - Proie dans l'écosystème.
 * TODO: Implémenter la fuite et le broutage.
 */
public class Sheep extends GameObject {

    private float speed = 1.5f;
    private int health = 50;

    public Sheep(float x, float y) {
        super(x, y, 64, 64);
    }

    @Override
    public void update() {
        // TODO: IA du mouton (fuir les loups, brouter)
    }

    @Override
    public void draw(Graphics2D g2) {
        if (image != null) {
            super.draw(g2);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillOval((int)position.x, (int)position.y, width, height);
        }
    }
}