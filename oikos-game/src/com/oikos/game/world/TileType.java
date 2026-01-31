package com.oikos.game.world;

import com.oikos.engine.world.BiomeType;

/**
 * Types de tiles pour Oikos.
 * Les IDs DOIVENT correspondre à ceux du MapGenerator.generateSimpleMap() :
 * 0=eau, 1=sable, 2=herbe, 3=forêt, 4=roche
 */
public enum TileType implements BiomeType {

    // ⚠️ IDs corrigés pour correspondre au générateur
    WATER  (0, "water",  true,  0.0f,  0.35f),
    SAND   (1, "sand",   false, 0.35f, 0.42f),
    GRASS  (2, "grass",  false, 0.42f, 0.70f),
    FOREST (3, "forest", true,  0.50f, 0.75f),
    ROCK   (4, "rock",   true,  0.85f, 1.0f),
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
        return GRASS; // Par défaut
    }
}