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
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            team.addMember(member);

            Team findTeam = em.find(Team.class, team.getId()); // 팀 조회 SELECT query
            List<Member> members = findTeam.getMembers(); // members 조회 SELECT query
            for (Member m : members) {
                System.out.println("m.getUsername() = " + m.getUsername());
            }

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
