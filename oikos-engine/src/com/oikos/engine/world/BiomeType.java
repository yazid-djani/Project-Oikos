package com.oikos.engine.world;

/**
 * Interface pour définir les types de terrain.
 * Chaque jeu implémente ses propres types.
 */

public interface BiomeType {

    /**
     * Identifiant unique du biome.
     */
    int getId();

    /**
     * Nom du biome.
     */
    String getName();

    /**
     * Si true, bloque le passage.
     */
    boolean isSolid();

    /**
     * Hauteur minimale pour ce biome (0.0 à 1.0).
     * Utilisé par le générateur de terrain.
     */
    float getMinHeight();

    /**
     * Hauteur maximale pour ce biome (0.0 à 1.0).
     */
    float getMaxHeight();
}