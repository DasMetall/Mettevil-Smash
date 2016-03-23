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
public class CollisionCircle extends CollisionObject {

    private float radius;

    public CollisionCircle(Vector centerPoint, float radius) {
        super(centerPoint);

    }

    @Override
    public boolean collide(Vector start, Vector end, boolean fastCheck) {
        if (containsPoint(start) || containsPoint(end))
            return true;
        if (fastCheck)
            return false;
        Vector b = end.sub(start);
        Vector m = super.getCenterPoint();
        Vector a = start;
        float d = b.x * b.x * b.y * b.y;
        if (d == 0)
            return false;
        float p = (b.x * (a.x - m.x) + b.y * (a.y - m.y)) / d;
        float q = ((a.x - m.x) * (a.x - m.x) + (a.y - m.y) * (a.y - m.y) - radius * radius) / d;
        float rad = p * p / 4 - q;
        if (rad < 0)
            return false;
        rad = (float)Math.sqrt(rad);
        float s = -p / 2 - rad;
        if (s >= 0 && s <= 1)
            return true;
        s = -p / 2 + rad;
        return s >= 0 && s <= 1;
    }

    @Override
    public boolean containsPoint(Vector vector) {
        if (vector == null)
            throw new IllegalArgumentException();
        return vector.sub(super.getCenterPoint()).lengthSqr() <= radius * radius;
    }

    /**
     * @return the radius
     */
    public float getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }

}
