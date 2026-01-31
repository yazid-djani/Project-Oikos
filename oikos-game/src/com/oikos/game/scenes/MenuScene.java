package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class MenuScene extends Scene {

    public MenuScene(GameLoop gp) {
        super(gp);
    }

    @Override
    public void update() {
        // Si on appuie sur ENTRÉE (Enter), on lance le jeu
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ENTER)) {
            System.out.println("Lancement du jeu !");
            gp.currentScene = new PlayScene(gp); // On change la scène active !
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Fond d'écran
        g2.setColor(new Color(40, 40, 60)); // Bleu nuit
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Titre
        g2.setFont(new Font("Arial", Font.BOLD, 48));
        g2.setColor(Color.WHITE);
        String title = "OIKOS";
        // Astuce pour centrer le texte
        int textWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (gp.screenWidth - textWidth) / 2, 200);

        // Sous-titre
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        g2.drawString("Appuyez sur ENTRÉE pour jouer", (gp.screenWidth - 300) / 2, 300);
    }
}