package com.oikos.engine.math;

public class Vector2 {
	public float x;
	public float y;

	// Constructeurs
	public Vector2() {
		this.x = 0;
		this.y = 0;
	}

	public Vector2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2 other) {
		this.x += other.x;
		this.y += other.y;
	}

	//Calcule la distance entre deux objets
	public float distance(Vector2 other) {
		float dx = this.x - other.x;
		float dy = this.y - other.y;
		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	@Override
	public String toString() {
		return "Vector2{" + x + ", " + y + "}";
	}
}