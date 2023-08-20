package ISTP.service;

import ISTP.domain.bloodDonation.request.Request;
import ISTP.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    @Transactional
    public Long save(Request request) {
        Request saveRequest = requestRepository.save(request);
        return saveRequest.getId();
    }

    public Request findById(Long requestId) {
        return requestRepository.findById(requestId).
                orElseThrow(() -> new IllegalArgumentException());
    }

    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Transactional
    public void changeStatus(Request request) { // 신청에서 진행중으로 바꾸기
        request.update_status();
    }

    @Transactional
    public void changeStatus2(Request request) { // 진행중에서 완료로 바꾸기
        request.update_finish();
    }

    @Transactional
    public void changeStatus3(Request request) { // 취소누르면 다시 신청으로 바꾸기
        request.update_request();
    }
}
