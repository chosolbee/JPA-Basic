package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // EntityManagerFactory는 db당 하나 존재
        // persistance.xml의 설정정보 이름이 hello -> 이를 읽어 가져옴
        EntityManagerFactory enf = Persistence.createEntityManagerFactory("hello");

        // 고객의 요청이 올 때마다 db 작업을 해야하면 EntityManager를 통해 작업 수행
        EntityManager em = enf.createEntityManager();

        // JPA의 모든 데이터 변경을 transaction 안에서 일어나야 한다
        EntityTransaction tx = em.getTransaction();

        // transaction 시작
        tx.begin();

        try{
            Member member1 = new Member();
            member1.setUsername("A");

            Member member2 = new Member();
            member2.setUsername("B");

            Member member3 = new Member();
            member3.setUsername("C");

            System.out.println("=================");

            em.persist(member1); // 1, 51로 맞춘다 -> 두번 call next value
            em.persist(member2); // Memory에서 호출 (미리 50개씩 가져왔으니까)
            em.persist(member3); // Memory에서 호출 (미리 50개씩 가져왔으니까)

            System.out.println("member1 = " + member1.getId());
            System.out.println("member2 = " + member2.getId());
            System.out.println("member3 = " + member3.getId());

            System.out.println("=================");

            tx.commit();
        }
        catch (Exception e){
            tx.rollback();
        }
        finally {
            em.close(); // db connection 반환
        }

        enf.close(); // resource release

    }
}
