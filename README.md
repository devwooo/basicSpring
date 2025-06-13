# basicSpring


### jar 파일 Build
 - 도스창으로 해당 프로젝트의 루트로 이동 후 ./gradlew build (jar 파일 생성)
 - root > build > libs > java -jar [생성된 파일] 이동하여 해당 서버를 작동할 수 있다.

### welcomePage 
- @Controller에서 "/"에 대한 매핑이 있으면 그걸 사용
- "/" 가 없다면src/main/resources/static/index.html or 또는 templates/index.html 템플릿


### resources/static 경로
- 정적 파일의 경로로써 localhost:8080/[static 하위 경로 ~]파일명 입력시 해당 파일을 그대로 노출시킨다.
- 즉 서버에서 로직 처리 없이 있는 그대로 전달된다.
  - Clinet url 요청시 > server 에서 스프링 컨테이너를 찾고 없을경우 static 하위의 파일을 찾아 노출시킨다
  - 보통 스프링 컨테이너에서 맵핑된 URL을 찾아 해당 메서드의 리턴값을 viewResolver 가 지정된 경로의 파일을 찾아 thymeleaf 엔진에게 넘긴 후 처리 하고 Client에게 다시 리턴한다.
- WebMvcConfigurer 를 상속하여 addResourceHandlers를 오버라이딩하여 resource경로를 추가할 수 있다.
- (Spring MVC so that you can modify that behavior by adding your own WebMvcConfigurer and overriding the addResourceHandlers method.)

```
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/assets/**") // 브라우저 경로
       .addResourceLocations("classpath:/my-static/"); // 실제 위치
    } 
```
### API
- @ResponseBody 사용시 
  - 스프링컨테이너가 @ResponseBody 가 붙어있을경우 HttpMessageConverter 위임  > ( 객체인경우 > MappingJackson2HttpMessageConverter, 기본 문자인경우 > StringHttpMessageConverter)
  - 그다음 웹브라우저 또는 서버로 리턴해준다.
  - HTTP Accept 헤더와 서버의 컨트롤러 반환 타입정보 둘을 조합해서 HttpMesaageConverter가 선택된다.


### DI(Dependency Injection)
- memberController > memberService > memberRepository 
- ComponentScan 을 통해  @Controller, @Service, @Repository 등의 어노테이션을 읽어 @Bean으로 생성하게된다.
- 생성하게 되면 서로의 의존관계에 맞게 스프링컨테이너가 주입하게 된다.
- 기본적으로 HelloSpringApplication 하위 패키지를 찾아 등록하게 된다.
- 기본으로 싱글톤으로 등록한다(유일하게 하나만 등록해서 공유한다)

### JAVA 코드로 bean 등록
- @Configuration 어노테이션으로 등록한 클래스 파일에서 @Bean 어노테이션을 통해 등록하게 된다.
- DI에는 생성자 주입(), 필드주입 @Autowired, setter주입 setMemberService() > public 해야 한다.
- 정형화 되지 않거나, 상황에 따라 구현 클래스를 변경해야 하면 설정을 통해 스프링 BEAN으로 등록한다.

### GetMapping/PostMapping


### DataBase 다운로드
- https://www.h2database.com/html/main.html > DOWNLOAD > all Platforms > 압축파일 풀고
- h2/bin/h2.bat(WINDOW 환경에선) 실행 
- 처음엔 그냥 연결 > 그 후 부터 접속할때 JDBC URL > jdbc:h2:tcp://localhost/~/test로 변경하여 접속 
- find /c -type f -name "test.mv.db" 2>/dev/null 명력어로 test.mv.db 파일의 위치를 확인할 수 있다.
- 
   
### 순수 JDBC
- 기존의 MemberRepository 를 확장하여 JdbcMemberRepository를 생성하여 DataSource 를 주입받고 
- Connection을 생성하여 DB와 연결하여 실제 DATA를 처리할 수 있도록 하였다.
- Connectiont 생성과 PrepareStatement, close 처리등이 반복된다.

- Member ->     MemberRepository(interface)
-                |                    |
-         MemoryMemberRepository   JdbcMemberRepository     
  


### 스프링 통합 테스트
- @SpringBootTest를 붙이면 스프링 컨테이너와 함께 테스트를 실행하게 된다.
- 기본적인 Service와 Repository를 주입을 해준다.
- Data는 insert, update .. 등등  commit 이 되어야 DB에 적용이 된다. (AutoCommit이 아닌경우) 하지만 @Transactional을
- 붙이면 테스트 후에 rollback을 실행해주기 때문에 테스트가 계속 진행이 가능하다.


### 스프링 JdbcTemplate
- 기존의 반복 코드를 제거해준다.
```
RowMapper<E> 를 통해 쿼리의 결과값을 리턴받는 Domain에 셋팅해 리턴해준다.
private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
```

### JPA
- JPA dependency 추가 및 application-property JPA 속성 추가
- 엔티티 맵핑해야 한다.
- Service 계층에 @Transactional 어노테이션을 붙여줘 트랜잭션을 유지시켜준다.
- ORM  Object 와 DB를 Mapping 시켜주는걸로 DTO에 @Entity 어노테이션을 붙여준다.
- @ ID는 Primary Key를 지정해준다.
- @ 자동생성 해주기위해  @GeneratedValue(strategy = GenerationType.IDENTITY) 어노테이션을 붙여준다
- @Column(name = "name") // DB의 컬럼명 을 지정시켜주는 어노테이션이다
- PK를 대상으로 조회하는 경우는 다음과 같이 조회한다
```
 Member member = em.find(Member.class, id);
```
- PK가 아닌경우는 다음과 같이 조회한다. JPQL을 사용한다.
- 객체를 대상으로 쿼리를 날린다. Member Entity를 대상으로 조회하며 조회대상은 Entity 그자체 m을 조회한다.
```
==
List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class).setParameter("name", name).getResultList();
return result.stream().findAny();
==
List<member> reuslt = em.createQuery("select m from Member m", Member.class).getResultList();
==
```


### 스프링 데이터 JPA
- 스프링 부트 + JPA > 스프링 데이터 JPA
- 공부 순서 JPA > 스프링 데이터 JPA
- interface를 생성하고 JpaRepository<Member, Long>, MemberRepository 를 상속하면
- Spring data JPA가 자동으로 구현체를 만들어준다. 따라서 service에서 MemberRepository 만 주입해주면 한번에 해결된다.
- 공통화 할 수 없는 메서드인경우는 interface에 직접 명시해준다.


### AOP
- 공통 관심사(cross-cutting concern) : 모든 부분에 동일하게 동작하는 부분 / 핵심 관심 사항(core concern) : 비즈니스 로직
- AOP(Aspect Oriented Programing) : 공통 관심사와 핵심 관심사를 분리
- @Aspect 어노테이션을 붙여줘야 하며, @Component 어노테이션을 붙여서 @Bean 등록을 하거나 아니면, Config에서 직접 @Bean 등록을 해준다.
- 해당 공통 메서드의 실행 조건은 @Around 를 통해서 지정할 수 있다.
- ProceedingJoinPoint 를 통해서 현재 진행중인 메서드 확인과, 다음을 호출할 수 있다. 
- helloController -> 프록시 memberService ---(joinPoint.proceed())---> 실제 memberService


