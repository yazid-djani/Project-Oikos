package com.oikos.game.entities;

import com.oikos.engine.core.GameObject;
import java.awt.Graphics2D;
import java.awt.Color;

/**
 * Loup - Prédateur de l'écosystème.
 * TODO: Implémenter la logique de chasse.
 */
public class Wolf extends GameObject {

    private float speed = 2f;
    private int health = 100;

    public Wolf(float x, float y) {
        super(x, y, 64, 64);
    }

    @Override
    public void update() {
        // TODO: IA du loup (chasser les moutons)
    }

    @Override
    public void draw(Graphics2D g2) {
        // Dessin temporaire (carré gris)
        if (image != null) {
            super.draw(g2);
        } else {
            g2.setColor(new Color(100, 100, 100));
            g2.fillRect((int)position.x, (int)position.y, width, height);
        }
    }
}