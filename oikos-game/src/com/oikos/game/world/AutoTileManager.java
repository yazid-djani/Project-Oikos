package com.oikos.game.world;

import com.oikos.engine.graphics.AssetManager;

/**
 * Gère le chargement des tiles.
 * Structure : oikos-game/resources/environment/day/ et night/
 */
public class AutoTileManager {

    private AssetManager assets;

    public AutoTileManager(AssetManager assets, String basePath) {
        this.assets = assets;
    }

    /**
     * Charge tous les sprites de tiles.
     */
    public void loadAllTiles() {
        System.out.println("[AutoTileManager] Chargement des tiles...");

        loadGrass();
        loadWater();
        loadRock();
        loadTrees();
        loadDecorations();

        System.out.println("[AutoTileManager] Tiles chargées : " + assets.size());
    }

    // === HERBE ===
    private void loadGrass() {
        // Jour
        assets.loadSprite("grass_day", "environment/day/grass.png", false);
        assets.loadSprite("herbe1_day", "environment/day/herbe1.png", false);
        assets.loadSprite("herbe2_day", "environment/day/herbe2.png", false);

        // Nuit
        assets.loadSprite("grass_night", "environment/night/grassN.png", false);
        assets.loadSprite("herbe1_night", "environment/night/herbe1.png", false);
        assets.loadSprite("herbe2_night", "environment/night/herbe2.png", false);
    }

    // === EAU ===
    private void loadWater() {
        // === JOUR ===
        assets.loadSprite("water_center_day", "environment/day/eau/_wD.png", true);
        assets.loadSprite("water_top_day", "environment/day/eau/_wD_h.png", true);
        assets.loadSprite("water_bottom_day", "environment/day/eau/_wD_b.png", true);
        assets.loadSprite("water_left_day", "environment/day/eau/_wD_g.png", true);
        assets.loadSprite("water_right_day", "environment/day/eau/_wD_d.png", true);
        assets.loadSprite("water_top_left_day", "environment/day/eau/_wD_hg.png", true);
        assets.loadSprite("water_top_right_day", "environment/day/eau/_wD_hd.png", true);
        assets.loadSprite("water_bottom_left_day", "environment/day/eau/_wD_bg.png", true);
        assets.loadSprite("water_bottom_right_day", "environment/day/eau/_wD_bd.png", true);

        // Coins intérieurs (eau entourée de terre)
        assets.loadSprite("water_c0_day", "environment/day/eau/_wD_c0.png", true);
        assets.loadSprite("water_c1_day", "environment/day/eau/_wD_c1.png", true);
        assets.loadSprite("water_c1b_day", "environment/day/eau/_wD_c1b.png", true);
        assets.loadSprite("water_c1d_day", "environment/day/eau/_wD_c1d.png", true);
        assets.loadSprite("water_c1g_day", "environment/day/eau/_wD_c1g.png", true);
        assets.loadSprite("water_c1h_day", "environment/day/eau/_wD_c1h.png", true);
        assets.loadSprite("water_cb_day", "environment/day/eau/_wD_cb.png", true);
        assets.loadSprite("water_cbd_day", "environment/day/eau/_wD_cbd.png", true);
        assets.loadSprite("water_cbg_day", "environment/day/eau/_wD_cbg.png", true);

        // === NUIT ===
        assets.loadSprite("water_center_night", "environment/night/eau/_wN.png", true);
        assets.loadSprite("water_top_night", "environment/night/eau/_wN_h.png", true);
        assets.loadSprite("water_bottom_night", "environment/night/eau/_wN_b.png", true);
        assets.loadSprite("water_left_night", "environment/night/eau/_wN_g.png", true);
        assets.loadSprite("water_right_night", "environment/night/eau/_wN_d.png", true);
        assets.loadSprite("water_top_left_night", "environment/night/eau/_wN_hg.png", true);
        assets.loadSprite("water_top_right_night", "environment/night/eau/_wN_hd.png", true);
        assets.loadSprite("water_bottom_left_night", "environment/night/eau/_wN_bg.png", true);
        assets.loadSprite("water_bottom_right_night", "environment/night/eau/_wN_bd.png", true);

        // Coins intérieurs
        assets.loadSprite("water_c0_night", "environment/night/eau/_wN_c0.png", true);
        assets.loadSprite("water_c1_night", "environment/night/eau/_wN_c1.png", true);
        assets.loadSprite("water_c1b_night", "environment/night/eau/_wN_c1b.png", true);
        assets.loadSprite("water_c1d_night", "environment/night/eau/_wN_c1d.png", true);
        assets.loadSprite("water_c1g_night", "environment/night/eau/_wN_c1g.png", true);
        assets.loadSprite("water_c1h_night", "environment/night/eau/_wN_c1h.png", true);
        assets.loadSprite("water_cb_night", "environment/night/eau/_wN_cb.png", true);
        assets.loadSprite("water_cbd_night", "environment/night/eau/_wN_cbd.png", true);
        assets.loadSprite("water_cbg_night", "environment/night/eau/_wN_cbg.png", true);
    }

    // === HAUTEUR / ROCHERS ===
    private void loadRock() {
        // === JOUR ===
        assets.loadSprite("rock_center_day", "environment/day/hauteur/_hDB.png", true);
        assets.loadSprite("rock_left_day", "environment/day/hauteur/_hDG.png", true);
        assets.loadSprite("rock_right_day", "environment/day/hauteur/_hDD.png", true);
        assets.loadSprite("rock_top_left_day", "environment/day/hauteur/_hDGH.png", true);
        assets.loadSprite("rock_bottom_right_day", "environment/day/hauteur/_hDBD.png", true);

        // === NUIT ===
        assets.loadSprite("rock_center_night", "environment/night/hauteur/_hNB.png", true);
        assets.loadSprite("rock_left_night", "environment/night/hauteur/_hNG.png", true);
        assets.loadSprite("rock_right_night", "environment/night/hauteur/_hND.png", true);
        assets.loadSprite("rock_top_left_night", "environment/night/hauteur/_hNGH.png", true);
        assets.loadSprite("rock_bottom_right_night", "environment/night/hauteur/_hNBD.png", true);
    }

    // === ARBRES ===
    private void loadTrees() {
        // === JOUR ===
        assets.loadSprite("tree1_day", "environment/day/arbre_taille1.png", true);
        assets.loadSprite("tree2_day", "environment/day/arbre_taille2.png", true);
        assets.loadSprite("tree3_day", "environment/day/arbre_taille3.png", true);
        assets.loadSprite("tree4_bottom_day", "environment/day/arbre_taille4_bas.png", true);
        assets.loadSprite("tree4_top_day", "environment/day/arbre_taille4_haut.png", false);

        // === NUIT ===
        assets.loadSprite("tree1_night", "environment/night/arbre_taille1.png", true);
        assets.loadSprite("tree2_night", "environment/night/arbre_taille2.png", true);
        assets.loadSprite("tree3_night", "environment/night/arbre_taille3.png", true);
        assets.loadSprite("tree4_bottom_night", "environment/night/arbre_taille4_bas.png", true);
        assets.loadSprite("tree4_top_night", "environment/night/arbre_taille4_haut.png", false);

        // === FEU (arbres en feu) ===
        assets.loadSprite("tree1_fire_a", "environment/fire/arbre_taille1a.png", true);
        assets.loadSprite("tree1_fire_b", "environment/fire/arbre_taille1b.png", true);
        assets.loadSprite("tree2_fire_a", "environment/fire/arbre_taille2a.png", true);
        assets.loadSprite("tree2_fire_b", "environment/fire/arbre_taille2b.png", true);
        assets.loadSprite("tree3_fire_a", "environment/fire/arbre_taille3a.png", true);
        assets.loadSprite("tree3_fire_b", "environment/fire/arbre_taille3b.png", true);
        assets.loadSprite("cendre", "environment/fire/cendre.png", false);
    }

    // === DÉCORATIONS ===
    private void loadDecorations() {
        // Jour
        assets.loadSprite("log_day", "environment/day/buche.png", true);
        assets.loadSprite("mushroom_day", "environment/day/champignon.png", false);
        assets.loadSprite("fdc_day", "environment/day/FDC.png", false);

        // Nuit
        assets.loadSprite("log_night", "environment/night/buche.png", true);
        assets.loadSprite("mushroom_night", "environment/night/champignon.png", false);
    }

    // === SÉLECTION DES SPRITES ===

    public String getWaterSpriteKey(boolean[] neighbors, boolean isNight) {
        boolean top = neighbors[0];
        boolean right = neighbors[1];
        boolean bottom = neighbors[2];
        boolean left = neighbors[3];

        String suffix = isNight ? "_night" : "_day";

        // Tous les côtés = centre (eau complète)
        if (top && right && bottom && left) {
            return "water_center" + suffix;
        }

        // Bords simples (un seul côté touche la terre)
        if (!top && right && bottom && left) return "water_top" + suffix;
        if (top && right && !bottom && left) return "water_bottom" + suffix;
        if (top && right && bottom && !left) return "water_left" + suffix;
        if (top && !right && bottom && left) return "water_right" + suffix;

        // Coins extérieurs (deux côtés touchent la terre)
        if (!top && !left && right && bottom) return "water_top_left" + suffix;
        if (!top && !right && left && bottom) return "water_top_right" + suffix;
        if (!bottom && !left && right && top) return "water_bottom_left" + suffix;
        if (!bottom && !right && left && top) return "water_bottom_right" + suffix;

        // Par défaut
        return "water_center" + suffix;
    }

    public String getRockSpriteKey(boolean[] neighbors, boolean isNight) {
        boolean top = neighbors[0];
        boolean right = neighbors[1];
        boolean bottom = neighbors[2];
        boolean left = neighbors[3];

        String suffix = isNight ? "_night" : "_day";

        // Coin haut-gauche
        if (!top && !left && right && bottom) return "rock_top_left" + suffix;
        if (!top && left && !right && bottom) return "rock_top_left" + suffix; // Miroir

        // Coin bas-droite
        if (top && !bottom && !right && left) return "rock_bottom_right" + suffix;
        if (top && !bottom && right && !left) return "rock_bottom_right" + suffix;

        // Bord gauche
        if (!left && right) return "rock_left" + suffix;

        // Bord droit
        if (left && !right) return "rock_right" + suffix;

        return "rock_center" + suffix;
    }

    public String getTreeSpriteKey(boolean isNight) {
        // Retourne un arbre aléatoire parmi les tailles 1, 2, 3
        int treeType = (int) (Math.random() * 3) + 1;
        String suffix = isNight ? "_night" : "_day";
        return "tree" + treeType + suffix;
    }

    public String getSimpleSpriteKey(TileType type, boolean isNight) {
        String suffix = isNight ? "_night" : "_day";

        switch (type) {
            case GRASS:  return "grass" + suffix;
            case SAND:   return "grass" + suffix; // Pas de sable, utiliser herbe
            case PATH:   return "grass" + suffix; // Pas de chemin, utiliser herbe
            case FOREST: return "tree1" + suffix; // Utiliser arbre taille 1
            default:     return "grass" + suffix;
        }
    }
}