/**
 * 重写可变参数的方法
 * <p/>
 * Created by Billin on 2016/11/10.
 */
public class OverLoad {

    private static void f(float f, Character... c) {
        System.out.println("first");
    }

    private static void f(Character i, Character... c) {
        System.out.println("second");
    }

    public static void main(String[] args) {
        f(1, 'a');
        /*
        包装类型自动转换为float
         */
//        f('a', 'b');
    }
}
