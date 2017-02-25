package type;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * Test source of dynamic proxy
 * <p/>
 * Created by Billin on 2017/2/24.
 */
public class DynamicProxy {

    interface TestInterface {

        void doSomething();

        void doElse();
    }

    public static void consumer(TestInterface testInterface) {
        testInterface.doSomething();
        testInterface.doElse();
    }

    public static void main(String[] args) {
        TestInterface testInterface = new TestInterface() {
            @Override
            public void doSomething() {
                System.out.println("do something");
            }

            @Override
            public void doElse() {
                System.out.println("do else");
            }
        };

        consumer(testInterface);

        // Insert a proxy and try invoke the method again
        TestInterface proxy = (TestInterface) Proxy.newProxyInstance(
                TestInterface.class.getClassLoader(),
                new Class[]{TestInterface.class},
                new DynamicProxyHandler(testInterface));
        consumer(proxy);
    }

    /*
    result:
    do something
    do else
    proxy: class type.$Proxy0 method: public abstract void type.DynamicProxy$TestInterface.doSomething() args: null
    do something
    proxy: class type.$Proxy0 method: public abstract void type.DynamicProxy$TestInterface.doElse() args: null
    do else
     */

}

class DynamicProxyHandler implements InvocationHandler {
    private Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("proxy: " + proxy.getClass()
                + " method: " + method + " args: " + Arrays.toString(args));

        if (args != null) {
            for (Object arg : args)
                System.out.println(" " + arg);
        }

        return method.invoke(proxied, args);
    }
}
