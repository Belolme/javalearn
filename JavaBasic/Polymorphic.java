/**
 * Created by billin on 16-5-30.
 * Test demo for polymorphic
 */
public class Polymorphic {
    static {
        Shape circleShape = new Circle();
        Shape squareShape = new Square();
        System.out.println(circleShape);
        System.out.println(squareShape);


        try {
            System.out.println(((Square) squareShape));
            System.out.println(((Circle) squareShape));

        } catch (ClassCastException e) {
            System.out.println("Class cast error");
        }

        circleShape.dispose();
        squareShape.dispose();
    }

    public static void main(String[] args) {
    }
}


class Shape {
    public static void draw() {
        System.out.println("Draw a shape");
    }

    @Override
    public String toString() {
        return "Shape";
    }

    public void dispose() {
        System.out.println("dispose Shape");
    }
}

class Circle extends Shape {

    /**
     * Can't override a static method
     */
//    @Override
//    public static void draw(){
//
//    }
    @Override
    public String toString() {
        return "Circle";
    }

    @Override
    public void dispose() {
        super.dispose();
        System.out.println("dispose Circle");
    }
}

class Square extends Shape {
    @Override
    public void dispose() {
        super.dispose();
        System.out.println("dispose Square");
    }

    @Override
    public String toString() {
        return "Square";
    }
}