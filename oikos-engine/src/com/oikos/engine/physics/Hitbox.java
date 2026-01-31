package com.oikos.engine.physics;

import com.oikos.engine.math.Vector2;

/**
 * Rectangle de collision attaché à un GameObject.
 * Offset par rapport à la position de l'objet.
 */
public class Hitbox {

    // Offset par rapport à la position du parent
    public float offsetX;
    public float offsetY;

    // Dimensions de la hitbox
    public float width;
    public float height;

    public Hitbox(float offsetX, float offsetY, float width, float height) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
    }

    /**
     * Crée une hitbox qui couvre tout l'objet.
     */
    public Hitbox(float width, float height) {
        this(0, 0, width, height);
    }

    /**
     * Calcule les coordonnées absolues de la hitbox.
     */
    public float getLeft(Vector2 parentPos) {
        return parentPos.x + offsetX;
    }

    public float getRight(Vector2 parentPos) {
        return parentPos.x + offsetX + width;
    }

    public float getTop(Vector2 parentPos) {
        return parentPos.y + offsetY;
    }

    public float getBottom(Vector2 parentPos) {
        return parentPos.y + offsetY + height;
    }

    /**
     * Vérifie si cette hitbox chevauche une autre (AABB).
     */
    public boolean intersects(Hitbox other, Vector2 myPos, Vector2 otherPos) {
        float myLeft = getLeft(myPos);
        float myRight = getRight(myPos);
        float myTop = getTop(myPos);
        float myBottom = getBottom(myPos);

        float otherLeft = other.getLeft(otherPos);
        float otherRight = other.getRight(otherPos);
        float otherTop = other.getTop(otherPos);
        float otherBottom = other.getBottom(otherPos);

        return myLeft < otherRight
                && myRight > otherLeft
                && myTop < otherBottom
                && myBottom > otherTop;
    }

    /**
     * Vérifie si un point est dans la hitbox.
     */
    public boolean contains(Vector2 parentPos, float pointX, float pointY) {
        return pointX >= getLeft(parentPos)
                && pointX <= getRight(parentPos)
                && pointY >= getTop(parentPos)
                && pointY <= getBottom(parentPos);
    }

    @Override
    public String toString() {
        return "Hitbox[offset=(" + offsetX + "," + offsetY + "), size=(" + width + "x" + height + ")]";
    }
}