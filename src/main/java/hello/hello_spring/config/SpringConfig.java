package hello.hello_spring.config;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.*;
import hello.hello_spring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {


//   Jdbc
//    private DataSource dataSouce;
//
//    @Autowired
//    public SpringConfig(DataSource dataSouce) {
//        this.dataSouce = dataSouce;
//    }

//    JPA
//    private EntityManager em;
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    // Spring data JPA 가 구현체를 자동으로 만들어주고 주입해준다
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // JAVA 코드로 bean등록
    // Jdbc, Jpa
//    @Bean
//    public MemberService memberService() {
//        return new MemberService(memberRepository());
//    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }


//    Jdbc, Jpa
//    @Bean
//    public MemberRepository memberRepository() {
////        return new MemoryMemberRepository();
////        return new JdbcMemberRepository(dataSouce);
////        return new JdbcTemplateMemberRepository(dataSouce);
////        return new JpaMemberRepository(em);
//    }

//    @Bean
//    public TimeTraceAop timeTraceAop() {
//        return new TimeTraceAop();
//    }

}
