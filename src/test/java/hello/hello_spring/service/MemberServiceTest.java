package hello.hello_spring.service;

import hello.hello_spring.domain.Member;
import hello.hello_spring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;


class MemberServiceTest {

    MemberService service;
    MemoryMemberRepository mm;


    //Dependency Injection
    // MemberService 에 MemoryMemberRepository 를 주입해준다.
    @BeforeEach
    public void beforeEach() {
        mm = new MemoryMemberRepository();
        service = new MemberService(mm);
    }

    @AfterEach
    public void clearStore() {
        mm.clearStore();
    }

    @Test
    void join() {
        //given 주어진것
        Member mem = new Member();
        mem.setName("one");



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
    }

    @Test
    void findOne() {
    }
}