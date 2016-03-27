/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Physics.test;

import Physics.*;

/**
 *
 * @author Max Brauer
 */
public class CollisionTest {

    public static void main(String[] args) {
        //Test: Schnittpunkt ermitteln [erfolgreich]
        testCut();
        //Test: Winkel manipulieren [erfolgreich]
        testAngle();
        //Test: Reflektion [erfolgreich]
        testReflection();
        //Test: Rechteck [erfolgreich]
        test(new CollisionRectangle(new Vector(4, 2), new Vector(2, 2)));
        //Test: Gedrehtes Rechteck [erfolgreich]
        test(new CollisionRotated(new Vector(4, 2), new CollisionRectangle(
                                  Vector.ZERO, new Vector(2, 2)),
                                  30 * (float)Math.PI / 180));
        //Test: Kreis [erfolgreich, aber kleine Ungenauigkeiten]
        test(new CollisionCircle(new Vector(4, 1.5f), 1));
        //Test: Dreieck [erfolgreich]
        test(new CollisionTriangle(new Vector(3,1), new Vector(4,3), new Vector(5, 1)));
    }

    private static void test(CollisionObject object) {
        System.out.println(
                "================================================================================");
        System.out.println("Collision: " + object.getClass().getName());
        Vector a = new Vector(2, 2),
                b = new Vector(4, 2),
                c = new Vector(6, 2);
//        a = new Vector(2.27f, 3);
        test(object, a, b);
        test(object, a, c);

    }

    private static void test(CollisionObject object, Vector v1, Vector v2) {
        System.out.println("\nPoints:\t\ta=" + v1 + "   b=" + v2);
        System.out.println("Near:\t\ta="
                + (object.isNear(v1) ? "true " : "false") + "   b="
                + (object.isNear(v2) ? "true " : "false"));
        System.out.println("Contains:\ta="
                + (object.containsPoint(v1) ? "true " : "false") + "   b="
                + (object.containsPoint(v2) ? "true " : "false"));
        System.out.println("Collide (fast):\t"
                + (object.collide(v1, v2, true) ? "true" : "false"));
        System.out.println("Collide (opt):\t"
                + (object.collide(v1, v2, false) ? "true" : "false"));
        CollisionData data = object.getCollisionData(v1, v2);
        System.out.println("Data:\t\tObject=\t\t\t" + data.getCollisionObject().
                getClass().getName());
        System.out.println("\t\tOwner=\t\t\t" + data.getCollisionOwner().
                getClass().getName());
        System.out.println("\t\tCollide=\t\t" + data.hasCollision());
        if (!data.hasCollision())
            return;
        System.out.println("\t\tCollision Point=\t" + data.getCollisionPoint());
        System.out.
                println("\t\tCollision Target=\t" + data.getCollisionTarget());
        System.out.println("\t\tNew Movement Vector=\t" + data.
                getMovementVectorAfterCollision());
        System.out.println("\t\tMove To Collision=\t" + data.
                getMovementLengthToCollision() + " (" + (100 * data.
                getMovementToCollision()) + "%)");
        System.out.println("\t\tMove After Collision=\t" + data.
                getMovementLengthAfterCollision() + " (" + (100 * data.
                getMovementAfterCollision()) + "%)");
//        System.out.println();
    }

    private static void testCut() {
        Vector g1 = new Vector(2, 2),
                g2 = new Vector(4, 2),
                h1 = new Vector(3, 1),
                h2 = new Vector(3, 3);
        System.out.println("================== CUT TEST ==================");
        System.out.println("g:\t\t" + g1 + " - " + g2);
        System.out.println("h:\t\t" + h1 + " - " + h2);
        System.out.println("cut (full):\t" + Vector.getCutPosition(g1, g2, h1,
                                                                   h2, false));
        System.out.println("cut (limit):\t" + Vector.getCutPosition(g1, g2, h1,
                                                                    h2, true));
    }

    private static void testAngle() {
        System.out.println("================== ANGLE TEST ==================");
        for (int i = 0; i < 10; ++i) {
            Vector v = Vector.UNITX.turn(10 * i * (float)Math.PI / 180);
            System.out.println(
                    "" + i + "0°:\t" + v
                    + (i == 0 ? "\t\t" : ((i + 2) / 3) % 2 == 1 || i == 0 ? "\t" : "")
                    + "\tlength=" + v.length() + "\tangle="
                    + (v.angle(Vector.UNITX) / (float)Math.PI * 180) + "°");
        }
    }

    private static void testReflection() {
        Vector dir = new Vector(1, 0), axis = new Vector(-1, (float)Math.sqrt(3));
        System.out.println(
                "================== REFLECTION TEST ==================");
        System.out.println("Direction:\t" + dir);
        System.out.println("Axis:\t\t" + axis);
        System.out.println("Reflection:\t" + Vector.getReflectDirection(dir,
                                                                        axis));
    }
}
