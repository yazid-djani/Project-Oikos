package com.oikos.game.world;

import com.oikos.engine.graphics.AssetManager;
import com.oikos.engine.graphics.Sprite;

/**
 * Gère le chargement et la sélection des tiles auto (bordures, coins).
 * Adapté à la structure d'assets existante : decors/jour/ et decors/nuit/
 */
public class AutoTileManager {

    private AssetManager assets;
    private String basePath;

    // Clés pour les sprites d'eau (jour)
    private static final String[] WATER_KEYS_DAY = {
            "water_center",      // 0 - _wD.png
            "water_top",         // 1 - _wD_h.png
            "water_bottom",      // 2 - _wD_b.png
            "water_left",        // 3 - _wD_g.png
            "water_right",       // 4 - _wD_d.png
            "water_top_left",    // 5 - _wD_hg.png
            "water_top_right",   // 6 - _wD_hd.png
            "water_bottom_left", // 7 - _wD_bg.png
            "water_bottom_right" // 8 - _wD_bd.png
            // ... ajouter les autres variantes selon tes fichiers
    };

    public AutoTileManager(AssetManager assets, String basePath) {
        this.assets = assets;
        this.basePath = basePath; // ex: "/environment/decors"
    }

    /**
     * Charge tous les sprites de tiles.
     */
    public void loadAllTiles() {
        loadGrass();
        loadWater();
        loadRock();
    }

    private void loadGrass() {
        // Jour
        assets.loadSprite("grass_day", basePath + "/jour/grass.png", false);
        // Nuit
        assets.loadSprite("grass_night", basePath + "/nuit/grassN.png", false);
    }

    private void loadWater() {
        // === JOUR ===
        assets.loadSprite("water_center_day", basePath + "/jour/eau/_wD.png", true);
        assets.loadSprite("water_top_day", basePath + "/jour/eau/_wD_h.png", true);
        assets.loadSprite("water_bottom_day", basePath + "/jour/eau/_wD_b.png", true);
        assets.loadSprite("water_left_day", basePath + "/jour/eau/_wD_g.png", true);
        assets.loadSprite("water_right_day", basePath + "/jour/eau/_wD_d.png", true);
        assets.loadSprite("water_top_left_day", basePath + "/jour/eau/_wD_hg.png", true);
        assets.loadSprite("water_top_right_day", basePath + "/jour/eau/_wD_hd.png", true);
        assets.loadSprite("water_bottom_left_day", basePath + "/jour/eau/_wD_bg.png", true);
        assets.loadSprite("water_bottom_right_day", basePath + "/jour/eau/_wD_bd.png", true);
        // Coins intérieurs
        assets.loadSprite("water_corner_tl_day", basePath + "/jour/eau/_wD_c1h.png", true);
        assets.loadSprite("water_corner_tr_day", basePath + "/jour/eau/_wD_c1d.png", true);
        assets.loadSprite("water_corner_bl_day", basePath + "/jour/eau/_wD_c1g.png", true);
        assets.loadSprite("water_corner_br_day", basePath + "/jour/eau/_wD_c1b.png", true);
        // Centre variantes
        assets.loadSprite("water_c0_day", basePath + "/jour/eau/_wD_c0.png", true);
        assets.loadSprite("water_c1_day", basePath + "/jour/eau/_wD_c1.png", true);
        assets.loadSprite("water_cb_day", basePath + "/jour/eau/_wD_cb.png", true);
        assets.loadSprite("water_cbd_day", basePath + "/jour/eau/_wD_cbd.png", true);
        assets.loadSprite("water_cbg_day", basePath + "/jour/eau/_wD_cbg.png", true);

        // === NUIT ===
        assets.loadSprite("water_center_night", basePath + "/nuit/eau/_wN.png", true);
        assets.loadSprite("water_top_night", basePath + "/nuit/eau/_wN_h.png", true);
        assets.loadSprite("water_bottom_night", basePath + "/nuit/eau/_wN_b.png", true);
        assets.loadSprite("water_left_night", basePath + "/nuit/eau/_wN_g.png", true);
        assets.loadSprite("water_right_night", basePath + "/nuit/eau/_wN_d.png", true);
        assets.loadSprite("water_top_left_night", basePath + "/nuit/eau/_wN_hg.png", true);
        assets.loadSprite("water_top_right_night", basePath + "/nuit/eau/_wN_hd.png", true);
        assets.loadSprite("water_bottom_left_night", basePath + "/nuit/eau/_wN_bg.png", true);
        assets.loadSprite("water_bottom_right_night", basePath + "/nuit/eau/_wN_bd.png", true);
        // Coins intérieurs
        assets.loadSprite("water_corner_tl_night", basePath + "/nuit/eau/_wN_c1h.png", true);
        assets.loadSprite("water_corner_tr_night", basePath + "/nuit/eau/_wN_c1d.png", true);
        assets.loadSprite("water_corner_bl_night", basePath + "/nuit/eau/_wN_c1g.png", true);
        assets.loadSprite("water_corner_br_night", basePath + "/nuit/eau/_wN_c1b.png", true);
        // Centre variantes
        assets.loadSprite("water_c0_night", basePath + "/nuit/eau/_wN_c0.png", true);
        assets.loadSprite("water_c1_night", basePath + "/nuit/eau/_wN_c1.png", true);
        assets.loadSprite("water_cb_night", basePath + "/nuit/eau/_wN_cb.png", true);
        assets.loadSprite("water_cbd_night", basePath + "/nuit/eau/_wN_cbd.png", true);
        assets.loadSprite("water_cbg_night", basePath + "/nuit/eau/_wN_cbg.png", true);
    }

    private void loadRock() {
        // === JOUR (hauteur) ===
        assets.loadSprite("rock_center_day", basePath + "/jour/hauteur/_hDB.png", true);
        assets.loadSprite("rock_left_day", basePath + "/jour/hauteur/_hDG.png", true);
        assets.loadSprite("rock_right_day", basePath + "/jour/hauteur/_hDD.png", true);
        assets.loadSprite("rock_bottom_right_day", basePath + "/jour/hauteur/_hDBD.png", true);
        assets.loadSprite("rock_top_left_day", basePath + "/jour/hauteur/_hDGH.png", true);

        // === NUIT (hauteur) ===
        assets.loadSprite("rock_center_night", basePath + "/nuit/hauteur/_hNB.png", true);
        assets.loadSprite("rock_left_night", basePath + "/nuit/hauteur/_hNG.png", true);
        assets.loadSprite("rock_right_night", basePath + "/nuit/hauteur/_hND.png", true);
        assets.loadSprite("rock_bottom_right_night", basePath + "/nuit/hauteur/_hNBD.png", true);
        assets.loadSprite("rock_top_left_night", basePath + "/nuit/hauteur/_hNGH.png", true);
    }

    /**
     * Sélectionne le bon sprite d'eau en fonction des voisins.
     * @param neighbors Tableau de 4 booleans [haut, droite, bas, gauche] - true si c'est aussi de l'eau
     * @param isNight Mode nuit ?
     * @return La clé du sprite à utiliser
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

        // Logique similaire à l'eau
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