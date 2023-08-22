package ISTP.dtos.request;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

//내가 요청 수락한 리스 - 마이페이지에서 보는 리스트

@Data
public class MyAcceptDto {

    private BloodType blood_type;
    private String title;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public MyAcceptDto(Request request) {
        this.blood_type = request.getBloodType();
        this.title = request.getTitle();
        this.status = request.getStatus();
        this.createdTime = request.getCreateDate();
    }
}
