package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findById(Long requestId);

    //혈액형 타입으로 요청리스트 찾는 메서드 -> 내가 받은 알림 리스트에 뿌려줄 데이터가 필요해서 제가 추가했어요!
    List<Request> findAllByBloodType(BloodType bloodType);
}
