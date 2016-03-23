/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Physics;

/**
 *
 * @author Max Brauer
 */
public class Vector {

    public float x;
    public float y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector add(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public Vector sub(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector neg(Vector other) {
        return new Vector(-this.x, -this.y);
    }

    public float mul(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector mul(float value) {
        return new Vector(this.x * value, this.y * value);
    }

    public float length() {
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float lengthSqr() {
        return this.x * this.x + this.y * this.y;
    }

    public float angle(Vector second) {
        return (float)Math.acos(this.mul(second) / (this.length() * second.
                length()));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector))
            return false;
        return this.x == ((Vector)other).x && this.y == ((Vector)other).y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Float.floatToIntBits(this.x);
        hash = 61 * hash + Float.floatToIntBits(this.y);
        return hash;
    }

    @Override
    public String toString() {
        return "{" + this.x + "; " + this.y + "}";
    }

    public static boolean cutLines(Vector g1, Vector g2, Vector h1, Vector h2) {
        Vector gd = g2.sub(g1),
                hd = h2.sub(h1);
        if (gd.mul(hd) == 1)
            return false;
        float d = hd.y * gd.x - hd.x * gd.y;
        if (d == 0)
            return false;
        float s = (gd.x * (g1.y - h1.y) - gd.y * (g1.x - h1.x)) / d;
        if (s < 0 || s > 1)
            return false;
        float r = (h1.x - g1.x + s * hd.x) / gd.x;
        return r >= 0 && r <= 1;
    }
}
