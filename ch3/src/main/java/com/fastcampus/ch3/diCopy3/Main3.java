package com.fastcampus.ch3.diCopy3;

import com.google.common.reflect.ClassPath;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component class Car {}//guara library
@Component class SportsCar extends Car {}
@Component class Truck extends Car {}
@Component class Engine {}
//이클래스들이 자바 빈으로 등록된다는 뜻(왼쪽에 초록색 콩모양)

class AppContext {
    Map map; //객체 저장소
    
    AppContext(){
        map = new HashMap();
        doComponentScan();
    }

    private void doComponentScan() {
        try {
            // 1. 패키지내의 클래스 목록을 가져온다.
            // 2. 반복문으로 클래스를 하나씩 읽어와서 @Component이 붙어 있는지 확인
            // 3. @Component가 붙어있으면 객체를 생성해서 map에 저장
            ClassLoader classLoader = AppContext.class.getClassLoader();//클래스목록
            ClassPath classPath = ClassPath.from(classLoader);//ClassPath: 클래스를 찾기위한

            Set<ClassPath.ClassInfo> set = classPath.getTopLevelClasses("com.fastcampus.ch3.diCopy3");

            for(ClassPath.ClassInfo classInfo : set) {
                Class clazz = classInfo.load();
                Component component =(Component) clazz.getAnnotation(Component.class);
                if(component != null) {//@Component가 붙어있으면
                    String id = StringUtils.uncapitalize(classInfo.getSimpleName());
                    map.put(id, clazz.newInstance());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Object getBean(String key) { //byName
        return map.get(key);
    }
    Object getBean(Class clazz) { //byType
        for (Object obj : map.values()) {
            if (clazz.isInstance(obj))
                return obj;
        }
        return null;
    }
}

public class Main3 {
    public static void main(String[] args) throws Exception {
        AppContext ac = new AppContext();
        Car car = (Car) ac.getBean("car");//byName으로 객체를 검색!
        Car car2 = (Car) ac.getBean(Car.class);//byType으로 객체를 검색!
        Engine engine = (Engine) ac.getBean("engine");
        System.out.println("car = " + car);
        System.out.println("engine = " + engine);
    }
}
