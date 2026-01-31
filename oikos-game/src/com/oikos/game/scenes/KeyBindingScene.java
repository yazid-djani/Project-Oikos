package com.oikos.game.scenes;

import com.oikos.engine.core.GameLoop;
import com.oikos.engine.core.Scene;
import com.oikos.engine.input.KeyBinding;
import com.oikos.engine.input.KeyBinding.Action;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Scène de configuration des touches.
 */
public class KeyBindingScene extends Scene {

    private Action[] actions;
    private int selectedIndex = 0;
    private boolean waitingForKey = false;
    private Action actionToRebind = null;

    // Scroll si trop d'options
    private int scrollOffset = 0;
    private static final int VISIBLE_ITEMS = 10;

    // Animation
    private float pulseTime = 0;
    private float blinkTime = 0;

    public KeyBindingScene(GameLoop gp) {
        super(gp);

        // Actions configurables (exclure certaines)
        actions = new Action[] {
                Action.MOVE_UP,
                Action.MOVE_DOWN,
                Action.MOVE_LEFT,
                Action.MOVE_RIGHT,
                Action.ACTION,
                Action.TOGGLE_MAP,
                Action.TOGGLE_NIGHT,
                Action.REGENERATE,
                Action.ZOOM_IN,
                Action.ZOOM_OUT,
                Action.ZOOM_RESET
        };
    }

    @Override
    public void update() {
        pulseTime += 0.1f;
        blinkTime += 0.2f;

        if (waitingForKey) {
            // Attendre une touche
            if (gp.inputH.hasKeyBeenPressed()) {
                int newKey = gp.inputH.getLastKeyPressed();

                // Annuler avec ÉCHAP
                if (newKey == KeyEvent.VK_ESCAPE) {
                    waitingForKey = false;
                    gp.inputH.stopWaitingForKey();
                    return;
                }

                // Assigner la nouvelle touche
                gp.inputH.keyBinding.setKey(actionToRebind, newKey);
                waitingForKey = false;
                gp.inputH.stopWaitingForKey();
            }
        } else {
            // Navigation
            if (gp.inputH.isKeyPressed(KeyEvent.VK_UP) || gp.inputH.isKeyPressed(KeyEvent.VK_Z)) {
                selectedIndex--;
                if (selectedIndex < 0) selectedIndex = actions.length; // +1 pour "Reset" et "Retour"
                updateScroll();
            }

            if (gp.inputH.isKeyPressed(KeyEvent.VK_DOWN) || gp.inputH.isKeyPressed(KeyEvent.VK_S)) {
                selectedIndex++;
                if (selectedIndex > actions.length) selectedIndex = 0;
                updateScroll();
            }

            // Sélection
            if (gp.inputH.isKeyPressed(KeyEvent.VK_ENTER) || gp.inputH.isKeyPressed(KeyEvent.VK_SPACE)) {
                if (selectedIndex < actions.length) {
                    // Configurer une touche
                    actionToRebind = actions[selectedIndex];
                    waitingForKey = true;
                    gp.inputH.startWaitingForKey();
                } else if (selectedIndex == actions.length) {
                    // Reset
                    gp.inputH.keyBinding.resetToDefaults();
                }
            }

            // Retour
            if (gp.inputH.isKeyPressed(KeyEvent.VK_ESCAPE)) {
                gp.inputH.keyBinding.save(); // Sauvegarder avant de quitter
                gp.currentScene = new SettingsScene(gp);
            }
        }
    }

    private void updateScroll() {
        if (selectedIndex < scrollOffset) {
            scrollOffset = selectedIndex;
        } else if (selectedIndex >= scrollOffset + VISIBLE_ITEMS) {
            scrollOffset = selectedIndex - VISIBLE_ITEMS + 1;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        // Fond
        g2.setColor(new Color(25, 25, 45));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        // Titre
        g2.setFont(new Font("Arial", Font.BOLD, 36));
        g2.setColor(Color.WHITE);
        String title = "CONFIGURATION DES TOUCHES";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, (gp.screenWidth - titleWidth) / 2, 80);

        // Sous-titre
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.setColor(new Color(150, 150, 150));
        String subtitle = waitingForKey ?
                "Appuyez sur une touche... (ÉCHAP pour annuler)" :
                "Sélectionnez une action et appuyez sur ENTRÉE pour la modifier";
        int subWidth = g2.getFontMetrics().stringWidth(subtitle);
        g2.drawString(subtitle, (gp.screenWidth - subWidth) / 2, 115);

        // Liste des actions
        int startY = 160;
        int itemHeight = 45;
        int listX = gp.screenWidth / 2 - 250;
        int keyX = gp.screenWidth / 2 + 100;

        // Cadre de la liste
        g2.setColor(new Color(40, 40, 60));
        g2.fillRoundRect(listX - 20, startY - 10, 540, VISIBLE_ITEMS * itemHeight + 20, 15, 15);
        g2.setColor(new Color(60, 60, 80));
        g2.drawRoundRect(listX - 20, startY - 10, 540, VISIBLE_ITEMS * itemHeight + 20, 15, 15);

        // En-têtes
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(100, 150, 255));
        g2.drawString("ACTION", listX, startY - 20);
        g2.drawString("TOUCHE", keyX, startY - 20);

        // Actions
        int displayCount = Math.min(VISIBLE_ITEMS, actions.length + 1 - scrollOffset);

        for (int i = 0; i < displayCount; i++) {
            int actualIndex = i + scrollOffset;
            int y = startY + i * itemHeight + 30;

            boolean isSelected = (actualIndex == selectedIndex);
            boolean isAction = actualIndex < actions.length;

            // Fond de sélection
            if (isSelected) {
                g2.setColor(new Color(60, 80, 120));
                g2.fillRoundRect(listX - 15, y - 25, 530, 40, 10, 10);
            }

            if (isAction) {
                Action action = actions[actualIndex];
                String keyName = gp.inputH.keyBinding.getKeyNameForAction(action);

                // Nom de l'action
                if (isSelected) {
                    float pulse = (float) (1 + 0.05 * Math.sin(pulseTime));
                    g2.setFont(new Font("Arial", Font.BOLD, (int)(20 * pulse)));
                    g2.setColor(new Color(255, 220, 100));
                } else {
                    g2.setFont(new Font("Arial", Font.PLAIN, 18));
                    g2.setColor(Color.WHITE);
                }
                g2.drawString(action.getDisplayName(), listX, y);

                // Touche assignée
                if (waitingForKey && actionToRebind == action) {
                    // Animation clignotante
                    if ((int)(blinkTime) % 2 == 0) {
                        g2.setColor(new Color(255, 100, 100));
                        g2.drawString("???", keyX, y);
                    }
                } else {
                    // Cadre de la touche
                    int keyWidth = Math.max(60, g2.getFontMetrics().stringWidth(keyName) + 20);
                    g2.setColor(new Color(50, 50, 70));
                    g2.fillRoundRect(keyX - 5, y - 20, keyWidth, 28, 8, 8);
                    g2.setColor(isSelected ? new Color(255, 220, 100) : new Color(100, 200, 255));
                    g2.drawRoundRect(keyX - 5, y - 20, keyWidth, 28, 8, 8);

                    g2.setFont(new Font("Consolas", Font.BOLD, 16));
                    g2.drawString(keyName, keyX + 5, y);
                }
            } else {
                // Option "Réinitialiser"
                if (isSelected) {
                    g2.setFont(new Font("Arial", Font.BOLD, 20));
                    g2.setColor(new Color(255, 150, 100));
                } else {
                    g2.setFont(new Font("Arial", Font.PLAIN, 18));
                    g2.setColor(new Color(200, 150, 100));
                }
                g2.drawString("⟲ Réinitialiser par défaut", listX, y);
            }
        }

        // Indicateurs de scroll
        if (scrollOffset > 0) {
            g2.setColor(new Color(150, 150, 150));
            g2.drawString("▲", gp.screenWidth / 2, startY - 5);
        }
        if (scrollOffset + VISIBLE_ITEMS < actions.length + 1) {
            g2.setColor(new Color(150, 150, 150));
            g2.drawString("▼", gp.screenWidth / 2, startY + VISIBLE_ITEMS * itemHeight + 10);
        }

        // Instructions
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        g2.setColor(new Color(120, 120, 120));
        g2.drawString("↑↓ Naviguer | ENTRÉE Modifier | ÉCHAP Sauvegarder et quitter",
                gp.screenWidth / 2 - 220, gp.screenHeight - 30);
    }
}