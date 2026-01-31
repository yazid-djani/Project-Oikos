package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Menu principal avec navigation.
 */
public class MenuScene extends Scene {

    private String[] options = {
            "Jouer",
            "Paramètres",
            "Quitter"
    };

    private int selectedOption = 0;

    // Animation
    private float pulseTime = 0;
    private float titleWave = 0;

    public MenuScene(GameLoop gp) {
        super(gp);
    }

    @Override
    public void update() {
        pulseTime += 0.1f;
        titleWave += 0.05f;

        // Navigation avec flèches ou ZQSD
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

        // Raccourci P pour paramètres
        if (gp.inputH.isKeyPressed(KeyEvent.VK_P)) {
            gp.currentScene = new SettingsScene(gp);
        }
    }

    private void selectOption() {
        switch (selectedOption) {
            case 0: // Jouer
                System.out.println("[Menu] Lancement du jeu !");
                gp.currentScene = new PlayScene(gp);
                break;
            case 1: // Paramètres
                gp.currentScene = new SettingsScene(gp);
                break;
            case 2: // Quitter
                System.out.println("[Menu] Au revoir !");
                System.exit(0);
                break;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Fond dégradé
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(20, 30, 50),
                0, gp.screenHeight, new Color(40, 50, 80)
        );
        g2.setPaint(gradient);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Particules décoratives (optionnel)
        drawParticles(g2);

        // Titre avec effet de vague
        drawTitle(g2);

        // Options du menu
        drawOptions(g2);

        // Instructions
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.setColor(new Color(120, 120, 140));
        g2.drawString("↑↓ Naviguer | ENTRÉE Sélectionner | P Paramètres",
                gp.screenWidth / 2 - 180, gp.screenHeight - 40);
    }

    private void drawParticles(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 30));
        for (int i = 0; i < 50; i++) {
            double x = (i * 73 + pulseTime * 10) % gp.screenWidth;
            double y = (i * 47 + Math.sin(pulseTime + i) * 20) % gp.screenHeight;
            int size = 2 + (i % 3);
            g2.fillOval((int)x, (int)y, size, size);
        }
    }

    private void drawTitle(Graphics2D g2) {
        String title = "OIKOS";
        g2.setFont(new Font("Arial", Font.BOLD, 72));

        int titleWidth = g2.getFontMetrics().stringWidth(title);
        int startX = (gp.screenWidth - titleWidth) / 2;
        int baseY = 180;

        // Ombre
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(title, startX + 4, baseY + 4);

        // Lettres avec effet de vague
        for (int i = 0; i < title.length(); i++) {
            char c = title.charAt(i);
            float offset = (float) Math.sin(titleWave + i * 0.5) * 5;

            // Couleur dégradée
            float hue = (float) (0.55 + i * 0.05 + Math.sin(titleWave) * 0.1);
            g2.setColor(Color.getHSBColor(hue, 0.5f, 1f));

            int charWidth = g2.getFontMetrics().charWidth(c);
            g2.drawString(String.valueOf(c), startX, baseY + (int)offset);
            startX += charWidth;
        }

        // Sous-titre
        g2.setFont(new Font("Arial", Font.ITALIC, 20));
        g2.setColor(new Color(180, 180, 200));
        String subtitle = "Simulateur d'écosystème";
        int subWidth = g2.getFontMetrics().stringWidth(subtitle);
        g2.drawString(subtitle, (gp.screenWidth - subWidth) / 2, 230);
    }

    private void drawOptions(Graphics2D g2) {
        int startY = 350;
        int spacing = 70;

        for (int i = 0; i < options.length; i++) {
            int y = startY + i * spacing;
            boolean isSelected = (i == selectedOption);

            if (isSelected) {
                // Option sélectionnée
                float pulse = (float) (1 + 0.1 * Math.sin(pulseTime * 2));
                int fontSize = (int)(36 * pulse);

                g2.setFont(new Font("Arial", Font.BOLD, fontSize));
                g2.setColor(new Color(255, 220, 100));

                // Fond de sélection
                int textWidth = g2.getFontMetrics().stringWidth(options[i]);
                int boxX = (gp.screenWidth - textWidth) / 2 - 30;
                int boxWidth = textWidth + 60;

                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(boxX, y - 35, boxWidth, 50, 25, 25);

                // Indicateurs
                g2.setColor(new Color(255, 220, 100));
                g2.drawString("▶", boxX - 30, y);
                g2.drawString("◀", boxX + boxWidth + 10, y);

                // Texte
                g2.drawString(options[i], (gp.screenWidth - textWidth) / 2, y);
            } else {
                // Option normale
                g2.setFont(new Font("Arial", Font.PLAIN, 28));
                g2.setColor(new Color(150, 150, 170));

                int textWidth = g2.getFontMetrics().stringWidth(options[i]);
                g2.drawString(options[i], (gp.screenWidth - textWidth) / 2, y);
            }
        }
    }
}