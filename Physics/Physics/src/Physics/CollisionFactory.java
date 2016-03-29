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
public class CollisionFactory {

    /**
     * Create a CollisionRectangle-Object
     * @param centerPoint
     * @param size
     * @return the CollisionRectangle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint,
            Vector size) {
        return createCollision(centerPoint, size, 0);
    }

    /**
     * Create a CollisionRectangle-Object
     * @param centerPoint
     * @param size
     * @param angle
     * @return the CollisionRectangle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint,
            Vector size, float angle) {
        return new CollisionRotated(centerPoint, new CollisionRectangle(
                                    Vector.ZERO, size), angle);
    }

    /**
     * Create a CollisionCircle-Object
     * @param centerPoint
     * @param radius
     * @return the CollisionCircle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint,
            float radius) {
        return createCollision(centerPoint, radius, 0);
    }

    /**
     * Create a CollisionCircle-Object
     * @param centerPoint
     * @param radius
     * @param angle
     * @return the CollisionCircle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint,
            float radius, float angle) {
        return new CollisionRotated(centerPoint,
                                    new CollisionCircle(Vector.ZERO, radius),
                                    angle);
    }

    /**
     * Create a CollisionTriangle-Object
     * @param p1
     * @param p2
     * @param p3
     * @return the CollisionTriangle-Object
     */
    public static CollisionObject createCollision(Vector p1, Vector p2,
            Vector p3) {
        return createCollision(p1, p2, p3, 0);
    }

    /**
     * Create a CollisionTriangle-Object
     * @param p1
     * @param p2
     * @param p3
     * @param angle
     * @return the CollisionTriangle-Object
     */
    public static CollisionObject createCollision(Vector p1, Vector p2,
            Vector p3, float angle) {
        Vector c = CollisionTriangle.getCenter(p1, p2, p3);
        return new CollisionRotated(c, new CollisionTriangle(p1.sub(c), p2.
                                                             sub(c), p3.sub(c)),
                                    angle);
    }

    /**
     * Create a CollisionTriangle-Object
     * @param centerPoint
     * @param p1
     * @param p2
     * @param p3
     * @return the CollisionTriangle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint, Vector p1,
            Vector p2, Vector p3) {
        return createCollision(centerPoint, p1, p2, p3, 0);
    }

    /**
     * Create a CollisionTriangle-Object
     * @param centerPoint
     * @param p1
     * @param p2
     * @param p3
     * @param angle
     * @return the CollisionTriangle-Object
     */
    public static CollisionObject createCollision(Vector centerPoint, Vector p1,
            Vector p2, Vector p3, float angle) {
        return new CollisionRotated(centerPoint,
                                    new CollisionTriangle(p1.sub(centerPoint),
                                                          p2.sub(centerPoint),
                                                          p3.sub(centerPoint),
                                                          Vector.ZERO), angle);
    }
}
