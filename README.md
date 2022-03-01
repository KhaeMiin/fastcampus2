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
		
