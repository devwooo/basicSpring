package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

    @AfterEach
    public void afterEach() {
        // @Test 가 실행될때마다 > @AfterEach가 실행된다.
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("One");

        repository.save(member);
        Member result =  repository.findById(member.getId()).get();
        
        //alt + Enter static Import 단축키
        assertThat(member).isEqualTo(result);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("one");
        repository.save(member1);
        
        // shift + F6 > 한번에 Rename 가능
        Member member2 = new Member();
        member2.setName("two");
        repository.save(member2);

        Member findMember = repository.findByName("one").get();
        assertThat(member1).isEqualTo(findMember);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("one");
        repository.save(member1);

        // shift + F6 > 한번에 Rename 가능
        Member member2 = new Member();
        member2.setName("two");
        repository.save(member2);

        List<Member> list =  repository.findAll();

        assertThat(list.size()).isEqualTo(2);
    }

}
