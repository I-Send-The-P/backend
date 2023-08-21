package ISTP.dtos;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestDto {
    private BloodType blood_type;
    private String title;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public RequestDto(Request request) {
        this.blood_type = request.getBloodType();
        this.title = request.getTitle();
        this.status = request.getStatus();
        this.createdTime = request.getCreateDate();
    }
}
