//package com.fastcampus.ch3;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.GenericXmlApplicationContext;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component class Engine {} //<bean id="engine" class="com.fastcampus.ch3.Engine/>
//@Component class SuperEngine extends Engine {}
//@Component class TurboEngine extends Engine {}
//@Component class Door {}
//
//@Component
//class Car {
//    @Value("red") String color;
//    @Value("100") int oil;//value는 String 이지만 자동 변환 됨
//    @Autowired Engine engine; //Autowired: type먼저 검색, 여러개면 이름으로 검색 - engine, superEngine, TurboEngine
//    @Autowired Door[] doors;
//
//    public Car() { //기본 생성자 만드는 습관 기르기
//    }
//
//    public Car(String color, int oil, Engine engine, Door[] doors) {
//        this.color = color;
//        this.oil = oil;
//        this.engine = engine;
//        this.doors = doors;
//    }
//
////    public void setColor(String color) {
////        this.color = color;
////    }
////
////    public void setOil(int oil) {
////        this.oil = oil;
////    }
////
////    public void setEngine(Engine engine) {
////        this.engine = engine;
////    }
////
////    public void setDoors(Door[] doors) {
////        this.doors = doors;
////    }
//
//    @Override
//    public String toString() {
//        return "Car{" +
//                "color='" + color + '\'' +
//                ", oil=" + oil +
//                ", engine=" + engine +
//                ", doors=" + Arrays.toString(doors) +
//                '}';
//    }
//}
//
//public class SpringDiTest {
//    public static void main(String[] args) {
//        ApplicationContext ac = new GenericXmlApplicationContext("config.xml");
////        Car car = (Car) ac.getBean("car"); //byName
//        Car car = ac.getBean("car",Car.class); //byName (뒤에 타입을 작성해서 형변환을 생략함)
//        Car car2 = (Car) ac.getBean(Car.class); //byType
////        Engine engine = (Engine) ac.getBean("engine");//byName
////        Engine engine = (Engine) ac.getBean(Engine.class);//에러!같은 타입이 SuperEngine,TurboEngine,Engine 총 3개가 발견되기 때문에
////        Engine engine = (Engine) ac.getBean("superEngine");
////        Door door = (Door) ac.getBean("door");
//
//        //set 대신에: config.xml에서 <property>를 활용해서 값을 넣어줄 수 있다.//또는 @Autowired
////        car.setColor("red");
////        car.setOil(100);
////        car.setEngine(engine);
////        car.setDoors(new Door[] {ac.getBean("door", Door.class), ac.getBean("door", Door.class)});
//
//        System.out.println("car = " + car);
////        System.out.println("engine = " + engine);
//
//    }
//}
