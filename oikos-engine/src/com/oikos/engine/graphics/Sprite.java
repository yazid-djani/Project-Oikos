package com.oikos.engine.graphics;

import java.awt.image.BufferedImage;

/**
 * Représente une image chargée avec ses métadonnées.
 * Utilisé pour les tiles et les entités.
 */
public class Sprite {

    public BufferedImage image;
    public boolean solid;       // Collision activée ?
    public String name;         // Identifiant (ex: "grass", "water")

    public Sprite() {
        this.image = null;
        this.solid = false;
        this.name = "";
    }

    public Sprite(BufferedImage image, String name, boolean solid) {
        this.image = image;
        this.name = name;
        this.solid = solid;
    }

    public int getWidth() {
        return image != null ? image.getWidth() : 0;
    }

    public int getHeight() {
        return image != null ? image.getHeight() : 0;
    }
}