package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    // JPA는 EntityManager로 동작한다.
    private final EntityManager em;

    //Spring Boot 가 EntityManager 를 생성하여 자동으로 DI 해준다.
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // PK 같은경우는 아래와 같이 조회할 수 있는데
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // PK가 아닌경우는 다음과 같이 조회한다. JPQL을 사용한다.
        // 객체를 대상으로 쿼리를 날린다. Member Entity를 대상으로 조회하며 조회대상은 Entity 그자체 m을 조회한다.
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
