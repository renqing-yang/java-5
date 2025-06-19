package week3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicProxyExample {
    public static void main(String[] args) {
        Calculate proxyInstance = (Calculate) Proxy.newProxyInstance(
                DynamicProxyExample.class.getClassLoader(),
                new Class[]{Calculate.class},
                new CalInvocationHandler(new CalculatorImpl())
        );
//        proxyInstance.add(1, 2);
        int res = proxyInstance.minus(1, 2);
        System.out.println(res);
    }
}

interface Calculate {
    int add(int a, int b);
    int minus(int a, int b);
}
class CalculatorImpl implements Calculate {
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int minus(int a, int b) {
        return a - b;
    }
}

class CalInvocationHandler implements InvocationHandler {
    private final Object obj;

    public CalInvocationHandler(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
//        System.out.println(method);
//        System.out.println(Arrays.toString((args)));
        Object res = method.invoke(obj, args);
//        System.out.println(res);
        System.out.println("after");
        return res;
    }
}






/**
 *  ------------------------------------------------------------------------
 */

class CalculatorImplStaticProxy implements Calculate {
    private final Calculate originalInstance;

    public CalculatorImplStaticProxy(Calculate calculate) {
        this.originalInstance = calculate;
    }

    @Override
    public int add(int a, int b) {
        System.out.println("before");
        int ans = originalInstance.add(a, b);
        System.out.println("after");
        return ans;
    }

    @Override
    public int minus(int a, int b) {
        //..
        return 0;
    }
}

class CalculatorImplInheritanceProxy extends CalculatorImpl implements Calculate {
    @Override
    public int add(int a, int b) {
        System.out.println("before");
        int ans = super.add(a, b);
        System.out.println("after");
        return ans;
    }
}
