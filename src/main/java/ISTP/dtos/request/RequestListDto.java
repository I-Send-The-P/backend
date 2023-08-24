package ISTP.dtos.request;


import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestListDto {
    private BloodType blood_type;
    private String title;
    private String content;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public RequestListDto(Request request) {
        this.blood_type = request.getBloodType();
        this.title = request.getTitle();
        this.content = request.getContent();
        this.status = request.getStatus();
        this.createdTime = request.getCreateDate();
    }
}
