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
            // my logic
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.getName() = " + member.getName());
            }

            // logic end

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
