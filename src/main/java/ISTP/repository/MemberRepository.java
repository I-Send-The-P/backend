package ISTP.repository;

import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByLoginId(String loginId);
    public Member findByNickname(String nickname);
}
