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
            // 영속
            Member member1 = em.find(Member.class, 150L); // SELECT 쿼리로 조회 후 1차캐시에 저장
            member1.setName("AAAAA");

            em.clear(); // 1차 캐시 초기화

            Member member2 = em.find(Member.class, 150L); // 다시 SELECT 쿼리 필요

            System.out.println("==================");
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
