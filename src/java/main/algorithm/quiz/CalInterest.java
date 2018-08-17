package java.main.algorithm.quiz;

import java.util.Scanner;
import java.lang.System;

/**
 * Created by Billin on 2016/5/18.
 *
 */
public class CalInterest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double money = scanner.nextDouble();
        int year = scanner.nextInt();
        double rate = scanner.nextDouble();

        double interest = Math.pow(1+rate, year)*money - money;
        System.out.printf("%.2f", interest);

    }
}
