package ISTP.dtos.alarm;


import ISTP.domain.bloodDonation.request.Request;
import ISTP.domain.bloodDonation.request.RequestStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AlarmSummaryDto {

    private String title;
    private String content;
    private RequestStatus status;
    private LocalDateTime createdTime;
    private boolean isRead;

    public AlarmSummaryDto(AcceptAndIsReadDto acceptAndIsReadDto) {
        title = acceptAndIsReadDto.getRequest().getTitle();
        content = acceptAndIsReadDto.getRequest().getContent();
        status = acceptAndIsReadDto.getRequest().getStatus();
        createdTime = acceptAndIsReadDto.getRequest().getCreateDate();
        isRead = acceptAndIsReadDto.isRead();
    }
}
