package java.main.algorithm.quiz;

import java.util.Scanner;

/**
 * Created by Billin on 2016/5/18.
 *
 */
public class ConvertAlpha {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //radix 是进制数
        String b = scanner.next();
        System.out.println((char)(b.getBytes()[0]+32) + " " + (b.getBytes()[0]+32));
        char c = '你';
        System.out.println(c + " " + (int)c);
    }
}
