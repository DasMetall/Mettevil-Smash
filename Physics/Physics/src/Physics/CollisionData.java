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
public class CollisionData {

    private CollisionObject collision,
            collisionOwner;
    private Vector start,
            end,
            collisionPoint,
            collisionTarget;
    private boolean hasCollision;

    public CollisionData(CollisionObject collision,
            CollisionObject collisionOwner, Vector start, Vector end,
            Vector collisionPoint, Vector collisionTarget) {
        this.collision = collision;
        this.collisionOwner = collisionOwner;
        this.start = start;
        this.end = end;
        this.collisionPoint = collisionPoint;
        this.collisionTarget = collisionTarget;
        this.hasCollision = true;
    }

    public CollisionData(CollisionObject collision,
            CollisionObject collisionOwner, Vector start, Vector end) {
        this.collision = collision;
        this.collisionOwner = collisionOwner;
        this.start = start;
        this.end = end;
        this.hasCollision = false;
    }

    /**
     * @return the collided collision object
     */
    public CollisionObject getCollisionObject() {
        return collision;
    }

    /**
     * @return the owner of the collided collision object
     */
    public CollisionObject getCollisionOwner() {
        return collisionOwner;
    }

    public void setCollisionOwner(CollisionObject owner) {
        collisionOwner = owner;
    }

    public Vector getMovementStart() {
        return start;
    }

    public Vector getMovementEnd() {
        return end;
    }

    public Vector getCollisionPoint() {
        return collisionPoint;
    }

    public Vector getCollisionTarget() {
        return collisionTarget;
    }

    public boolean hasCollision() {
        return this.hasCollision;
    }

    /**
     * @return the % of movement to the collision point
     */
    public float getMovementToCollision() {
        return collisionPoint.sub(start).length() / end.sub(start).length();
    }

    /**
     * @return the length of movement to the collision point
     */
    public float getMovementLengthToCollision() {
        return collisionPoint.sub(start).length();
    }

    /**
     * @return the % of movement after the collision point
     */
    public float getMovementAfterCollision() {
        return collisionTarget.sub(collisionPoint).length()
                / end.sub(start).length();
    }
    
    /**
     * @return the length of movement after the collision point
     */
    public float getMovementLengthAfterCollision() {
        return collisionTarget.sub(collisionPoint).length();
    }
    
    public Vector getMovementVectorAfterCollision() {
        return collisionTarget.sub(collisionPoint);
    }
}
