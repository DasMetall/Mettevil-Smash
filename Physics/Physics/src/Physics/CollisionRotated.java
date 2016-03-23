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
public class CollisionRotated extends CollisionObject {

    private CollisionObject collision;
    private float angle;

    public CollisionRotated(Vector centerPoint, CollisionObject collision,
            float angle) {
        super(centerPoint);
        this.collision = collision;
        this.angle = angle % (2 * (float)Math.PI);
    }

    @Override
    public boolean collide(Vector start, Vector end, boolean fastCheck) {
        return getCollision().collide(transformToLocal(start), transformToLocal(
                                      end), fastCheck);
    }

    @Override
    public boolean containsPoint(Vector vector) {
        return getCollision().containsPoint(transformToLocal(vector));
    }

    @Override
    public boolean isNear(Vector vector) {
        return getCollision().isNear(transformToLocal(vector));
    }

    protected Vector transformToLocal(Vector global) {
        return global.sub(super.getCenterPoint()).turn(getAngle());
    }

    protected Vector transformToGlobal(Vector local) {
        return local.turn(-getAngle()).add(super.getCenterPoint());
    }

    /**
     * @return the collision
     */
    public CollisionObject getCollision() {
        return collision;
    }

    /**
     * @param collision the collision to set
     */
    public void setCollision(CollisionObject collision) {
        this.collision = collision;
    }

    /**
     * @return the angle
     */
    public float getAngle() {
        return angle;
    }

    /**
     * @param angle the angle to set
     */
    public void setAngle(float angle) {
        this.angle = angle % (2 * (float)Math.PI);
    }

    public void turnAngle(float angle) {
        this.angle = (this.angle + angle) % (2 * (float)Math.PI);
    }
}
