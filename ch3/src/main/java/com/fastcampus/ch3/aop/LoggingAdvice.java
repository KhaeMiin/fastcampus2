package com.fastcampus.ch3.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingAdvice {
//    @Around("execution(* com.fastcampus.ch3.aop.MyMath.*(..))") // pointcut - 부가기능이 적용될 메서드 패턴
    @Around("execution(* com.fastcampus.ch3.aop.MyMath.add*(..))") // add로 시작하는 메서드만 호출시키기
    public Object methodCallLog(ProceedingJoinPoint pjp) throws Throwable {

        //Before
        long start = System.currentTimeMillis();
        System.out.println("<<[start]" + pjp.getSignature().getName() + Arrays.toString(pjp.getArgs()));

        Object result = pjp.proceed();//target의 메서드를 호출

        //after
        System.out.println("result = " + result);
        System.out.println("[end]>> " + (System.currentTimeMillis() - start) + "ms");
        return result;
    }
}
