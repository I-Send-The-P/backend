package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findById(Long requestId);

    List<Request> findAllByBloodTypeAndMemberNot(BloodType bloodType, Member member);
    List<Request> findAllByMemberNickname(String nickname);
}
