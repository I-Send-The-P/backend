package ISTP.repository;

import ISTP.domain.MemberAlarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAlarmRepository extends JpaRepository<MemberAlarm, Long> {
}
