package ISTP.Dtos.request;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import ISTP.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    private RequestStatus status;
    private String title;
    private String sickness;
    private String number;
    private String hospital;
    private BloodType bloodType;
    private LocalDateTime duration;
    private String relationship;
    private String content;
    private String requests_blood_type;
    private LocalDateTime createdTime;
    private String nickname;
    private String phone_number;

    public RequestDto(Request request, Member member) {
        this.status = request.getStatus();
        this.title = request.getTitle();
        this.sickness = request.getSickness();
        this.number = request.getNumber();
        this.hospital = request.getHospital();
        this.bloodType = request.getBloodType();
        this.duration = request.getDuration();
        this.relationship = request.getRelationship();
        this.content = request.getContent();
        this.requests_blood_type = request.getRequests_blood_type();
        this.createdTime = request.getCreateDate();
        this.nickname = member.getNickname();
        this.phone_number = member.getPhoneNumber();
    }
}
