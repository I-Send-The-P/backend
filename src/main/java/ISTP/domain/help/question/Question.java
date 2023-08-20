package ISTP.domain.help.question;

import ISTP.domain.help.Answer;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;
    private String content;
    private LocalDateTime createDate; // 작성 시간
    private LocalDateTime updateDate; // 수정 시간

    @Enumerated
    @Column(name = "inquiry_status")
    private InquiryStatus status; //문의 상태

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();
}
