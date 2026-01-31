package com.oikos.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire centralisé des assets (images).
 * Supporte le chargement depuis le classpath ET le système de fichiers.
 */
public class AssetManager {

    // Cache des sprites par clé
    private Map<String, Sprite> spriteCache;

    // Classe de référence pour le chargement des ressources
    private Class<?> resourceClass;

    // Chemin de base pour les ressources (système de fichiers)
    private String basePath;

    // Mode debug pour afficher les chemins testés
    private boolean debugMode = true;

    public AssetManager(Class<?> resourceClass) {
        this.spriteCache = new HashMap<>();
        this.resourceClass = resourceClass;
        this.basePath = findResourceBasePath();

        if (debugMode) {
            System.out.println("[AssetManager] Initialisé");
            System.out.println("[AssetManager] Chemin de base détecté : " + basePath);
        }
    }

    /**
     * Trouve le chemin de base des ressources.
     */
    private String findResourceBasePath() {
        // Liste des chemins possibles à tester
        String[] possiblePaths = {
                "oikos-game/resources",
                "resources",
                "../oikos-game/resources",
                "src/main/resources",
                "oikos-game/src/main/resources"
        };

        for (String path : possiblePaths) {
            File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                return dir.getAbsolutePath();
            }
        }

        // Par défaut, utiliser le répertoire courant
        return new File("").getAbsolutePath();
    }

    /**
     * Charge une image depuis le classpath ou le système de fichiers.
     * @param key Identifiant unique (ex: "player_idle")
     * @param path Chemin relatif (ex: "/environment/decors/jour/grass.png")
     * @param solid Si true, l'objet bloque les collisions
     * @return Le Sprite chargé, ou null si introuvable
     */
    public Sprite loadSprite(String key, String path, boolean solid) {
        // Si déjà en cache, on retourne directement
        if (spriteCache.containsKey(key)) {
            return spriteCache.get(key);
        }

        BufferedImage image = null;

        // 1. Essayer depuis le classpath
        image = loadFromClasspath(path);

        // 2. Si échoué, essayer depuis le système de fichiers
        if (image == null) {
            image = loadFromFileSystem(path);
        }

        // 3. Si toujours échoué, essayer avec des variantes de chemin
        if (image == null) {
            image = loadWithPathVariants(path);
        }

        if (image != null) {
            Sprite sprite = new Sprite(image, key, solid);
            spriteCache.put(key, sprite);
            if (debugMode) {
                System.out.println("[AssetManager] ✓ Chargé : " + key);
            }
            return sprite;
        }

        // Échec total
        if (debugMode) {
            System.err.println("[AssetManager] ✗ Introuvable : " + key + " (" + path + ")");
        }
        return null;
    }

    /**
     * Charge depuis le classpath (resources dans le JAR ou le classpath).
     */
    private BufferedImage loadFromClasspath(String path) {
        try {
            // Essayer plusieurs formats de chemin
            String[] pathsToTry = {
                    path,
                    path.startsWith("/") ? path : "/" + path,
                    path.startsWith("/") ? path.substring(1) : path
            };

            for (String p : pathsToTry) {
                InputStream is = resourceClass.getResourceAsStream(p);
                if (is != null) {
                    if (debugMode) {
                        System.out.println("[AssetManager] Trouvé dans classpath : " + p);
                    }
                    return ImageIO.read(is);
                }
            }
        } catch (IOException e) {
            // Ignorer, on essaiera le système de fichiers
        }
        return null;
    }

    /**
     * Charge depuis le système de fichiers.
     */
    private BufferedImage loadFromFileSystem(String path) {
        // Nettoyer le chemin
        String cleanPath = path.startsWith("/") ? path.substring(1) : path;

        // Chemins à tester
        String[] pathsToTry = {
                basePath + "/" + cleanPath,
                basePath + "/environment/" + cleanPath,
                "oikos-game/resources/" + cleanPath,
                "oikos-game/resources/environment/" + cleanPath,
                "resources/" + cleanPath,
                "resources/environment/" + cleanPath
        };

        for (String fullPath : pathsToTry) {
            File file = new File(fullPath);
            if (file.exists() && file.isFile()) {
                try {
                    if (debugMode) {
                        System.out.println("[AssetManager] Trouvé dans fichiers : " + fullPath);
                    }
                    return ImageIO.read(file);
                } catch (IOException e) {
                    // Continuer avec le prochain chemin
                }
            }
        }

        return null;
    }

    /**
     * Essaie différentes variantes du chemin.
     */
    private BufferedImage loadWithPathVariants(String path) {
        // Extraire le nom du fichier
        String fileName = path;
        if (path.contains("/")) {
            fileName = path.substring(path.lastIndexOf("/") + 1);
        }

        // Dossiers où chercher
        String[] folders = {
                "oikos-game/resources/environment/decors/jour",
                "oikos-game/resources/environment/decors/nuit",
                "oikos-game/resources/environment/decors/jour/eau",
                "oikos-game/resources/environment/decors/jour/hauteur",
                "oikos-game/resources/environment/decors/nuit/eau",
                "oikos-game/resources/environment/decors/nuit/hauteur",
                "oikos-game/resources/characters",
                "resources/environment/decors/jour",
                "resources/environment/decors/nuit"
        };

        for (String folder : folders) {
            File file = new File(folder + "/" + fileName);
            if (file.exists()) {
                try {
                    if (debugMode) {
                        System.out.println("[AssetManager] Trouvé avec recherche : " + file.getPath());
                    }
                    return ImageIO.read(file);
                } catch (IOException e) {
                    // Continuer
                }
            }
        }

        return null;
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
     */
    public void loadSpritesheet(String baseName, String path, int frameWidth, int frameHeight, int count) {
        BufferedImage sheet = null;

        // Essayer les différentes méthodes de chargement
        sheet = loadFromClasspath(path);
        if (sheet == null) {
            sheet = loadFromFileSystem(path);
        }
        if (sheet == null) {
            sheet = loadWithPathVariants(path);
        }

        if (sheet == null) {
            System.err.println("[AssetManager] Spritesheet introuvable : " + path);
            return;
        }

        int cols = sheet.getWidth() / frameWidth;

        for (int i = 0; i < count; i++) {
            int x = (i % cols) * frameWidth;
            int y = (i / cols) * frameHeight;

            BufferedImage frame = sheet.getSubimage(x, y, frameWidth, frameHeight);
            String key = baseName + "_" + i;
            spriteCache.put(key, new Sprite(frame, key, false));
        }

        System.out.println("[AssetManager] Spritesheet chargé : " + baseName + " (" + count + " frames)");
    }

    /**
     * Vide le cache.
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

    /**
     * Active/désactive le mode debug.
     */
    public void setDebugMode(boolean debug) {
        this.debugMode = debug;
    }

    /**
     * Définit le chemin de base manuellement.
     */
    public void setBasePath(String path) {
        this.basePath = path;
        if (debugMode) {
            System.out.println("[AssetManager] Chemin de base défini : " + basePath);
        }
    }

    /**
     * Liste tous les fichiers trouvés dans un dossier (debug).
     */
    public void listAvailableAssets(String folderPath) {
        System.out.println("[AssetManager] Recherche dans : " + folderPath);

        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            listFilesRecursive(folder, "");
        } else {
            System.out.println("[AssetManager] Dossier introuvable : " + folderPath);
        }
    }

    private void listFilesRecursive(File folder, String indent) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "📁 " + file.getName() + "/");
                    listFilesRecursive(file, indent + "  ");
                } else if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")) {
                    System.out.println(indent + "🖼️ " + file.getName());
                }
            }
        }
    }
}