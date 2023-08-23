package ISTP.repository;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    /*@Query("select r from Request r join fetch r.member m where m.alarm = true and m.address like %:address% " +
            "and m.myBloodType = :bloodType")
    List<Request> findRegionByMemberBloodType(@Param(value = "address") String address,
                                              @Param(value = "bloodType") BloodType bloodType);*/

    @Query("select m from Member m where m.alarm = true and m.address like %:address% " +
            "and m.myBloodType = :bloodType")
    List<Member> findRegionByMemberBloodType(@Param(value = "address") String address,
                                              @Param(value = "bloodType") BloodType bloodType);
    // 성능최적화 무조건 필요

    @Query("select m from Member m where m.alarm = true and m.myBloodType = :bloodType")
    List<Member> findAllByMemberBloodType(@Param(value = "bloodType") BloodType bloodType);
    // 사용자가 address에 아무것도 입력 안했으면 다 alarm으로만 조회하는거 호출??
}
