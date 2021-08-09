package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


//스프링부트에서는 기본적으로 In-Memory 방식으로 테스트 실행
@SpringBootTest //스프링 부트를 빌드한 상태로 테스트
@ExtendWith(SpringExtension.class)  //junit 실행시 스프링과 엮여서 실행
@Transactional  //테스트 케이스에서는 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //ginve
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush(); //insert 실행
        Assertions.assertEquals(member, memberRepository.findById(savedId).get());

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);

        Assertions.assertThrows(IllegalStateException.class,()->{memberService.join(member2);});

    }
}
