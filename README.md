# fastcampus
[스프링의 정석]수업 정리
# Spring DI
### 1. 빈(Bean)이란?
 **Spring Bean** 
- POJO(plain Old java Object): EJB의 반댓말 즉 심플하다., 단순하고 독립적
- Spring Container가 관리

**Bean**
- Spring Container가 관리하는 객체
	- Spring Container: Bean저장소, Bean을 저장, 관리(생성, 소멸, 연결(@Autowired같은)
		1. BeanFactoy - 
			Bean을 생성, 연결 등의 기본 기능을 정의해놓은 인터페이스(콩만드는 공장)
		2. ApplicationContext
		BeanFactory를 확장해서 **여러 기능을 추가** 정의
		
	※다양한 종류의 ApplicationContext구현체 제공		
	|AC의 종류|XML(```<bean>```)|Java Config(```@Bean```)|
	|---|----|----|
	|non-Web|GenericXmlApplicationContext|AnnotationConfigApplicationContext|
	|Web|XmlWebApplicationContext|AnnotionConfigWebApplicationCotext|
	|부가설명 |text문서이기 때문에 컴파일러보단 떨어짐 |자바코드가 더 유리(컴파일러 체크)



**ApplicationContext의 주요 메서드**
GitHub 참고
[Gti: ApplicationContext Method 코드 보기](https://github.com/KhaeMiin/fastcampus2/blob/master/ch3/src/main/java/com/fastcampus/ch3/ApplicationContextTest.java)		

---
### Ioc와 DI
- **제어의 역전Ioc** 
 제어의 흐름을 전통적인 방식과 다르게 뒤바꾸는 것
- **의존성 주입DI**
사용할 객체를 외부에서 주입받는 것
'의존성 = 사용할 객체' 라고 생각하면 이해하기 쉽다. 

<br>
[전통적인 방식]사용자 코드가 Framework호출

```
Car car = new Car();
car.turboDrive();
///////////////////////////////////
void turboDrive() { //잘 변하지 않는 코드
	engine = new TurboEngine();//TurboEngine 잘 변하는 코드(SuperEngine으로 바뀔 수도 있고~)
	engine.start();
	...
}
```
<br>
[Ioc[ Framework 코드가 사용자 코드를 호출

```
Car car = new Car();
car.turboDrive(new SuperEngine);//new SuperEngine(Engine의 자손): 사용할 객체를 수동 주입(변경될 수 있는 부분을 밖으로 빼낸것)
///////////////////////////////////
//@Autowired: 사용할 객체를 자동 주입
void turboDrive(Engine engine) { //변하지 않는 코드 | 매개변수로 외부에서 주입받음)
	engine.start();
	...
}
//디자인 패턴: 전략패턴
```
---
### 스프링 에노테이션 @Autowired
인스턴스 변수(iv) setter, 참조형 매개변수를 가진 생성자, 메서드에 적용
- **byType**
- 생성자의 @Autowired 생략 가능(기본생성자 포함 생성자가 여러개일 경우 생략 불가능)
- setter  or  iv에 @Autowired를 직접붙이는 것은 주입해야할 빈을 누락할 수 있기 때문에 참조형 매개변수를 가진 생성자에 붙이는 것을 추천
- Spring container에서 타입으로 빈을 검색해서 참조 변수에 자동 주입(DI)
	- 검색된 빈이 n개이면, 그 중에 참조변수와 **이름이 일치**하는 것을 주입
	- 주입 대상이 **변수**일 때, 검색된 빈이 **1개**가 아니면 예외 발생
	- 주입 대방이 **배열**일 떄, 검색된 빈이 **n개**라도 예외 발생X
	- [참고] @Autowired(required=false)일 때, 주입할 빈을 못찾아도 예외 발생 안됨.


### 스프링 에노테이션 @Resource
Spring container에서 **이름으로 빈을 검색**해서 참조 변수에 자동 주입(DI)<br>
일치하는 이름의 빈이 없으면, 예외 발생
- byName
```
Class Car {
// 	@Resource(name="engine")//name생략 가능
	@Resource//name 생략시 참조변수명이 name이 됩니다.
	Engine engien
}
```	

<br>

### 스프링 에노테이션 @Component
```<component-scan>```으로 ```@Component```가 클래스를 자동 검색해서 빈으로 등록
```
//.xml
<context:component-scan base-package="com.project.package"/>
```
```
// <bean id="superEngine" class="com.project.package"/>
//Component("superEngine") //id 생략 가능
@Component
class SuperEngine extends Engine {}
```
- @Controller, @Service, @Repository, @ContollerAdvice의 메타 에노테이션

# TDD(Test Driven Development) 테스트 주도 개발
▶실행 결과를 눈으로 직접 확인하는게 아니라 JUnit이라는 프레임워크를 이용해서 테스트 자동화> 일괄적으로 한번에 돌려서 성공 실패 확인 가능

# DAO
### 1. DAO(Data Access Object)란?
- **데이터(data)에 접근(access)하기 위한 객체(Object)**
- Database에 저장된 데이터를 **쓰기(Create), 읽기(Read), 변경(Update), 삭제(Delete)을 수행** → **CRUD**
- **DB테이블당 하나의 DAO를 작성**

### 2. 계층(layer)의 분리
Controller(Presentation Layer : Data를 보여주는 계층)가 DAO(영속계층 Persistence Layer or Data Access Layer)를 통해서 간접적으로 Database에 접근한다.
- 중복코드 제거
- 관심사의 분리(데이터를 보여주는: Controller || 데이터에 접근하는: DAO)
- 변경이 유리하다.
- 보통 비즈니스Layer까지 총 Controller - Business Layer - DAO 3계층으로 나뉜다.
