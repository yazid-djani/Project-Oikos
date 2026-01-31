package com.oikos.engine.core;

import com.oikos.engine.math.Vector2;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class GameObject {

    public Vector2 position;
    public BufferedImage image; // L'image du perso
    public int width, height;   // Taille

    public GameObject(float x, float y, int width, int height) {
        this.position = new Vector2(x, y);
        this.width = width;
        this.height = height;
    }

    // Chaque objet devra définir comment il se met à jour (IA, mouvement)
    public abstract void update();

    // Chaque objet sait se dessiner
    public void draw(Graphics2D g2) {
        if (image != null) {
            // On dessine l'image à la position X,Y (arrondie en entier pour l'écran)
            g2.drawImage(image, (int)position.x, (int)position.y, width, height, null);
        }
    }
}