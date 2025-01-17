package com.project.yozmcafe.service.auth;

import com.project.yozmcafe.controller.auth.OAuthProvider;
import com.project.yozmcafe.controller.dto.AuthorizationUrlDto;
import com.project.yozmcafe.controller.dto.TokenResponse;
import com.project.yozmcafe.domain.member.Member;
import com.project.yozmcafe.domain.member.MemberInfo;
import com.project.yozmcafe.domain.member.MemberRepository;
import com.project.yozmcafe.service.UnViewedCafeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UnViewedCafeService unViewedCafeService;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository,
                       final UnViewedCafeService unViewedCafeService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.unViewedCafeService = unViewedCafeService;
    }

    @Transactional
    public TokenResponse createAccessToken(final String code, final OAuthProvider provider) {
        final MemberInfo memberInfo = provider.getUserInfo(code);

        final Member member = memberRepository.findById(memberInfo.openId())
                .orElseGet(() -> memberRepository.save(memberInfo.toMember()));
        unViewedCafeService.refillWhenUnViewedCafesSizeUnderTwenty(member);

        return new TokenResponse(jwtTokenProvider.createAccessFrom(member.getId()));
    }

    public TokenResponse createRefreshToken() {
        return new TokenResponse(jwtTokenProvider.createRefresh());
    }

    public TokenResponse refreshAccessToken(final String access, final String refresh) {
        final String token = jwtTokenProvider.refreshAccessToken(access, refresh);
        return new TokenResponse(token);
    }

    public List<AuthorizationUrlDto> getAuthorizationUrls() {
        return Arrays.stream(OAuthProvider.values())
                .map(provider -> new AuthorizationUrlDto(provider.getProviderName(), provider.getAuthorizationUrl()))
                .collect(Collectors.toList());
    }
}
