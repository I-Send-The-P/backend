package ISTP.dtos.alarm;

import ISTP.domain.bloodDonation.request.Request;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcceptAndIsReadDto {

    private Request request;
    private boolean isRead;

    public AcceptAndIsReadDto(Request request, boolean isRead) {
        this.request = request;
        this.isRead = isRead;
    }
}
