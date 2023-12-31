package ISTP.repository;

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

    @Query("select r from Request r where r.requestStatusId = 1L or r.requestStatusId = 2L order by r.createDate desc")
    Page<Request> findByDESC(Pageable pageable);

    @EntityGraph(attributePaths = {"member"})
    Request findOneById(Long requestId);
    List<Request> findAllByBloodTypeIdAndMemberNot(Long bloodTypeId, Member member);
    List<Request> findAllByMemberNickname(String nickname);

    @Query("select m from Member m where m.alarmStatus = true and m.address like %:address% " +
            "and m.myBloodTypeId = :bloodType")
    List<Member> findRegionByMemberBloodTypeId(@Param(value = "address") String address,
                                              @Param(value = "bloodType") Long bloodTypeId);
    // 성능최적화 무조건 필요

    @Query("select m from Member m where m.alarmStatus = true and m.myBloodTypeId = :bloodTypeId")
    List<Member> findAllByMemberBloodType(@Param(value = "bloodTypeId") Long bloodTypeId);
    // 사용자가 address에 아무것도 입력 안했으면 다 alarm으로만 조회하는거 호출??
    void deleteByMemberId(Long memberId);
}
