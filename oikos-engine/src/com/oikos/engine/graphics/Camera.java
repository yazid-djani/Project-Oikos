package com.oikos.engine.graphics;

import com.oikos.engine.math.Vector2;

/**
 * Caméra qui suit une cible avec support du zoom.
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

    // Cible à suivre
    private Vector2 target;

    // === ZOOM ===
    private float zoom = 1.0f;          // 1.0 = normal, 0.5 = dézoomé 2x, 2.0 = zoomé 2x
    private float targetZoom = 1.0f;    // Pour une transition fluide
    private float zoomSpeed = 0.1f;     // Vitesse de transition
    private float minZoom = 0.1f;       // Dézoom max (voir toute la map)
    private float maxZoom = 2.0f;       // Zoom max

    // Mode vue globale (voir toute la map)
    private boolean fullMapView = false;

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
     * Met à jour la position de la caméra.
     */
    public void update() {
        // Transition fluide du zoom
        if (Math.abs(zoom - targetZoom) > 0.001f) {
            zoom += (targetZoom - zoom) * zoomSpeed;
        } else {
            zoom = targetZoom;
        }

        if (fullMapView) {
            // Mode vue globale : centrer sur la map
            position.x = 0;
            position.y = 0;
        } else if (target != null) {
            // Mode normal : suivre la cible
            float effectiveViewWidth = viewportWidth / zoom;
            float effectiveViewHeight = viewportHeight / zoom;

            float targetCamX = target.x - (effectiveViewWidth / 2f);
            float targetCamY = target.y - (effectiveViewHeight / 2f);

            // Clamp pour ne pas sortir des limites
            position.x = clamp(targetCamX, 0, Math.max(0, worldWidth - effectiveViewWidth));
            position.y = clamp(targetCamY, 0, Math.max(0, worldHeight - effectiveViewHeight));
        }
    }

    // === MÉTHODES DE ZOOM ===

    /**
     * Définit le niveau de zoom.
     * @param newZoom 1.0 = normal, < 1 = dézoom, > 1 = zoom
     */
    public void setZoom(float newZoom) {
        this.targetZoom = clamp(newZoom, minZoom, maxZoom);
    }

    /**
     * Zoom progressif (molette souris par exemple).
     */
    public void zoomIn(float amount) {
        setZoom(targetZoom + amount);
    }

    public void zoomOut(float amount) {
        setZoom(targetZoom - amount);
    }

    /**
     * Active/désactive la vue globale (voir toute la map).
     */
    public void toggleFullMapView() {
        fullMapView = !fullMapView;

        if (fullMapView) {
            // Calculer le zoom pour voir toute la map
            float zoomX = (float) viewportWidth / worldWidth;
            float zoomY = (float) viewportHeight / worldHeight;
            targetZoom = Math.min(zoomX, zoomY);
            System.out.println("[Camera] Vue globale activée (zoom: " + targetZoom + ")");
        } else {
            // Retour au zoom normal
            targetZoom = 1.0f;
            System.out.println("[Camera] Vue normale");
        }
    }

    /**
     * Active la vue globale.
     */
    public void setFullMapView(boolean enabled) {
        if (enabled != fullMapView) {
            toggleFullMapView();
        }
    }

    /**
     * Retourne le zoom actuel.
     */
    public float getZoom() {
        return zoom;
    }

    /**
     * Est-ce qu'on est en vue globale ?
     */
    public boolean isFullMapView() {
        return fullMapView;
    }

    // === OFFSETS AVEC ZOOM ===

    public int getOffsetX() {
        return (int) position.x;
    }

    public int getOffsetY() {
        return (int) position.y;
    }

    /**
     * Convertit une position monde en position écran (avec zoom).
     */
    public int worldToScreenX(float worldX) {
        return (int) ((worldX - position.x) * zoom);
    }

    public int worldToScreenY(float worldY) {
        return (int) ((worldY - position.y) * zoom);
    }

    /**
     * Convertit une position écran en position monde (avec zoom).
     */
    public float screenToWorldX(int screenX) {
        return screenX / zoom + position.x;
    }

    public float screenToWorldY(int screenY) {
        return screenY / zoom + position.y;
    }

    /**
     * Retourne la taille d'affichage d'un élément (avec zoom).
     */
    public int scaleToScreen(int worldSize) {
        return (int) (worldSize * zoom);
    }

    /**
     * Vérifie si un rectangle est visible (avec zoom).
     */
    public boolean isVisible(float x, float y, int width, int height) {
        float screenX = (x - position.x) * zoom;
        float screenY = (y - position.y) * zoom;
        float screenW = width * zoom;
        float screenH = height * zoom;

        return screenX + screenW > 0
                && screenX < viewportWidth
                && screenY + screenH > 0
                && screenY < viewportHeight;
    }

    public void setWorldSize(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    private float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}