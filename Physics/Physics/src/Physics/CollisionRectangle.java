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
        if (containsPoint(start) || containsPoint(end))
            return true;
        if (fastCheck)
            return false;
        Vector half = halfSize();
        Vector center = super.getCenterPoint();
        Vector hinv = new Vector(half.x, -half.y);
        return Vector.cutLines(start, end, center.add(half), center.add(hinv))
                || Vector.cutLines(start, end, center.add(hinv), center.
                                   sub(half))
                || Vector.cutLines(start, end, center.sub(half), center.
                                   sub(hinv))
                || Vector.cutLines(start, end, center.sub(hinv), center.
                                   add(half));
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

    @Override
    public boolean isNear(Vector vector) {
        return vector.sub(super.getCenterPoint()).lengthSqr() <= halfSize().
                lengthSqr();
    }

    @Override
    public CollisionData getCollisionData(Vector start, Vector end) {
        Vector half = halfSize();
        Vector center = super.getCenterPoint();
        Vector hinv = new Vector(half.x, -half.y);

        Vector _pos = null;
        int best = -1;
        Vector[][] data = {
            {half, hinv},
            {hinv, half.neg()},
            {half.neg(), hinv.neg()},
            {hinv.neg(), half}
        };

        for (int i = 0; i < data.length; ++i) {
            Vector pos = Vector.getCutPosition(start, end,
                                               center.add(data[i][0]),
                                               center.add(data[i][1]));
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
