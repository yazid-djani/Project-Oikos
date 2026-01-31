package com.oikos.engine.graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire des assets avec recherche automatique.
 * Cherche dans le classpath ET le système de fichiers.
 */
public class AssetManager {

    private Map<String, Sprite> spriteCache;
    private Class<?> resourceClass;
    private boolean debugMode = true;

    // Chemins de base à essayer (système de fichiers)
    private static final String[] BASE_PATHS = {
            "oikos-game/resources/",
            "resources/",
            "../oikos-game/resources/",
            "src/main/resources/",
            "oikos-game/src/main/resources/",
            ""
    };

    public AssetManager(Class<?> resourceClass) {
        this.spriteCache = new HashMap<>();
        this.resourceClass = resourceClass;

        if (debugMode) {
            System.out.println("[AssetManager] Initialisé");
            System.out.println("[AssetManager] Répertoire : " + new File("").getAbsolutePath());
        }
    }

    /**
     * Charge un sprite en essayant plusieurs méthodes.
     */
    public Sprite loadSprite(String key, String path, boolean solid) {
        // Déjà en cache ?
        if (spriteCache.containsKey(key)) {
            return spriteCache.get(key);
        }

        BufferedImage image = null;
        String foundPath = null;

        // 1. Essayer le classpath
        image = loadFromClasspath(path);
        if (image != null) foundPath = "classpath:" + path;

        // 2. Essayer le système de fichiers
        if (image == null) {
            for (String basePath : BASE_PATHS) {
                String fullPath = basePath + path;
                image = loadFromFile(fullPath);
                if (image != null) {
                    foundPath = fullPath;
                    break;
                }
            }
        }

        // 3. Essayer avec le chemin Windows (backslash)
        if (image == null) {
            String windowsPath = path.replace("/", "\\");
            for (String basePath : BASE_PATHS) {
                String fullPath = basePath.replace("/", "\\") + windowsPath;
                image = loadFromFile(fullPath);
                if (image != null) {
                    foundPath = fullPath;
                    break;
                }
            }
        }

        // Succès ?
        if (image != null) {
            Sprite sprite = new Sprite(image, key, solid);
            spriteCache.put(key, sprite);
            if (debugMode) {
                System.out.println("[AssetManager] ✓ " + key + " <- " + foundPath);
            }
            return sprite;
        }

        // Échec (pas d'erreur, on utilisera le fallback couleur)
        if (debugMode) {
            System.out.println("[AssetManager] ✗ " + key + " (introuvable: " + path + ")");
        }
        return null;
    }

    /**
     * Charge depuis le classpath (pour les JAR).
     */
    private BufferedImage loadFromClasspath(String path) {
        // Variantes de chemin à essayer
        String[] variants = {
                "/" + path,
                path,
                "/resources/" + path,
                "/" + path.replace("\\", "/")
        };

        for (String p : variants) {
            try {
                InputStream is = resourceClass.getResourceAsStream(p);
                if (is != null) {
                    return ImageIO.read(is);
                }
            } catch (IOException e) {
                // Continuer
            }

            // Essayer avec le ClassLoader
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream(p.startsWith("/") ? p.substring(1) : p);
                if (is != null) {
                    return ImageIO.read(is);
                }
            } catch (IOException e) {
                // Continuer
            }
        }
        return null;
    }

    /**
     * Charge depuis le système de fichiers.
     */
    private BufferedImage loadFromFile(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            try {
                return ImageIO.read(file);
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    public Sprite loadSprite(String key, String path) {
        return loadSprite(key, path, false);
    }

    public Sprite getSprite(String key) {
        return spriteCache.get(key);
    }

    public boolean hasSprite(String key) {
        return spriteCache.containsKey(key);
    }

    public int size() {
        return spriteCache.size();
    }

    public void clear() {
        spriteCache.clear();
    }

    public void setDebugMode(boolean debug) {
        this.debugMode = debug;
    }

    /**
     * Charge un spritesheet et le découpe.
     */
    public void loadSpritesheet(String baseName, String path, int frameWidth, int frameHeight, int count) {
        BufferedImage sheet = null;

        // Essayer classpath
        sheet = loadFromClasspath(path);

        // Essayer fichiers
        if (sheet == null) {
            for (String basePath : BASE_PATHS) {
                sheet = loadFromFile(basePath + path);
                if (sheet != null) break;
            }
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

        System.out.println("[AssetManager] Spritesheet : " + baseName + " (" + count + " frames)");
    }

    /**
     * Liste les fichiers disponibles (debug).
     */
    public void listAvailableAssets(String folderPath) {
        System.out.println("\n[AssetManager] === SCAN DES ASSETS ===");

        for (String basePath : BASE_PATHS) {
            File folder = new File(basePath + folderPath);
            if (folder.exists() && folder.isDirectory()) {
                System.out.println("Trouvé : " + folder.getAbsolutePath());
                listFilesRecursive(folder, "  ");
                System.out.println("=================================\n");
                return;
            }
        }

        System.out.println("Dossier non trouvé : " + folderPath);
        System.out.println("Répertoire courant : " + new File("").getAbsolutePath());
        System.out.println("=================================\n");
    }

    private void listFilesRecursive(File folder, String indent) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(indent + "📁 " + file.getName() + "/");
                    listFilesRecursive(file, indent + "  ");
                } else if (file.getName().toLowerCase().endsWith(".png")) {
                    System.out.println(indent + "🖼️ " + file.getName());
                }
            }
        }
    }
}