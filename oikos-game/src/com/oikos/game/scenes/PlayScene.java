package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public class PlayScene extends Scene {

    public PlayScene(GameLoop gp) {
        super(gp);
        // Ici, on chargera la map et les entités plus tard
    }

    @Override
    public void update() {
        // Touche ECHAP pour revenir au menu
        if (gp.inputH.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            gp.currentScene = new MenuScene(gp);
        }

        // Ici on mettra : player.update();
    }

    @Override
    public void draw(Graphics2D g2) {
        // Fond vert (l'herbe)
        g2.setColor(new Color(34, 139, 34));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        g2.setColor(Color.WHITE);
        g2.drawString("C'est le JEU ! (ZQSD pour bouger - bientôt)", 50, 50);
    }
}