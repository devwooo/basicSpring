package hello.hello_spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
public class HelloController implements WebMvcConfigurer {

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

    @GetMapping(value = "hello-string")
    @ResponseBody
    public String helloString(@RequestParam("name") String name) {
        // Data를 return 값 그대로 내려준다.
        return "hello " + name;
    }

    @GetMapping(value = "hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        // Data를 return 값 그대로 내려준다.
        Hello h = new Hello();
        h.setName(name);
        // return ==> {"name":"String!!!!!!!!!!!!!!"} << @ResponseBody 명명시 > JSON 데이터로 리턴된다.
        return h;
    }

    static class Hello {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
