package ISTP.dtos.request;
import ISTP.domain.bloodDonation.BloodType;
import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;

import java.time.LocalDateTime;

//내가 요청한 리스트 - 마이페이제이서 보는 리스트임
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
