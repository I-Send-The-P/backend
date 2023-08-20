package ISTP.domain.help.question;

import ISTP.domain.BaseEntity;
import ISTP.domain.help.Answer;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.*;

@Entity
public class Question extends BaseEntity { // 문의사항

    @Id
    @GeneratedValue
    @Column(name = "question_id")
    private Long id;

    private String title;
    private String content;

    @Enumerated(STRING)
    @Column(name = "inquiry_status")
    private InquiryStatus status; //문의 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(STRING)
    private ContentType contentType;

}
