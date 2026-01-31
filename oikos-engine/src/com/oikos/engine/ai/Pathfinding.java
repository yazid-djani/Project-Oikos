package com.oikos.engine.ai;

import com.oikos.engine.math.Vector2;
import java.util.*;

/**
 * Algorithme A* générique pour la recherche de chemin.
 */
public class Pathfinding {

    // Carte des obstacles (true = bloqué)
    private boolean[][] blocked;

    private int mapWidth;
    private int mapHeight;

    // Directions possibles (4 directions)
    private static final int[][] DIRECTIONS = {
            {0, -1},  // Haut
            {1, 0},   // Droite
            {0, 1},   // Bas
            {-1, 0}   // Gauche
    };

    // Directions avec diagonales (8 directions)
    private static final int[][] DIRECTIONS_8 = {
            {0, -1}, {1, -1}, {1, 0}, {1, 1},
            {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
    };

    private boolean allowDiagonals;

    public Pathfinding(boolean allowDiagonals) {
        this.allowDiagonals = allowDiagonals;
    }

    /**
     * Charge la carte des obstacles.
     */
    public void loadMap(boolean[][] blockedMap) {
        this.blocked = blockedMap;
        this.mapWidth = blockedMap.length;
        this.mapHeight = blockedMap[0].length;
    }

    /**
     * Trouve le chemin le plus court entre deux points (en coordonnées tiles).
     * @return Liste de Vector2 (positions tiles) du chemin, ou null si impossible
     */
    public List<Vector2> findPath(int startX, int startY, int endX, int endY) {
        if (blocked == null) return null;

        // Vérifier que start et end sont valides
        if (!isValid(startX, startY) || !isValid(endX, endY)) {
            return null;
        }

        if (blocked[startX][startY] || blocked[endX][endY]) {
            return null;
        }

        // A* Algorithm
        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<String> closedList = new HashSet<>();
        Map<String, Node> allNodes = new HashMap<>();

        Node startNode = new Node(startX, startY, null, 0, heuristic(startX, startY, endX, endY));
        openList.add(startNode);
        allNodes.put(startNode.key(), startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            // Objectif atteint !
            if (current.x == endX && current.y == endY) {
                return reconstructPath(current);
            }

            closedList.add(current.key());

            // Explorer les voisins
            int[][] dirs = allowDiagonals ? DIRECTIONS_8 : DIRECTIONS;

            for (int[] dir : dirs) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (!isValid(nx, ny) || blocked[nx][ny] || closedList.contains(nx + "," + ny)) {
                    continue;
                }

                // Coût du mouvement (diagonal = 1.4, sinon 1)
                float moveCost = (dir[0] != 0 && dir[1] != 0) ? 1.414f : 1f;
                float newG = current.g + moveCost;

                String key = nx + "," + ny;
                Node neighbor = allNodes.get(key);

                if (neighbor == null) {
                    neighbor = new Node(nx, ny, current, newG, heuristic(nx, ny, endX, endY));
                    allNodes.put(key, neighbor);
                    openList.add(neighbor);
                } else if (newG < neighbor.g) {
                    // Meilleur chemin trouvé
                    openList.remove(neighbor);
                    neighbor.parent = current;
                    neighbor.g = newG;
                    neighbor.f = neighbor.g + neighbor.h;
                    openList.add(neighbor);
                }
            }
        }

        // Pas de chemin trouvé
        return null;
    }

    /**
     * Trouve un chemin en coordonnées pixels.
     * @param tileSize Taille d'une tile en pixels
     */
    public List<Vector2> findPathPixels(float startX, float startY, float endX, float endY, int tileSize) {
        int startTileX = (int) (startX / tileSize);
        int startTileY = (int) (startY / tileSize);
        int endTileX = (int) (endX / tileSize);
        int endTileY = (int) (endY / tileSize);

        List<Vector2> tilePath = findPath(startTileX, startTileY, endTileX, endTileY);

        if (tilePath == null) return null;

        // Convertir en pixels (centre des tiles)
        List<Vector2> pixelPath = new ArrayList<>();
        for (Vector2 tile : tilePath) {
            pixelPath.add(new Vector2(
                    tile.x * tileSize + tileSize / 2f,
                    tile.y * tileSize + tileSize / 2f
            ));
        }

        return pixelPath;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    private float heuristic(int x1, int y1, int x2, int y2) {
        // Distance de Manhattan (ou Euclidienne pour diagonales)
        if (allowDiagonals) {
            float dx = Math.abs(x2 - x1);
            float dy = Math.abs(y2 - y1);
            return (float) Math.sqrt(dx * dx + dy * dy);
        }
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    private List<Vector2> reconstructPath(Node endNode) {
        List<Vector2> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(0, new Vector2(current.x, current.y));
            current = current.parent;
        }

        return path;
    }

    /**
     * Noeud interne pour A*.
     */
    private static class Node implements Comparable<Node> {
        int x, y;
        Node parent;
        float g; // Coût depuis le départ
        float h; // Heuristique vers l'arrivée
        float f; // g + h

        Node(int x, int y, Node parent, float g, float h) {
            this.x = x;
            this.y = y;
            this.parent = parent;
            this.g = g;
            this.h = h;
            this.f = g + h;
        }

        String key() {
            return x + "," + y;
        }

        @Override
        public int compareTo(Node other) {
            return Float.compare(this.f, other.f);
        }
    }
}