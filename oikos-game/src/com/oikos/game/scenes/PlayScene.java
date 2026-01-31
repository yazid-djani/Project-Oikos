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
    private static final int MAP_WIDTH = 48;
    private static final int MAP_HEIGHT = 36;

    public PlayScene(GameLoop gp) {
        super(gp);
        initializeGame();
    }

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

        // TileMap
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

        // Spawn
        float[] spawn = tileMap.findSpawnPoint();
        playerX = spawn[0];
        playerY = spawn[1];

        System.out.println("[PlayScene] Spawn : " + playerX + ", " + playerY);
    }

    @Override
    public void update() {
        // === CONTRÔLES ===

        // ECHAP = menu
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            gp.currentScene = new MenuScene(gp);
            return;
        }

        // R = régénérer la map
        if (gp.inputH.isKeyPressed(KeyEvent.VK_R)) {
            System.out.println("[PlayScene] Régénération...");
            initializeGame();
            return;
        }

        // N = toggle jour/nuit
        if (gp.inputH.isKeyPressed(KeyEvent.VK_N)) {
            tileMap.setNight(!tileMap.isNight());
        }

        // === ZOOM ===

        // M = voir toute la map (toggle)
        if (gp.inputH.isKeyPressed(KeyEvent.VK_M)) {
            camera.toggleFullMapView();
        }

        // + / = = zoom in
        if (gp.inputH.isKeyPressed(KeyEvent.VK_EQUALS) || gp.inputH.isKeyPressed(KeyEvent.VK_ADD)) {
            camera.zoomIn(0.2f);
        }

        // - = zoom out
        if (gp.inputH.isKeyPressed(KeyEvent.VK_MINUS) || gp.inputH.isKeyPressed(KeyEvent.VK_SUBTRACT)) {
            camera.zoomOut(0.2f);
        }

        // 0 = reset zoom
        if (gp.inputH.isKeyPressed(KeyEvent.VK_0) || gp.inputH.isKeyPressed(KeyEvent.VK_NUMPAD0)) {
            camera.setZoom(1.0f);
            camera.setFullMapView(false);
        }

        // === DÉPLACEMENT (seulement si pas en vue globale) ===
        if (!camera.isFullMapView()) {
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

            // Collision
            float newX = playerX + dx;
            float newY = playerY + dy;

            float halfTile = gp.tileSize / 2f;
            newX = Math.max(halfTile, Math.min(newX, tileMap.getWorldPixelWidth() - halfTile));
            newY = Math.max(halfTile, Math.min(newY, tileMap.getWorldPixelHeight() - halfTile));

            if (!tileMap.isSolidAtPixel(newX, playerY)) {
                playerX = newX;
            }
            if (!tileMap.isSolidAtPixel(playerX, newY)) {
                playerY = newY;
            }
        }

        // Caméra suit le joueur
        camera.setTarget(new Vector2(playerX, playerY));
        camera.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        // 1. Map
        tileMap.draw(g2, camera);

        // 2. Joueur (seulement si visible)
        if (!camera.isFullMapView() || camera.getZoom() > 0.1f) {
            drawPlayer(g2);
        }

        // 3. Indicateur du joueur en vue globale
        if (camera.isFullMapView()) {
            drawPlayerIndicator(g2);
        }

        // 4. UI
        drawUI(g2);
    }

    private void drawPlayer(Graphics2D g2) {
        int size = camera.scaleToScreen(gp.tileSize);
        int screenX = camera.worldToScreenX(playerX) - size / 2;
        int screenY = camera.worldToScreenY(playerY) - size / 2;

        // Ombre
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillOval(screenX + size/8, screenY + size - size/6, size - size/4, size/6);

        // Corps
        g2.setColor(new Color(70, 130, 180));
        g2.fillRoundRect(screenX, screenY, size, size, size/6, size/6);

        // Contour
        g2.setColor(new Color(30, 80, 130));
        g2.drawRoundRect(screenX, screenY, size, size, size/6, size/6);
    }

    private void drawPlayerIndicator(Graphics2D g2) {
        // Cercle rouge clignotant pour montrer où est le joueur
        int screenX = camera.worldToScreenX(playerX);
        int screenY = camera.worldToScreenY(playerY);

        // Animation de pulsation
        long time = System.currentTimeMillis();
        int pulse = (int) (5 + 3 * Math.sin(time * 0.01));

        g2.setColor(Color.RED);
        g2.fillOval(screenX - pulse, screenY - pulse, pulse * 2, pulse * 2);

        g2.setColor(Color.WHITE);
        g2.drawOval(screenX - pulse - 2, screenY - pulse - 2, pulse * 2 + 4, pulse * 2 + 4);
    }

    private void drawUI(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        int uiY = 20;
        g2.drawString("Seed: " + tileMap.getSeed(), 10, uiY);
        g2.drawString("Position: " + (int)playerX + ", " + (int)playerY, 10, uiY += 18);
        g2.drawString("Zoom: " + String.format("%.1f", camera.getZoom()) + "x", 10, uiY += 18);
        g2.drawString("Mode: " + (tileMap.isNight() ? "Nuit" : "Jour"), 10, uiY += 18);

        if (camera.isFullMapView()) {
            g2.setColor(Color.YELLOW);
            g2.drawString("📍 VUE GLOBALE", 10, uiY += 18);
        }

        // Aide en bas
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 11));
        String help = "[ZQSD] Déplacer | [M] Vue globale | [+/-] Zoom | [0] Reset | [N] Jour/Nuit | [R] Nouvelle map | [ESC] Menu";
        g2.drawString(help, 10, gp.screenHeight - 10);
    }
}