package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByLoginId(String loginId);
    Member findByNickname(String nickname);

    Optional<Member> findLoginByLoginId(String loginId);

    //혈액형 타입이 같은 모든 멤버를 찾는 메서드 -> 알림 요청을 위해
    List<Member> findAllByMyBloodType(BloodType bloodType);

    List<Member> findAllByMyBloodTypeAndAlarmStatusAndAddress(BloodType bloodType, boolean alarmStatus, String address);
    @Query("SELECT m FROM Member m WHERE m.count > 10 ORDER BY m.count DESC LIMIT 5")
    List<Member> findTop5ByCount();
}
