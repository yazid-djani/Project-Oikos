package com.oikos.game.world;

import com.oikos.engine.graphics.AssetManager;

/**
 * Gère le chargement et la sélection des tiles auto (bordures, coins).
 * Adapté à la structure : oikos-game/resources/environment/decors/
 */
public class AutoTileManager {

    private AssetManager assets;
    private String basePath;

    public AutoTileManager(AssetManager assets, String basePath) {
        this.assets = assets;
        this.basePath = basePath;

        // Debug : lister les assets disponibles
        System.out.println("[AutoTileManager] Recherche des assets...");
        assets.listAvailableAssets("oikos-game/resources/environment");
    }

    /**
     * Charge tous les sprites de tiles.
     */
    public void loadAllTiles() {
        System.out.println("[AutoTileManager] Chargement des tiles...");
        loadGrass();
        loadWater();
        loadRock();
        System.out.println("[AutoTileManager] " + assets.size() + " sprites chargés.");
    }

    private void loadGrass() {
        // Jour - essayer plusieurs chemins
        loadTile("grass_day", "decors/jour/grass.png", false);
        // Nuit
        loadTile("grass_night", "decors/nuit/grassN.png", false);
    }

    private void loadWater() {
        // === JOUR ===
        loadTile("water_center_day", "decors/jour/eau/_wD.png", true);
        loadTile("water_top_day", "decors/jour/eau/_wD_h.png", true);
        loadTile("water_bottom_day", "decors/jour/eau/_wD_b.png", true);
        loadTile("water_left_day", "decors/jour/eau/_wD_g.png", true);
        loadTile("water_right_day", "decors/jour/eau/_wD_d.png", true);
        loadTile("water_top_left_day", "decors/jour/eau/_wD_hg.png", true);
        loadTile("water_top_right_day", "decors/jour/eau/_wD_hd.png", true);
        loadTile("water_bottom_left_day", "decors/jour/eau/_wD_bg.png", true);
        loadTile("water_bottom_right_day", "decors/jour/eau/_wD_bd.png", true);

        // === NUIT ===
        loadTile("water_center_night", "decors/nuit/eau/_wN.png", true);
        loadTile("water_top_night", "decors/nuit/eau/_wN_h.png", true);
        loadTile("water_bottom_night", "decors/nuit/eau/_wN_b.png", true);
        loadTile("water_left_night", "decors/nuit/eau/_wN_g.png", true);
        loadTile("water_right_night", "decors/nuit/eau/_wN_d.png", true);
        loadTile("water_top_left_night", "decors/nuit/eau/_wN_hg.png", true);
        loadTile("water_top_right_night", "decors/nuit/eau/_wN_hd.png", true);
        loadTile("water_bottom_left_night", "decors/nuit/eau/_wN_bg.png", true);
        loadTile("water_bottom_right_night", "decors/nuit/eau/_wN_bd.png", true);
    }

    private void loadRock() {
        // === JOUR ===
        loadTile("rock_center_day", "decors/jour/hauteur/_hDB.png", true);
        loadTile("rock_left_day", "decors/jour/hauteur/_hDG.png", true);
        loadTile("rock_right_day", "decors/jour/hauteur/_hDD.png", true);
        loadTile("rock_bottom_right_day", "decors/jour/hauteur/_hDBD.png", true);
        loadTile("rock_top_left_day", "decors/jour/hauteur/_hDGH.png", true);

        // === NUIT ===
        loadTile("rock_center_night", "decors/nuit/hauteur/_hNB.png", true);
        loadTile("rock_left_night", "decors/nuit/hauteur/_hNG.png", true);
        loadTile("rock_right_night", "decors/nuit/hauteur/_hND.png", true);
        loadTile("rock_bottom_right_night", "decors/nuit/hauteur/_hNBD.png", true);
        loadTile("rock_top_left_night", "decors/nuit/hauteur/_hNGH.png", true);
    }

    /**
     * Charge une tile en essayant plusieurs chemins.
     */
    private void loadTile(String key, String relativePath, boolean solid) {
        // Chemins à essayer
        String[] pathsToTry = {
                basePath + "/" + relativePath,                    // /environment/decors/jour/...
                "/environment/" + relativePath,                   // Classpath
                "environment/" + relativePath,                    // Sans slash
                relativePath                                       // Chemin direct
        };

        for (String path : pathsToTry) {
            if (assets.loadSprite(key, path, solid) != null) {
                return; // Succès
            }
        }
    }

    /**
     * Sélectionne le bon sprite d'eau en fonction des voisins.
     */
    public String getWaterSpriteKey(boolean[] neighbors, boolean isNight) {
        boolean top = neighbors[0];
        boolean right = neighbors[1];
        boolean bottom = neighbors[2];
        boolean left = neighbors[3];

        String suffix = isNight ? "_night" : "_day";

        // Tous les côtés = centre
        if (top && right && bottom && left) {
            return "water_center" + suffix;
        }

        // Bords simples
        if (!top && right && bottom && left) return "water_top" + suffix;
        if (top && right && !bottom && left) return "water_bottom" + suffix;
        if (top && right && bottom && !left) return "water_left" + suffix;
        if (top && !right && bottom && left) return "water_right" + suffix;

        // Coins extérieurs
        if (!top && !left && right && bottom) return "water_top_left" + suffix;
        if (!top && !right && left && bottom) return "water_top_right" + suffix;
        if (!bottom && !left && right && top) return "water_bottom_left" + suffix;
        if (!bottom && !right && left && top) return "water_bottom_right" + suffix;

        // Par défaut : centre
        return "water_center" + suffix;
    }

    /**
     * Sélectionne le bon sprite de roche/hauteur en fonction des voisins.
     */
    public String getRockSpriteKey(boolean[] neighbors, boolean isNight) {
        boolean top = neighbors[0];
        boolean right = neighbors[1];
        boolean bottom = neighbors[2];
        boolean left = neighbors[3];

        String suffix = isNight ? "_night" : "_day";

        if (left && !right && !top && bottom) return "rock_top_left" + suffix;
        if (!left && right && top && !bottom) return "rock_bottom_right" + suffix;
        if (!left && right && !top && bottom) return "rock_left" + suffix;
        if (left && !right && top && !bottom) return "rock_right" + suffix;

        return "rock_center" + suffix;
    }

    /**
     * Récupère le sprite simple pour les types sans auto-tiling.
     */
    public String getSimpleSpriteKey(TileType type, boolean isNight) {
        String suffix = isNight ? "_night" : "_day";

        switch (type) {
            case GRASS: return "grass" + suffix;
            case SAND: return "sand" + suffix;
            case PATH: return "path" + suffix;
            case FOREST: return "forest" + suffix;
            default: return "grass" + suffix;
        }
    }
}