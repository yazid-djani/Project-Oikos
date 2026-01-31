package com.oikos.engine.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Gère les entrées clavier et souris.
 * Utilise KeyBinding pour les touches configurables.
 */
public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {

    // État des touches
    private boolean[] keys = new boolean[1024];
    private boolean[] keysJustPressed = new boolean[1024];

    // Configuration des touches
    public KeyBinding keyBinding;

    // Souris
    public int mouseX, mouseY;
    public boolean mousePressed;

    // Dernière touche pressée (pour la configuration)
    private int lastKeyPressed = -1;
    private boolean waitingForKey = false;

    public InputHandler() {
        this.keyBinding = new KeyBinding();
    }

    // === MÉTHODES POUR LES ACTIONS ===

    public boolean isUp() {
        return keys[keyBinding.getKey(KeyBinding.Action.MOVE_UP)];
    }

    public boolean isDown() {
        return keys[keyBinding.getKey(KeyBinding.Action.MOVE_DOWN)];
    }

    public boolean isLeft() {
        return keys[keyBinding.getKey(KeyBinding.Action.MOVE_LEFT)];
    }

    public boolean isRight() {
        return keys[keyBinding.getKey(KeyBinding.Action.MOVE_RIGHT)];
    }

    public boolean isAction() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.ACTION));
    }

    public boolean isToggleMap() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.TOGGLE_MAP));
    }

    public boolean isToggleNight() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.TOGGLE_NIGHT));
    }

    public boolean isRegenerate() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.REGENERATE));
    }

    public boolean isZoomIn() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.ZOOM_IN));
    }

    public boolean isZoomOut() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.ZOOM_OUT));
    }

    public boolean isZoomReset() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.ZOOM_RESET));
    }

    public boolean isPause() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.PAUSE));
    }

    public boolean isConfirm() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.CONFIRM));
    }

    public boolean isBack() {
        return isKeyPressed(keyBinding.getKey(KeyBinding.Action.BACK));
    }

    // === MÉTHODES GÉNÉRIQUES ===

    public boolean isKeyDown(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
            return keys[keyCode];
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keysJustPressed.length) {
            if (keysJustPressed[keyCode]) {
                keysJustPressed[keyCode] = false;
                return true;
            }
        }
        return false;
    }

    // === CONFIGURATION DES TOUCHES ===

    /**
     * Commence à attendre une touche pour la configuration.
     */
    public void startWaitingForKey() {
        waitingForKey = true;
        lastKeyPressed = -1;
    }

    /**
     * Arrête d'attendre une touche.
     */
    public void stopWaitingForKey() {
        waitingForKey = false;
    }

    /**
     * Est-ce qu'on attend une touche ?
     */
    public boolean isWaitingForKey() {
        return waitingForKey;
    }

    /**
     * Récupère la dernière touche pressée.
     */
    public int getLastKeyPressed() {
        int key = lastKeyPressed;
        lastKeyPressed = -1;
        return key;
    }

    /**
     * Vérifie si une touche a été capturée.
     */
    public boolean hasKeyBeenPressed() {
        return lastKeyPressed != -1;
    }

    // === GESTION DES ÉVÉNEMENTS ===

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code >= 0 && code < keys.length) {
            if (!keys[code]) {
                keysJustPressed[code] = true;
            }
            keys[code] = true;
        }

        // Si on attend une touche pour la configuration
        if (waitingForKey) {
            lastKeyPressed = code;
            waitingForKey = false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code >= 0 && code < keys.length) {
            keys[code] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

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

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}