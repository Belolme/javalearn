package effetivejava.methods_commond;


/**
 * Created by billin on 16-8-12.
 * Compare Test
 */
public class CompareTest {

    public static void main(String[] args) {
        Decimals d_1_1 = new Decimals(1, 1);
        Decimals d_2_2 = new Decimals(2, 2);
        Decimals d_1_1_c = new Decimals(1, 1);

        System.out.println(d_1_1.compareTo(d_2_2));
        //return -1

        System.out.println(d_2_2.compareTo(d_1_1));
        //return 1

        System.out.println(d_1_1.compareTo(d_1_1_c));
        //return 0
    }
}

class Decimals implements Comparable<Decimals> {

    private int real;
    private int imagenary;

    public Decimals(int real, int imagenary) {
        this.real = real;
        this.imagenary = imagenary;
    }

    @Override
    public int compareTo(Decimals other) {
        return real > other.real ?
                1 : real == other.real ? imagenary > other.imagenary ? 1 : imagenary == other.imagenary ? 0 : -1
                : -1;
    }

    @Override
    public String toString() {
        return "Decimals{" +
                "real=" + real +
                ", imagenary=" + imagenary +
                '}';
    }
}
