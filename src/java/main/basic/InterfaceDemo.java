package java.main.basic;

/**
 * Created by billin on 16-5-30.
 */
public class InterfaceDemo {

    interface Shape {
        int SIZE = 10;

        void draw();

        void dispose();
    }

    interface Color {
        void getColor();

        void setColor();

        interface ColorType {
            void setColorType();

            void getColorType();
        }
    }

    interface ColorShape extends Shape, Color {
        void draw();
    }

    private class Circle implements ColorShape {

        @Override
        public void draw() {
            System.out.println("Draw Circle");
        }

        @Override
        public void dispose() {
            System.out.println("dispose Circle");
        }

        @Override
        public void getColor() {
            System.out.println("Circle get color");
        }

        @Override
        public void setColor() {
            System.out.println("Circle set color");
        }

        class ColorTypeImp implements ColorType {

            @Override
            public void setColorType() {
                System.out.println("set circle color type");
            }

            @Override
            public void getColorType() {
                System.out.println("get circle color type");
            }
        }

    }

    private class Square implements Shape {

        class SquareColorType implements Color.ColorType {

            @Override
            public void setColorType() {
                System.out.println("set square color type");
            }

            @Override
            public void getColorType() {

                System.out.println("get square color type");
            }
        }

        @Override
        public void draw() {
            System.out.println("Draw Square");
        }

        @Override
        public void dispose() {
            System.out.println("dispose Square");
        }
    }


    static {

        System.out.println();
        System.out.println("Interface demo");
        InterfaceDemo interfaceDemo = new InterfaceDemo();
        Shape squareShape = interfaceDemo.new Square();
        squareShape.draw();
        System.out.println("square Shape size: " + Shape.SIZE);
        squareShape.dispose();


    }

    static {
        System.out.println();
        System.out.println("Inner interface demo");
        InterfaceDemo interfaceDemo = new InterfaceDemo();
        Circle circle = interfaceDemo.new Circle();
        circle.getColor();
        circle.setColor();
        circle.draw();
        circle.dispose();
    }

    public static void main(String[] args) {

    }
}