package com.oikos.game.world;

import com.oikos.engine.world.MapGenerator;

/**
 * Générateur de maps spécifique à Oikos.
 * Configure le générateur générique avec les paramètres du jeu.
 */
public class OikosMapGenerator {

    private MapGenerator generator;

    public OikosMapGenerator() {
        this.generator = new MapGenerator();
        configureForOikos();
    }

    public OikosMapGenerator(long seed) {
        this.generator = new MapGenerator(seed);
        configureForOikos();
    }

    /**
     * Configure les paramètres pour un monde Oikos typique.
     */
    private void configureForOikos() {
        generator
                .setHeightScale(20f)      // Terrain assez varié
                .setMoistureScale(25f)    // Zones humides moyennes
                .setOctaves(4)            // Bon niveau de détail
                .setPersistence(0.5f);    // Terrain naturel
    }

    /**
     * Génère une map Oikos complète.
     * @param width Largeur en tiles
     * @param height Hauteur en tiles
     * @return Tableau 2D des IDs de TileType
     */
    public int[][] generate(int width, int height) {
        // Générer le terrain de base avec les biomes Oikos
        int[][] map = generator.generateSimpleMap(
                width, height,
                0.35f,  // waterLevel - 35% de chances d'eau en dessous
                0.42f,  // sandLevel - plages étroites
                0.70f,  // grassLevel - beaucoup d'herbe
                0.85f   // rockLevel - montagnes rares
        );

        // Convertir les IDs génériques vers les TileType d'Oikos
        // 0=eau -> WATER(0), 1=sable -> SAND(1), 2=herbe -> GRASS(2),
        // 3=forêt -> FOREST(3), 4=roche -> ROCK(4)

        // Ajouter des forêts (arbres) sur l'herbe
        generator.scatterElements(map, TileType.GRASS.getId(), TileType.FOREST.getId(), 0.08f);

        // Créer des clusters de forêts denses
        generator.createClusters(
                map,
                TileType.GRASS.getId(),   // Sur l'herbe
                TileType.FOREST.getId(),  // Placer des arbres
                5,                         // 5 forêts
                3, 6                       // Rayon de 3 à 6 tiles
        );

        // Lisser pour éviter les tiles isolées
        generator.smoothMap(map, 1);

        return map;
    }

    /**
     * Génère une map avec une rivière.
     */
    public int[][] generateWithRiver(int width, int height) {
        int[][] map = generate(width, height);

        // Ajouter une rivière qui traverse la map
        int startX = width / 3 + (int)(Math.random() * width / 3);
        generator.generateRiver(map, TileType.WATER.getId(), startX, 0);

        // Ajouter du sable autour de la rivière
        addRiverBanks(map);

        return map;
    }

    /**
     * Ajoute des berges de sable autour de l'eau.
     */
    private void addRiverBanks(int[][] map) {
        int width = map.length;
        int height = map[0].length;

        int[][] newMap = new int[width][height];

        // Copier la map
        for (int x = 0; x < width; x++) {
            System.arraycopy(map[x], 0, newMap[x], 0, height);
        }

        // Ajouter du sable à côté de l'eau
        for (int x = 1; x < width - 1; x++) {
            for (int y = 1; y < height - 1; y++) {
                if (map[x][y] == TileType.GRASS.getId()) {
                    // Vérifier si adjacent à l'eau
                    if (isAdjacentTo(map, x, y, TileType.WATER.getId())) {
                        if (Math.random() > 0.3) {
                            newMap[x][y] = TileType.SAND.getId();
                        }
                    }
                }
            }
        }

        // Appliquer les changements
        for (int x = 0; x < width; x++) {
            System.arraycopy(newMap[x], 0, map[x], 0, height);
        }
    }

    private boolean isAdjacentTo(int[][] map, int x, int y, int type) {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir : dirs) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length) {
                if (map[nx][ny] == type) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Génère une map de village (zone centrale dégagée).
     */
    public int[][] generateVillageMap(int width, int height) {
        int[][] map = generate(width, height);

        // Zone centrale dégagée pour le village
        int centerX = width / 2;
        int centerY = height / 2;
        int villageRadius = Math.min(width, height) / 6;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float dist = (float) Math.sqrt(
                        Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2)
                );

                if (dist < villageRadius) {
                    // Centre = herbe
                    if (map[x][y] != TileType.WATER.getId()) {
                        map[x][y] = TileType.GRASS.getId();
                    }
                } else if (dist < villageRadius * 1.5f) {
                    // Anneau = chemins
                    if (map[x][y] == TileType.GRASS.getId() && Math.random() > 0.7) {
                        map[x][y] = TileType.PATH.getId();
                    }
                }
            }
        }

        return map;
    }

    // --- Getters ---

    public long getSeed() {
        return generator.getSeed();
    }

    public void setSeed(long seed) {
        generator.setSeed(seed);
    }

    public MapGenerator getBaseGenerator() {
        return generator;
    }
}