package com.oikos.game.world;

import com.oikos.engine.graphics.AssetManager;
import com.oikos.engine.graphics.Camera;
import com.oikos.engine.graphics.Sprite;
import com.oikos.engine.physics.CollisionSystem;

import java.awt.Graphics2D;
import java.awt.Color;

/**
 * TileMap avec support de l'auto-tiling et du zoom.
 */
public class TileMap {

    private int[][] tileData;
    private TileType[][] tileTypes;

    private int mapWidth;
    private int mapHeight;
    private int tileSize;

    private AssetManager assets;
    private AutoTileManager autoTiles;
    private boolean isNight;

    private int viewportWidth;
    private int viewportHeight;

    private long seed;

    public TileMap(AssetManager assets, int tileSize, int viewportWidth, int viewportHeight) {
        this.assets = assets;
        this.tileSize = tileSize;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        this.isNight = false;
        this.seed = System.currentTimeMillis();

        this.autoTiles = new AutoTileManager(assets, "/environment");
    }

    public void generate(int width, int height, long seed) {
        this.seed = seed;
        this.mapWidth = width;
        this.mapHeight = height;

        System.out.println("[TileMap] Génération : " + width + "x" + height + " (seed: " + seed + ")");

        autoTiles.loadAllTiles();
        loadExtraSprites();

        OikosMapGenerator generator = new OikosMapGenerator(seed);
        tileData = generator.generateWithRiver(width, height);

        tileTypes = new TileType[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tileTypes[x][y] = TileType.fromId(tileData[x][y]);
            }
        }

        System.out.println("[TileMap] Map générée !");
    }

    public void generate(int width, int height) {
        generate(width, height, System.currentTimeMillis());
    }

    private void loadExtraSprites() {
        // À compléter selon tes assets
    }

    /**
     * Dessine la map avec support du zoom.
     */
    public void draw(Graphics2D g2, Camera camera) {
        if (tileData == null) return;

        float zoom = camera.getZoom();
        int scaledTileSize = (int) (tileSize * zoom);

        // Si les tiles sont trop petites, ne pas les dessiner individuellement
        if (scaledTileSize < 2) {
            drawMinimap(g2, camera);
            return;
        }

        // Calculer les tiles visibles (avec zoom)
        float effectiveViewWidth = viewportWidth / zoom;
        float effectiveViewHeight = viewportHeight / zoom;

        int startCol = Math.max(0, (int)(camera.getOffsetX() / tileSize) - 1);
        int endCol = Math.min(mapWidth, (int)((camera.getOffsetX() + effectiveViewWidth) / tileSize) + 2);
        int startRow = Math.max(0, (int)(camera.getOffsetY() / tileSize) - 1);
        int endRow = Math.min(mapHeight, (int)((camera.getOffsetY() + effectiveViewHeight) / tileSize) + 2);

        for (int y = startRow; y < endRow; y++) {
            for (int x = startCol; x < endCol; x++) {
                TileType type = tileTypes[x][y];

                // Position à l'écran avec zoom
                int screenX = camera.worldToScreenX(x * tileSize);
                int screenY = camera.worldToScreenY(y * tileSize);

                // Taille avec zoom
                int drawSize = camera.scaleToScreen(tileSize);

                // Choisir le sprite
                String spriteKey = getSpriteKey(x, y, type);
                Sprite sprite = assets.getSprite(spriteKey);

                if (sprite != null && sprite.image != null) {
                    g2.drawImage(sprite.image, screenX, screenY, drawSize, drawSize, null);
                } else {
                    // Fallback couleur
                    g2.setColor(getFallbackColor(type));
                    g2.fillRect(screenX, screenY, drawSize, drawSize);
                }
            }
        }
    }

    /**
     * Dessine une minimap quand le zoom est très petit.
     */
    private void drawMinimap(Graphics2D g2, Camera camera) {
        float zoom = camera.getZoom();

        for (int y = 0; y < mapHeight; y++) {
            for (int x = 0; x < mapWidth; x++) {
                TileType type = tileTypes[x][y];

                int screenX = (int) ((x * tileSize - camera.getOffsetX()) * zoom);
                int screenY = (int) ((y * tileSize - camera.getOffsetY()) * zoom);
                int drawSize = Math.max(1, (int) (tileSize * zoom));

                g2.setColor(getFallbackColor(type));
                g2.fillRect(screenX, screenY, drawSize, drawSize);
            }
        }
    }

    private String getSpriteKey(int x, int y, TileType type) {
        if (type == TileType.WATER) {
            boolean[] neighbors = getNeighborsOfType(x, y, TileType.WATER);
            return autoTiles.getWaterSpriteKey(neighbors, isNight);
        }

        if (type == TileType.ROCK) {
            boolean[] neighbors = getNeighborsOfType(x, y, TileType.ROCK);
            return autoTiles.getRockSpriteKey(neighbors, isNight);
        }

        return autoTiles.getSimpleSpriteKey(type, isNight);
    }

    private boolean[] getNeighborsOfType(int x, int y, TileType type) {
        return new boolean[] {
                getTileAt(x, y - 1) == type,
                getTileAt(x + 1, y) == type,
                getTileAt(x, y + 1) == type,
                getTileAt(x - 1, y) == type
        };
    }

    private Color getFallbackColor(TileType type) {
        switch (type) {
            case WATER:  return isNight ? new Color(20, 50, 80) : new Color(64, 164, 223);
            case SAND:   return isNight ? new Color(120, 100, 60) : new Color(210, 180, 140);
            case GRASS:  return isNight ? new Color(20, 60, 30) : new Color(34, 139, 34);
            case FOREST: return isNight ? new Color(10, 40, 20) : new Color(0, 100, 0);
            case ROCK:   return isNight ? new Color(50, 50, 60) : new Color(128, 128, 128);
            case PATH:   return isNight ? new Color(80, 60, 40) : new Color(160, 120, 80);
            default:     return Color.MAGENTA;
        }
    }

    // --- Collision & Getters ---

    public boolean[][] generateCollisionMap() {
        if (tileTypes == null) return null;
        boolean[][] collisionMap = new boolean[mapWidth][mapHeight];
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                collisionMap[x][y] = tileTypes[x][y].isSolid();
            }
        }
        return collisionMap;
    }

    public void applyToCollisionSystem(CollisionSystem collisionSystem) {
        boolean[][] collisionMap = generateCollisionMap();
        if (collisionMap != null) {
            collisionSystem.loadCollisionMap(collisionMap);
        }
    }

    public int getMapWidth() { return mapWidth; }
    public int getMapHeight() { return mapHeight; }
    public int getWorldPixelWidth() { return mapWidth * tileSize; }
    public int getWorldPixelHeight() { return mapHeight * tileSize; }
    public int getTileSize() { return tileSize; }
    public long getSeed() { return seed; }

    public TileType getTileAt(int tileX, int tileY) {
        if (tileX < 0 || tileX >= mapWidth || tileY < 0 || tileY >= mapHeight) {
            return TileType.WATER;
        }
        return tileTypes[tileX][tileY];
    }

    public TileType getTileAtPixel(float pixelX, float pixelY) {
        return getTileAt((int)(pixelX / tileSize), (int)(pixelY / tileSize));
    }

    public boolean isSolidAtPixel(float pixelX, float pixelY) {
        return getTileAtPixel(pixelX, pixelY).isSolid();
    }

    public float[] findSpawnPoint() {
        int centerX = mapWidth / 2;
        int centerY = mapHeight / 2;

        for (int radius = 0; radius < Math.max(mapWidth, mapHeight); radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    int x = centerX + dx;
                    int y = centerY + dy;
                    if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
                        if (tileTypes[x][y] == TileType.GRASS || tileTypes[x][y] == TileType.PATH) {
                            return new float[] { x * tileSize + tileSize / 2f, y * tileSize + tileSize / 2f };
                        }
                    }
                }
            }
        }
        return new float[] { mapWidth * tileSize / 2f, mapHeight * tileSize / 2f };
    }

    public void setNight(boolean night) { this.isNight = night; }
    public boolean isNight() { return isNight; }
}