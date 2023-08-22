package ISTP.service;

import ISTP.domain.bloodDonation.accept.Accept;
import ISTP.repository.AcceptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AcceptService {

    private final AcceptRepository acceptRepository;

    @Transactional
    public Long save(Accept accept) {
        Accept save = acceptRepository.save(accept);
        return save.getId();
    }

    public Accept findById(Long id) {
        return acceptRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException());
    }

    @Transactional
    public void update_cancel(Accept accept) {
        accept.update_cancel();
    }

    @Transactional
    public void update_request(Accept accept) {
        accept.update_request();
    }

    @Transactional
    public void update_finish(Accept accept) {
        accept.update_finish();
    }
}
