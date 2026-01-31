package com.oikos.game;

import com.oikos.engine.core.GameLoop;
import com.oikos.game.scenes.MenuScene; // Importe ton menu

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Project Oikos");

        GameLoop gamePanel = new GameLoop();

        // --- NOUVEAU : On branche la première scène ---
        gamePanel.currentScene = new MenuScene(gamePanel);

        window.add(gamePanel);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.startGameThread();
    }
}