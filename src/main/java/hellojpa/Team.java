package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") // Member의 team으로 mapping되었음을 의미 (team에 의해 관리된다)
                                  // 여기에 값을 넣어봐야 아무 소용 없다, just 조회만 가능
    private List<Member> members = new ArrayList<Member>();

    // getter&setter========================
    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

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

    public void addMember(Member member){
        member.setTeam(this);
        members.add(member);
    }
}
