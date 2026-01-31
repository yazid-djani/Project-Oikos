package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Menu des paramètres.
 */
public class SettingsScene extends Scene {

    private String[] options = {
            "Configuration des touches",
            "Retour au menu"
    };

    private int selectedOption = 0;

    // Animation
    private float pulseTime = 0;

    public SettingsScene(GameLoop gp) {
        super(gp);
    }

    @Override
    public void update() {
        pulseTime += 0.1f;

        // Navigation
        if (gp.inputH.isKeyPressed(KeyEvent.VK_UP) || gp.inputH.isKeyPressed(KeyEvent.VK_Z)) {
            selectedOption--;
            if (selectedOption < 0) selectedOption = options.length - 1;
        }

        if (gp.inputH.isKeyPressed(KeyEvent.VK_DOWN) || gp.inputH.isKeyPressed(KeyEvent.VK_S)) {
            selectedOption++;
            if (selectedOption >= options.length) selectedOption = 0;
        }

        // Sélection
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ENTER) || gp.inputH.isKeyPressed(KeyEvent.VK_SPACE)) {
            selectOption();
        }

        // Retour
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            gp.currentScene = new MenuScene(gp);
        }
    }

    private void selectOption() {
        switch (selectedOption) {
            case 0: // Configuration des touches
                gp.currentScene = new KeyBindingScene(gp);
                break;
            case 1: // Retour
                gp.currentScene = new MenuScene(gp);
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Fond
        g2.setColor(new Color(30, 30, 50));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Titre
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        g2.setColor(Color.WHITE);
        String title = "PARAMÈTRES";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (gp.screenWidth - titleWidth) / 2, 120);

        // Ligne décorative
        g2.setColor(new Color(100, 100, 150));
        g2.fillRect(gp.screenWidth / 4, 150, gp.screenWidth / 2, 3);

        // Options
        g2.setFont(new Font("Arial", Font.PLAIN, 28));
        int startY = 250;
        int spacing = 60;

        for (int i = 0; i < options.length; i++) {
            int y = startY + i * spacing;

            if (i == selectedOption) {
                // Option sélectionnée
                float pulse = (float) (1 + 0.1 * Math.sin(pulseTime));
                g2.setFont(new Font("Arial", Font.BOLD, (int)(32 * pulse)));
                g2.setColor(new Color(255, 220, 100));

                // Indicateur
                String indicator = "▶ ";
                g2.drawString(indicator + options[i], gp.screenWidth / 2 - 150, y);

                // Soulignement
                int textWidth = g2.getFontMetrics().stringWidth(indicator + options[i]);
                g2.setColor(new Color(255, 220, 100, 100));
                g2.fillRect(gp.screenWidth / 2 - 150, y + 5, textWidth, 3);
            } else {
                // Option normale
                g2.setFont(new Font("Arial", Font.PLAIN, 28));
                g2.setColor(new Color(180, 180, 180));
                g2.drawString("  " + options[i], gp.screenWidth / 2 - 150, y);
            }
        }

        // Instructions
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.setColor(new Color(150, 150, 150));
        g2.drawString("↑↓ Naviguer | ENTRÉE Sélectionner | ÉCHAP Retour",
                gp.screenWidth / 2 - 200, gp.screenHeight - 40);
    }
}