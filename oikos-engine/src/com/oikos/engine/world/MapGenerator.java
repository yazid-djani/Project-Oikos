package com.oikos.engine.world;

import java.util.Random;

/**
 * Générateur de maps procédurales générique.
 * Utilise le bruit de Perlin pour créer des terrains naturels.
 *
 * Réutilisable dans n'importe quel projet.
 */
public class MapGenerator {

    private NoiseGenerator noise;
    private Random random;
    private long seed;

    // Paramètres de génération
    private float heightScale = 25f;      // Échelle pour les hauteurs
    private float moistureScale = 30f;    // Échelle pour l'humidité
    private int octaves = 4;              // Détail du terrain
    private float persistence = 0.5f;     // Rugosité

    public MapGenerator() {
        this(System.currentTimeMillis());
    }

    public MapGenerator(long seed) {
        this.seed = seed;
        this.noise = new NoiseGenerator(seed);
        this.random = new Random(seed);
    }

    /**
     * Génère une carte de hauteurs (0.0 à 1.0).
     * @param width Largeur en tiles
     * @param height Hauteur en tiles
     * @return Tableau 2D de valeurs de hauteur
     */
    public float[][] generateHeightMap(int width, int height) {
        float[][] heightMap = new float[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                heightMap[x][y] = noise.fractalNoise(x, y, heightScale, octaves, persistence);
            }
        }

        return heightMap;
    }

    /**
     * Génère une carte d'humidité (0.0 à 1.0).
     * Utilisé pour placer l'eau, les forêts, etc.
     */
    public float[][] generateMoistureMap(int width, int height) {
        float[][] moistureMap = new float[width][height];

        // Utiliser un offset pour avoir un pattern différent
        float offsetX = 1000f;
        float offsetY = 1000f;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                moistureMap[x][y] = noise.fractalNoise(x + offsetX, y + offsetY, moistureScale, octaves, persistence);
            }
        }

        return moistureMap;
    }

    /**
     * Génère une map avec des biomes basés sur hauteur et humidité.
     * @param width Largeur en tiles
     * @param height Hauteur en tiles
     * @param biomes Tableau de BiomeType triés par hauteur croissante
     * @return Tableau 2D des IDs de biome
     */
    public int[][] generateBiomeMap(int width, int height, BiomeType[] biomes) {
        float[][] heightMap = generateHeightMap(width, height);
        float[][] moistureMap = generateMoistureMap(width, height);

        int[][] biomeMap = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float h = heightMap[x][y];
                float m = moistureMap[x][y];

                // Trouver le biome correspondant à cette hauteur
                BiomeType selectedBiome = biomes[0]; // Par défaut

                for (BiomeType biome : biomes) {
                    if (h >= biome.getMinHeight() && h <= biome.getMaxHeight()) {
                        selectedBiome = biome;
                        break;
                    }
                }

                biomeMap[x][y] = selectedBiome.getId();
            }
        }

        return biomeMap;
    }

    /**
     * Génère une map simple sans biomes personnalisés.
     * Utilise des seuils de hauteur basiques.
     *
     * @param waterLevel Seuil pour l'eau (ex: 0.35)
     * @param sandLevel Seuil pour le sable (ex: 0.4)
     * @param grassLevel Seuil pour l'herbe (ex: 0.7)
     * @param rockLevel Seuil pour la roche (ex: 0.85)
     *
     * @return IDs: 0=eau, 1=sable, 2=herbe, 3=forêt, 4=roche
     */
    public int[][] generateSimpleMap(int width, int height,
                                     float waterLevel, float sandLevel,
                                     float grassLevel, float rockLevel) {
        float[][] heightMap = generateHeightMap(width, height);
        float[][] moistureMap = generateMoistureMap(width, height);

        int[][] map = new int[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float h = heightMap[x][y];
                float m = moistureMap[x][y];

                if (h < waterLevel) {
                    map[x][y] = 0; // Eau
                } else if (h < sandLevel) {
                    map[x][y] = 1; // Sable/Plage
                } else if (h < grassLevel) {
                    // Zone herbe ou forêt selon humidité
                    if (m > 0.6f) {
                        map[x][y] = 3; // Forêt (humide)
                    } else {
                        map[x][y] = 2; // Herbe
                    }
                } else if (h < rockLevel) {
                    map[x][y] = 2; // Herbe haute altitude
                } else {
                    map[x][y] = 4; // Roche/Montagne
                }
            }
        }

        return map;
    }

    /**
     * Ajoute des éléments aléatoires sur la map (arbres, rochers, etc.)
     * @param map La map à modifier
     * @param baseType Le type de tile sur lequel placer
     * @param newType Le type à placer
     * @param density Probabilité (0.0 à 1.0)
     */
    public void scatterElements(int[][] map, int baseType, int newType, float density) {
        int width = map.length;
        int height = map[0].length;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (map[x][y] == baseType && random.nextFloat() < density) {
                    map[x][y] = newType;
                }
            }
        }
    }

    /**
     * Crée des groupes/clusters d'éléments (forêts, lacs).
     */
    public void createClusters(int[][] map, int baseType, int clusterType,
                               int numClusters, int minSize, int maxSize) {
        int width = map.length;
        int height = map[0].length;

        for (int i = 0; i < numClusters; i++) {
            int cx = random.nextInt(width);
            int cy = random.nextInt(height);
            int radius = minSize + random.nextInt(maxSize - minSize + 1);

            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    int nx = cx + dx;
                    int ny = cy + dy;

                    if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                        // Forme circulaire avec bords irréguliers
                        float dist = (float) Math.sqrt(dx * dx + dy * dy);
                        float threshold = radius * (0.7f + random.nextFloat() * 0.5f);

                        if (dist <= threshold && map[nx][ny] == baseType) {
                            map[nx][ny] = clusterType;
                        }
                    }
                }
            }
        }
    }

    /**
     * Génère une rivière sinueuse.
     */
    public void generateRiver(int[][] map, int waterType, int startX, int direction) {
        int width = map.length;
        int height = map[0].length;

        float x = startX;
        int riverWidth = 2 + random.nextInt(2);

        for (int y = 0; y < height; y++) {
            // Ondulation avec bruit
            x += noise.noise(y, 0, 10f) * 3 - 1.5f;
            x = Math.max(riverWidth, Math.min(width - riverWidth - 1, x));

            for (int w = -riverWidth; w <= riverWidth; w++) {
                int rx = (int) x + w;
                if (rx >= 0 && rx < width) {
                    map[rx][y] = waterType;
                }
            }
        }
    }

    /**
     * Lisse la map en supprimant les tiles isolées.
     */
    public void smoothMap(int[][] map, int iterations) {
        int width = map.length;
        int height = map[0].length;

        for (int iter = 0; iter < iterations; iter++) {
            int[][] newMap = new int[width][height];

            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    newMap[x][y] = getMostCommonNeighbor(map, x, y);
                }
            }

            // Copier le résultat
            for (int x = 0; x < width; x++) {
                System.arraycopy(newMap[x], 0, map[x], 0, height);
            }
        }
    }

    private int getMostCommonNeighbor(int[][] map, int x, int y) {
        int width = map.length;
        int height = map[0].length;

        java.util.Map<Integer, Integer> counts = new java.util.HashMap<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                int nx = x + dx;
                int ny = y + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    int type = map[nx][ny];
                    counts.put(type, counts.getOrDefault(type, 0) + 1);
                }
            }
        }

        // Retourner le type le plus fréquent
        return counts.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(map[x][y]);
    }

    // --- SETTERS pour la configuration ---

    public MapGenerator setHeightScale(float scale) {
        this.heightScale = scale;
        return this;
    }

    public MapGenerator setMoistureScale(float scale) {
        this.moistureScale = scale;
        return this;
    }

    public MapGenerator setOctaves(int octaves) {
        this.octaves = octaves;
        return this;
    }

    public MapGenerator setPersistence(float persistence) {
        this.persistence = persistence;
        return this;
    }

    public long getSeed() {
        return seed;
    }

    public void setSeed(long seed) {
        this.seed = seed;
        this.noise = new NoiseGenerator(seed);
        this.random = new Random(seed);
    }
}