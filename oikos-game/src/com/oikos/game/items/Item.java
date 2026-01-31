package com.oikos.game.items;

import com.oikos.engine.core.GameObject;
import java.awt.Graphics2D;

/**
 * Objet ramassable dans le monde.
 * TODO: Implémenter le système d'inventaire.
 */
public abstract class Item extends GameObject {

    protected String name;
    protected boolean collected = false;

    public Item(float x, float y, String name) {
        super(x, y, 32, 32); // Items plus petits
        this.name = name;
    }

    @Override
    public void update() {
        // Les items ne bougent pas par défaut
    }

    public String getName() {
        return name;
    }

    public boolean isCollected() {
        return collected;
    }

    public void collect() {
        this.collected = true;
    }
}