package com.oikos.engine.core;

import com.oikos.engine.input.InputHandler;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameLoop extends JPanel implements Runnable {

    // --- PARAMÈTRES ÉCRAN (Modifiés pour 32x32) ---
    final int originalTileSize = 32; // Tes images font 32x32 pixels
    final int scale = 2; // On grossit x2 (32*2 = 64 pixels sur l'écran)

    public final int tileSize = originalTileSize * scale; // 64x64 pixels
    public final int maxScreenCol = 16; // 16 colonnes
    public final int maxScreenRow = 12; // 12 lignes

    // Résolution finale : 1024 x 768 pixels
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    // Système
    Thread gameThread;
    public InputHandler inputH = new InputHandler();

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

        // Code temporaire pour vérifier que la résolution marche
        g2.setColor(Color.WHITE);
        g2.drawString("Oikos Engine - 32px Mode", 10, 20);
        g2.drawRect(100, 100, tileSize, tileSize); // Dessine un carré de la taille d'une tuile (64x64)

        g2.dispose();
    }
}