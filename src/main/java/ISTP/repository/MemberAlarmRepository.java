package ISTP.repository;

import ISTP.domain.MemberAlarm;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long> {

    List<MemberAlarm> findAllByAcceptMemberOrderByCreateDateDesc(Member acceptMember);
}
