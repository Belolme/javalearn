package javax.main.effetivejava.enums_and_annotations;

/**
 * Created by billin on 16-8-12.
 * enum test
 */
public class Planet {

    private enum PlanetEnum {
        MERCURY(3.302e+23, 2.439e6),
        VENUS(4.869e+24, 6.052e6),
        EARTH(5.975e+24, 6.378e6),
        MARS(6.419e+23, 3.393e6),
        JUPITER(1.899e+27, 7.149e7),
        SATURN(5.685e+26, 6.027e7),
        URANUS(8.683e+25, 2.556e7),
        NEPTUNE(1.024e+26, 2.477e7);

        private final double mass;
        // In kilograms
        private final double radius;
        // In meters
        private final double surfaceGravity; // In m / s^2
        // Universal gravitational constant in m^3 / kg s^2
        private static final double G = 6.67300E-11;

        // Constructor
        PlanetEnum(double mass, double radius) {
            this.mass = mass;
            this.radius = radius;
            surfaceGravity = G * mass / (radius * radius);
        }

        public double mass() {
            return mass;
        }

        public double radius() {
            return radius;
        }

        public double surfaceGravity() {
            return surfaceGravity;
        }

        public double surfaceWeight(double mass) {
            return mass * surfaceGravity; // F = ma
        }
/*
   如果改了这个方法, 那么程序运行不正常.
   enum的行为和final类十分相似.
   甚至可以拥有抽象方法
 */
//        @Override
//        public String toString() {
//            return "sd";
//        }
    }

    public static void main(String[] args) {

        double earthWeight = Double.parseDouble("99");
        double mass = earthWeight / PlanetEnum.EARTH.surfaceGravity();

        for (PlanetEnum p : PlanetEnum.values())
            System.out.printf("Weight on %s is %f%n",
                    p, p.surfaceWeight(mass));
        /*
        output:
        Weight on MERCURY is 37.412763
        Weight on VENUS is 89.600050
        Weight on EARTH is 99.000000
        Weight on MARS is 37.580796
        Weight on JUPITER is 250.438264
        Weight on SATURN is 105.485897
        Weight on URANUS is 89.580693
        Weight on NEPTUNE is 112.490088
         */
    }

}
