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

/**
 * Scène de jeu principale.
 */
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
        // === CONTRÔLES (utilisant les bindings configurables) ===

        // ÉCHAP/Pause = menu
        if (gp.inputH.isPause()) {
            gp.currentScene = new MenuScene(gp);
            return;
        }

        // R = régénérer la map
        if (gp.inputH.isRegenerate()) {
            System.out.println("[PlayScene] Régénération de la map...");
            initializeGame();
            return;
        }

        // N = toggle jour/nuit
        if (gp.inputH.isToggleNight()) {
            tileMap.setNight(!tileMap.isNight());
            System.out.println("[PlayScene] Mode : " + (tileMap.isNight() ? "Nuit" : "Jour"));
        }

        // === ZOOM ===

        // M = voir toute la map (toggle)
        if (gp.inputH.isToggleMap()) {
            camera.toggleFullMapView();
        }

        // + = zoom in
        if (gp.inputH.isZoomIn()) {
            camera.zoomIn(0.2f);
        }

        // - = zoom out
        if (gp.inputH.isZoomOut()) {
            camera.zoomOut(0.2f);
        }

        // 0 = reset zoom
        if (gp.inputH.isZoomReset()) {
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

            // Normaliser le mouvement diagonal
            if (dx != 0 && dy != 0) {
                dx *= 0.707f;
                dy *= 0.707f;
            }

            // Vérifier les collisions séparément pour glisser le long des murs
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

    /**
     * Dessine le joueur (carré temporaire).
     */
    private void drawPlayer(Graphics2D g2) {
        int size = camera.scaleToScreen(gp.tileSize);
        int screenX = camera.worldToScreenX(playerX) - size / 2;
        int screenY = camera.worldToScreenY(playerY) - size / 2;

        // Ombre
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillOval(screenX + size/8, screenY + size - size/6, size - size/4, size/6);

        // Corps
        g2.setColor(new Color(70, 130, 180)); // Steel Blue
        g2.fillRoundRect(screenX, screenY, size, size, size/6, size/6);

        // Contour
        g2.setColor(new Color(30, 80, 130));
        g2.drawRoundRect(screenX, screenY, size, size, size/6, size/6);

        // Yeux (pour montrer la direction)
        int eyeSize = Math.max(4, size / 8);
        g2.setColor(Color.WHITE);
        g2.fillOval(screenX + size/4, screenY + size/4, eyeSize, eyeSize);
        g2.fillOval(screenX + size/2 + size/8, screenY + size/4, eyeSize, eyeSize);
    }

    /**
     * Dessine un indicateur clignotant pour le joueur en vue globale.
     */
    private void drawPlayerIndicator(Graphics2D g2) {
        int screenX = camera.worldToScreenX(playerX);
        int screenY = camera.worldToScreenY(playerY);

        // Animation de pulsation
        long time = System.currentTimeMillis();
        int pulse = (int) (5 + 3 * Math.sin(time * 0.01));

        // Cercle extérieur blanc
        g2.setColor(Color.WHITE);
        g2.drawOval(screenX - pulse - 2, screenY - pulse - 2, pulse * 2 + 4, pulse * 2 + 4);

        // Cercle intérieur rouge
        g2.setColor(Color.RED);
        g2.fillOval(screenX - pulse, screenY - pulse, pulse * 2, pulse * 2);

        // Texte "JOUEUR"
        g2.setFont(new Font("Arial", Font.BOLD, 10));
        g2.setColor(Color.WHITE);
        g2.drawString("▼", screenX - 4, screenY - pulse - 5);
    }

    /**
     * Dessine l'interface utilisateur.
     */
    private void drawUI(Graphics2D g2) {
        // Fond semi-transparent pour le texte
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(5, 5, 200, 95, 10, 10);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        int uiY = 22;
        g2.drawString("Seed: " + tileMap.getSeed(), 12, uiY);
        g2.drawString("Position: " + (int)playerX + ", " + (int)playerY, 12, uiY += 18);
        g2.drawString("Zoom: " + String.format("%.1f", camera.getZoom()) + "x", 12, uiY += 18);
        g2.drawString("Mode: " + (tileMap.isNight() ? "🌙 Nuit" : "☀️ Jour"), 12, uiY += 18);

        // Indicateur vue globale
        if (camera.isFullMapView()) {
            g2.setColor(new Color(0, 0, 0, 150));
            g2.fillRoundRect(gp.screenWidth / 2 - 80, 5, 160, 30, 10, 10);

            g2.setColor(new Color(255, 220, 100));
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.drawString("🗺️ VUE GLOBALE", gp.screenWidth / 2 - 65, 26);
        }

        // Aide en bas
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(5, gp.screenHeight - 30, gp.screenWidth - 10, 25, 10, 10);

        g2.setColor(new Color(200, 200, 200));
        g2.setFont(new Font("Arial", Font.PLAIN, 12));

        // Afficher les touches configurées
        String helpText = String.format(
                "[%s%s%s%s] Déplacer | [%s] Carte | [%s/%s] Zoom | [%s] Jour/Nuit | [%s] Régénérer | [%s] Menu",
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.MOVE_UP)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.MOVE_LEFT)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.MOVE_DOWN)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.MOVE_RIGHT)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.TOGGLE_MAP)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.ZOOM_IN)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.ZOOM_OUT)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.TOGGLE_NIGHT)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.REGENERATE)),
                getKeyName(gp.inputH.keyBinding.getKey(com.oikos.engine.input.KeyBinding.Action.PAUSE))
        );
        g2.drawString(helpText, 12, gp.screenHeight - 12);
    }

    /**
     * Raccourci pour obtenir le nom d'une touche.
     */
    private String getKeyName(int keyCode) {
        return com.oikos.engine.input.KeyBinding.getKeyName(keyCode);
    }
}