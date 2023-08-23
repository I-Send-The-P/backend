package ISTP.repository;

import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcceptRepository extends JpaRepository<Accept, Long> {


    //멤버가 요청
    List<Accept> findByMemberOrderByCreateDateDesc(Member member);
    void deleteByMemberId(Long memberId);
}
