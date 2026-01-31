package com.oikos.engine.physics;

import com.oikos.engine.math.Vector2;

/**
 * Système de détection de collisions générique.
 * Gère les collisions avec les tiles et entre GameObjects.
 */
public class CollisionSystem {

    // Référence à la map (tableau de tiles solides)
    private boolean[][] solidTiles;

    // Taille d'une tile en pixels
    private int tileSize;

    // Dimensions de la map en tiles
    private int mapWidth;
    private int mapHeight;

    public CollisionSystem(int tileSize) {
        this.tileSize = tileSize;
        this.solidTiles = null;
    }

    /**
     * Charge la carte des collisions depuis les données de la map.
     * @param solidMap Tableau 2D : true = solide, false = traversable
     */
    public void loadCollisionMap(boolean[][] solidMap) {
        this.solidTiles = solidMap;
        this.mapWidth = solidMap.length;
        this.mapHeight = solidMap[0].length;
    }

    /**
     * Vérifie si un déplacement est valide (pas de collision avec les tiles).
     * @param hitbox La hitbox de l'objet
     * @param currentPos Position actuelle
     * @param deltaX Déplacement horizontal souhaité
     * @param deltaY Déplacement vertical souhaité
     * @return true si le mouvement est autorisé
     */
    public boolean canMove(Hitbox hitbox, Vector2 currentPos, float deltaX, float deltaY) {
        if (solidTiles == null) return true;

        // Position future
        Vector2 futurePos = new Vector2(currentPos.x + deltaX, currentPos.y + deltaY);

        // On vérifie les 4 coins de la hitbox future
        float left = hitbox.getLeft(futurePos);
        float right = hitbox.getRight(futurePos);
        float top = hitbox.getTop(futurePos);
        float bottom = hitbox.getBottom(futurePos);

        // Vérifier chaque coin
        if (isTileSolid(left, top)) return false;
        if (isTileSolid(right - 1, top)) return false;
        if (isTileSolid(left, bottom - 1)) return false;
        if (isTileSolid(right - 1, bottom - 1)) return false;

        return true;
    }

    /**
     * Vérifie si on peut bouger horizontalement.
     */
    public boolean canMoveX(Hitbox hitbox, Vector2 currentPos, float deltaX) {
        return canMove(hitbox, currentPos, deltaX, 0);
    }

    /**
     * Vérifie si on peut bouger verticalement.
     */
    public boolean canMoveY(Hitbox hitbox, Vector2 currentPos, float deltaY) {
        return canMove(hitbox, currentPos, 0, deltaY);
    }

    /**
     * Vérifie si une position (en pixels) est sur une tile solide.
     */
    public boolean isTileSolid(float pixelX, float pixelY) {
        int tileX = (int) (pixelX / tileSize);
        int tileY = (int) (pixelY / tileSize);

        // Hors limites = solide (on ne peut pas sortir de la map)
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return true;
        }

        return solidTiles[tileX][tileY];
    }

    /**
     * Vérifie la collision entre deux hitbox.
     */
    public boolean checkCollision(Hitbox a, Vector2 posA, Hitbox b, Vector2 posB) {
        return a.intersects(b, posA, posB);
    }

    /**
     * Retourne la tile à une position donnée.
     */
    public int getTileAt(float pixelX, float pixelY) {
        int tileX = (int) (pixelX / tileSize);
        int tileY = (int) (pixelY / tileSize);

        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return -1;
        }

        return solidTiles[tileX][tileY] ? 1 : 0;
    }
}