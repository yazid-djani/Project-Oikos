package com.oikos.engine.input;

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Gère les raccourcis clavier configurables.
 * Sauvegarde/charge depuis un fichier.
 */
public class KeyBinding {

    // Actions disponibles
    public enum Action {
        MOVE_UP("Haut", KeyEvent.VK_Z),
        MOVE_DOWN("Bas", KeyEvent.VK_S),
        MOVE_LEFT("Gauche", KeyEvent.VK_Q),
        MOVE_RIGHT("Droite", KeyEvent.VK_D),
        ACTION("Action", KeyEvent.VK_E),
        TOGGLE_MAP("Vue carte", KeyEvent.VK_M),
        TOGGLE_NIGHT("Jour/Nuit", KeyEvent.VK_N),
        REGENERATE("Régénérer", KeyEvent.VK_R),
        ZOOM_IN("Zoom +", KeyEvent.VK_EQUALS),
        ZOOM_OUT("Zoom -", KeyEvent.VK_MINUS),
        ZOOM_RESET("Zoom reset", KeyEvent.VK_0),
        PAUSE("Pause", KeyEvent.VK_ESCAPE),
        CONFIRM("Confirmer", KeyEvent.VK_ENTER),
        BACK("Retour", KeyEvent.VK_ESCAPE);

        private final String displayName;
        private final int defaultKey;

        Action(String displayName, int defaultKey) {
            this.displayName = displayName;
            this.defaultKey = defaultKey;
        }

        public String getDisplayName() { return displayName; }
        public int getDefaultKey() { return defaultKey; }
    }

    // Mappings actuels
    private Map<Action, Integer> bindings;

    // Fichier de sauvegarde
    private static final String CONFIG_FILE = "keybindings.cfg";

    public KeyBinding() {
        bindings = new HashMap<>();
        resetToDefaults();
        load(); // Charger les paramètres sauvegardés
    }

    /**
     * Remet toutes les touches par défaut.
     */
    public void resetToDefaults() {
        for (Action action : Action.values()) {
            bindings.put(action, action.getDefaultKey());
        }
        System.out.println("[KeyBinding] Touches réinitialisées par défaut");
    }

    /**
     * Récupère la touche assignée à une action.
     */
    public int getKey(Action action) {
        return bindings.getOrDefault(action, action.getDefaultKey());
    }

    /**
     * Assigne une nouvelle touche à une action.
     */
    public void setKey(Action action, int keyCode) {
        // Vérifier si la touche est déjà utilisée
        for (Map.Entry<Action, Integer> entry : bindings.entrySet()) {
            if (entry.getValue() == keyCode && entry.getKey() != action) {
                // Échanger les touches
                bindings.put(entry.getKey(), bindings.get(action));
                break;
            }
        }

        bindings.put(action, keyCode);
        System.out.println("[KeyBinding] " + action.getDisplayName() + " = " + getKeyName(keyCode));
    }

    /**
     * Récupère le nom lisible d'une touche.
     */
    public static String getKeyName(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_SPACE: return "ESPACE";
            case KeyEvent.VK_ENTER: return "ENTRÉE";
            case KeyEvent.VK_ESCAPE: return "ÉCHAP";
            case KeyEvent.VK_SHIFT: return "SHIFT";
            case KeyEvent.VK_CONTROL: return "CTRL";
            case KeyEvent.VK_ALT: return "ALT";
            case KeyEvent.VK_TAB: return "TAB";
            case KeyEvent.VK_UP: return "↑";
            case KeyEvent.VK_DOWN: return "↓";
            case KeyEvent.VK_LEFT: return "←";
            case KeyEvent.VK_RIGHT: return "→";
            case KeyEvent.VK_EQUALS: return "+";
            case KeyEvent.VK_MINUS: return "-";
            default: return KeyEvent.getKeyText(keyCode).toUpperCase();
        }
    }

    /**
     * Sauvegarde les paramètres dans un fichier.
     */
    public void save() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CONFIG_FILE))) {
            for (Map.Entry<Action, Integer> entry : bindings.entrySet()) {
                writer.println(entry.getKey().name() + "=" + entry.getValue());
            }
            System.out.println("[KeyBinding] Paramètres sauvegardés");
        } catch (IOException e) {
            System.err.println("[KeyBinding] Erreur de sauvegarde : " + e.getMessage());
        }
    }

    /**
     * Charge les paramètres depuis un fichier.
     */
    public void load() {
        File file = new File(CONFIG_FILE);
        if (!file.exists()) {
            return; // Pas de fichier, utiliser les valeurs par défaut
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    try {
                        Action action = Action.valueOf(parts[0]);
                        int keyCode = Integer.parseInt(parts[1]);
                        bindings.put(action, keyCode);
                    } catch (IllegalArgumentException e) {
                        // Action inconnue, ignorer
                    }
                }
            }
            System.out.println("[KeyBinding] Paramètres chargés");
        } catch (IOException e) {
            System.err.println("[KeyBinding] Erreur de chargement : " + e.getMessage());
        }
    }

    /**
     * Récupère toutes les actions pour l'affichage.
     */
    public Action[] getAllActions() {
        return Action.values();
    }

    /**
     * Récupère le nom de la touche pour une action.
     */
    public String getKeyNameForAction(Action action) {
        return getKeyName(getKey(action));
    }
}