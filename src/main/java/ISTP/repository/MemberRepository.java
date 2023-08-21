package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByLoginId(String loginId);
    public Member findByNickname(String nickname);

    public Optional<Member> findLoginByLoginId(String loginId);

    //혈액형 타입이 같은 모든 멤버를 찾는 메서드 -> 알림 요청을 위해
    List<Member> findAllByMyBloodType(BloodType bloodType);
}
