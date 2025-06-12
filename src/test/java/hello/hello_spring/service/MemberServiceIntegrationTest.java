package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.JdbcMemberRepository;
import hello.hello_spring.repository.MemberRepository;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {

    @Autowired
    MemberService service;

    @Autowired
    MemberRepository mm;


    @Test
    void join() {
        //given 주어진것
        Member mem = new Member();
        mem.setName("ss");

        //when   상황
        Long saveId = service.join(mem);
        Member findMember = service.findOne(saveId).get();

        //then   그 결과
        assertThat(mem.getId()).isEqualTo(findMember.getId());
    }

    @Test
    public void 중복_회원_예외() {
        // given
        Member mem1 = new Member();
        mem1.setName("one");

        Member mem2 = new Member();
        mem2.setName("one");

        // when
        service.join(mem1);
/*

        try {
            service.join(mem2);
            fail("실패");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");
        }
*/
        //assertThrows 는 junit org.junit.jupiter.api.Assertions 를 import 해야한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> service.join(mem2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원 입니다.");


        //then


    }

    @Test
    void findMembers() {
        // given
        Member mem1 = new Member();
        mem1.setName("one");
        service.join(mem1);
        Member mem2 = new Member();
        mem2.setName("two");
        service.join(mem2);

        List<Member> members = service.findMembers();
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    void findOne() {
        Member mem1 = new Member();
        mem1.setName("one");
        service.join(mem1);

        service.findOne(mem1.getId()).ifPresent(m -> {
            if (m.getId().equals(mem1.getId())) {
            }
        });


    }
}