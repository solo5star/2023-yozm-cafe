package com.project.yozmcafe.service;

import com.project.yozmcafe.controller.dto.LikedCafeResponse;
import com.project.yozmcafe.domain.cafe.Cafe;
import com.project.yozmcafe.domain.cafe.CafeRepository;
import com.project.yozmcafe.domain.cafe.LikedCafe;
import com.project.yozmcafe.domain.member.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LikedCafeService {

    private final CafeRepository cafeRepository;
    private final MemberService memberService;

    public LikedCafeService(final CafeRepository cafeRepository,
                            final MemberService memberService) {
        this.cafeRepository = cafeRepository;
        this.memberService = memberService;
    }

    public List<LikedCafeResponse> findLikedCafesById(final String memberId, final int pageNumber, final int pageSize) {
        final Member member = memberService.findMemberByIdOrElseThrow(memberId);

        List<LikedCafe> likedCafes = getLikedCafes(pageNumber, pageSize, member);

        return likedCafes.stream()
                .map(LikedCafeResponse::from)
                .toList();
    }

    private List<LikedCafe> getLikedCafes(final int pageNumber, final int pageSize, final Member member) {
        if (pageNumber > member.getLikedCafes().size() / pageSize) {
            return Collections.emptyList();
        }
        return member.getLikedCafesSection(pageNumber, pageSize);
    }

    @Transactional
    public void updateLike(final Member member, final long cafeId, final boolean isLiked) {
        final Cafe cafe = cafeRepository.findById(cafeId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 카페가 존재하지 않습니다."));

        member.updateLikedCafesBy(cafe, isLiked);
    }
}
