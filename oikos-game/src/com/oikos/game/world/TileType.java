package com.oikos.game.world;

import com.oikos.engine.world.BiomeType;

/**
 * Types de tiles pour Oikos.
 * Adapté à la structure d'assets existante.
 */
public enum TileType implements BiomeType {

    // Types de base (sans auto-tiling)
    GRASS  (0, "grass",  false, 0.42f, 0.70f),
    ROCK   (1, "rock",   true,  0.85f, 1.0f),   // Hauteur/falaise
    WATER  (2, "water",  true,  0.0f,  0.35f),
    FOREST (3, "forest", true,  0.50f, 0.75f),  // Arbres (à ajouter)
    SAND   (4, "sand",   false, 0.35f, 0.42f),
    PATH   (5, "path",   false, 0.0f,  0.0f);

    private final int id;
    private final String name;
    private final boolean solid;
    private final float minHeight;
    private final float maxHeight;

    TileType(int id, String name, boolean solid, float minHeight, float maxHeight) {
        this.id = id;
        this.name = name;
        this.solid = solid;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override public int getId() { return id; }
    @Override public String getName() { return name; }
    @Override public boolean isSolid() { return solid; }
    @Override public float getMinHeight() { return minHeight; }
    @Override public float getMaxHeight() { return maxHeight; }

    public static TileType fromId(int id) {
        for (TileType type : values()) {
            if (type.id == id) return type;
        }
        return GRASS;
    }
}