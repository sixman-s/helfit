package sixman.helfit.domain.like.service;

import org.springframework.stereotype.Service;

import sixman.helfit.domain.like.repository.LikeRepository;


@Service
public class LikeService {
    private final LikeRepository likeRepository;

    public LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }


}
