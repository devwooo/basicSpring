package hello.hello_spring.domain;

import jakarta.persistence.*;


// ORM  Object 와 DB를 Mapping 시켜주는걸로 DTO에 @Entity 어노테이션을 붙여준다.
// @ ID는 Primary Key를 지정해준다.
// @ 자동생성 해주기위해  @GeneratedValue(strategy = GenerationType.IDENTITY) 어노테이션을 붙여준다
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name") // DB의 컬럼명 을 지정시켜주는 어노테이션이다
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
