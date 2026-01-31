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
    public final int screenCols = 16;
    public final int screenRows = 12;
    public final int screenWidth = tileSize * screenCols;   // 1024px
    public final int screenHeight = tileSize * screenRows;  // 768px

    // Taille du MONDE (map 9x plus grande : 48x36 tiles)
    public final int worldCols = 48;
    public final int worldRows = 36;
    public final int worldWidth = tileSize * worldCols;
    public final int worldHeight = tileSize * worldRows;

    // Système
    Thread gameThread;
    public InputHandler inputH = new InputHandler();

    // --- SCÈNE ACTIVE ---
    public Scene currentScene;

    // FPS
    int FPS = 60;

    public GameLoop() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
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
        double drawInterval = 1000000000.0 / FPS;
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

    // ⚠️ CORRECTION ICI : Appeler currentScene.update()
    public void update() {
        if (currentScene != null) {
            currentScene.update();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (currentScene != null) {
            currentScene.draw(g2);
        } else {
            g2.setColor(Color.WHITE);
            g2.drawString("Loading...", 100, 100);
        }

        g2.dispose();
    }
}