package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(Model model){
        model.addAttribute("data","Spring Boot!!");
        return "hello";
    }

    @GetMapping(value = "hello-mvc")
    public String helloMvc(@RequestParam(value = "name", required = true) String name, Model model) {
        // parameter 확인 ctrl+p
        // viewResolver 가 지정된 경로의 hello-template.html을 찾아서
        // thymeleaf 엔진에게 넘긴 후 후처리 하고 html 파일을 던진다
        // static 경로의 파일인경우 엔진을 후처리 없이 바로 html 파일을 던진다.
        model.addAttribute("name", name);
        return "hello-template";
    }
}
