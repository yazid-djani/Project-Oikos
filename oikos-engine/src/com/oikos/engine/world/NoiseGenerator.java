package com.oikos.engine.world;

import java.util.Random;

/**
 * Générateur de bruit de Perlin simplifié.
 * Produit des valeurs continues pour un terrain naturel.
 */
public class NoiseGenerator {

    private int[] permutation;
    private long seed;

    public NoiseGenerator(long seed) {
        this.seed = seed;
        this.permutation = new int[512];
        Random random = new Random(seed);

        // Générer la table de permutation
        int[] p = new int[256];
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }

        // Mélanger
        for (int i = 255; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = p[i];
            p[i] = p[j];
            p[j] = temp;
        }

        // Dupliquer pour éviter les débordements
        for (int i = 0; i < 512; i++) {
            permutation[i] = p[i & 255];
        }
    }

    public NoiseGenerator() {
        this(System.currentTimeMillis());
    }

    /**
     * Génère une valeur de bruit 2D entre 0 et 1.
     * @param x Coordonnée X
     * @param y Coordonnée Y
     * @param scale Échelle du bruit (plus grand = plus lisse)
     */
    public float noise(float x, float y, float scale) {
        x /= scale;
        y /= scale;

        // Coordonnées de la cellule
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;

        // Position relative dans la cellule
        float xf = x - (float) Math.floor(x);
        float yf = y - (float) Math.floor(y);

        // Courbes de fondu
        float u = fade(xf);
        float v = fade(yf);

        // Hash des coins
        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        // Interpolation
        float x1 = lerp(grad(aa, xf, yf), grad(ba, xf - 1, yf), u);
        float x2 = lerp(grad(ab, xf, yf - 1), grad(bb, xf - 1, yf - 1), u);

        // Normaliser entre 0 et 1
        return (lerp(x1, x2, v) + 1) / 2f;
    }

    /**
     * Génère du bruit avec plusieurs octaves (plus de détails).
     * @param octaves Nombre de couches de bruit
     * @param persistence Réduction d'amplitude par octave (0.5 recommandé)
     */
    public float fractalNoise(float x, float y, float scale, int octaves, float persistence) {
        float total = 0;
        float frequency = 1;
        float amplitude = 1;
        float maxValue = 0;

        for (int i = 0; i < octaves; i++) {
            total += noise(x * frequency, y * frequency, scale) * amplitude;
            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }

        return total / maxValue;
    }

    private float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    private float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    private float grad(int hash, float x, float y) {
        int h = hash & 3;
        float u = h < 2 ? x : y;
        float v = h < 2 ? y : x;
        return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
    }

    public long getSeed() {
        return seed;
    }
}