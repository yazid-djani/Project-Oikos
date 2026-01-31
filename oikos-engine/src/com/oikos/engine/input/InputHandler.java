package com.oikos.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    // On stocke l'état de toutes les touches physiques
    private boolean[] keys = new boolean[256];

    // --- CONFIGURATION DES TOUCHES (MAPPING) ---
    // Ce sont ces variables que ton Menu Paramètres modifiera plus tard.
    // Par défaut, on met ZQSD (Code ASCII pour un clavier standard)
    public int upKey = KeyEvent.VK_Z;
    public int downKey = KeyEvent.VK_S;
    public int leftKey = KeyEvent.VK_Q;
    public int rightKey = KeyEvent.VK_D;
    public int actionKey = KeyEvent.VK_E; // Touche pour interagir (Couper bois, etc.)

    // Souris
    public int mouseX, mouseY;
    public boolean mousePressed;

    // --- MÉTHODES UTILITAIRES POUR LE JEU ---

    // Vérifie l'action abstraite (ex: "Le joueur veut monter")
    public boolean isUp() { return keys[upKey]; }
    public boolean isDown() { return keys[downKey]; }
    public boolean isLeft() { return keys[leftKey]; }
    public boolean isRight() { return keys[rightKey]; }
    public boolean isAction() { return keys[actionKey]; }

    // --- GESTION TECHNIQUE (NE PAS TOUCHER) ---

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
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