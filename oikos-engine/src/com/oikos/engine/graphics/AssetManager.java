package com.oikos.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire centralisé des assets (images).
 * Charge une seule fois, réutilise partout.
 */
public class AssetManager {

    // Cache des sprites par clé
    private Map<String, Sprite> spriteCache;

    // Classe de référence pour le chargement des ressources
    private Class<?> resourceClass;

    public AssetManager(Class<?> resourceClass) {
        this.spriteCache = new HashMap<>();
        this.resourceClass = resourceClass;
    }

    /**
     * Charge une image depuis le classpath et la met en cache.
     * @param key Identifiant unique (ex: "player_idle")
     * @param path Chemin relatif dans resources (ex: "/sprites/player.png")
     * @param solid Si true, l'objet bloque les collisions
     * @return Le Sprite chargé
     */
    public Sprite loadSprite(String key, String path, boolean solid) {
        // Si déjà en cache, on retourne directement
        if (spriteCache.containsKey(key)) {
            return spriteCache.get(key);
        }

        try {
            InputStream is = resourceClass.getResourceAsStream(path);
            if (is == null) {
                System.err.println("[AssetManager] Fichier introuvable : " + path);
                return null;
            }

            BufferedImage image = ImageIO.read(is);
            Sprite sprite = new Sprite(image, key, solid);
            spriteCache.put(key, sprite);

            System.out.println("[AssetManager] Chargé : " + key + " (" + path + ")");
            return sprite;

        } catch (IOException e) {
            System.err.println("[AssetManager] Erreur chargement : " + path);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Charge une image sans collision (raccourci).
     */
    public Sprite loadSprite(String key, String path) {
        return loadSprite(key, path, false);
    }

    /**
     * Récupère un sprite déjà chargé.
     */
    public Sprite getSprite(String key) {
        return spriteCache.get(key);
    }

    /**
     * Vérifie si un sprite existe dans le cache.
     */
    public boolean hasSprite(String key) {
        return spriteCache.containsKey(key);
    }

    /**
     * Charge un spritesheet et le découpe en plusieurs sprites.
     * @param baseName Préfixe des clés (ex: "player" -> "player_0", "player_1", ...)
     * @param path Chemin du spritesheet
     * @param frameWidth Largeur d'une frame
     * @param frameHeight Hauteur d'une frame
     * @param count Nombre de frames à extraire
     */
    public void loadSpritesheet(String baseName, String path, int frameWidth, int frameHeight, int count) {
        try {
            InputStream is = resourceClass.getResourceAsStream(path);
            if (is == null) {
                System.err.println("[AssetManager] Spritesheet introuvable : " + path);
                return;
            }

            BufferedImage sheet = ImageIO.read(is);
            int cols = sheet.getWidth() / frameWidth;

            for (int i = 0; i < count; i++) {
                int x = (i % cols) * frameWidth;
                int y = (i / cols) * frameHeight;

                BufferedImage frame = sheet.getSubimage(x, y, frameWidth, frameHeight);
                String key = baseName + "_" + i;
                spriteCache.put(key, new Sprite(frame, key, false));
            }

            System.out.println("[AssetManager] Spritesheet chargé : " + baseName + " (" + count + " frames)");

        } catch (IOException e) {
            System.err.println("[AssetManager] Erreur spritesheet : " + path);
            e.printStackTrace();
        }
    }

    /**
     * Vide le cache (utile pour changer de niveau).
     */
    public void clear() {
        spriteCache.clear();
        System.out.println("[AssetManager] Cache vidé.");
    }

    /**
     * Retourne le nombre d'assets chargés.
     */
    public int size() {
        return spriteCache.size();
    }
}