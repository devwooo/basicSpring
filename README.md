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





   
