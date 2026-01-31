package com.oikos.game.world;

import com.oikos.engine.graphics.AssetManager;

/**
 * Charge les sprites des personnages.
 */
public class CharacterAssetManager {

    private AssetManager assets;

    public CharacterAssetManager(AssetManager assets) {
        this.assets = assets;
    }

    /**
     * Charge tous les sprites de personnages.
     */
    public void loadAllCharacters() {
        System.out.println("[CharacterAssets] Chargement des personnages...");

        loadAnimaux();
        loadBucheron();
        loadFermiere();
        loadEnfants();

        System.out.println("[CharacterAssets] Personnages chargés.");
    }

    private void loadAnimaux() {
        // Loup
        assets.loadSprite("loup_droit", "characters/animaux/loup_droit.png", false);
        assets.loadSprite("loup_gauche", "characters/animaux/loup_gauche.png", false);

        // Mouton
        assets.loadSprite("mouton_droit", "characters/animaux/mouton_droit.png", false);
        assets.loadSprite("mouton_gauche", "characters/animaux/mouton_gauche.png", false);
    }

    private void loadBucheron() {
        String[] dirs = {"dos", "droit", "face", "gauche"};

        for (String dir : dirs) {
            assets.loadSprite("bucheron_" + dir, "characters/bucheron/bucheron_" + dir + ".png", false);
            assets.loadSprite("bucheron_" + dir + "_mov1", "characters/bucheron/bucheron_" + dir + "_mov1.png", false);
            assets.loadSprite("bucheron_" + dir + "_mov2", "characters/bucheron/bucheron_" + dir + "_mov2.png", false);
        }
    }

    private void loadFermiere() {
        String[] dirs = {"dos", "droit", "face", "gauche"};

        for (String dir : dirs) {
            assets.loadSprite("fermiere_" + dir, "characters/fermiere/fermiere_" + dir + ".png", false);
            assets.loadSprite("fermiere_" + dir + "_mov1", "characters/fermiere/fermiere_" + dir + "_mov1.png", false);
            assets.loadSprite("fermiere_" + dir + "_mov2", "characters/fermiere/fermiere_" + dir + "_mov2.png", false);
        }
    }

    private void loadEnfants() {
        String[] enfants = {"bilal", "clara", "justine", "louis", "rose", "theaud"};
        String[] dirs = {"dos", "droit", "face", "gauche"};

        for (String enfant : enfants) {
            for (String dir : dirs) {
                String baseName = enfant + "_" + dir;
                String basePath = "characters/enfant/" + enfant + "/" + enfant + "_" + dir;

                assets.loadSprite(baseName, basePath + ".png", false);
                assets.loadSprite(baseName + "_mov1", basePath + "_mov1.png", false);
                assets.loadSprite(baseName + "_mov2", basePath + "_mov2.png", false);
            }
        }
    }
}