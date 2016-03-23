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
        if (containsPoint(start)||containsPoint(end)) return true;
        if (fastCheck) return false;
        //... genaue Überprüfung
        return false;
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
