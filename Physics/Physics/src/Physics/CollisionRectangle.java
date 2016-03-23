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
public class CollisionRectangle extends CollisionObject {

    private Vector size;

    public CollisionRectangle(Vector centerPoint, Vector size) {
        super(centerPoint);
        setSize(size);
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
        Vector center = super.getCenterPoint(),
                p1 = center.sub(halfSize()),
                p2 = center.add(halfSize());
        return p1.x <= vector.x && p2.x >= vector.x
                && p1.y <= vector.y && p2.y >= vector.y;
    }

    /**
     * @return the size
     */
    public final Vector getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public final void setSize(Vector size) {
        if (size == null)
            throw new IllegalArgumentException();
        if (size.x < 0)
            size.x = -size.x;
        if (size.y < 0)
            size.y = -size.y;
        this.size = size;
    }

    private Vector halfSize() {
        return size.mul(0.5f);
    }
}