package java.main.basic;

/**
 * Created by billin on 16-5-30.
 * Inner class java.main.functional.demo
 */
public class InnerClass {

    interface Fruit {
        void eat();
    }

    interface Meat {
        void eat();
    }

    interface FruitField {
        Fruit plant();
    }

    static class Apple implements Fruit {

        private Apple() {
        }

        @Override
        public void eat() {
            System.out.println("Eat Apples");
        }

        public static FruitField AppleField = Apple::new;
    }

    static class Banana implements Fruit {
        private Banana() {
        }

        @Override
        public void eat() {
            System.out.println("Eat Banana");
        }

        public static FruitField BananaField = Banana::new;
    }

    static private void eatFruit(FruitField fruitField) {
        fruitField.plant().eat();
    }

    static {
        System.out.println();
        System.out.println("Eat fruit");
        eatFruit(Apple.AppleField);
        eatFruit(Banana.BananaField);
    }

    /**
     * 多继承的实现方法之一
     */
    static class Hamburger {
        private class Lettuce implements Fruit {

            @Override
            public void eat() {
                System.out.println("Eat lettuce");
            }
        }

        private class Beef implements Meat {

            @Override
            public void eat() {
                System.out.println("Eat meat");
            }
        }

        private void eat() {
            Lettuce lettuce = new Lettuce();
            Beef beef = new Beef();
            lettuce.eat();
            beef.eat();
        }
    }

    static {
        System.out.println();
        System.out.println("Multi Implement test");
        new Hamburger().eat();
    }

    public static void main(String[] args) {

    }
}
