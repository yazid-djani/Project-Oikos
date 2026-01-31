package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;
import com.oikos.engine.graphics.AssetManager;
import com.oikos.engine.graphics.Camera;
import com.oikos.engine.math.Vector2;
import com.oikos.engine.physics.CollisionSystem;
import com.oikos.game.world.TileMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class PlayScene extends Scene {

    // Systèmes
    private AssetManager assets;
    private Camera camera;
    private CollisionSystem collisionSystem;
    private TileMap tileMap;

    // Joueur temporaire
    private float playerX;
    private float playerY;
    private float playerSpeed = 4f;

    // Map générée
    private static final int MAP_WIDTH = 48;   // 3x plus large
    private static final int MAP_HEIGHT = 36;  // 3x plus haut

    public PlayScene(GameLoop gp) {
        super(gp);
        initializeGame();
    }

    /**
     * Constructeur avec seed spécifique.
     */
    public PlayScene(GameLoop gp, long seed) {
        super(gp);
        initializeGame(seed);
    }

    private void initializeGame() {
        initializeGame(System.currentTimeMillis());
    }

    private void initializeGame(long seed) {
        // AssetManager
        assets = new AssetManager(getClass());

        // TileMap avec génération automatique
        tileMap = new TileMap(assets, gp.tileSize, gp.screenWidth, gp.screenHeight);
        tileMap.generate(MAP_WIDTH, MAP_HEIGHT, seed);

        // Caméra
        camera = new Camera(
                gp.screenWidth,
                gp.screenHeight,
                tileMap.getWorldPixelWidth(),
                tileMap.getWorldPixelHeight()
        );

        // Collisions
        collisionSystem = new CollisionSystem(gp.tileSize);
        tileMap.applyToCollisionSystem(collisionSystem);

        // Trouver un point de spawn valide
        float[] spawn = tileMap.findSpawnPoint();
        playerX = spawn[0];
        playerY = spawn[1];

        System.out.println("[PlayScene] Spawn : " + playerX + ", " + playerY);
    }

    @Override
    public void update() {
        // ECHAP = menu
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            gp.currentScene = new MenuScene(gp);
            return;
        }

        // R = régénérer la map
        if (gp.inputH.isKeyPressed(KeyEvent.VK_R)) {
            System.out.println("[PlayScene] Régénération de la map...");
            initializeGame(); // Nouvelle seed
            return;
        }

        // N = toggle jour/nuit
        if (gp.inputH.isKeyPressed(KeyEvent.VK_N)) {
            tileMap.setNight(!tileMap.isNight());
        }

        // Déplacement
        float dx = 0, dy = 0;

        if (gp.inputH.isUp()) dy -= playerSpeed;
        if (gp.inputH.isDown()) dy += playerSpeed;
        if (gp.inputH.isLeft()) dx -= playerSpeed;
        if (gp.inputH.isRight()) dx += playerSpeed;

        // Normaliser diagonale
        if (dx != 0 && dy != 0) {
            dx *= 0.707f;
            dy *= 0.707f;
        }

        // Vérifier collisions séparément pour glisser le long des murs
        float newX = playerX + dx;
        float newY = playerY + dy;

        // Limites du monde
        float halfTile = gp.tileSize / 2f;
        newX = Math.max(halfTile, Math.min(newX, tileMap.getWorldPixelWidth() - halfTile));
        newY = Math.max(halfTile, Math.min(newY, tileMap.getWorldPixelHeight() - halfTile));

        // Collision X
        if (!tileMap.isSolidAtPixel(newX, playerY)) {
            playerX = newX;
        }
        // Collision Y
        if (!tileMap.isSolidAtPixel(playerX, newY)) {
            playerY = newY;
        }

        // Caméra suit le joueur
        camera.setTarget(new Vector2(playerX, playerY));
        camera.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        // 1. Map
        tileMap.draw(g2, camera);

        // 2. Joueur (carré temporaire)
        int screenX = camera.worldToScreenX(playerX) - gp.tileSize / 2;
        int screenY = camera.worldToScreenY(playerY) - gp.tileSize / 2;

        // Ombre
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillOval(screenX + 4, screenY + gp.tileSize - 8, gp.tileSize - 8, 12);

        // Corps
        g2.setColor(new Color(70, 130, 180)); // Steel Blue
        g2.fillRoundRect(screenX, screenY, gp.tileSize, gp.tileSize, 10, 10);

        // Contour
        g2.setColor(new Color(30, 80, 130));
        g2.drawRoundRect(screenX, screenY, gp.tileSize, gp.tileSize, 10, 10);

        // 3. UI
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        int uiY = 20;
        g2.drawString("Seed: " + tileMap.getSeed(), 10, uiY);
        g2.drawString("Position: " + (int)playerX + ", " + (int)playerY, 10, uiY += 18);
        g2.drawString("Mode: " + (tileMap.isNight() ? "Nuit" : "Jour"), 10, uiY += 18);

        g2.setFont(new Font("Arial", Font.PLAIN, 12));
        g2.drawString("[ZQSD] Déplacer | [N] Jour/Nuit | [R] Nouvelle map | [ECHAP] Menu", 10, gp.screenHeight - 10);
    }
}