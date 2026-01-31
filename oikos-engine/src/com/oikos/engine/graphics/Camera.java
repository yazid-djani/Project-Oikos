package com.oikos.engine.graphics;

import com.oikos.engine.math.Vector2;

/**
 * Caméra qui suit une cible (généralement le joueur).
 * Calcule l'offset pour dessiner le monde.
 */
public class Camera {

    // Position de la caméra dans le monde
    public Vector2 position;

    // Dimensions de l'écran (viewport)
    private int viewportWidth;
    private int viewportHeight;

    // Dimensions du monde (en pixels)
    private int worldWidth;
    private int worldHeight;

    // Cible à suivre (position du joueur)
    private Vector2 target;

    public Camera(int viewportWidth, int viewportHeight, int worldWidth, int worldHeight) {
        this.position = new Vector2(0, 0);
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        this.target = null;
    }

    /**
     * Définit la cible à suivre (position du joueur).
     */
    public void setTarget(Vector2 target) {
        this.target = target;
    }

    /**
     * Met à jour la position de la caméra pour centrer sur la cible.
     * Appelé à chaque frame dans update().
     */
    public void update() {
        if (target == null) return;

        // On veut que la cible soit au centre de l'écran
        float targetCamX = target.x - (viewportWidth / 2f);
        float targetCamY = target.y - (viewportHeight / 2f);

        // Clamp pour ne pas sortir des limites du monde
        position.x = clamp(targetCamX, 0, worldWidth - viewportWidth);
        position.y = clamp(targetCamY, 0, worldHeight - viewportHeight);
    }

    /**
     * Retourne l'offset X à soustraire lors du dessin.
     */
    public int getOffsetX() {
        return (int) position.x;
    }

    /**
     * Retourne l'offset Y à soustraire lors du dessin.
     */
    public int getOffsetY() {
        return (int) position.y;
    }

    /**
     * Convertit une position monde en position écran.
     */
    public int worldToScreenX(float worldX) {
        return (int) (worldX - position.x);
    }

    public int worldToScreenY(float worldY) {
        return (int) (worldY - position.y);
    }

    /**
     * Convertit une position écran en position monde.
     */
    public float screenToWorldX(int screenX) {
        return screenX + position.x;
    }

    public float screenToWorldY(int screenY) {
        return screenY + position.y;
    }

    /**
     * Vérifie si un rectangle (en coordonnées monde) est visible.
     */
    public boolean isVisible(float x, float y, int width, int height) {
        return x + width > position.x
                && x < position.x + viewportWidth
                && y + height > position.y
                && y < position.y + viewportHeight;
    }

    /**
     * Met à jour les dimensions du monde (si la map change).
     */
    public void setWorldSize(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}