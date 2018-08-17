package java.main.algorithm.quiz;

/**
 * Created by Billin on 2016/5/18.
 *
 */
public class FactorialIteratorAdd {
    private static double factorial(int tailNumber){
        if(tailNumber<=1) return 1;
        return tailNumber*factorial(tailNumber-1);
    }

    private static double addBy(int tailNum){
        if(tailNum==1)  return 1;
        return 1.0/factorial(tailNum) + addBy(tailNum-1);
    }

    public static void main(String[]args){
        System.out.println(addBy(92));
        System.out.printf("%.2f",addBy(20));
    }

}
