package com.oikos.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    // On stocke l'état de toutes les touches physiques
    private boolean[] keys = new boolean[256];
    private boolean[] keysJustPressed = new boolean[256]; // Pour détecter un appui unique

    // --- CONFIGURATION DES TOUCHES (MAPPING) ---
    public int upKey = KeyEvent.VK_Z;
    public int downKey = KeyEvent.VK_S;
    public int leftKey = KeyEvent.VK_Q;
    public int rightKey = KeyEvent.VK_D;
    public int actionKey = KeyEvent.VK_E;

    // Souris
    public int mouseX, mouseY;
    public boolean mousePressed;

    // --- MÉTHODES UTILITAIRES POUR LE JEU ---

    public boolean isUp() { return keys[upKey]; }
    public boolean isDown() { return keys[downKey]; }
    public boolean isLeft() { return keys[leftKey]; }
    public boolean isRight() { return keys[rightKey]; }
    public boolean isAction() { return keys[actionKey]; }

    /**
     * Vérifie si une touche est actuellement enfoncée.
     */
    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return keys[keyCode];
        }
        return false;
    }

    /**
     * Vérifie si une touche vient d'être pressée (une seule fois).
     * À appeler dans update(), se reset automatiquement.
     */
    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keysJustPressed.length) {
            if (keysJustPressed[keyCode]) {
                keysJustPressed[keyCode] = false; // Consomme l'événement
                return true;
            }
        }
        return false;
    }

    // --- GESTION TECHNIQUE ---

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            if (!keys[code]) { // Première pression
                keysJustPressed[code] = true;
            }
            keys[code] = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = false;
        }
    }

    @Override public void keyTyped(KeyEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { mousePressed = true; }
    @Override
    public void mouseReleased(MouseEvent e) { mousePressed = false; }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}