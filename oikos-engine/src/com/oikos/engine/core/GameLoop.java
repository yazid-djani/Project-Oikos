package com.oikos.engine.core;

import com.oikos.engine.input.InputHandler;
import javax.swing.JPanel;
import java.awt.*;

public class GameLoop extends JPanel implements Runnable {
    // --- PARAMÈTRES ÉCRAN (viewport) ---
    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale; // 64x64 pixels

    // Ce qu'on AFFICHE à l'écran
    public final int screenCols = 16;  // 16 colonnes visibles
    public final int screenRows = 12;  // 12 lignes visibles
    public final int screenWidth = tileSize * screenCols;   // 1024px
    public final int screenHeight = tileSize * screenRows;  // 768px

    // Taille du MONDE (map 9x plus grande : 48x36 tiles)
    public final int worldCols = 48;  // 16 * 3
    public final int worldRows = 36;  // 12 * 3
    public final int worldWidth = tileSize * worldCols;    // 3072px
    public final int worldHeight = tileSize * worldRows;   // 2304px

    // Système
    Thread gameThread;
    public InputHandler inputH = new InputHandler();

    // --- SCÈNE ACTIVE ---
    public Scene currentScene;

    // FPS
    int FPS = 60;

    public GameLoop() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black); // Fond noir par défaut
        this.setDoubleBuffered(true);
        // Connexion des contrôles
        this.addKeyListener(inputH);
        this.addMouseListener(inputH);
        this.addMouseMotionListener(inputH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        // La mise à jour du jeu se fera ici via le SceneManager plus tard
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // On dessine la scène active
        if (currentScene != null) {
            currentScene.draw(g2);
        } else {
            // Si aucune scène n'est chargée (écran noir de chargement)
            g2.setColor(Color.WHITE);
            g2.drawString("Loading...", 100, 100);
        }

        g2.dispose();
    }
}