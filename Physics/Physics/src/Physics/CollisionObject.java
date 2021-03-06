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
public abstract class CollisionObject {
    private Vector centerPoint = new Vector();
    
    public CollisionObject(Vector centerPoint) {
        this.centerPoint = centerPoint;
    }
    
    public abstract boolean collide(Vector start, Vector end, boolean fastCheck);
    
    public abstract boolean containsPoint(Vector vector);

    public abstract boolean isNear(Vector vector);
    
    public abstract CollisionData getCollisionData(Vector start, Vector end);
    
    /**
     * @return the centerPoint
     */
    public final Vector getCenterPoint() {
        return centerPoint;
    }

    /**
     * @param centerPoint the centerPoint to set
     */
    public final void setCenterPoint(Vector centerPoint) {
        if (centerPoint==null) throw new IllegalArgumentException();
        this.centerPoint = centerPoint;
        moveCenter();
    }
    
    public final void moveCenterPoint(Vector movement) {
        if (movement==null) throw new IllegalArgumentException();
        this.centerPoint = this.centerPoint.add(movement);
        movePoints(movement);
        moveCenter();
    }
    
    protected void movePoints(Vector movement) {}
    
    protected void moveCenter() {}
}
