package week3;

import java.lang.annotation.*;
import java.lang.reflect.Constructor;
import java.util.Arrays;

public class AnnotationsExample {
    public static void main(String[] args) {
        Class<Week3StudentTest> clazz = Week3StudentTest.class;
        Annotation[] annotations = clazz.getDeclaredAnnotations();
//        System.out.println(Arrays.toString(annotations));
        MyAnnotation myAnnotation = (MyAnnotation) annotations[0];
        System.out.println(myAnnotation.str());
    }
}

@MyAnnotation(str = "ddddd")
class Week3StudentTest {

}


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String str() default "abc";
}