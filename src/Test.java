/**
 * Created by Billin on 2016/6/16.
 * Just for test
 */
public class Test {
    public static void main(String[] args) {

        int sum = 0;
        int sum1 = 0;
        int row = 100;

        for(int i=1, j=row; i<row; i++, j--){
            sum += i*j;
            sum1 += 8*j;
	    
        }
        System.out.println(sum);
        System.out.println(sum1);
    }
}
