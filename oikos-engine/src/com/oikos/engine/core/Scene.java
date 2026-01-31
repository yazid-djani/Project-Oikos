package com.oikos.engine.core;

import java.awt.Graphics2D;

public abstract class Scene {

    protected GameLoop gp; // Accès au moteur (pour changer de scène, lire les touches...)

    public Scene(GameLoop gp) {
        this.gp = gp;
    }

    public abstract void update();
    public abstract void draw(Graphics2D g2);
}