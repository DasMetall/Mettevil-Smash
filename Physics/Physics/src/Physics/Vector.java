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
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Vector))
            return false;
        return this.x == ((Vector)other).x && this.y == ((Vector)other).y;
    }

    @Override
    public String toString() {
        return "{" + this.x + "; " + this.y + "}";
    }
}
