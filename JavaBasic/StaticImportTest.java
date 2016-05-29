/**
 * Created by billin on 16-5-29.
 * Use to imported by other with static
 */
public class StaticImportTest {

    public static void print(String... strings){
        for(String s: strings){
            System.out.print(s);
        }
        System.out.println();
    }
}
