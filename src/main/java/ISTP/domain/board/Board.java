package ISTP.domain.board;

import ISTP.domain.BaseEntity;
import ISTP.domain.member.Member;
import jakarta.persistence.*;

@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
