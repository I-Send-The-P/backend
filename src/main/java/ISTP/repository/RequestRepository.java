package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    Optional<Request> findById(Long requestId);

    @Query("select r from Request r order by r.createDate desc")
    Page<Request> findByDESC(Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    Request findOneById(Long requestId);
    List<Request> findAllByBloodTypeAndMemberNot(BloodType bloodType, Member member);
    List<Request> findAllByMemberNickname(String nickname);
}
