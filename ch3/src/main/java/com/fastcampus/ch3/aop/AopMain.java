package com.fastcampus.ch3.aop;

import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AopMain {
    public static void main(String[] args) throws Exception {
        MyAdvice myAdvice = new MyAdvice();

        Class myClass = Class.forName("com.fastcampus.ch3.aop.MyClass");
        Object obj = myClass.newInstance();

        for(Method m : myClass.getDeclaredMethods()) {//getDeclaredMethods: 클래스에 정의되어있는 메서드들을 배열로 가져옴
            myAdvice.invoke(m, obj, null);
        }
    }
}

class MyAdvice {
    Pattern p = Pattern.compile("a.*");//조건주기

    boolean matches(Method m) {
        Matcher matcher = p.matcher(m.getName());
        return matcher.matches();
    }

    void invoke(Method m, Object obj, Object... args) throws Exception {
        //
        if (m.getAnnotation(Transactional.class) != null) {//@Transactional 에노테이션이 붙은 메서드만
            System.out.println("[before]{");
        }
        //
        m.invoke(obj, args);// aaa(), aaa2(), bbb() 호출 가능
        //
        if (m.getAnnotation(Transactional.class) != null) {
            System.out.println("}[after]");
        }
    }
}

class MyClass {
    @Transactional
    void aaa() {
        System.out.println("aaa() is called.");
    }
    void aaa2() {
        System.out.println("aaa2() is called.");
    }
    void bbb() {
        System.out.println("bbb() is called.");
    }
}
