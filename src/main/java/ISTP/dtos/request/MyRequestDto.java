package ISTP.Dtos.request;

import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
public class MyRequestDto {
    private BloodType blood_type;
    private String title;
    private RequestStatus status;
    private LocalDateTime createdTime;

    public MyRequestDto(Request request) {
        this.blood_type = request.getBloodType();
        this.title = request.getTitle();
        this.status = request.getStatus();
        this.createdTime = request.getCreateDate();
    }
}
