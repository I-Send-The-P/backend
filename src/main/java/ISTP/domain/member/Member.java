package ISTP.domain.member;

import ISTP.domain.BaseEntity;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.help.Answer;
import ISTP.domain.help.question.Question;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;

@Entity
public class Member extends BaseEntity { // 사용자

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;
    private String password;
    private String name;
    private String nickname;
    private int age;
    @Enumerated(STRING)
    private Gender gender;
    private String phoneNumber;
    @Enumerated(STRING)
    private BloodType myBloodType;

    private String address;
    private LocalDateTime lastDate; // 마지막 헌혈 날짜
    @Enumerated(STRING)
    private Alarm alarm; // 알람기능
    private int count; // 헌혈 횟수

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Accept> accepts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Answer> boards = new ArrayList<>();
}
