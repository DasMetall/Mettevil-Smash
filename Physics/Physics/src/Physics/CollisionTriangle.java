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
public class CollisionTriangle extends CollisionObject {

    private Vector p1, p2, p3;
    private float radiusSqr;

    public CollisionTriangle(Vector p1, Vector p2, Vector p3) {
        super(getCenter(p1, p2, p3));
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        setRadius();
    }

    public CollisionTriangle(Vector p1, Vector p2, Vector p3, Vector centerPoint) {
        super(centerPoint);
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        setRadius();
    }

    @Override
    public boolean collide(Vector start, Vector end, boolean fastCheck) {
        return Vector.cutLines(start, end, p1, p2)
                || Vector.cutLines(start, end, p2, p3)
                || Vector.cutLines(start, end, p3, p1);
    }

    @Override
    public boolean containsPoint(Vector vector) {
        Vector ap = vector.sub(p1),
                bp = vector.sub(p2),
                cp = vector.sub(p3),
                ab = p2.sub(p1),
                bc = p3.sub(p2),
                ca = p1.sub(p3);
        float angle_a = angleSqrNoCos(ab, ca.neg()),
                angle_b = angleSqrNoCos(bc, ab.neg()),
                angle_c = angleSqrNoCos(ca, bc.neg()),
                angle_pa = angleSqrNoCos(ab, ap),
                angle_pb = angleSqrNoCos(bc, bp),
                angle_pc = angleSqrNoCos(ca, cp);
        return angle_a <= angle_pa && angle_b <= angle_pb && angle_c <= angle_pc;
    }

    @Override
    public boolean isNear(Vector vector) {
        return vector.sub(super.getCenterPoint()).lengthSqr() <= radiusSqr;
    }

    @Override
    protected void movePoints(Vector movement) {
        p1 = p1.add(movement);
        p2 = p2.add(movement);
        p3 = p3.add(movement);
    }

    @Override
    protected void moveCenter() {
        setRadius();
    }
    
    @Override
    public CollisionData getCollisionData(Vector start, Vector end) {
        Vector _pos = null;
        int best = -1;
        Vector[][] data = {
            {p1, p2},
            {p2, p3},
            {p3, p1}
        };

        for (int i = 0; i < data.length; ++i) {
            Vector pos = Vector.getCutPosition(start, end, data[i][0],
                                               data[i][1]);
            if (pos == null)
                continue;
            if (_pos != null)
                if (_pos.sub(start).lengthSqr() < pos.sub(start).lengthSqr())
                    continue;
            _pos = pos;
            best = i;
        }

        if (_pos == null)
            return new CollisionData(this, this, start, end);
        else {
            Vector dir = Vector.getReflectDirection(end.sub(start),
                                                    data[best][1].sub(
                                                            data[best][0]));
            return new CollisionData(this, this, start, end, _pos,
                                     _pos.add(dir.mul(end.sub(start).length()
                                             - _pos.sub(start).length())));
        }
    }
    
    private float angleSqrNoCos(Vector a, Vector b) {
        float v = a.mul(b);
        return v * v / (a.lengthSqr() * b.lengthSqr());
    }

    private void setRadius() {
        Vector center = super.getCenterPoint();
        radiusSqr = Math.max(center.sub(p1).lengthSqr(),
                             Math.max(center.sub(p2).lengthSqr(),
                                      center.sub(p3).lengthSqr()));
    }

    private static Vector getCenter(Vector p1, Vector p2, Vector p3) {
        Vector _p1 = p1.add(p2.sub(p1).mul(0.5f));
        Vector _p2 = p2.add(p3.sub(p1).mul(0.5f));
        return Vector.getCutPosition(p3, _p1, p1, _p2);
    }

    /**
     * @return the p1
     */
    public Vector getP1() {
        return p1;
    }

    /**
     * @param p1 the p1 to set
     */
    public void setP1(Vector p1) {
        this.p1 = p1;
        setRadius();
    }

    /**
     * @return the p2
     */
    public Vector getP2() {
        return p2;
    }

    /**
     * @param p2 the p2 to set
     */
    public void setP2(Vector p2) {
        this.p2 = p2;
        setRadius();
    }

    /**
     * @return the p3
     */
    public Vector getP3() {
        return p3;
    }

    /**
     * @param p3 the p3 to set
     */
    public void setP3(Vector p3) {
        this.p3 = p3;
        setRadius();
    }
}
